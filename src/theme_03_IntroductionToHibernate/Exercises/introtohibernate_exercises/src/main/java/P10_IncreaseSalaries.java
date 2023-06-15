import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class P10_IncreaseSalaries {
    public static void main(String[] args) {

        final EntityManager entityManager = MyUtils.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> employeesForUpdate = entityManager.createQuery("SELECT e FROM Employee e " +
                                "WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')",
                        Employee.class)
                .getResultList();

        employeesForUpdate.forEach(e -> {
            BigDecimal newSalary = e.getSalary().multiply(BigDecimal.valueOf(1.12));
            e.setSalary(newSalary);
            entityManager.persist(e);
            System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary());
        });

        entityManager.getTransaction().commit();

        entityManager.close();

    }
}
