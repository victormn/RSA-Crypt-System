package keys;

import java.util.*;
import static toolbox.Maths.*;

public class Keys {
    
    private static long publicKey_E;
    private static long publicKey_N;
    private static long privateKey_D;
    private static long privateKey_N;
    
    public long getPublicKeyE(){
        return publicKey_E;
    }
    public long getPublicKeyN(){
        return publicKey_N;
    }
    public long getPrivateKeyD(){
        return privateKey_D;
    }
    public long getPrivateKeyN(){
        return privateKey_N;
    }
        
    public Keys(){
        keyGenerator();
    }
    
    public static void keyGenerator(){
        // gerando p e q, grandes e primos
        long p,q;

        Random generator = new Random();
        
        p = generator.nextInt(1073741824) + 1073741824;
        while(!(isPrimo(p))){
            p++;
        }
        
        q = generator.nextInt(1073741824) + 1073741824;
        while(!(isPrimo(q))){
            q++;
        }
                
        // gerando n e z
        long n = p*q;
        long z = (p-1)*(q-1);
        
        long d, e;
                   
        // gerando um número e<n, sendo e e z primos entre si   
        e = 0;

        for (long i = 3; i < z; i+=2) {
            if (mdc(i, z) == 1){
                e = i;
                break;
            }      
        }

        // inversor modular para calcular d
        d = estEuclides(e, z);
        
        // se d for negativo, calcula de novo, ate o mesmo ser positivo
        if(d>0){
            
            publicKey_E = e;
            publicKey_N = n;
            privateKey_D = d;
            privateKey_N = n;
            
        }else keyGenerator();
    }
}
