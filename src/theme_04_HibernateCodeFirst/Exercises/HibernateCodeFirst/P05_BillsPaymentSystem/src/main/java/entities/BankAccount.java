package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail {

    @Column (name = "bank_name", length = 50)
    private String name;

    @Column(name = "SWIFT_code", length = 8)
    private String swiftCode;
}
