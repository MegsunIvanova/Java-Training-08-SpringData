import entities.User;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
            throws SQLException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        final EntityManager<User> userEntityManager = new EntityManager<>();

//        userEntityManager.doCreate(User.class);

//        User user = new User();
//        user.setId(1L);

//        userEntityManager.doAlter(User.class);
//        User user1 = new User("Pesho", 25, LocalDate.now());
//        User user2 = new User("Gosho", 30, LocalDate.now());
//
//        userEntityManager.persist(user1);
//        userEntityManager.persist(user2);

//        user.setId(1);
//        user.setAge(30);
//        user.setUsername("Gosho");
//
//        userEntityManager.persist(user);
//
        User firstFirstWithArgument = userEntityManager.findFirst(User.class, "age >= 30");
//        User firstFirstWithoutArgument = userEntityManager.findFirst(User.class);
        Iterable<User> getAllWithArgument = userEntityManager.find(User.class, "age >= 30");
//        Iterable<User> getAll = userEntityManager.find(User.class);

//        EntityManager<Account> accountEntityManager = new EntityManager<>();

//        accountEntityManager.doCreate(Account.class);
//        accountEntityManager.doAlter(Account.class);

//        Account account = new Account("Nikolai", LocalDate.now(), 111);


        System.out.println();
    }
}
