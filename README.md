# springboot-spring-security-jwt
基于springboot，使用spring-security与jwt实现项目的单点登录与权限控制，spring-security，实际上是基于servlet，实现了多个filter组成了过滤连，用户也可以自行在过滤连中加入自己的自定义filter。

## 项目包括：
  * 使用**io.jsonwebtoken**进行token管理。
    *  如果不使用token，则需要开启csrf，因为spring security会把用户信息保存在session中，然后通过客户端传cookie来锁定sessionId,如http basic auth。
  * 基于**spring-security**的角色权限控制，实现根据用户角色来确定对资源的访问权限。
  * 匿名用户访问拦截、用户权限不足等统一异常处理。
  * controller层使用统一返回标准的Json格式数据。
  * mybatis sql插入、编辑拦截处理（用来自动生成，更新创建时间和编辑时间）。
  * 项目依赖数据库sql文件。

## 主要类介绍：
  * **WebSecurityConfig**：spring-security配置类
    * 配置用户验证类（实现UserDetailsService接）。
    * 设置密码加密方式（使用BCrypt进行密码的hash）。
    * 设置异常（权限不足，匿名用户访问等）处理类。
    * 设置路径允许匿名访问路径等。
    * 添加拦截类（此项目设置拦截类用来验证token）。
  * **RestAuthenticationAccessDeniedHandler**:用来解决认证过用户但无权限访问的处理类
  * **JwtAuthenticationTokenFilter**：拦截请求并获取请求头中的token并解析，认证通过后存入**SecurityContextHolder**，保存当前用户信息。
  * **JwtAuthenticationEntryPoint**： 用来解决匿名用户访问无权限资源时的异常。
  * **CustomUserDetailsServiceImpl**：实现UserDetailsService接口，用于UsernamePasswordAuthenticationFilter调用，验证用户信息。
