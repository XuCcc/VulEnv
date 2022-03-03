# Apache Shiro CVE-2020-13933

## Build

1. `mvn install`
2. `run person.xu.vulEnv.ShiroBypassApplication`

## Environment

1. `Shiro version < 1.6.0`
2. Shiro 在设置权限时参考 `person.xu.vulEnv.AuthConfig.shiroFilterFactoryBean`

## Analyse

由于 `Shiro` 与 `Springboot` 在处理URL的过程中存在差异，导致在实际访问 `info/%3bAdmin` 时

- Shiro 认为访问的页面为 `info/` 此页面不需要鉴权，通过权限校验
- SpringBoot 认为访问的页面为 `info/;Admin` 返回为此页面下的内容导致绕过

两者处理URl的差异在

- Shiro: urldecode **--->** remove `;`
- Springboot: remove `;` **--->** urldecode

## Reference

- [shiro < 1.6.0的认证绕过漏洞分析(CVE-2020-13933) - 安全客，安全资讯平台](https://www.anquanke.com/post/id/214964#h2-1)