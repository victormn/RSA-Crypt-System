package encryption;

import java.io.*;
import java.math.*;
import java.util.*;
import static toolbox.Converters.*;
import static toolbox.Operations.*;

public class CriptIn {
    
    public static String[] encryption(String path, String key) throws IOException{
        int[] cipher = readPlainText(path);
        ArrayList<Long> keys = new ArrayList<>(2);
        keys = getKeys(key);
        return rsaIn(cipher, keys.get(0), keys.get(1));
    }
    
    // Le o arquivo txt que sera criptografado e armazena seu texto em um vetor de inteiros 
    public static int[] readPlainText(String path) throws IOException{
    
        int[] intPlainText = new int[1];
        intPlainText[0] = -1;    
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));

        StringBuilder stringBuilder = new StringBuilder();   
        String line = reader.readLine();
        
        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
            line = reader.readLine();
        }
        String plainText = stringBuilder.toString();     

        
        // Converte a string que armazena o texto em um vetor de char
        char[] charPlainText = plainText.toCharArray();
    
        // Armazena o valor decimal de cada caractere
        for(int i=0; i<plainText.length(); i++){
            intPlainText = addArrayInt(intPlainText, String.valueOf(plainText).codePointAt(i));
        }    
        reader.close();  

        return intPlainText;
    }
    
    // Criptografa a mensagem contida no plainText com a chave publica
    //representada por seus parametros 'e' e 'n'
    public static String[] rsaIn (int[] plainText, long e, long n) throws IOException{
                
        // Transforma os parametros da chave publica para BigInteger
        BigInteger bigE = toBigInteger(e);
        BigInteger bigN = toBigInteger(n);
             
        String[] dataString = new String[plainText.length*19]; // Variavel que ira conter os dados
        
        String[] result; // Variavel que ira conter o tamanho e os dados
        String auxString;
        
        // Converte cada elemento de plainText em um numero cifrado, segundo o algoritmo RSA
        // Esse numero cifrado eh separado em 19 numeros menores, onde cada um pode ser representado por 8bits
        for (int i = 0, j = 0; i < plainText.length; i++) {
            
            BigInteger ch = toBigInteger(plainText[i]);
            
            auxString = (ch.modPow(bigE, bigN)).toString();

            byte[] auxByte = auxString.getBytes();

            for (int k = 0; k < auxByte.length; k++, j++) {
                dataString[j] = Integer.toString(auxByte[k]);          
            }
            for (int k = 0; k < 19-auxByte.length; k++, j++) {
                dataString[j] =  "0";
            }
        
        }
     
        result = new String[dataString.length+3]; 
        
        // Converte um numero de 24 bits em 3 numeros de 8 bits e
        //adiciona no comeco do vetor
        String[] size = new String[3];
        size = to3BinaryString(dataString.length+3);
        
        for (int i = 0; i < 3; i++) {
            result[i] = size[i];
        }
        for (int i = 3, j = 0; i < dataString.length+3; i++, j++) {
            result[i] = dataString[j];
        }
    
        return result;       
    }
    
}
