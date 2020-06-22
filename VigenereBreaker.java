import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    private int[] validKey;
    private char commonchar;
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder answer = new StringBuilder();
        for (int i = whichSlice; i<message.length(); i += totalSlices) {
         answer.append(message.charAt(i));
        
        }  
         return answer.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker ccr = new CaesarCracker(mostCommon);
        String[] encr = new String[klength];
        for (int i=0; i<klength; i++) {
        String encryptedpart =  sliceString(encrypted,i,klength);
        encr[i] = encryptedpart;
        int keys = ccr.getKey(encryptedpart);
        key[i] = keys;
        }
        return key;
    }

    
    
    
    
    
    public HashSet<String> readDictionary (FileResource fr) {
        HashSet<String> hs = new HashSet<String>();
        for (String line : fr.lines()) {
            line = line.toLowerCase();
            hs.add(line);
        }
        return hs;
    }
    
    public int countWords (String message, HashSet<String> dictionnary) {
        String[] messageSplit = message.split("\\W+");
        int commonwords = 0;
        for (int i=0; i<messageSplit.length; i++) {
            String word = messageSplit[i].toLowerCase();
            if(dictionnary.contains(word)) {
                commonwords ++;
            }
        }
        return commonwords;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionnary) {
        String decrypt = "";
        int words = 0;
        
        for (int i=1; i<100; i++) {
        char ch = mostCommonCharIn(dictionnary);
        int[] keys = tryKeyLength(encrypted, i, ch);
        VigenereCipher vc = new VigenereCipher(keys);
        String decrypted = vc.decrypt(encrypted);
        int curwords = countWords(decrypted,dictionnary);
        if (curwords>words) {
            words = curwords;
            decrypt = decrypted;
            validKey = keys;
            commonchar = ch;
        }
        }
        //System.out.println(" The Decrypted message for max words of " + words + " is for keys");
        //System.out.println(Arrays.toString(validKey));
        //System.out.println("Key length is " + validKey.length);
        return decrypt;
    }
    
    
    
    
    public int breakForLanguageDenis(String encrypted, HashSet<String> dictionnary) {
        String decrypt = "";
        int words = 0;
        
        for (int i=1; i<100; i++) {
        int[] keys = tryKeyLength(encrypted, i,'e');
        VigenereCipher vc = new VigenereCipher(keys);
        String decrypted = vc.decrypt(encrypted);
        int curwords = countWords(decrypted,dictionnary);
        if (curwords>words) {
            words = curwords;
            decrypt = decrypted;
            validKey = keys;
        }
        }
        //System.out.println(" The Decrypted message for max words of " + words + " is for keys");
        //System.out.println(Arrays.toString(validKey));
        //System.out.println("Key length is " + validKey.length);
        return words;
    }
    
    
    
    
    
    
    
    
    public char mostCommonCharIn (HashSet<String> dictionnary) {
        HashMap<Character, Integer> characters = new HashMap<Character, Integer>();
        char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
                        'p','q','r','s','t','u','v','w','x','y','z'};
        for( int i=0; i<chars.length; i++){
            characters.put(chars[i],0);
        for (String word : dictionnary) {
            for(char s : characters.keySet()){
               if (word.contains(Character.toString(s))){
                   characters.put(s, characters.get(s)+1);
                }
            }
        }
        }
        int maxValue = 0;
        for(char s : characters.keySet()){
            int value = characters.get(s);
            if (value > maxValue){
                maxValue = value;
                
            }
        }
        for(char s : characters.keySet()){
            if (characters.get(s) == maxValue){
                return s;
            }
        }
        return 'a';
        
    }
    
    public void breakForAllLangs (String encrypted, HashMap<String,HashSet<String>> languages) {
        int maxwords = 0;
        String decrypted = "";
        String language = "";
        for (String s : languages.keySet()) {
        int words = breakForLanguageDenis(encrypted, languages.get(s)); 
        if (words>maxwords) {
            maxwords = words;
            decrypted = breakForLanguage(encrypted, languages.get(s));
            language = s;
        }
       }
       System.out.println(" Language is: " + language + "  for maxwords " + maxwords);
       System.out.println(" Valid key is : " + Arrays.toString(validKey));
       System.out.println("Key length is " + validKey.length);
       System.out.println("Common char is: " + commonchar);
       System.out.print(decrypted);
    }
    
    
    public void breakVigenere2 () {
     FileResource f = new FileResource();
       String textToStr = f.asString();
       /*I think it is allright to set the keylength manually to 5 as the exercise tells it.
        * Our next exercise is about gessing the length of the key anyway. 
        */
       int[] applykey = tryKeyLength(textToStr,38,'e');
       //int[] applykey = {19, 13, 10, 11, 13, 14, 5, 6, 25, 20, 18, 20, 12, 15, 9, 19, 0, 13, 15, 4, 15, 20, 15, 19, 0, 25, 17, 15, 17, 8, 16, 24, 19, 11, 9, 13, 6, 20, 8, 8, 21, 14, 23, 16, 6, 23, 21, 4, 3, 12, 16, 16, 22, 6, 10, 21, 20};
       VigenereCipher vignere = new VigenereCipher(applykey);
       System.out.println(vignere.decrypt(textToStr));
    }
   
    public void breakVigenere3 () {
    FileResource text = new FileResource();
    HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
    FileResource English = new FileResource("dictionaries/English");
        languages.put("English", readDictionary(English));
        FileResource Danish = new FileResource("dictionaries/Danish");
        languages.put("Danish", readDictionary(Danish));
        FileResource Dutch = new FileResource("dictionaries/Dutch");
        languages.put("Dutch", readDictionary(Dutch));
        FileResource French = new FileResource("dictionaries/French");
        languages.put("French", readDictionary(French));
        FileResource German = new FileResource("dictionaries/German");
        languages.put("German", readDictionary(German));
        FileResource Italian = new FileResource("dictionaries/Italian");
        languages.put("Italian", readDictionary(Italian));
        FileResource Portuguese = new FileResource("dictionaries/Portuguese");
        languages.put("Portuguese", readDictionary(Portuguese));
        FileResource Spanish = new FileResource("dictionaries/Spanish");
        languages.put("Spanish", readDictionary(Spanish));
    
    
    String testingFile = text.asString();
    breakForAllLangs(testingFile, languages);
    //System.out.print(perfectdecryption);    
    //VigenereCipher vc = new VigenereCipher(a);
    //String decrypted = vc.decrypt(message);
    //System.out.println(decrypted);
    }
    
    public void breakVigenere () {
    FileResource fr = new FileResource();
    String message = fr.asString();
    //String message = "Muo Gffmdxq au Aazaii, Irherv ed Onasuzs";

    //String message = "Hhdiu LVXNEW uxh WKWVCEW, krg k wbbsqa si MmwcjiqmFcdmcyxw:  Zy alfv ey wdnswicoh; viw ew vo vudmvzsig.Fuodyv:";
    FileResource dicti = new FileResource("dictionaries/English");
    HashSet<String> dictionary = readDictionary(dicti);
    String perfectdecryption = breakForLanguage(message,dictionary);
    //System.out.print(perfectdecryption);    
    //VigenereCipher vc = new VigenereCipher(a);
    //String decrypted = vc.decrypt(message);
    //System.out.println(decrypted);
    }
    
    public void tests() {
        //System.out.println(sliceString("abcdefghijklm", 0, 3));
        //breakVigenere2();
        breakVigenere3();
        
    }
}
