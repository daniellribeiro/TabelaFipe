package Principal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Modelo.Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Conversor {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> List<T> converterJsonToClass(String json, Class<T> classe) {
        try {
            List<T> dados = new ArrayList<T>();
            JsonNode jsonNode = mapper.readTree(json);
            for (JsonNode node : jsonNode) {
                T dado = (T) mapper.readValue(node.toString(), classe);
                dados.add(dado);
            }
            return dados;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> converterArrayJsonToClass(String json, Class<T> classe,String palavraChave) {
        try {
            List<T> dados = new ArrayList<T>();
            JsonNode jsonNode = mapper.readTree(json).at("/" + palavraChave);
            for (JsonNode node : jsonNode) {
                T dado = (T) mapper.readValue(node.toString(), classe);
                dados.add(dado);
            }
            return dados;
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public <T> T converterJson(String json, Class<T> classe){
        try {
            return (T) mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
