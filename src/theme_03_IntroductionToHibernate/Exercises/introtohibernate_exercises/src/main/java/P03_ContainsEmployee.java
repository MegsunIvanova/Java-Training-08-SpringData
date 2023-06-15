import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P03_ContainsEmployee {
    public static void main(String[] args) {

        final EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("soft_uni");

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        final Scanner scanner = new Scanner(System.in);

        String[] name = scanner.nextLine().split("\\s+");

        String firstName = name[0];
        String lastName = name[1];

        Long countOfMatches = entityManager
                .createQuery("SELECT count(e) FROM Employee e " +
                        "WHERE e.firstName = :fn AND e.lastName = :ln",
                        Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();

        if (countOfMatches == 0) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }

        entityManager.close();

    }
}
