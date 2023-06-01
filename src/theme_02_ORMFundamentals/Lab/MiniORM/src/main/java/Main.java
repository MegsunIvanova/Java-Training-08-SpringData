import entities.Student;
import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        Connector.createConnection("root", "123456", "soft_uni");
        Connection connection = Connector.getConnection();

        EntityManager<User> userManager = new EntityManager<User>(connection);
//        User user = new User("First", 28, LocalDate.now());
//        userManager.persist(user);

        EntityManager<Student> studentManager = new EntityManager<>(connection);
//        Student student = new Student("name");
//        studentManager.persist(student);

//        User first = userManager.findFirst(User.class);

//        System.out.println(first.getId() + " " + first.getUsername());

//        Student first1 = studentManager.findFirst(Student.class, "name = 'name2'");

//        System.out.println(first1.getId() + " " + first1.getName());

//        userManager
//                .find(User.class, "age > 18 AND registration_date > '2022-06-06'")
//                .forEach(user -> System.out.println(user.toString()));

        User user2 = new User("Second", 30, LocalDate.now());
//        userManager.persist(user2);

        user2.setId(8);
        user2.setUsername("SecondChanged");
        user2.setAge(40);
        user2.setRegistration(LocalDate.now());

        userManager.persist(user2);
    }
}
