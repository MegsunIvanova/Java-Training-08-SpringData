import entities.Address;

import javax.persistence.EntityManager;

public class P07_AddressesWithEmployeeCount {

    public static void main(String[] args) {
        EntityManager entityManager = MyUtils.createEntityManager();

        entityManager.createQuery("SELECT a FROM Address a ORDER BY size(a.employees) DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(System.out::println);


        entityManager.close();

    }
}
