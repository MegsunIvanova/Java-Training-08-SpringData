import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Scanner;

public class P06_AddingANewAddressAndUpdatingTheEmployee {
    public static void main(String[] args) {

        final EntityManager entityManager = MyUtils.createEntityManager();

        final String lastName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");

        final String townName = "Sofia";
        final Town town = entityManager.createQuery("SELECT t FROM Town t WHERE t.name = :tn", Town.class)
                .setParameter("tn", townName)
                .getSingleResult();
        newAddress.setTown(town);

        entityManager.persist(newAddress);


        int count = entityManager.createQuery("UPDATE Employee e SET e.address = :newAddress WHERE e.lastName = :ln ")
                .setParameter("newAddress", newAddress)
                .setParameter("ln", lastName)
                .executeUpdate();

        if (count > 0) {
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();

        System.out.println(count);
    }
}
