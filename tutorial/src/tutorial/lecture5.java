package tutorial;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;

public class lecture5 {

    public static void main(String[] args) {
        
        
        //Classic arrays
        Book b = new Book("The Foundation", 1999);
        Book[] books = new Book[10];
        int nextIndex = 0;
        books[nextIndex] = b;
        nextIndex = nextIndex + 1;
        
        //ArrayList
        //modifiable list
        
        ArrayList<Book> books = 
                new ArrayList<Book>();
        
        books.add(b);
        
        
        //Sets
        //Only one copy of each object, no array index
        //TreeSet -> Sorted low to high
        //HashSet Unordered pseudo-random
        TreeSet<String> strings = new TreeSet<String>();
        strings.add("Evan");
        strings.add("Eugene");
        strings.add("Adam");
        
        strings.size();
        strings.first();
        strings.last();
        
        strings.remove("Adam");
        
        for (String s : strings) {
            System.out.println(s);
        }
        
        //Maps
        //Stores a (key, value) pair of objects
        //TreeMap -> sorted
        //HashMap -> Unordered pseudo-random
        
        HashMap<String, String> strings = new HashMap<String, String>();

        strings.put("Evan", "1@gmail.com");
        strings.put("Eugene", "2@gmail.com");
        strings.put("Adam", "3@gmail.com");
        
        strings.size();
        
        strings.remove("Evan");
        
        strings.get("Eugene");
        
        
        for (String s : strings.keySet()) {
            System.out.println(s);
        }
        for (String s : strings.values()) {
            System.out.println(s);
        }
        for (Map.Entry<String, String> pairs : strings.entrySet()) {
            System.out.println(pairs);
        }
        
    }
    
}
//To use treeset and treemap read about 
//Comparable interfaces

//To use hashset and hashmap read about
// read about equals and hashcode methods


