package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Sale extends BaseEntity {

    //bi-directional relation
    @ManyToOne
    private Product product;

    //bi-directional relation
    @ManyToOne
    private Customer customer;

    //bi-directional relation
    @ManyToOne
    private StoreLocation storeLocation;

    @Column
    private Date date;
}
