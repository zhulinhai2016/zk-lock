# zk-lock
zookeeper 分布式锁，分布式配置中心
1. zk-lock-demo 分布式锁实现案例
2. zk-lock-test 分布式锁测试案例，模拟集群服务器并发测试
现在demo里面实现了公平锁
非公平锁比较简单，使用零时节点就行，不用考虑锁顺序问题
