package club.ityuchao.client;

import club.ityuchao.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-provide", fallback = club.ityuchao.fallback.UserFeignClientFallback.class)
public interface UserFeignClient {

    @GetMapping("/user/{id}")
    public User queryUserById(@PathVariable Long id);
}
