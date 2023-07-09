# CVE-2022-25845

## 简介

> The package com.alibaba:fastjson before 1.2.83 are vulnerable to Deserialization of Untrusted Data by bypassing the default autoType shutdown restrictions, which is possible under certain conditions. Exploiting this vulnerability allows attacking remote servers. Workaround: If upgrading is not possible, you can enable [safeMode](https://github.com/alibaba/fastjson/wiki/fastjson_safemode).

## 原理

`fastjson` 允许反序列化任何继承自 `Throwable` 的类。当调用 `JSON.parse` 时 最终会进入 `com.alibaba.fastjson.parser.ParserConfig#getDeserializer(java.lang.Class<?>, java.lang.reflect.Type)`

```bash
getDeserializer:901, ParserConfig (com.alibaba.fastjson.parser)
getDeserializer:613, ParserConfig (com.alibaba.fastjson.parser)
parseObject:396, DefaultJSONParser (com.alibaba.fastjson.parser)
parse:1430, DefaultJSONParser (com.alibaba.fastjson.parser)
parse:1390, DefaultJSONParser (com.alibaba.fastjson.parser)
parse:181, JSON (com.alibaba.fastjson)
parse:191, JSON (com.alibaba.fastjson)
parse:147, JSON (com.alibaba.fastjson)
main:9, Main (person.xu.vulEnv)
```

检查了是否继承自 `Throwable`

```java
} else if (Throwable.class.isAssignableFrom(clazz)) {
    deserializer = new ThrowableDeserializer(this, clazz);
```


### 触发点

- `JSON.parse`
- `JSON.parseObject`

## 攻击

通过 `Groovy/aspectj`[^1] 链可进行攻击

## 修复

开启 `safeMode`


## 相关链接

- [CVE-2022-25845 - Fastjson RCE vulnerability analysis](https://jfrog.com/blog/cve-2022-25845-analyzing-the-fastjson-auto-type-bypass-rce-vulnerability/)

---

[^1]: [Fastjson CVE-2022-25845 漏洞复现 - TT0TT - 博客园](https://www.cnblogs.com/TT0TT/p/16654022.html)