import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class P02_ChangeCasing {

    private static final String DATABASE_NAME = "soft_uni";
    private static final String UPDATE_ALL_TOWNS_WITH_NAME_LENGTH_MORE_THAN_5
            = "UPDATE Town t SET t.name = UPPER(t.name) WHERE CHAR_LENGTH(t.name) > 5";

    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

//        entityManager.createQuery(UPDATE_ALL_TOWNS_WITH_NAME_LENGTH_MORE_THAN_5).executeUpdate();

        final Query towns = entityManager.createQuery("Select t from Town t", Town.class);

        List<Town> resultList = towns.getResultList();

        for (Town town : resultList) {
            final String townName = town.getName();

            if (townName.length() <= 5) {
                town.setName(townName.toUpperCase());

                entityManager.persist(town);
            }

        }

        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
