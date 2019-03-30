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

## 5.服务网关

![architecture](https://github.com/SummerWindL/imgrepository/blob/master/spring-cloud/zuul/Microservice-architecture.png)

### 5.1 使用zuul


* 引入依赖
```aidl
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zuul</artifactId>
    </dependency>
```
* 应⽤主类使⽤ ```@EnableZuulProxy``` 注解开启Zuul

```aidl
@EnableZuulProxy
@SpringCloudApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
```
 * <b>ps</b>: 这⾥⽤了 ```@SpringCloudApplication``` 注解，之前没有提过，通过源码我们看到，它整合
     了 ```@SpringBootApplication``` 、 ```@EnableDiscoveryClient ```、 ```@EnableCircuitBreaker```


* ```application.properties``` 中配置Zuul应⽤的基础信息，如：应⽤名、服务端⼝等。

```aidl
spring.application.name=api-gateway
server.port=5555
```

### 5.2 配置zuul

>通过服务路由的功能，我们在对外提供服务的时候，只需要通过暴露Zuul中配置的调⽤地址就可以让
调⽤⽅统⼀的来访问我们的服务，⽽不需要了解具体提供服务的主机信息了。
在Zuul中提供了两种映射⽅式：
* 通过url直接映射，我们可以如下配置：

```aidl
# routes to url
zuul.routes.api-a-url.path=/api-a-url/**
zuul.routes.api-a-url.url=http://localhost:2222/
```

>该配置，定义了，所有到Zuul的中规则为： /api-a-url/** 的访问都映射
到 http://localhost:2222/ 上，也就是说当我们访问 http://localhost:5555/api-a-url/add?
a=1&b=2 的时候，Zuul会将该请求路由到： http://localhost:2222/add?a=1&b=2 上。
其中，配置属性zuul.routes.api-a-url.path中的api-a-url部分为路由的名字，可以任意定义，但是⼀组映
射关系的path和url要相同，下⾯讲serviceId时候也是如此。

* 通过url映射的⽅式对于Zuul来说，并不是特别友好，Zuul需要知道我们所有为服务的地址，才能
  完成所有的映射配置。⽽实际上，我们在实现微服务架构时，服务名与服务实例地址的关系在
  eureka server中已经存在了，所以只需要将Zuul注册到eureka server上去发现其他服务，我们就
  可以实现对serviceId的映射。例如，我们可以如下配置：
  
 ```aidl
zuul.routes.api-a.path=/api-a/**
zuul.routes.api-a.serviceId=service-A
zuul.routes.api-b.path=/api-b/**
zuul.routes.api-b.serviceId=service-B
eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/
```

**尝试通过服务⽹关来访问service-A和service-B，根据配置的映射关系，分别访问下⾯的url**
* http://localhost:5555/api-a/add?a=1&b=2 ：通过serviceId映射访问service-A中的add服务
* http://localhost:5555/api-b/add?a=1&b=2 ：通过serviceId映射访问service-B中的add服务
* http://localhost:5555/api-a-url/add?a=1&b=2 ：通过url映射访问service-A中的add服务

### 5.3 服务过滤

>在完成了服务路由之后，我们对外开放服务还需要⼀些安全措施来保护客户端只能访问它应该访问到
的资源。所以我们需要利⽤Zuul的过滤器来实现我们对外服务的安全控制。
在服务⽹关中定义过滤器只需要继承 ZuulFilter 抽象类实现其定义的四个抽象函数就可对请求进⾏
拦截与过滤。

⽐如下⾯的例⼦，定义了⼀个Zuul过滤器，实现了在请求被路由之前检查请求中是否
有 accessToken 参数，若有就进⾏路由，若没有就拒绝访问，返回 401 Unauthorized 错误。

```aidl
public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 0;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.get
                RequestURL().toString()));
        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
```
**⾃定义过滤器的实现，需要继承 ZuulFilter ，需要重写实现下⾯四个⽅法：**

* filterType ：返回⼀个字符串代表过滤器的类型，在zuul中定义了四种不同⽣命周期的过滤器
  类型，具体如下：
  * pre ：可以在请求被路由之前调⽤
  * routing ：在路由请求时候被调⽤
  * post ：在routing和error过滤器之后被调⽤
  * error ：处理请求时发⽣错误时被调⽤
* filterOrder ：通过int值来定义过滤器的执⾏顺序
* shouldFilter ：返回⼀个boolean类型来判断该过滤器是否要执⾏，所以通过此函数可实现过滤
  器的开关。在上例中，我们直接返回true，所以该过滤器总是⽣效。
* run ：过滤器的具体逻辑。需要注意，这⾥我们通过 ctx.setSendZuulResponse(false) 令zuul
  过滤该请求，不对其进⾏路由，然后通过 ctx.setResponseStatusCode(401) 设置了其返回的错
  误码，当然我们也可以进⼀步优化我们的返回，⽐如，通过 ctx.setResponseBody(body) 对返
  回body内容进⾏编辑等。

>在实现了⾃定义过滤器之后，还需要实例化该过滤器才能⽣效，我们只需要在应⽤主类中增加如下内
容：
```aidl
@Bean
public AccessFilter accessFilter() {
    return new AccessFilter();
}
```
**ps:可以做一个统一配置bean的config，交给spring管理所有的bean**

启动该服务⽹关后，访问：
* http://localhost:5555/api-a/add?a=1&b=2 ：返回401错误
* http://localhost:5555/api-a/add?a=1&b=2&accessToken=token ：正确路由到server-A，并
返回计算内容

![filter](https://github.com/SummerWindL/imgrepository/blob/master/spring-cloud/zuul/demo.png)

对于其他⼀些过滤类型，这⾥就不⼀⼀展开了，根据之前对 filterType ⽣命周期介绍，可以参考下
图去理解，并根据⾃⼰的需要在不同的⽣命周期中去实现不同类型的过滤器。

![filter](https://github.com/SummerWindL/imgrepository/blob/master/spring-cloud/zuul/req-filter.png)

最后，总结⼀下为什么服务⽹关是微服务架构的重要部分，是我们必须要去做的原因：
* 不仅仅实现了路由功能来屏蔽诸多服务细节，更实现了服务级别、均衡负载的路由。
* 实现了接⼝权限校验与微服务业务逻辑的解耦。通过服务⽹关中的过滤器，在各⽣命周期中去校
验请求的内容，将原本在对外服务层做的校验前移，保证了微服务的⽆状态性，同时降低了微服
务的测试难度，让服务本身更集中关注业务逻辑的处理。
* 实现了断路器，不会因为具体微服务的故障⽽导致服务⽹关的阻塞，依然可以对外服务。

感谢 ： http://blog.didispace.com/springcloud5/

[Spring Cloud源码分析（四）Zuul：核⼼过滤器](http://martinfowler.com/bliki/CircuitBreaker.html)</br>

[Spring Cloud实战⼩贴⼠：Zuul统⼀异常处理（⼀）](http://blog.didispace.com/spring-cloud-zuul-exception/)</br>

[Spring Cloud实战⼩贴⼠：Zuul统⼀异常处理（⼆）](http://blog.didispace.com/spring-cloud-zuul-exception-2/)</br>

[Spring Cloud实战⼩贴⼠：Zuul处理Cookie和重定向](http://blog.didispace.com/spring-cloud-zuul-cookie-redirect/)</br>