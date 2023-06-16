package reading2;

public class test1 {

    public static void main(String[] args) {
        
        String name = "Phillips Edwards William Dickson";
        String[] words = name.split(" ");
        char[] result = new char[4];
        
        for(int i=0; i < words.length; i++) {
            result[i] = words[i].charAt(0);
        }
        
        System.out.println(result);
        
        System.out.println(anagram( "parliament", "partial men" ));
        System.out.println(anagram( "goodbye", "hi" ));
    }
    
    public static boolean anagram(String w1, String w2) {
        
        w1 = w1.trim();
        w2 = w2.trim();
        
        CharSequence characters = w1.subSequence(0, w1.length());
        
        for(int i = 0; i < characters.length()-1; i++) {
            char t = characters.charAt(i);
            if (!w2.contains(Character.toString(t))) {
                return false;
            }
            
        }
        return true;
        
    }
    
}
