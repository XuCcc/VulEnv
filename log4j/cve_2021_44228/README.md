# CVE-2021-44228

## Environment

```bash
➜  cve_2021_44228 git:(master) ✗ java -version
openjdk version "1.8.0_362"
OpenJDK Runtime Environment (build 1.8.0_362-8u372-ga~us1-0ubuntu1~22.04-b09)
OpenJDK 64-Bit Server VM (build 25.362-b09, mixed mode)
```

## Run

**Main**

启动时添加 jvm 参数 `-Dcom.sun.jndi.ldap.object.trustURLCodebase=true`

**JNDIExploit1.4**

> https://github.com/WhiteHSBG/JNDIExploit

```bash
➜  JNDIExploit1.4 java -jar JNDIExploit-1.4-SNAPSHOT.jar -i  127.0.0.1
[+] LDAP Server Start Listening on 1389...
[+] HTTP Server Start Listening on 3456...
```

## Breakpoints

- `org.apache.logging.log4j.core.pattern.MessagePatternConverter#format` 处理 `$`

- `org.apache.logging.log4j.core.lookup.StrSubstitutor#substitute` 递归处理

- `org.apache.logging.log4j.core.lookup.StrSubstitutor#resolveVariable` 根据协议类型调用响应 resolver

- `org.apache.logging.log4j.core.net.JndiManager#lookup` 出发JNDI注入



## Reference

- [如何绕过高版本JDK的限制进行JNDI注入利用 – KINGX](https://kingx.me/Restrictions-and-Bypass-of-JNDI-Manipulations-RCE.html)
