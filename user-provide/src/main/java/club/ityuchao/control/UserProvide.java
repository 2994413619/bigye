package club.ityuchao.control;

import club.ityuchao.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserProvide {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        //测试熔断、服务降级
        /*try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        if(id % 2 == 0){
            throw new RuntimeException();
        }

        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setPassword("123456");
        return user;

    }


}
