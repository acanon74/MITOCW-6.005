package tutorial;

public class Baby {

    //Having a main method means the class can be run.
    //Class names are Capitalized
    //1 Class = 1 file
    
    //fields
    String name;
    boolean isMale;
    double weight = 5.0;
    double decibels;
    int numPoops = 0;
    Baby[] siblings;
    
    //static works just as in cpp, for fields and methods.
    static int numBabiesMade = 0;
    
    Baby() {
        numBabiesMade += 1;
    }
    
    Baby(String myname, boolean maleBaby) {
        name = myname;
        isMale = maleBaby;
    }
    
    //methods
    void sayHi() {
        System.out.println("Hi, my name is " + name);
    }
    
    void eat(double foodWeight) {
        if (foodWeight >= 0 && foodWeight < weight) {
            weight = weight + foodWeight;
        }
    }
    
    void poop() {
        numPoops += 1;
        System.out.println("Blabblabaldsab");
    }
}

