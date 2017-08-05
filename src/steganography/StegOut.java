package steganography;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import toolbox.Configurations;
import static toolbox.Converters.*;

public class StegOut {
       
    public static String[] StegO(File img, Configurations config) throws IOException{
    
        final int SIZEIMG = config.getSIZEIMG();
        
        BufferedImage imageIn = ImageIO.read(img);
                
        String[][] pixelRed   = new String[SIZEIMG][SIZEIMG];
        String[][] pixelBlue  = new String[SIZEIMG][SIZEIMG];
        String[][] pixelGreen = new String[SIZEIMG][SIZEIMG];

        // Cada matriz declarada acima recebera as componentes de cores de cada pixel (i,j) em suas posicoes (i,j)
        for(int i = 0; i < SIZEIMG; i ++)
            for(int j = 0; j < SIZEIMG; j++){
                
                int rgb = imageIn.getRGB(i, j);
                pixelRed[i][j]   = String.format("%8s", Integer.toBinaryString((rgb >> 16) & 0x000000FF)).replace(' ', '0');
                pixelGreen[i][j]  = String.format("%8s", Integer.toBinaryString((rgb >> 8 ) & 0x000000FF)).replace(' ', '0');
                pixelBlue[i][j] = String.format("%8s", Integer.toBinaryString((rgb      ) & 0x000000FF)).replace(' ', '0');
            }
        
        char[] data = new char[8];
        char[] auxRed, auxGreen, auxBlue;
        
        // Recuperando o tamanho
        // Formato do tamanho:
        // [0][0] = bits mais significativos; [0][1] = bits centrais; [0][2] = bits menos significativos
        String sSize = "";
        
        for (int i = 0; i < 3; i++) {
            auxRed   = pixelRed[0][i].toCharArray();
            auxGreen = pixelGreen[0][i].toCharArray();
            auxBlue  = pixelBlue[0][i].toCharArray();

            data[0] = auxRed[5];
            data[1] = auxRed[6];
            data[2] = auxRed[7];
            data[3] = auxGreen[6];
            data[4] = auxGreen[7];
            data[5] = auxBlue[5];
            data[6] = auxBlue[6];
            data[7] = auxBlue[7];

            sSize += String.copyValueOf(data);
        }
              
        int size = (int)Integer.parseInt(sSize, 2);      
      
        // Recuperando restante dos dados
        String[] stringImg = new String[size-3];
        int counter = 0;
        
        for (int j = 0; j < SIZEIMG; j++){
            for (int i = 0; i < SIZEIMG; i++, counter++){

                if (counter > 2 && counter < size){
                
                    auxRed   = pixelRed[j][i].toCharArray();
                    auxGreen = pixelGreen[j][i].toCharArray();
                    auxBlue  = pixelBlue[j][i].toCharArray();

                    data[0] = auxRed[5];
                    data[1] = auxRed[6];
                    data[2] = auxRed[7];
                    data[3] = auxGreen[6];
                    data[4] = auxGreen[7];
                    data[5] = auxBlue[5];
                    data[6] = auxBlue[6];
                    data[7] = auxBlue[7];
                
                    stringImg[counter-3] = toDecimal(String.copyValueOf(data));
                }
            }
            if (counter == size) break;
        }
    
        // Retorna a mensagem original
        return stringImg;
    }
}
