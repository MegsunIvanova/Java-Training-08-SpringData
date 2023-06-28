import entities.Result;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class P12_EmployeesMaximumSalaries {
    public static void main(String[] args) {
        final EntityManager entityManager = MyUtils.createEntityManager();

        final List<Object[]> resultList = entityManager
                .createQuery("SELECT department.name, MAX(salary) " +
                        "FROM Employee " +
                        "GROUP BY department.name " +
                        "HAVING MAX(salary) NOT BETWEEN 30000 AND 70000").getResultList();

        //or with POJO class
//        entityManager.createQuery("SELECT NEW entities.models.Result (department.name, MAX(salary) " +
//                        "FROM Employee " +
//                        "GROUP BY department.name " +
//                        "HAVING MAX(salary) NOT BETWEEN 30000 AND 70000", Result.class).getResultList()
//                .forEach(System.out::println);

        entityManager.close();

        for (Object[] result : resultList) {
            final String department = (String) result[0];
            final BigDecimal salary = (BigDecimal) result[1];
            System.out.printf("%s %.2f%n", department, salary);
        }

    }


}
