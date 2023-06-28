package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wizards")
public class Wizard {

    //wizard ingo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // first_name – Text field with a max length of 50 symbols
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private Integer age;

    @OneToOne
    @JoinColumn(name = "magic_wand")
    private MagicWand magicWand;

    @OneToMany
    @JoinTable (name = "wizard_deposits")
    private List<Deposit> deposits;

}
