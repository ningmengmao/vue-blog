---

---

# Mybatis Plus 学习笔记

*  application.yml中的配置

```yaml
mybatis-plus:
  global-config:
    db-config:
      id-type: auto
      table-prefix: b_
  mapper-locations: classpath*:mapper/*.xml

```
>id-type 设置**主键类型**
>table-prefix 设置**表前缀**, 表名默认为**实体类名**
---
#### 条件构造器

  在使用mapper方法前, 先了解条件构造器

>条件构造器用来构造查询条件, 用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件
>注意: entity 生成的 where 条件与 使用各个 api 生成的 where 条件没有任何关联行为
>使用的是数据库字段，不是Java属性!

* #### 基类AbstractWrapper
* 实现类 QueryWrapper,  UpdateWrapper

[查询方法](https://mp.baomidou.com/guide/wrapper.html#abstractwrapper) 
```java
        IPage<Article> articleIPage = articleMapper.selectPage(new Page<Article>(),
                new QueryWrapper<Article>()
                        .ge("create_time", 1555519854473L)
                        .orderByDesc("update_time")
        );
```

---
#### mapper方法


* **插入方法**
```java
    int insert(T entity);
```
* **insert**方法插入时, 会根据实体做**非空判断**, 如果为null, sql语句就不包含这个字段.

---
* **更新方法**
```java
		 int updateById(@Param("et") T entity);

    int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper);
```
* updateById 类似于insert, 同样**不会对空字段进行更新**

---
* **查询方法**
```java
    T selectById(Serializable id);

    T selectOne(@Param("ew") Wrapper<T> queryWrapper);

    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    List<T> selectByMap(@Param("cm") Map<String, Object> columnMap);

    Integer selectCount(@Param("ew") Wrapper<T> queryWrapper);

    List<T> selectList(@Param("ew") Wrapper<T> queryWrapper);

    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper);

    List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper);

    IPage<T> selectPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper);

    IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper);
}
```

* **selectByMap** 方法传入的map是列名和列的值, 
```java
	map.put("last_name", "Tom");
	map.put("age", 10);
```
>查询last_name为Tom, age为10的人

* **分页查询**
**selectPage** ,  使用`Page`类完成分页
**Page类构造方法**, 传入当前页码和页面大小
```java
		 public Page(long current, long size)
```

---
* **删除方法**
```java
    int deleteById(Serializable id);

    int deleteByMap(@Param("cm") Map<String, Object> columnMap);

    int delete(@Param("ew") Wrapper<T> wrapper);

    int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList);
```
---
#### 原理

* **mappedStatement**

----
#### 代码生成器

>可以生成 ---> 实体类, `Mapper`接口, `Mapper`映射文件, `Service`, `Controller`
>
1. 全局配置

   >```java
   >
   >```

2. 数据源配置

3. 策略配置

4. 包名策略配置

5. 整合配置

#### 分页插件

> 开启物理分页, 替代内存分页

```java
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        //作用！阻止恶意的全表更新删除
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }
```
#### 乐观锁

> 通过一个`version`(版本号)来控制数据的状态
> 如果一个事务写回数据时, 版本号不对就回滚操作
>
> 需要自己维护, 实现乐观锁
#### 读写锁

> **`读锁`**具有**`共享性`**,  **不阻止**其他**读操作**, **阻止**其他**写操作**
>
> **`写锁`**具有**`排他性`**, **阻止**其他**`读、写操作`**
---

#### 自定义全局操作

> 注入器配置
>
> 全局配置 `sqlInjector` 用于注入 `ISqlInjector` 接口的子类，实现自定义方法注入。
>
> 例如逻辑删除注入器 [LogicSqlInjector](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/injector/LogicSqlInjector.java)

