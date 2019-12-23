package club.ityuchao.control;

import club.ityuchao.client.UserFeignClient;
import club.ityuchao.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
//@DefaultProperties(defaultFallback = "getUsersFallbackMethod")
public class UserControl {

    /*@Autowired
    public RestTemplate template;*/

   /* @Autowired
    public DiscoveryClient discoveryClient;*/

   @Autowired
    private UserFeignClient userClient;

    @RequestMapping("/user")
    //@HystrixCommand(fallbackMethod = "getUsersFallbackMethod")
    /*@HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="6000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
    })*/
    public String getUsers(Long[] ids){

        //测试熔断
        /*if(ids[0] % 2 == 0){
            throw new RuntimeException();
        }*/

        List<User> users = new ArrayList<User>();

        //List<ServiceInstance> instances = discoveryClient.getInstances("user-provide");
        //ServiceInstance instance = instances.get(0);
       // String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/user/";

        //String url = "http://user-provide/user/";

        for (long id:ids) {
            //User user = template.getForObject(url + id, User.class);
            User user = userClient.queryUserById(id);
            users.add(user);
        }

        ObjectMapper mapper = new ObjectMapper();

        String string = null;
        try {
            string = mapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return string;
    }


    //服务降级逻辑
    /*public String getUsersFallbackMethod(){
        return "对不起，当前服务器繁忙！";
    }*/

}
