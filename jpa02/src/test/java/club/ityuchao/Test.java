package club.ityuchao;

import club.ityuchao.dao.CustomerDao;
import club.ityuchao.entity.Customer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * @Author yuchao
 * @date: 2019/12/24 15:34
 * @Description: Spring data jpa 单表练习
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

    /*-----------------------------------------------jpql查询begin----------------------------------------------------------*/

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

    /*-----------------------------------------------jpql查询end----------------------------------------------------------*/

    /*-----------------------------------------------sql查询begin----------------------------------------------------------*/

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

    /*-----------------------------------------------sql查询end----------------------------------------------------------*/

    /*-----------------------------------------------方法命名规则查询begin------------------------------------------------*/

    //单条件查询：根据客户名
    @org.junit.Test
    public void test11(){
        Customer cu = customerDao.findByCustName("阿里巴巴");
        System.out.println(cu);
    }

    //模糊查询：根据客户名
    @org.junit.Test
    public void test12(){
        List<Customer> list = customerDao.findByCustNameLike("腾讯%");
        for(Customer customer : list){
            System.out.println(customer);
        }
    }

    //多条件查询：客户名称模糊匹配、客户所属行业精准匹配
    @org.junit.Test
    public void test13(){
        Customer customer = customerDao.findByCustNameLikeAndCustIndustry("阿里巴巴", "互联网");
        System.out.println(customer);
    }

    /*-----------------------------------------------方法命名规则查询end----------------------------------------------------------*/

    /*-----------------------------------------------specifications查询begin------------------------------------------------------*/

    //单条件查询：根据id
    @org.junit.Test
    public void specTest1(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //获取比较的属性
                Path<Object> custId = root.get("custId");
                //构造查询条件
                Predicate predicate = criteriaBuilder.equal(custId, 2l);
                return predicate;
            }
        };

        Optional<Customer> one = customerDao.findOne(spec);
        System.out.println(one.get());
    }

    //多条件查询：客户名、所属行业
    @org.junit.Test
    public void specTest2(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate p1 = criteriaBuilder.equal(custName, "阿里巴巴");
                Predicate p2 = criteriaBuilder.equal(custIndustry, "互联网");
                //组合两个条件
                Predicate and = criteriaBuilder.and(p1, p2);
                return and;
            }
        };

        Customer customer = customerDao.findOne(spec).get();
        System.out.println(customer);

    }

    //模糊查询并排序：custName模糊查询，custID降序
    @org.junit.Test
    public void specTest3(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path custName = root.get("custName");
                Predicate pr = criteriaBuilder.like(custName, "腾讯%");
                return pr;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "custId");

        List<Customer> all = customerDao.findAll(spec, sort);
        for(Customer customer : all){
            System.out.println(customer);
        }

    }

    //分页查询
    @org.junit.Test
    public void specTest4(){
        Specification<Customer> spec = null;
        PageRequest page = PageRequest.of(0, 2);
        Page<Customer> all = customerDao.findAll(spec, page);
        System.out.println(all.getContent());//得到数据集合
        System.out.println(all.getTotalElements());//总条数
        System.out.println(all.getTotalPages());//总页数
    }

    /*-----------------------------------------------specifications查询end--------------------------------------------------------*/


}
