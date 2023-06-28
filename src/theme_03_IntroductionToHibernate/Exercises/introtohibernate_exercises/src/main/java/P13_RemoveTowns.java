import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class P13_RemoveTowns {
    public static void main(String[] args) {
        final EntityManager entityManager = MyUtils.createEntityManager();

        String cityToDelete = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        Town town = entityManager.createQuery("SELECT t FROM Town t WHERE t.name = :city", Town.class)
                .setParameter("city", cityToDelete)
                .getSingleResult();


        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a WHERE a.town = town.id", Address.class)
                .getResultList();

        for (Address address : addresses) {
            address.getEmployees().forEach(e -> e.setAddress(null));
            address.setTown(null);
            entityManager.remove(address);
        }

        entityManager.remove(town);

        entityManager.getTransaction().commit();


        System.out.printf("%d address in %s deleted", addresses.size(), town.getName());

        entityManager.close();


    }
}
