package steganography;

import java.awt.Color;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import toolbox.Configurations;

public class StegIn{
    
    public static void StegI(String[] cipher, Configurations config) throws IOException{
        
        final int SIZEIMG = config.getSIZEIMG();
        final String NAME_IMGOUT = config.getNAME_IMGOUT();
        
    	// Imagem de dimensao [SIZEIMG] x [SIZEIMG]
        String current = new java.io.File( "." ).getCanonicalPath();
    	BufferedImage imageIn = ImageIO.read(new File (current + "\\img\\img.bmp"));

        String[][] pixelRed   = new String[SIZEIMG][SIZEIMG];
        String[][] pixelBlue  = new String[SIZEIMG][SIZEIMG];
        String[][] pixelGreen = new String[SIZEIMG][SIZEIMG];

        // Cada matriz declarada acima recebera as componentes de cores de cada pixel (i,j) em suas posicoes (i,j)
    	for (int i = 0; i < SIZEIMG; i++)
            for (int j = 0; j < SIZEIMG; j++){
                
                int rgb = imageIn.getRGB(i, j);
                pixelRed[i][j]   = String.format("%8s", Integer.toBinaryString((rgb >> 16) & 0x000000FF)).replace(' ', '0');
                pixelGreen[i][j] = String.format("%8s", Integer.toBinaryString((rgb >> 8 ) & 0x000000FF)).replace(' ', '0');
                pixelBlue[i][j]  = String.format("%8s", Integer.toBinaryString((rgb      ) & 0x000000FF)).replace(' ', '0');
            }  

        // Essas varaiveis serao utilizadas para trabalhar com o bit ao inves de byte
        char[] charCipher, auxRed, auxGreen, auxBlue;
        byte[] byteCipher;
        String stringCipher;

        // Utilizado para saber quando a string cipher acabara
        int counter = 0;

        // Cipher sendo escondida nos bits menos significativos de cada pixel da imagem
        for (int i = 0; i < SIZEIMG; i++){
            for (int j = 0; j < SIZEIMG; j++, counter++){

                // Cada elemento de pixelRed(Green ou Blue) representa uma componente de um pixel da imagem
                // Cada elemento de auxRed(Green ou Blue) representa um bit da componente acima             
                auxRed   = pixelRed[i][j].toCharArray();
                auxGreen = pixelGreen[i][j].toCharArray();
                auxBlue  = pixelBlue[i][j].toCharArray();

                if (counter < cipher.length){

                    // Cada cipher[count] representa um caracter na mensagem cifrada
                    // StringCipher representa uma string desse binario
                    // Foi separado os elementos da string "stringCipher" no vetor de char "charCipher"

                    stringCipher    = String.format("%8s", Integer.toBinaryString(Integer.parseInt(cipher[counter], 10))).replace(' ', '0');                  
                    charCipher      = stringCipher.toCharArray();

                    auxRed[5]   = charCipher[0]; 
                    auxRed[6]   = charCipher[1]; 
                    auxRed[7]   = charCipher[2]; 
                    auxGreen[6] = charCipher[3]; 
                    auxGreen[7] = charCipher[4]; 
                    auxBlue[5]  = charCipher[5]; 
                    auxBlue[6]  = charCipher[6]; 
                    auxBlue[7]  = charCipher[7]; 
  
                }else{

                    Random generator = new Random();

                    // Coloca aleatorios entre 0 e 1 (inclusive) no restante da imagem
                    // Dessa forma nao ha como comparar com a imagem original e obter somente a informacao
                    auxRed[5]   = Character.forDigit(generator.nextInt(2), 10); 
                    auxRed[6]   = Character.forDigit(generator.nextInt(2), 10);  
                    auxRed[7]   = Character.forDigit(generator.nextInt(2), 10);  
                    auxGreen[6] = Character.forDigit(generator.nextInt(2), 10);  
                    auxGreen[7] = Character.forDigit(generator.nextInt(2), 10); 
                    auxBlue[5]  = Character.forDigit(generator.nextInt(2), 10);  
                    auxBlue[6]  = Character.forDigit(generator.nextInt(2), 10);  
                    auxBlue[7]  = Character.forDigit(generator.nextInt(2), 10); 


                }

                // Copia de volta para as matrizes de pixel
                pixelRed[i][j]   = String.copyValueOf(auxRed);
                pixelGreen[i][j] = String.copyValueOf(auxGreen);
                pixelBlue[i][j]  = String.copyValueOf(auxBlue);
                
            }
        }
                
        // Salvando as matrizes com as cores modificadas na imagem de saida
        for(int i = 0; i < SIZEIMG; i ++)
            for(int j = 0; j < SIZEIMG; j++){ 
                
                int red   = Integer.parseInt(pixelRed[i][j], 2);
                int green = Integer.parseInt(pixelGreen[i][j], 2);
                int blue  = Integer.parseInt(pixelBlue[i][j], 2);
                               
                Color auxColor = new Color(red, green, blue);                   
                int rgb = auxColor.getRGB(); 
                
                imageIn.setRGB(i, j, rgb);
                
            }

        // Salva numa imagem de saida
        ImageIO.write(imageIn, "BMP", new File (current + "\\out\\"+NAME_IMGOUT+".bmp"));
    }
}