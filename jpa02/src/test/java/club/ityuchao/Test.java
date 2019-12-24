package club.ityuchao;

import club.ityuchao.dao.CustomerDao;
import club.ityuchao.entity.Customer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yuchao
 * @date: 2019/12/24 15:34
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "classpath:applicationContext.xml")
public class Test {

    @Autowired
    private CustomerDao customerDao;

    //添加
    @org.junit.Test
    public void addTest(){
        Customer customer = new Customer();
        customer.setCustName("facebook");
        customerDao.save(customer);
    }

    //更新：save(obj)方法：如果obj中没有id就是add，有id就是update
    @org.junit.Test
    public void updateTest() {
        /*List<Customer> all = customerDao.findAll();
        System.out.println(all.size());*/
        Customer customer = customerDao.findById(2l).get();
        customer.setCustName("阿里巴巴");
        customerDao.save(customer);
    }

    //根据id查询：findOne已经没有了，使用findById(id).get()代替
    @org.junit.Test
    public void findOndTest(){
        Customer customer = customerDao.findById(2l).get();
        System.out.println(customer);
    }

    //根据id查询：getOne延迟加载
    @org.junit.Test
    @Transactional//不加事务，会报错
    public void getOndTest(){
        Customer customer = customerDao.getOne(2l);
        System.out.println(customer);
    }

    //根据id删除
    @org.junit.Test
    public void removeTest(){
        customerDao.deleteById(13l);
    }

    //查询记录条数
    @org.junit.Test
    public void countTest(){
        long count = customerDao.count();
        System.out.println(count);
    }

    //查询id为4的客户是否存在
    @org.junit.Test
    public void existsTest(){
        boolean exists = customerDao.existsById(4l);
        System.out.println(exists);
    }

    //jpql查询:查询所有customer
    @org.junit.Test
    public void test1(){
        List<Customer> list = customerDao.getAllCustomer();
        for(Customer customer : list){
            System.out.println(customer);
        }
    }

    //jpql查询：根据name查询customer
    @org.junit.Test
    public void test2(){
        Customer customer = customerDao.getByName("阿里巴巴");
        System.out.println(customer);
    }

    //jpql更新：根据id修改name
    @org.junit.Test
    @Transactional
    @Rollback(value = false)
    public void test3(){
        customerDao.updateCustomer("百度", 5l);
    }

    //使用sql所有customer
    @org.junit.Test
    public void selectAllBySqlTest(){
        List<Customer> list = customerDao.selectAllBySql();
        for(Customer customer : list){
            System.out.println(customer);
        }
    }

    //使用sql进行条件查询
    @org.junit.Test
    public void SelectByNameSql(){
        List<Customer> list = customerDao.selectByNameSql("腾讯%");
        for(Customer customer : list){
            System.out.println(customer);
        }
    }

    //根据命名规则进行查询
    @org.junit.Test
    public void test11(){
        Customer cu = customerDao.findByCustName("阿里巴巴");
        System.out.println(cu);
    }

    //根据命名规则进行模糊查询
    @org.junit.Test
    public void test12(){
        List<Customer> list = customerDao.findByCustNameLike("腾讯%");
        for(Customer customer : list){
            System.out.println(customer);
        }
    }

    //使用客户名称模糊匹配和客户所属行业精准匹配的查询
    @org.junit.Test
    public void test13(){
        Customer customer = customerDao.findByCustNameLikeAndCustIndustry("阿里巴巴", "互联网");
        System.out.println(customer);
    }
}
