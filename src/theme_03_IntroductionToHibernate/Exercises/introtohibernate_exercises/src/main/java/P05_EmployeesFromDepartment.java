import entities.Employee;

import javax.persistence.EntityManager;

public class P05_EmployeesFromDepartment {

    private static final String PRINT_EMPLOYEE_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {

        final EntityManager entityManager = MyUtils.createEntityManager();

        final String department = "Research and Development";

        entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = :dp " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("dp", department)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_EMPLOYEE_FORMAT,
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment().getName(),
                        e.getSalary()));

        entityManager.close();

    }
}

