import entities.Employee;

import javax.persistence.EntityManager;

public class P10_IncreaseSalaries_v02 {
    public static void main(String[] args) {

        final EntityManager entityManager = MyUtils.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery("UPDATE Employee AS e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department IN (1, 2, 14, 3)")
                .executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.createQuery("SELECT e FROM Employee e " +
                                "WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')",
                        Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary()));

        entityManager.close();

    }
}
