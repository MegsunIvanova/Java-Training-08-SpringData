import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P11_FindEmployeesByFirstName {
    public static void main(String[] args) {

        final EntityManager entityManager = MyUtils.createEntityManager();

        final String pattern = new Scanner(System.in).nextLine();

        entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        e.getSalary()));

        entityManager.close();
    }
}
