## 项目介绍
> 版本参考：https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
> 项目地址：
java游戏服务器开发项目，暂时先提供游戏服务器基础功能

## 模块介绍
认证服务器，采用用户名密码模式
简介：提供认证服务，用户输入用户名密码后，验证客户端用户id与密码，调用认证服务获取用户信息验证。完成后返回token等信息。token通过jwt非对称加密生成、
注意：实际生产中给客户端合适的授权而不是所有权限
获取token:http://localhost:9999/oauth/token?username=tyc&password=123456&grant_type=password&client_id=client&client_secret=123456&scope=all
校验token:http://localhost:9999/oauth/check_token?token={access_token}
注意校验token时 认证类型为（Basic Auth） 传入client_id(123)与client_secret(123456)
测试刷新token:http://localhost:9999/oauth/token?grant_type=refresh_token&client_id=client&client_secret=123456&refresh_token={refresh_token}
实现JWT非对称加密（公钥私钥）
命令格式 
keytool 
-genkeypair  生成密钥对
-alias jwt(别名) 
-keypass 123456(别名密码) 
-keyalg RSA(生证书的算法名称，RSA是一种非对称加密算法) 
-keysize 1024(密钥长度,证书大小) 
-validity 365(证书有效期，天单位) 
-keystore D:/jwt/jwt.jks(指定生成证书的位置和证书名称) 
-storepass 123456(获取keystore信息的密码)
-storetype (指定密钥仓库类型)
keytool  -genkeypair -alias jwt -keyalg RSA -keysize 2048 -keystore D:/jwt/jwt.jks
将生成的jwt.jks文件cope到授权服务器的resource目录下
查看公钥
keytool -list -rfc -keystore jwt.jks -storepass 你的密钥
或者 http://127.0.0.1:9999/oauth/token_key（携带clientid与密钥）


gateway模块
简介：对外只暴露网关服务、网关收到登录请求后（根据配置不需要拦截），转发到认证服务，认证服务认证通过后返回token。当客户端访问资源服务时（根据配置应该拦截）
拦截获取token，校验token信息。校验通过后将从token中解析得到的用户id等信息放入请求头、最终将请求转发到对应的资源服务器
//不需要认证的url,比如/oauth/**

//2. 获取token
// 从请求头中解析 Authorization  value格式:bearer {access_token}
// 或者从请求参数中解析 access_token（需要从认证服务器获取公钥）

//3. 校验token
// 拿到token后，通过公钥（需要从授权服务获取公钥）校验
// 校验失败或超时抛出异常

//4. 校验通过后，从token中获取的用户登录信息存储到请求头中

## 用户登录流程
通过本渠道用户注册，注册后登录获取Auth服务返回的token、登录时传入token,ucenter服务通过渠道判断如何进行校验获取sdkUid
根据Account表选择插入或登录，完成后显示服务器列表，用户选择服务器列表进入，游戏服务器判断用户是否含有角色没有则要求用户输入角色名
完成后进入游戏，进入地图前游戏服务器向用户服务获取用户信息向支付服务获取用户支付信息（便于自动补单）

## game-service

## 本地运行