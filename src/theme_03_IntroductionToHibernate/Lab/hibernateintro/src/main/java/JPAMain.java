import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAMain {

    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("school-db");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Student student = new Student("Teo");
        entityManager.persist(student);

        entityManager.flush();

        Student found = entityManager.find(Student.class, 1);

        System.out.println(found.getId() + " " + found.getName());

        entityManager.remove(found);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
