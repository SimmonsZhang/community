## 社区

运行MyBatis逆向工程：
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
处理全局异常
```
+-advice
    |---CustomizedExceptionHandler
```
处理4XX,5XX的`error`
```
+-controller
    |---CustomizedErrorController
```
用来向浏览器以json方式返回成功/异常信息
```
+-dto
    |---ResultDTO
```