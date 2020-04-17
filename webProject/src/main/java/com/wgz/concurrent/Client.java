package com.wgz.concurrent;

import java.util.Map;

/**
 * @Description:Java Master-Worker模式
 * @author: wenguozhang 
 * @date:   2019年10月11日 下午5:23:56  
 */
public class Client {

    public static void main(String[] args) {
        int count = Runtime.getRuntime().availableProcessors();// 当前设备所支持的线程数。
        Master master = new Master(count);
        for (int i = 0; i < (count * 3); i++) {
            Task task = new Task(i + 1, "name" + (i + 1));
            master.add(task);
        }

        System.out.println(count);
        long start = System.currentTimeMillis();
        master.execute();
        
        while (true) {
        // 等待所有任务执行完成。
            if (master.isComplete()) {
            	long end = System.currentTimeMillis();
                System.out.println("execute time:"+(end-start));
                Map<String, String> map = master.getResultMap();
                for (Map.Entry<String, String> en :
                        map.entrySet()) {
                    System.out.println("result : " + en.getKey() + " , " + en.getValue());
                }
                break;
            }
        }
    }
}