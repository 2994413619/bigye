package club.ityuchao;

import club.ityuchao.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootTest
class Jpa01ApplicationTests {

    /**
     * 第一个jpa代码，添加一个客户到表中，测试环境是否连接成功。
     */
    @Test
    void contextLoads() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setCustName("阿里巴巴");

        em.persist(customer);

        tx.commit();
        em.close();
        factory.close();
    }

}
