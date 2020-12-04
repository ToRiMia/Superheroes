package torimia.superheroes.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RarityTest {


    @Test
    void wrong_string() {
        Rarity.forValues("unknown");    }
}