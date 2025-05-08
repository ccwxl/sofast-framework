## 数据库隔离模式

- 行级别隔离(mybatis-plus)
- schema 级别隔离(动态切换schema)
    - pg是schema隔离
    - mysql是database隔离

## redis

- key 隔离

## 租户识别

- 租户标识解析: 从Cookie, Header, Domain,URL Path,Env中解析租户标识

## 租户业务执行器

- 设置和释放当前线程的租户上下文,执行定制业务功能. 满足定制化业务处理场景

## 个性化业务扩展点

- 扩展cola组件，使用扩展点的方式扩展租户业务. 满足定制化业务处理场景

