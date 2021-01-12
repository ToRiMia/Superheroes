package torimia.superheroes.arena.annotations;

public @interface NotContainsRepeatableElement {

    Class<?>[] constraints() default {};

    String message() default "{ValidCollection.message}";
}
