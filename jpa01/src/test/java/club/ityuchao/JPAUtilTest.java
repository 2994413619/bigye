package club.ityuchao;

import club.ityuchao.domain.Customer;
import club.ityuchao.utils.JPAUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * @Author yuchao
 * @date: 2019/12/23 18:36
 * @Description: 使用JPAUtil实现基本的增删查改
 */
public class JPAUtilTest {

    /**
     * 使用JPAUtil实现添加操作
     */
    @Test
    public void addTest(){
        //封装customer对象
        Customer customer = new Customer();
        customer.setCustName("腾讯2");
        customer.setCustAddress("北京");
        customer.setCustPhone("15789514755");
        customer.setCustIndustry("互联网");
        customer.setCustSource("网络");
        customer.setCustLevel("vip客户");

        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();//获得事务管理对象
            tx = em.getTransaction();//获得事务对象
            tx.begin();//开启事务
            em.persist(customer);//执行保存
            tx.commit();//提交事务
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * 根据id查询
     */
    @Test
    public void getByIdTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Customer customer = em.find(Customer.class, 2L);

            tx.commit();
            System.out.println(customer);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * 使用JPAUtil来进行更新操作
     */
    @Test
    public void updateTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            Customer customer = em.find(Customer.class, 1L);

            customer.setCustName("腾讯");
            em.merge(customer);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    /**
     * 删除操作
     */
    @Test
    public void removeTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Customer customer = em.find(Customer.class, 1L);
            em.remove(customer);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * 查询存在缓存问题
     */
    @Test
    public void cacheTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Customer c1 = em.find(Customer.class, 2L);
            Customer c2 = em.find(Customer.class, 2L);

            tx.commit();
            System.out.println(c1 == c2);//返回结果为true，存在缓存
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }



    /*-----------------------------------------JPQL查询------------------------------------------------------------------------*/

    //查询全部
    @Test
    public void findAllTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            String jpql = "from Customer";
            Query query = em.createQuery(jpql);
            List list = query.getResultList();
            for(Object obj: list){
                System.out.println(obj);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //分页查询
    @Test
    public void test(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            String jpql = "from Customer";
            Query query = em.createQuery(jpql);

            //设置分页参数
            query.setFirstResult(0);
            query.setMaxResults(2);

            List list = query.getResultList();
            for(Object obj: list){
                System.out.println(obj);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //条件查询
    @Test
    public void findConditionTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            String jpql = "from Customer where custName like ?1 ";//此处占位符后必须加上下标
            Query query = em.createQuery(jpql);

            //设置占位符内容，下标从1开始
            query.setParameter(1, "Goo%");

            //查询单个结果
            Object obj = query.getSingleResult();
            System.out.println(obj);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //排序查询
    @Test
    public void orderTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            String jpql = "from Customer order by custId desc";
            Query query = em.createQuery(jpql);
            List list = query.getResultList();
            for(Object obj: list){
                System.out.println(obj);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //统计查询
    @Test
    public void countTest(){
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            String jpql = "select count(custId) from Customer";
            Query query = em.createQuery(jpql);
            Object count = query.getSingleResult();
            System.out.println(count);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}
