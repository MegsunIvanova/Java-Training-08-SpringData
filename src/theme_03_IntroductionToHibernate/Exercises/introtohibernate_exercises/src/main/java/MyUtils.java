import javax.persistence.EntityManager;
import javax.persistence.Persistence;

enum MyUtils {
    ;

    public static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();
    }

}
