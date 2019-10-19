# Shiro权限控制框架

> * #### 用户
>
>   > 
>
> * #### 角色
>
> * #### 行为
>
>   > 操作者 : 行为 : 被操作者
>
> -----
>
> > 流程
> >
> > > 1. 获取当前的Subject, 调用SecurityUtils.getSubject();
> > > 2. 使用Subject的isAuthenticated()判断是否登录
> > > 3. 没有认证,则把用户名和密码封装为UsernamePasswordToken
> >
> > 匿名可访问
> >
> > 认证后可访问
> >
> > AuthenticationInfo代表了用户的角色信息集合
> >
> > AuthorizationInfo代表了角色的权限信息集合
> >
> > -----
> >
> > * ##### 自定义Realm
> >
> > ``` java
> > public class MyRealm extends AuthenticatingRealm {
> > 
> >  @Override
> >  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
> >      // 1.将AuthenticationToken 转化为UsernamePasswordToken
> >      // 2.从UsernamePasswordToken获取username
> >      // 3.调用数据库方法查询对于username记录
> >      // 4.用户不存在则抛出UnknownAccountException
> >      // 5.或抛出其他异常
> >      // 6.构建AuthenticationInfo
> >      // credentials 密码
> >      // principal 认证的实体信息
> >      new SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
> >      return null;
> >  }
> > }
> > ```
> >
> > ```java
> > // 封装前台传来的用户信息
> > new UsernamePasswordToken(username, password)
> > ```
> >
> > 