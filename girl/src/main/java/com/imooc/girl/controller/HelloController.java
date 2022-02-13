package com.imooc.girl.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.imooc.girl.propertites.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    /*@Value("${girl.cupSize}")
    private String cupSize;

    @Value("${girl.content}")
    private String content;
*/
    @Autowired
    private GirlProperties girlProperties;


    /***
     *   Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     *  blockHandler / blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项。
     *  blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，
     *  参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。
     *  blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，
     *  则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * @param ex
     * @return
     */
    public String exceptionHandler(BlockException ex) {
        return "Blocked by Sentinel: " + ex.getClass().getSimpleName();
    }


    /**
     * fallback / fallbackClass：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
     * fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。
     * fallback 函数签名和位置要求：
     * 返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     * fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     *
     * @return
     */
    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback() {
        return String.format("Halooooo %d", "123");
    }

    @SentinelResource(value = "say"/**, blockHandler = "exceptionHandler", fallback = "helloFallback"**/)
    @RequestMapping(value = "say", method = RequestMethod.GET)
    public String say() {
        return girlProperties.getCupSize() + "," + girlProperties.getAge();
    }


    @GetMapping(value = "say2")
    @SentinelResource(value = "say2")
    public String say2(@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        return "Id=" + id;
    }


    /**
     * 请求速率限流方法
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/access-limit/nginx-rate")
    public String nginxRate(@RequestParam("id") int id) {
        return "Id=" + id;
    }


    /**
     * 请求连接数限流方法
     *
     * @return
     */
    @GetMapping(value = "/access-limit/nginx-conn")
    public String nginxConn(@RequestParam("seconds") int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
