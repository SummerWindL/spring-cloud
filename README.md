# spring-cloud
spring-cloud-learning

## 1.服务注册与发现

## 2.服务消费者

## 3.断路器

[Circuit Breaker](http://martinfowler.com/bliki/CircuitBreaker.html)</br>
[Hystrix](https://github.com/Netflix/Hystrix)

使用spring类库spring-cloud-starter-hystrix</br>

### 3.1 Ribbon
>   1.在eureka-ribbon的主类`RibbonApplication` 中使⽤`@EnableCircuitBreaker`注解开启断路器功
能

>   2.改造原来的服务消费⽅式，新增`ComputeService`类，在使⽤`ribbon`消费服务的函数上增
加 `@HystrixCommand` 注解来指定回调⽅法

### 3.2 Feign

>* 使⽤ `@FeignClient` 注解中的fallback属性指定回调类

```
@FeignClient(value = "compute-service", fallback = ComputeClientHystrix.class)
public interface ComputeClient {
    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") In
                teger b);
}
```

>* 创建回调类 `ComputeClientHystrix` ，实现 `@FeignClient` 的接⼝，此时实现的⽅法就是对
应 `@FeignClient` 接⼝中映射的fallback函数

```
@Component
public class ComputeClientHystrix implements ComputeClient {
    @Override
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value ="b") Integer b) {
            return -9999;
        }
}
```

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
