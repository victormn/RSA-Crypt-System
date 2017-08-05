package toolbox;

import java.util.*;

public class Operations {
        
    // Adiciona um elemento em um vetor de int
    public static int[] addArrayInt(int[] array, int element){
        
        if(array[0] == -1){
            array[0] = element;
            return array;
        }else{   
            array  = Arrays.copyOf(array, array.length + 1);
            array[array.length - 1] = element;
            return array;
        }
    }
        
    // Retorna os parametros 'e' e 'n' da chave publica dada como entrada
    //(uma unica string com o caracter X como separador)
    public static ArrayList<Long> getKeys(String string){
        
        ArrayList<Long> keys = new ArrayList<>(2);
        
        String[] keysSplit = string.split("X");
        
        keys.add(0, Long.parseLong(keysSplit[0]));
        keys.add(1, Long.parseLong(keysSplit[1]));
        
        return keys;
    }
}
