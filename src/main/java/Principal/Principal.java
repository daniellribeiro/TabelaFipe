package Principal;

import Modelo.Marca;
import Modelo.Modelo;
import Modelo.Ano;
import Modelo.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

public class Principal {
    public void tabelaFipe() throws JsonProcessingException {

        String tipoVeiculo = consultarTipoVeiculo();

        String marca = consultar("https://parallelum.com.br/fipe/api/v1/carros/marcas/",
                                "marca",
                                Marca.class,0);

        String modelo = consultar("https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos",
                                    "modelo",
                                    "modelos",
                                    Modelo.class,1);

        String ano = consultar("https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos/" +
                                    modelo + "/anos",
                                "ano",
                                Ano.class,
                                0);

        consultarVeiculo("https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos/" +
                modelo + "/anos/" + ano);
    }

    private void consultarVeiculo(String endereco) {
        ConsultarAPI consultarAPI = new ConsultarAPI();
        Conversor conversor = new Conversor();
        Veiculo retorno;
        var json = consultarAPI.consultarAPI(endereco);
        retorno = conversor.converterJson(json, Veiculo.class);
        System.out.println(retorno);
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

    private <T> String consultar(String endereco, String valor, Class<T> classe, Integer tipoConversor){
        return this.consultar(endereco,valor, "",classe,tipoConversor);
    }

    private <T> String consultar(String endereco, String valor,String valorPlural,Class<T> classe, Integer tipoConversor){
        ConsultarAPI consultarAPI = new ConsultarAPI();
        Conversor conversor = new Conversor();
        var json = consultarAPI.consultarAPI(endereco);
        List<T> retorno = new ArrayList<>();
        if(tipoConversor == 0) {
            retorno = conversor.converterJsonToClass(json, classe);
        }else if(tipoConversor == 1){
            retorno = conversor.converterArrayJsonToClass(json,classe,valorPlural);
        }

        retorno.stream().forEach(System.out::println);

        final Scanner leitura = new Scanner(System.in);
        System.out.println("Informe o código da " + valor + " para consulta: ");
        String codigo = leitura.next();

        return codigo;
    }
}
