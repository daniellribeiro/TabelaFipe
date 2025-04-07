package Modelo;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Ano(@JsonAlias("codigo") String codigo, @JsonAlias("nome") String nome){
}
