package exercise.model;

import jakarta.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@AllArgsConstructor
@Entity
@Table(name = "products")
@Getter
@Setter
//@EqualsAndHashCode(of = {"price","title"})

public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @EqualsAndHashCode.Include
    private String title;
    private int price;




    public Product() {

    }
}

// END
