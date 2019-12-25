package club.ityuchao;

import club.ityuchao.dao.CustomerDao;
import club.ityuchao.dao.LinkManDao;
import club.ityuchao.entity.Customer;
import club.ityuchao.entity.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @Author yuchao
 * @date: 2019/12/25 15:23
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    //查询一个客户，获得该客户下的所有联系人并测试懒加载
    @Test
    @Transactional
    @Rollback(false)
    public void test1(){
        Customer customer = customerDao.findById(102l).get();
        Set<LinkMan> linkMans = customer.getLinkMans();
        for(LinkMan linkMan : linkMans){
            System.out.println(linkMan);
        }

        /*LinkMan linkMan = linkManDao.findById(6l).get();
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);*/

    }

}
