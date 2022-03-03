# Apache Shiro CVE-2016-4437

## Build

1. `mvn install`
2. `run person.xu.vulEnv.ShiroBypassApplication`

## Environment

可以通过修改 `pom.xml` 中 `shiro` 版本来进行不同场景下 `Shiro 弱密钥` 导致的反序列化漏洞问题

密钥 `fCq+/xW488hMTCD+cmJ3aQ==`

1. `1.2.1` Shiro-550
2. `1.4.0` AES-CBC 加密
3. `1.7.0` AES-GCM 加密

## Analyse


## Reference

- [j1anFen/shiro_attack: shiro反序列化漏洞综合利用,包含（回显执行命令/注入内存马）](https://github.com/j1anFen/shiro_attack)