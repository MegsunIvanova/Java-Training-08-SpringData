import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;

public class P09_FindTheLatest10Projects {

    public static void main(String[] args) {

        EntityManager entityManager = MyUtils.createEntityManager();

        entityManager.createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(System.out::println);

        entityManager.close();
    }
}
