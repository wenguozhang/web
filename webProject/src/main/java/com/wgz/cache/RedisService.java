package com.wgz.cache;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisStringCommands.SetOption;

/**
 * <p>
 * Title: RedisService
 * </p>
 * <p>
 * Description: redis基础操作类
 * </p>
 * <p>
 * @Qualifier的参数名称必须为我们之前定义@Service注解的名称之一！
 * </p>
 */
public interface RedisService {
	
	/**
	 * 根据项目类型实现tps控制
	 * 
	 * @param key		rediskey
	 * @param time		监控时间
	 * @param tps		极限数
	 * @param appType	项目类型
	 * @return
	 */
	public Boolean tpsHandlerByAppType(String key, Integer time, Integer tps, String appType);
	
	
	/* ----------- basics KEY methods --------- */
	
	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: DEL key [key ...]
	 *  https://redis.io/commands/del
	 * </p>
	 * @tags @param keys
	 * @tags @return 
	 */
	public Long delete(Collection<String> keys);

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: DEL key [key ...]
	 *  https://redis.io/commands/del
	 * </p>
	 * @tags @param key
	 * @tags @return 
	 */
	public Boolean delete(String key);
	
	/* ----------- basics STRING methods --------- */

	/**
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description: SET key value [expiration EX seconds|PX milliseconds] [NX|XX]
	 * 	https://redis.io/commands/set
	 * </p>
	 * @tags @param key
	 * @tags @param obj 
	 */
	public <T> void set(String key, T obj);
	
	/**
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description: SET key value [expiration EX seconds|PX milliseconds] [NX|XX]
	 * 	https://redis.io/commands/set
	 * 
	 * redis锁 纯原子化操作 由于SET命令加上选项已经可以完全取代SETNX, SETEX,
	 * PSETEX的功能，所以在将来的版本中，redis可能会不推荐使用并且最终抛弃这几个命令。
	 * </p>
	 * <p>
	 * 
	 * redis 127.0.0.1:6379> SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX] 
	 * Shell EX seconds − 设置指定的到期时间(以秒为单位)。 PX milliseconds -
	 * 设置指定的到期时间(以毫秒为单位)。 NX - 仅在键不存在时设置键。 XX - 只有在键已存在时才设置。 redis
	 * 127.0.0.1:6379> SET mykey "redis" EX 60 NX OK Shell
	 * 以上示例将在键“mykey”不存在时，设置键的值，到期时间为60秒。
	 * 
	 * </p>
	 * 
	 * @tags @param key
	 * @tags @param obj
	 * @tags @param ttl
	 * @tags @param unit 	TimeUnit.SECONDS
	 * @tags @param option	SetOption.ifAbsent() {@code NX} SetOption.fPresent() {@code XX}
	 * @tags @return
	 */
	public <T> Boolean set(String key, T obj, Long ttl, TimeUnit unit, SetOption option);

	/**
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description: GET key
	 * 	https://redis.io/commands/get
	 * </p>
	 * @tags @param key
	 * @tags @param clazz
	 * @tags @return 
	 */
	public <T> T get(String key, Class<T> clazz);

	/**
	 * <p>
	 * Title: incr
	 * </p>
	 * <p>
	 * Description: 递增
	 * </p>
	 * @tags @param key
	 * @tags @return 
	 */
	public Long incr(String key);

	/**
	 * <p>
	 * Title: decr
	 * </p>
	 * <p>
	 * Description: 递减
	 * </p>
	 * @tags @param key
	 * @tags @return 
	 */
	public Long decr(String key);
	
	/**
	 * 先清除key的值在进行赋值
	 */
	public <T> void cleanAndSet(String key, T obj);

	/**
	 * 删除全部的key
	 */
	void removeAllKeys();
	
}
