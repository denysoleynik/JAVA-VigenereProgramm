import java.util.*;
import edu.duke.*;
/**
 * Write a description of class tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tester {
    public void testCeasarCipher() {
        CaesarCipher cc = new CaesarCipher(8);
        FileResource fr = new FileResource();
        String message = fr.asString();
        System.out.println(cc.encrypt(message));
    }
    
    
    public void testCaesarCracker() {
        CaesarCracker cc = new CaesarCracker();
        FileResource fr = new FileResource();
        String message = fr.asString();
        System.out.println(cc.decrypt(message));
    }
    
    public void testVigenereCipher() {
        int[] key = {19, 13, 10, 11, 13, 14, 5, 6, 25, 20, 18, 20, 12, 15, 9, 19, 0, 13, 15, 4, 15, 20, 15, 19, 0, 25, 17, 15, 17, 8, 16, 24, 19, 11, 9, 13, 6, 20, 8, 8, 21, 14, 23, 16, 6, 23, 21, 4, 3, 12, 16, 16, 22, 6, 10, 21, 20};
        VigenereCipher vc = new VigenereCipher(key);
        FileResource fr = new FileResource();
        String message = fr.asString();
        System.out.println(vc.encrypt(message));
        System.out.println(vc.decrypt(vc.encrypt(message)));
    }
    
    
    public void testVigenereBreaker() {
        VigenereBreaker cc = new VigenereBreaker();
        FileResource fr = new FileResource();
        String message = fr.asString();
        int[] a = cc.tryKeyLength(message,4,'e');
        //System.out.println(a);
        String output = Arrays.toString(a);
        System.out.println(output);
    }
}
