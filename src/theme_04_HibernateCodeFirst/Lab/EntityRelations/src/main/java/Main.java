import entities.Bike;
import entities.Car;
import entities.Plane;
import entities.Vehicle;
import hasEntities.Article;
import hasEntities.Automobile;
import hasEntities.PlateNumber;
import hasEntities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("relations")
                .createEntityManager();
        entityManager.getTransaction().begin();

//        Vehicle car = new Car("Ford Focus", "Petrol", 5);
//        Vehicle bike = new Bike();
//        Vehicle plane = new Plane("Airbus", "Kerosene", 200);

//        entityManager.persist(car);
//        entityManager.persist(bike);
//        entityManager.persist(plane);

//        Car fromDB = entityManager.find(Car.class, 1);
//        System.out.println(fromDB.getSeats() + " " + fromDB.getModel());

//        PlateNumber plateNumber = new PlateNumber("CA1234AB");
//        Automobile automobile1 = new Automobile(plateNumber);
//        Automobile automobile2 = new Automobile(plateNumber);
//
//        entityManager.persist(plateNumber);
//        entityManager.persist(automobile1);
//        entityManager.persist(automobile2);

//        Article article = new Article("alabala");
//        User author = new User("Toshko");
//        article.setAuthor(author);
//        author.addArticle(article);
//
//        entityManager.persist(author);
//
//        User user = entityManager.find(User.class, 2);

        System.out.println();

        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
