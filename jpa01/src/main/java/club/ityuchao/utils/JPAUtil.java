package club.ityuchao.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory em;
    static{
        em = Persistence.createEntityManagerFactory("myJpa");
    }

    public static EntityManager getEntityManager(){
        return em.createEntityManager();
    }

}
