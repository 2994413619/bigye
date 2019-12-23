package club.ityuchao.fallback;

import club.ityuchao.client.UserFeignClient;
import club.ityuchao.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public User queryUserById(Long id) {
        User user = new User();
        user.setName("用户查询异常");
        return user;
    }
}
