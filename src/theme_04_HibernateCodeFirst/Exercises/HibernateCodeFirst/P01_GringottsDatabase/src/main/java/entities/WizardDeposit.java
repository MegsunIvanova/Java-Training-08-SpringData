//package entities;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "wizard_deposits")
//public class WizardDeposit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // first_name – Text field with a max length of 50 symbols
//    @Column(name = "first_name", length = 50)
//    private String firstName;
//
//    @Column(name = "last_name", length = 60)
//    private String lastName;
//
//    @Column(length = 1000)
//    private String notes;
//
//    @Column(nullable = false)
//    private Integer age;
//
//    @Column(name = "magic_wand_creator", length = 100)
//    private String magicWandCreator;
//
//    // magic_wand_size – Number in the range [1, 215-1]
//    @Column(name = "magic_wand_size")
//    private Integer magicWandSize;
//
//    @Column(name = "deposit_group", length = 20)
//    private String depositGroup;
//
//    @Column(name = "deposit_start_date")
//    private LocalDateTime depositStartDate;
//
//    @Column(name = "deposit_amount", scale = 2 , precision = 10)
//    private BigDecimal depositAmount;
//
//    @Column(name = "deposit_interest", scale = 2 , precision = 4)
//    private BigDecimal depositInterest;
//
//    @Column(name = "deposit_charge", scale = 2 , precision = 10)
//    private BigDecimal depositCharge;
//
//    @Column(name = "deposit_expiration_date")
//    private LocalDateTime depositExpirationDate;
//
//    @Column(name = "isDepositExpired")
//    private boolean is_deposit_expired;
//
//}
