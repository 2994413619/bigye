package club.ityuchao.dao;

import club.ityuchao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author yuchao
 * @date: 2019/12/24 15:34
 * @Description:
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("from Customer")
    public List<Customer> getAllCustomer();

    @Query("from Customer where custName = ?1")
    public Customer getByName(String name);

    @Query("update Customer set custName = ?1 where custId = ?2")
    @Modifying
    public void updateCustomer(String name, Long id);

    //nativeQuery默认值为false，表示使用jpql查询，true表示使用本地查询（sql查询）
    @Query(value = "select * from cst_customer", nativeQuery = true)
    public List<Customer> selectAllBySql();

    @Query(value = "select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Customer> selectByNameSql(String name);

    //根据方法名命名规则查询
    public Customer findByCustName(String custName);

    //根据方法名命名规则模糊查询
    public List<Customer> findByCustNameLike(String custName);

    //使用客户名称模糊匹配和客户所属行业精准匹配的查询
    public Customer findByCustNameLikeAndCustIndustry(String custName, String custIndustry);


}
