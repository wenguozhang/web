package com.wgz.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ClassName: SnowflakeIdWorker
 * Date: 2019/1/16 11:56
 * Content:
 *      Twitter_SnowflakeSnowFlake的结构如下:
 *      0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000</br>
 *
 *      1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0</br>
 *
 *      41位时间截(毫秒级):
 *          注意:41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截得到的值）
 *              这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的
 *              (如下下面程序IdWorker类的startTime属性)41位的时间截，
 *              可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69</br>
 *
 *      10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId</br>
 *
 *      12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号</br>
 *      加起来刚好64位，为一个Long型。<br>
 *
 *      算法优点是:整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，
 *      并且效率较高，经测试.每秒能够产生26万ID左右。
 *
 *      改进方法:
 *      使用单例模式,workId使用服务器hostName生成，dataCenterId使用IP生成，
 *      这样可以最大限度防止10位机器码重复，但是由于两个ID都不能超过32只能取余数，还是难免产生重复.
 *      但是实际使用中，hostName和IP的配置一般连续或相近,只要不是刚好相隔32位，就不会有问题.
 *      况且，hostName和IP同时相隔32的情况更加是几乎不可能的事，平时做的分布式部署，一般也不会超过10台容器。
 *
 *      需要依赖jar包org.apache.commons的commons-lang3
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
public final class SnowflakeIdWorker {

    /*--------------------filed--------------------*/

    /** 开始时间截 (2015-01-01) */
    private final long START_TIMESTAMP = 1489111610226L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long dataCenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long MAX_WORKER_ID = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long MAX_DATA_CENTER_ID = -1L ^ (-1L << dataCenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long WORKER_ID_SHIFT = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long DATA_CENTER_ID_SHIFT = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long SEQUENCE_MASK = -1L ^ (-1L << sequenceBits);

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    /** 静态对象 */
    private static SnowflakeIdWorker idWorker;


    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long dataCenterId;

    /**
     * 静态代码块初始化对象
     */
    static {
        idWorker = new SnowflakeIdWorker();
        idWorker.init(getWorkId(),getDataCenterId());
    }

    /*--------------------constructors--------------------*/

    /**
     * 私有构造
     */
    private SnowflakeIdWorker(){}

    /*--------------------static_method--------------------*/

    /**
     * 静态工具类
     *
     * @return
     */
    public static Long generateId(){
        return idWorker.nextId();
    }

    /*--------------------business_method--------------------*/

    private synchronized long nextId() {
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = ++sequence & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIMESTAMP) << timestampLeftShift)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }


    /*--------------------tools_method--------------------*/

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return              当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 初始化对象参数的方法
     */
    private void init(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 当前毫秒类型时间戳
     * @return  毫秒时间戳
     */
    private long timeGen() {
        return DateUtils.currentTimeMillis();
    }

    /**
     * workId使用服务器hostName生成
     * @return  机器id
     */
    private static Long getWorkId(){
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for(int b : ints){
                sums = sums + b;
            }
            return (long)(sums % 32);
        } catch (UnknownHostException e) {
            return RandomUtils.nextLong(0,31);
        }
    }

    /**
     * dataCenterId 使用IP生成
     * @return      数据中心id
     */
    private static Long getDataCenterId(){
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            int[] ints = StringUtils.toCodePoints(hostName);
            int sums = 0;
            for (int i: ints) {
                sums = sums + i;
            }
            return (long)(sums % 32);
        } catch (UnknownHostException e) {
            return RandomUtils.nextLong(0,31);
        }
    }
}
