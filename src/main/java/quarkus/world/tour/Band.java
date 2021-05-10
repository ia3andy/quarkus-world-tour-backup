package quarkus.world.tour;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Multi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Band extends PanacheEntity {

    @NotBlank
    @Column(unique = true)
    public String name;

    @Min(1940)
    public int creationYear;

    public int terminationYear = -1;

    public boolean alive;

    public static Multi<Band> stillAlive() {
        return Band.stream("alive", true);
    }

}
