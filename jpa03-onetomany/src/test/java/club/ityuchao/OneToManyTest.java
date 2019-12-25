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

/**
 * @Author yuchao
 * @date: 2019/12/25 14:33
 * @Description: Spring data jpa 一对多测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    //保存一个客户和一个联系人
    @Test
    @Transactional
    @Rollback(false)
    public void addTest(){
        Customer customer = new Customer();
        customer.setCustName("字节跳动");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("李一鸣");

        linkMan.setCustomer(customer);
        customer.getLinkMans().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);

    }

    //级联添加
    @Test
    @Transactional
    @Rollback(false)
    public void addCascadeTest(){
        Customer customer = new Customer();
        customer.setCustName("快播");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("王兴");

        linkMan.setCustomer(customer);
        customer.getLinkMans().add(linkMan);

        customerDao.save(customer);

    }

    //级联删除
    @Test
    @Transactional
    @Rollback(false)
    public void deleteCascadeTest(){
        Customer customer = customerDao.findById(105l).get();
        customerDao.delete(customer);

    }

}
