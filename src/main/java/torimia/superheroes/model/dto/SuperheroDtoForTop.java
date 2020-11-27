package torimia.superheroes.model.dto;

import lombok.Data;

@Data
public class SuperheroDtoForTop implements SuperheroViewForTop{

    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private Long amount;
}
