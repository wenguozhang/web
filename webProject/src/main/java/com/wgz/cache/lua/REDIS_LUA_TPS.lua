local current = redis.call('incr',KEYS[1]) 
if tonumber(current) == 1 
then redis.call('expire',KEYS[1],tonumber(ARGV[1])) 
end 
if tonumber(current) > tonumber(ARGV[2]) 
then return -1 
else return 1 
end