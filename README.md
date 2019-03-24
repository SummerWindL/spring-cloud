# spring-cloud
spring-cloud-learning

## 1.服务注册与发现

## 2.服务消费者

## 3.断路器

## 4.分布式配置中心
>配置中心 spring-cloud-config仓库，作为独立服务运行 </br>
启动模块 center-config 访问 http://localhost:9000/fyl/dev/config-label  </br>

返回如下表示成功</br>

实现高可用动态刷新，需要POST访问http://localhost:7002/refresh

```
{
    "name":"fyl",
    "profiles":[
        "dev"
    ],
    "label":"config-label",
    "version":"284d6274c1726a7d0fb311f884ee693118bab7c3",
    "propertySources":[
        {
            "name":"https://github.com/SummerWindL/spring-cloud-config.git/config-respo/fyl-dev.properties",
            "source":{
                "from":"fyl_dev_2.0"
            }
        },
        {
            "name":"https://github.com/SummerWindL/spring-cloud-config.git/config-respo/fyl.properties",
            "source":{
                "from":"fyl_default_2.0"
            }
        },
        {
            "name":"https://github.com/SummerWindL/spring-cloud-config.git/fyl.properties",
            "source":{
                "name":"fyl default 2.0"
            }
        }
    ]
}
```
