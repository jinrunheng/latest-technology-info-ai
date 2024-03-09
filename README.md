# latest-technology-info-crawler-ai

## Redis
你可以选择使用 Docker 容器，也可以在本地启动 Redis 服务，我在本项目使用的方式为本地启动 Redis，使用 Redis 版本号为 6.0.9。

进入到 Redis 目录下，执行命令：
```bash
redis-server
```

以开启 Redis 服务。

## SonarQube
- version 7.9.6
## MySQL

```bash
docker run --name article_ai -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE=article_ai -e TZ=Asia/Shanghai -p 3306:3306 -d {your_container_name}
```
## Swagger2

```json

{
  "messages":[
    {
      "role":"user",
      "content":"你好"
    }
  ]
}
```