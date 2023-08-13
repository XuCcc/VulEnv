# CVE-2023-20860

## Environment

```java
➜  cve_2023_20860 git:(master) ✗ java -version
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment (build 17.0.8+7-Ubuntu-122.04)
OpenJDK 64-Bit Server VM (build 17.0.8+7-Ubuntu-122.04, mixed mode, sharing)
```

## Run

修改 `http.securityMatcher("**");`  可观察到响应的变化 `200->401`

```java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("**");
        // http.securityMatcher("/**");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.httpBasic();
        return http.build();
    }
```


## Breakpoints

## Reference

