package toolbox;

public class Maths {
    
    public static long estEuclides(long a, long b){
        
        long s = 0;
        long r = b;
        long old_s = 1;
        long old_r = a;
        
        while(r!=0){   
            long quociente = old_r/r;
            long aux;
            
            aux = r;
            r = old_r - quociente*r;
            old_r = aux;
            
            aux = s;
            s = old_s - quociente*s;
            old_s = aux;   
        }
        
        return old_s;
        
    } 
    
    public static long mdc(long a, long b) {
        if (b == 0)
            return a;
        else
            return mdc(b, a % b);
    }

    public static boolean isPrimo(long n){      
        if (n%2==0) return false;

        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }
}
