package bg.softuni.accounts;

import bg.softuni.accounts.models.User;
import bg.softuni.accounts.services.AccountService;
import bg.softuni.accounts.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

   private AccountService accountService;

   private UserService userService;

    public ConsoleRunner(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
//        User someuser = new User("someuser", 20);
//        userService.register(someuser);

//        accountService.depositMoney(BigDecimal.TEN, 1L);

        accountService.withdrawMoney(BigDecimal.valueOf(30), 1L);

    }
}
