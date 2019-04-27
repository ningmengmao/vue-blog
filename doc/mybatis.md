# Mybatis 学习笔记

#### mapper.xml

* 获取主键自增值 

  > useGeneratedKeys = "true"   keyProperty="id"

* 参数封装

  > `@Param("id")` int num   -->将参数num绑定到sql的` #{id}`

  >```java
  >public Employee getEmp(@Param("id") int id, @Param("lastName") String lastName);
  >
  >public void update(Employee emp);
  >
  >public List<Employee> getEmps(List<Integer> ids);
  >```

  >`#{emp.lastname}`取出lastname
  >
  >`List`只能**` #{list[0]}`** 取出list的值

 * `sql`语句可以传入一个`Map`对象, #{key} 取得`Map` 中`key`对应的`value`

----

* ##### 规定参数的一些规则

  > javaType, jdbcType, mode,  numericScale
  >
  > resultMap, typeHandler, jdbcTypeName

-----

* #####  resultType

  > 包装的类型
  >
  > 返回`Map` :  resultType="map" , key 是列名, value字段值
  >
  > 返回`Map<Integer, Employee>` ,  resultType= Employee, 使用`@MapKey`指定使用那个字段做map的key

  > ```java
  > @MapKey("id")
  > public Map<Integer, Employee> get();
  > ```

* ##### resultMap

  > ```xml
  > <resultMap id="baseArticle" type="com.ningmeng.vueblog.entity.Article">
  >   <id column="id" property="id"/>
  >   <result column="title" property="title"/>
  >   <result column="abstract" property="articleAbstract"/>
  >   <result column="content" property="content"/>
  >   <result column="views" property="views"/>
  >   <result column="create_time" property="createTime"/>
  >   <result column="update_time" property="updateTime"/>
  >   <result column="is_top" property="isTop" javaType="boolean"/>
  > </resultMap>
  > ```
  >
  > * 使用`<association>`或者`<collection>`进行结果嵌套
  >
  >  ```xml
  >   <association property="tag" javaType="" select="" column="">
  >   <collection property="tagSet" ofType="" select="" column="">
  >   ```
  > 
  >  
  > 
  >* `select` 为调用mapper方法的id, column为传入的字段
  > 
  >> 扩展: 传入多列的值 column = "{key1=column1, key2=column2}" 
  >>
  >> 入column = "{tagId = id, tagName = name}"
  >>
  
  > * 根据结果值觉得使用那个resultMap
  >
  >   ```xml
  >      <discriminator javaType="int" column="draft">
  >        <case value="1" resultType="DraftPost"/>
  >      </discriminator>
  >   ```

* ##### 动态sql
  
  > 动态 SQL 通常要做的事情是根据条件包含 where 子句的一部分
  > > `if` , `choose`, `trim`, `foreach`
  >
  > * `if`  在 where 1=1 后添加是if的内容
  >
  >   > ```xml
  >   > <if test="id != null">
  >   > 	and id = #{id}
  >   > </if>
  >   > ```
  >   >
  > ---
  >
  >
  > * `choose`  有时我们不想应用到所有的条件语句，而只想从中择其一项。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。
  >
  >   > ```xml
  >   > <choose>
  >   >     <when test="title != null">
  >   >       AND title like #{title}
  >   >     </when>
  >   >     <when test="author != null and author.name != null">
  >   >       AND author_name like #{author.name}
  >   >     </when>
  >   >     <otherwise>
  >   >       AND featured = 1
  >   >     </otherwise>
  >   >   </choose>
  >   > ```
  >
  > ---
  >
  > * `set` 用于动态包含需要更新的列
  >
  >   > ```xml
  >    > <update id="updateAuthorIfNecessary">
  >    >   update Author
  >    >     <set>
  >    >       <if test="username != null">username=#{username},</if>
  >    >       <if test="password != null">password=#{password},</if>
  >    >       <if test="email != null">email=#{email},</if>
  >    >       <if test="bio != null">bio=#{bio}</if>
  >    >     </set>
  >    >   where id=#{id}
  >    > </update>
  >    > ```
  >    >
  >
  > ---
  >
  > * `foreach`  动态 SQL 的另外一个常用的操作需求是对一个集合进行遍历，通常是在构建 IN 条件语句的时候
  >
  >   > ```xml
  >   > <select id="selectPostIn" resultType="domain.blog.Post">
  >   >   SELECT *
  >   >   FROM POST P
  >   >   WHERE ID in
  >   >   <foreach item="item" index="index" collection="list"
  >   >       open="(" separator="," close=")">
  >   >         #{item}
  >   >   </foreach>
  >   > </select>
  >   > ```
  >   >
  >   
-------

#### 对象关系设计

* 泛化关系

  >Animal 和 Cat Dog 的关系, 

* 实现关系

  >接口和实现类的关系

* 依赖关系

  > A类依赖于B类, 失去了B类, A类失效
  >
  > > A类内有B类的字段
  > >
  > > A类内有方法形参为B类或返回值为B类

* 关联关系

  > * 按多重性分
  >
  >   * 一对一: 一个A对象属于一个B对象, 且一个B对象属于一个A对象
  >
  >   * 一对多: 一个A对象包含多个B对象
  >
  >   * 多对一: 多个A对象属于一个B对象, 且一个A对象只能属于一个B对象
  >
  >   * 多对多: 一个A对象属于多个B对象, 一个B对象属于多个A对象
  > * 按导向性分:
  >   * 单向: 只能通过A导向到B, 不能从B导向到A(B没有A类型的字段)
  >   * 双向: A对象和B对象可以互相导向(都有对方类型的字段)

* 聚合关系

  > has-a, 弱拥有,两者可以**独立存在**

* 组合关系

  > 强聚合关系, 两者**不能分开**, 生命周期必须相同**级联**

----

#### Mybatis 关系映射

>* ##### 方式一: **额外SQL**(1+n问题)
>
>  > 在orm框架中，比如hibernate和mybatis都可以设置关联对象，比如user对象关联dept .
>  > 假如查询出n个user，那么需要做n次查询dept，**查询user是一次select，查询user关联的 
>  > dept，是n次**，所以是n+1问题，其实叫**1+n更为合理一些** (出现重复查询的问题)。
>
>* ##### 方式二: **内联映射**(多表查询)
>
>--------
>


> * ##### 多对一
>
>   >  维护方
>
> * 多对多
>
>   >1. 维护对象间关系
>   >
>   >2. 维护中间表数据
>   >
>   >3.  关系维护在service层处理 **?**
>   >
>   >



