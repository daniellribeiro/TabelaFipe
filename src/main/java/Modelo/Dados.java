package Modelo;


public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return "\n codigo: " + this.codigo +
                ", nome: "  + this.nome;
    }
}
