package Modelo;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Veiculo(@JsonAlias("TipoVeiculo") Integer tipoVeiculo,
                      @JsonAlias("Valor") String valor,
                      @JsonAlias("Marca") String marca,
                      @JsonAlias("Modelo") String modelo,
                      @JsonAlias("AnoModelo") Integer ano,
                      @JsonAlias("Combustivel") String tipoCombustivel,
                      @JsonAlias("CodigoFipe") String codigoFipe,
                      @JsonAlias("MesReferencia") String mes,
                      @JsonAlias("SiglaCombustivel") String siglaCombustivel) {


    @Override
    public String toString() {
        return "TipoVeiculo: "       + tipoVeiculo      + ",\n" +
                "Valor: "            + valor            + ",\n" +
                "Marca: "            + marca            + ",\n" +
                "Modelo: "           + modelo           + ",\n" +
                "AnoModelo: "        + ano              + ",\n" +
                "Combustivel: "      + tipoCombustivel  + ",\n" +
                "CodigoFipe: "       + codigoFipe       + ",\n" +
                "MesReferencia: "    + mes              + ",\n" +
                "SiglaCombustivel: " + siglaCombustivel;
    }
}
