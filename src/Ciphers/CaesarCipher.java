package Ciphers;

import java.util.Scanner;

/**
*Caesar cipher
*Implements single encoding of entered text, can
*output solution to encrypted text given the key
* 
* @author Joe Kvedaras
**/

public class CaesarCipher{
    
    private boolean encode;
    
    public static void main(String[] args){
        
        //initialize scanner
        Scanner sc = new Scanner(System.in);
        
        while(true){
            System.out.println("Do you want to encrypt or decrypt string? Enter quit to exit");
            String option = sc.nextLine();
        
            if (option.equalsIgnoreCase("encrypt")){
                
                CaesarCipher cipher = new CaesarCipher(true);
                cipher.helper(sc,"encrypted");
                
            }
            else if (option.equalsIgnoreCase("decrypt")){
                
                CaesarCipher cipher = new CaesarCipher(false);
            	cipher.helper(sc,"decrypted");
                
            }
            else if (option.equalsIgnoreCase("quit")){
                break;
            }
            else{
                 System.out.println("Please enter valid input");
            }
            
            
        }
        //Close Scanner
        sc.close();
    }
    
    /**
     * Constructor. Encode is true for encrypting, false for decryption
     * @param encode
     */
    public CaesarCipher(boolean encode){
    	this.encode = encode;
    }
    
    
    private void helper (Scanner in, String value){
        System.out.println("Enter text to be " + value + ":");
        String text = in.nextLine();
        char[] characters = text.toCharArray();
        
        System.out.println("Enter an integer for cypher: ");
        int n= in.nextInt();
        
        //TODO: Need to check integer input. Can be positive or negative. Simplify input if over 94        
        
        encodeText(characters, n, encode);
         
    }
    

    /**
     * Handles encoding and decoding operation with boolean control. True for encode, false for decode
     * @param toBeEncoded
     * @param move
     * @param encode
     */
    private void encodeText(char[] toBeEncoded, int move, boolean encode){
        
        for (int i = 0; i < toBeEncoded.length; i++){
            //get character at position
            int asciiChar = toBeEncoded[i];
            //Make sure asciiChar is between 32 - 126. Those are the only valid characters I want to accept
            if ((asciiChar >= 32) && (asciiChar <= 126)){
                
                //add if encrypting
            	if(encode){
            		asciiChar += move;
            	}
            	//subtract if decrypting
            	else{
            		asciiChar -= move;
            	}
                
                //System.out.println(asciiChar);
                
                //if over 126, (asciiChar - 126) + 32. Difference will always be at least 1, thats why i can add from 31
                if (asciiChar > 126){
                    asciiChar = (asciiChar - 126) + 31;
                }
                //if under 32, subtract the difference from 32.Difference will always be at least 1 thats 
                //why I subtract from 127
                if (asciiChar < 32){
                    asciiChar = 127 - (32 - asciiChar);
                }
                
                //add that value to char array
                toBeEncoded[i] = (char) asciiChar;

            }
            else {
                System.out.println("Not a valid character");
            }
        }
        printCode(toBeEncoded);
    }
        
    
    private void printCode(char[] out){
        for (int i=0; i<out.length;i++){
            System.out.print(out[i]);
        }
        System.out.println();
    }
    
}



