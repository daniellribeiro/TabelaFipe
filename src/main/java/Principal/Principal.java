package Principal;

import Modelo.Dados;
import Modelo.Modelos;
import Modelo.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Principal {
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void tabelaFipe() throws JsonProcessingException {

        String endereco;

        String tipoVeiculo = consultarTipoVeiculo();

        endereco = URL_BASE + tipoVeiculo + "/marcas/";
        List<Dados> marcaLista = this.chamarAPI(endereco,Dados.class,0);
        marcaLista.forEach(System.out::println);
        String marca = this.lerCodigo("marca");

        endereco += marca + "/modelos/";
        List<Modelos> modeloLista = this.chamarAPI(endereco,
                                        Modelos.class,
                                        1);
        modeloLista.forEach(System.out::println);
        String modelo = this.lerCodigo("modelo");

        endereco += modelo + "/anos/";
        List<Dados> anoLista = this.chamarAPI(endereco,Dados.class,0);
        anoLista.forEach(System.out::println);
        String ano = this.lerCodigo("ano");

        endereco += ano;
        consultarVeiculo(endereco);
    }

    private void consultarVeiculo(String endereco) {
        ConsultarAPI consultarAPI = new ConsultarAPI();
        Conversor conversor = new Conversor();
        Veiculo retorno;
        var json = consultarAPI.consultarAPI(endereco);
        retorno = conversor.converterJson(json, Veiculo.class);
        System.out.println(retorno);
        System.out.println('\n');
    }

    private String consultarTipoVeiculo(){

        //Consultar Marca
        System.out.println("- carros");
        System.out.println("- motos");
        System.out.println("- caminhoes");
        System.out.println("Digite uma das opções para consultar valores: ");
        final Scanner leitura = new Scanner(System.in);

        String nomeOpcaoMarca = leitura.next().toLowerCase();

        if(!(
                Objects.equals(nomeOpcaoMarca, "carros")
                        || Objects.equals(nomeOpcaoMarca, "motos")
                        || Objects.equals(nomeOpcaoMarca, "caminhoes")
        )){
            System.out.println("Opcao invalida");
            System.exit(0);
        }

        return nomeOpcaoMarca;
    }

    private <T> String consultarJson(String endereco){
        ConsultarAPI consultarAPI = new ConsultarAPI();
        return consultarAPI.consultarAPI(endereco);
    }

    private <T> List<T> converterLista(String json, Class<T> classe){
         Conversor conversor = new Conversor();
         return conversor.obterJson(json,classe);
    }

    private <T> List<T> converter(String json, Class<T> classe){
        T dados;
        List<T> dadosLista = new ArrayList<T>();
        Conversor conversor = new Conversor();
        dados = conversor.obterJson2(json,classe);
        dadosLista.add(dados);
        return dadosLista;
    }

    private <T> List<T> consultar(String endereco, Class<T> classe,Integer tipoConversor){
        List<T> dados = new ArrayList<T>();

        var json = this.consultarJson(endereco);
        if(tipoConversor == 0) {
            dados = this.converterLista(json, classe);
        }else if(tipoConversor == 1) {
            dados = this.converter(json, classe);
        }
        return dados;
    }

    private <T> List<T> chamarAPI(String endereco, Class<T> classe, Integer tipoConversor){
        List<T> dados = new ArrayList<T>();

        dados = this.consultar(endereco,classe, tipoConversor);

        return dados;
    }

    private String lerCodigo(String valor){
        System.out.println("Digite " + valor.toUpperCase() + " escolhido: ");
        final Scanner leitura = new Scanner(System.in);
        return leitura.next();
    }

    private String lerTrechoNome(String valor){
        System.out.println("Digite trecho " + valor.toUpperCase() + " escolhido: ");
        final Scanner leitura = new Scanner(System.in);
        return leitura.next().toUpperCase();
    }

    private <T> String lerCodigoTrecho(List<Dados> marcaListaFiltrada, String valor){
        String retorno;
        if(marcaListaFiltrada.size() > 1){
            retorno = this.lerCodigo(valor);
        }else{
            retorno = marcaListaFiltrada.get(0).codigo();
        }
        return retorno;
    }

    private String consultarDados(String endereco, String valor) {
        List<Dados> lista;
        String trecho;
        List<Dados> listaFiltrada = new ArrayList<Dados>();

        lista = this.chamarAPI(endereco, Dados.class, 0);
        trecho = this.lerTrechoNome(valor);
        listaFiltrada = lista.stream().filter(e -> e.nome().toUpperCase().contains(trecho.toUpperCase())).toList();
        listaFiltrada.forEach(System.out::println);

        return this.lerCodigoTrecho(listaFiltrada, valor);
    }

    private String consultarDadosModelo(String endereco, String valor) {
        List<Modelos> lista;
        String trecho;
        List<Dados> listaFiltrada = new ArrayList<Dados>();

        lista = this.chamarAPI(endereco, Modelos.class, 1);
        trecho = this.lerTrechoNome(valor);
        listaFiltrada =
                lista.get(0).modelos().stream().filter(e -> e.nome().toUpperCase().contains(trecho.toUpperCase())).toList();
        listaFiltrada.forEach(System.out::println);

        return this.lerCodigoTrecho(listaFiltrada, valor);
    }

    public void tabelaFipePorNome() {
        String endereco;

        String tipoVeiculo = consultarTipoVeiculo();

        endereco = URL_BASE + tipoVeiculo + "/marcas/";
        String marca = this.consultarDados(endereco,"marca");

        endereco += marca + "/modelos/";
        String modelo = this.consultarDadosModelo(endereco,"modelo");

        endereco += modelo + "/anos/";
        List<Dados> anoLista = this.chamarAPI(endereco,Dados.class,0);
        //anoLista.forEach(System.out::println);
        //String ano = this.lerCodigo("ano");

        for (Dados dados : anoLista) {
            String enderecoAnos = endereco + dados.codigo();
            consultarVeiculo(enderecoAnos);
        }
    }
}
