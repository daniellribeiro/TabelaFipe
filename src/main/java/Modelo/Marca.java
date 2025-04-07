package Modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public record Marca(@JsonAlias("codigo") Integer codigo,@JsonAlias("nome") String nome) {

}
