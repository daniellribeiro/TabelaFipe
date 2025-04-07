package Modelo;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Modelo(@JsonAlias("codigo") Integer codigo, @JsonAlias("nome") String nome) {

}
