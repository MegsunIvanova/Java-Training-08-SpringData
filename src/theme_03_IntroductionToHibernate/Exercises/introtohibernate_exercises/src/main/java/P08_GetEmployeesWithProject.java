import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P08_GetEmployeesWithProject {
    public static void main(String[] args) {

        final EntityManager entityManager = MyUtils.createEntityManager();

        final int empId = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.println(entityManager.createQuery("SELECT e FROM Employee e WHERE e.id = :id",
                        Employee.class)
                .setParameter("id", empId)
                .getSingleResult()
                .toString());

        entityManager.close();
    }
}
