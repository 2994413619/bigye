package club.ityuchao;

import club.ityuchao.dao.RoleDao;
import club.ityuchao.dao.UserDao;
import club.ityuchao.entity.Role;
import club.ityuchao.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * @Author yuchao
 * @date: 2019/12/25 16:25
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    //添加一个用户，一个角色
    @Test
    @Transactional
    @Rollback(false)
    public void test(){
        User user = new User();
        user.setUserName("张三");

        Role role = new Role();
        role.setRoleName("管理员");

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);

        userDao.save(user);
        roleDao.save(role);

    }

    //级联删除一个用户，并删除相关的角色
    @Test
    @Transactional
    @Rollback(false)
    public void test3(){
        User user = userDao.findById(3l).get();
        userDao.delete(user);

    }

}
