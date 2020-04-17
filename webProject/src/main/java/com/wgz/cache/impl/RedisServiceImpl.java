package com.wgz.cache.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.wgz.cache.RedisScriptLua;
import com.wgz.cache.RedisService;
import com.wgz.utils.FunUtils;

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
@Service("redisService")
public class RedisServiceImpl implements RedisService{

	private Gson gson = new Gson();
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<?, ?> redisTemplate;

	@Autowired
	@Qualifier("stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;
	
	
	/* ----------- lua methods --------- */
	
	/**
	 * <p>
	 * Title: executeTpsHandler
	 * </p>
	 * <p>
	 * Description: 执行tps控制
	 * </p>
	 * <p>
 	 * 	local current
	 *	current = redis.call("incr",KEYS[1])
	 *	if tonumber(current) == 1 then
	 *	    redis.call("expire",KEYS[1],ARGV[1])
	 *	end
	 *	
	 *	if tonumber(current) > ARGV[2] then
	 *		return 1
	 *	else
	 *		return -1
	 *	end
	 *
	 * redis 命令
	 * eval "local current = redis.call("incr",KEYS[1]) if tonumber(current) == 1 then redis.call("expire",KEYS[1],ARGV[2]) end if tonumber(current) > tonumber(ARGV[1]) then return -1 else return 1 end" 1 test_127.0.0.1 1 1800
	 *
	 * </p>
	 * 使用场景多节点
	 * 
	 * @tags @param token	锁
	 * @tags @param time	过期时间
	 * @tags @param tps		TPS
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月27日上午10:13:19
	 */
	private Boolean executeTpsHandler(String token,Integer time,Integer tps){
		Assert.notNull(token, "Key must not be null!");
		Assert.notNull(time, "Time must not be null!");
		Assert.notNull(tps, "Tps must not be null!");
		@SuppressWarnings("unchecked")
		RedisScript<Boolean> rs = RedisScript.of(RedisScriptLua.REDIS_LUA_TPS, Boolean.class);
		return stringRedisTemplate.execute(rs, Collections.singletonList(token), String.valueOf(time) , String.valueOf(tps) );
	}
	
	/**
	 * 使用场景单节点
	 * 
	 * @param key	redis_key
	 * @param time	监测时间
	 * @param tps	访问次数
	 * @return
	 */
	private synchronized Boolean singleTpsHandler(String key, Integer time, Integer tps) {

		Boolean hasKey = stringRedisTemplate.hasKey(key);
		if (hasKey) {
			int count = Integer.parseInt(stringRedisTemplate.boundValueOps(key).get());
			if (count < tps ) {
				this.incr(key);
				this.removePermanentKey(key);
				return true;
			}else {
				return this.removePermanentKey(key);
			}
		}else {
			/**
			 * 不存在,设置key,和过期时间
			 */
			stringRedisTemplate.opsForValue().set(key,"1",time,TimeUnit.SECONDS);
			return true;
		}
	}
	/**
	 * 根据项目类型实现tps
	 * 
	 * @param key
	 * @param time
	 * @param tps
	 * @param appType
	 * @return
	 */
	@Override
	public Boolean tpsHandlerByAppType(String key, Integer time, Integer tps, String appType) {
		if (FunUtils.isNull(key) || FunUtils.isNull(key) || !(time > 0 && tps > 0)) {
			throw new IllegalArgumentException("tps...error...params(rediskey,time,tps,appType)");
		}
		Boolean falg;
		if ("SINGLE".equals(appType)) {
			falg = this.singleTpsHandler(key, time, tps);
		}else if("MANY".equals(appType)) {
			falg = this.executeTpsHandler(key, time, tps);
		}else {
			throw new IllegalArgumentException("tps...error...apptype...");
		}
		return falg;
	}
	
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
	@Override
	public Long delete(Collection<String> keys){
		Assert.notEmpty(keys, "keys must not be null!");
		return stringRedisTemplate.delete(keys);
	}
	
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
	@Override
	public Boolean delete(String key){
		Assert.notNull(key, "key must not be null!");
		return stringRedisTemplate.delete(key);
	}
	
	
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
	@Override
	public  <T> void set(String key, T obj) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(obj, "Value must not be null!");
		stringRedisTemplate.opsForValue().set(key, toJson(obj));
	}


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
	@Override
	public <T> Boolean set(String key, T obj, Long ttl, TimeUnit unit, SetOption option) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(obj, "Value must not be null!");
		Assert.notNull(ttl,"TTL must not be null!");
		Assert.notNull(unit, "TimeUnit must not be null!");
		Assert.notNull(option, "Option must not be null!");
		
		return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
				byte[] _key = serializer.serialize(key);
				byte[] _value = serializer.serialize(toJson(obj));
				return connection.set(_key, _value, Expiration.from(ttl, unit), option);
			}
		});
	}
	
	
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
	@Override
	public  <T> T get(String key, Class<T> clazz) {
		
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(clazz, "Clazz must not be null!");
		
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null){
        	return null;
        }
        return parseJson(value, clazz);
    }
	
	
	
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
	@Override
	public Long incr(String key){
		return stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
				byte[] _key = serializer.serialize(key);
				return connection.incr(_key);
			}
		});
	}
	
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
	@Override
	public Long decr(String key){
		return stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
				byte[] _key = serializer.serialize(key);
				return connection.decr(_key);
			}
		});
	}
	
	/**
	 * 先清除key在存放值
	 */
	@Override
	public <T> void cleanAndSet(String key, T obj) {
		this.delete(key);
		this.set(key, obj);
	}
	
	/**
	 * 删除前置全部的key
	 */
	@Override
	public void removeAllKeys() {
		String prefix = "FRONT_REDIS_*";
		Set<String> keys = stringRedisTemplate.keys(prefix);
		this.delete(keys);
	}
	
	
	
	
    /* ----------- tool methods --------- */
	
	/**
	 * redis_key剩余时间为-1
	 * 删除redis_key
	 * 
	 * @param key
	 * @return
	 */
	private Boolean removePermanentKey(String key) {
		Long expire = stringRedisTemplate.boundValueOps(key).getExpire();
		if(expire == -1) {
			Boolean delete = null;
			for (int i = 0; i < 10; i++) {
				delete = stringRedisTemplate.delete(key);
				if(delete) {
					return delete;
				}
			}
			return true;
		}else {
			return false;
		}
		
	}
	
	
    private String toJson(Object obj) {
    	return gson.toJson(obj);
    }
 
    private <T> T parseJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}