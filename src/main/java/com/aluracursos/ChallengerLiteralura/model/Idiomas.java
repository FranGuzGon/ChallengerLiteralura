package com.aluracursos.ChallengerLiteralura.model;

public enum Idiomas {
    ESPAÑOL("es", "ESPAÑOL"),
    INGLES("en", "INGLES"),
    FRANCES("fr", "FRANCES"),
    PORTUGUES("pt", "PORTUGUES");

    private String idiomaGutendex;
    private String idiomaUsuario;

    Idiomas(String idiomaGutendex, String idiomaUsuario) {
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaUsuario = idiomaUsuario;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idiomas : Idiomas.values()) {
            if (idiomas.idiomaGutendex.equalsIgnoreCase(text)) {
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public static Idiomas fromUsuario(String text) {
        for (Idiomas idiomas : Idiomas.values()) {
            if (idiomas.idiomaUsuario.equalsIgnoreCase(text)) {
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }

    public String toCamelCase(){
        String nombre = this.name().toLowerCase();
        return nombre.substring(0,1).toUpperCase() + nombre.substring(1);
    }
}

