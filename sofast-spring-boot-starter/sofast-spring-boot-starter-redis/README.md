## redis 功能封装

- 分布式锁
- 限流
- 发布订阅
- 缓存

## 增强处理

- redisTemplate 增强
- stringRedisTemplate 增强
- redisson 增强
- 确保redisson 的codec 与 redisTemplate 的codec一致,提升互操作性
- spring cache 支持ttl?
- spring cache 支持自定义扩展如多租户场景

## 自定义处理

- 提供自定义的接口对redisTemplate进行自定义配置
- 提供自定义接口对Redisson的Config进行自定义配置
