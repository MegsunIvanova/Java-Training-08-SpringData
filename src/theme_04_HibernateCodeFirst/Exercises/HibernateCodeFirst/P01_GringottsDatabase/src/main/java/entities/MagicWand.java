package entities;

import javax.persistence.*;

@Entity
@Table(name = "magic_wands")
public class MagicWand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "magic_wand_creator", length = 100)
    private String magicWandCreator;

    // magic_wand_size – Number in the range [1, 215-1]
    @Column(name = "magic_wand_size")
    private Integer magicWandSize;

    public MagicWand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    public Integer getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(Integer magicWandSize) {
        this.magicWandSize = magicWandSize;
    }
}
