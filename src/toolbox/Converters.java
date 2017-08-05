package toolbox;

import java.math.BigInteger;
import java.util.*;

public class Converters {
    
    // Retorna um BigInteger para dado 'long'
    public static BigInteger toBigInteger (long a){
        
        String string = String.valueOf(a);
        BigInteger bigI = new BigInteger(string);
        
        return bigI;
    }
    
    // Retorna um BigInteger para dado 'int'
    public static BigInteger toBigInteger (int a){
        
        String string = Integer.toString(a);
        BigInteger bigI = new BigInteger(string);
        
        return bigI;
    }
    
    // Retorna um BigInteger para dado 'string'
    public static BigInteger toBigInteger (String a){

        BigInteger bigI = new BigInteger(a);

        return bigI;
    }
    
    // Converte um numero binario, representado por uma string, 
    //em seu correspondente decimal
    public static String toDecimal(String string) { 

        return Integer.toString(Integer.parseInt(string, 2));

    }   
    
    // Converte um numero de 24 bits em 3 numeros de 8 bits
    public static String[] to3BinaryString (int n){
        
        String [] string = new String[3];
        String aux;
        
        aux = String.format("%24s", Integer.toBinaryString(n)).replace(' ', '0');
        
        string[0] = Integer.toString(Integer.parseInt(aux.substring(0,8), 2));
        string[1] = Integer.toString(Integer.parseInt(aux.substring(8,16), 2));
        string[2] = Integer.toString(Integer.parseInt(aux.substring(16), 2));
        
        return string;
    }
    
    public static String[] string19BytesToString1Byte(String[] string){
        
        int resultSize = string.length/19;
        int currentCounter = 0;
        
        String[] result = new String[resultSize];
        
        for (int i = 0; i < resultSize; i++) {

            String[] currentString = new String[19];
            
            for (int j = 0; j < 19; currentCounter++, j++) {
                currentString[j] = string[currentCounter];
            }

            int size = 0;
            for (int j = 0; j < 19; j++) {
                if(Integer.parseInt(currentString[j]) == 0){
                    size = j;
                    break;
                }    
            }
        
            String[] auxString = new String[size];
        
            for (int j = 0; j < size; j++) {
                auxString[j] = currentString[j];
            }
           
            Byte[] auxByte = new Byte[size]; // Variavel auxiliar
            
            for (int j = 0; j < size; j++){
                auxByte[j] = (byte)Integer.parseInt(auxString[j]);
            }

            byte[] bytes = new byte [size];

            int j = 0;

            for(Byte b: auxByte){
                bytes[j++] = b.byteValue();    
            }
            
            result[i] = new String(bytes);
        }
        
        return result;
    }
}
