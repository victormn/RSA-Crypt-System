package encryption;

import java.io.*;
import java.math.*;
import java.util.*;
import toolbox.Configurations;
import static toolbox.Converters.*;
import static toolbox.Operations.*;

public class CriptOut {
    
    public static void decryption(String[] string, String key, Configurations config) throws IOException{
        ArrayList<Long> keys = new ArrayList<>(2);
        keys = getKeys(key);
        int[] cipher = rsaOut(string, keys.get(0), keys.get(1));
        
        writePlainText(cipher, config);
    }
    
    // Decripta a mensagem contida em string com a chave privada
    //representada por seus parametros 'd' e 'n'
    public static int[] rsaOut (String [] string, long d, long n) throws FileNotFoundException, UnsupportedEncodingException, IOException{
     
        // O tamanho do resultado sera 19 vezes
        //menor que a entrada
        int resultSize = string.length/19;
    
        // Transforma os parametros da chave privada para BigInteger
        BigInteger bigD = toBigInteger(d);
        BigInteger bigN = toBigInteger(n);
   
        // Converte os 19 numeros de 8 bits para o numero original
        //do texto cifrado
        String[] result = new String[resultSize]; 
        result = string19BytesToString1Byte(string);
      
        // Converte o texto cifrado na mensagem original
        for (int i = 0, j = 0, k = 0; i < resultSize; i++, j++) {
            BigInteger ch = toBigInteger(result[i]);
            result[i] = (ch.modPow(bigD, bigN)).toString(); 
        
        }

        // Converte para um vetor de inteiros
        int[] resultInt = new int[result.length];
        for (int i = 0; i < result.length; i++) {
            resultInt[i] = Integer.parseInt(result[i]);
        }
    
        return resultInt;
    }
    
    public static void writePlainText(int[] intPlainText, Configurations config) throws IOException{
    
        String current = new java.io.File( "." ).getCanonicalPath();
        final String NAME_MSNOUT = config.getNAME_MSNOUT();
        
        // Arquivo em que sera armazenada a mensagem decriptografada
        File plainText = new File(current + "\\out\\"+NAME_MSNOUT+".txt");
        FileWriter fileWriter = new FileWriter(plainText, false);        
        PrintWriter printWriter = new PrintWriter(fileWriter);
        
        char[] charPlainText = new char[intPlainText.length];
                
        for(int i=0; i<intPlainText.length; i++){
            charPlainText[i] = (char)intPlainText[i];
        }
        
        // Escrevendo a mensagem decriptografada no arquivo
        String message = new String(charPlainText);               
        printWriter.println(message);
        
        printWriter.flush();
        printWriter.close();
    }
    
}
