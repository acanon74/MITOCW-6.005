package tutorial;

public class lecture6 {

    public static void main(String[] args) {
        //Assertions
        //If true nothing happens
        //If false, program crashes with an error

    }
    
    void printDifferenceFromFastest(int[] marathonTimes) {
        int fastestTime = findMinimum(marathonTimes);
        
        for (int time : marathonTimes) {
            int difference = time - fastestTime;
            assert difference >= 0;
            System.out.println("Difference: " + difference);
            
        }
    }
    
}
