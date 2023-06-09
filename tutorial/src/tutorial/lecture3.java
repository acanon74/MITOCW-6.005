package tutorial;

public class lecture3 {
    public static void main(String[] arguments) {
        
        int i = 0;
        while (i < 3) {
            System.out.println("Rule #" + i);
            i += 1;
        }
        
        for (int j = 0; j < 30; j++) {
            System.out.println("Rule2 #" + j);
            if (j == 15) {
                break;
            }
        }
        for (int q = 0; q < 10; q++) {
            System.out.println("Rule2 #" + q);
            if (q == 5) {
                continue;
            }
        }
        
        //Arrays
        
        int[] values = new int[5];
        //int[] values = {1,2,3,4,5};
        //int[][] values;
        //values.length
        for (int w = 0; w < 4; w++) {
            System.out.println(values[w]);
        }
        System.out.println("Args:" + arguments.length);
        
        for (int k = 0; k < values.length; k++) {
            values[k] = k;
            int y = values[k] * values[k];
            System.out.println(y);
        }
        
        int r = 0;
        while (r < values.length) {
            values[r] = r;
            int y = values[r] * values[r];
            System.out.println(y);
            r++;
        }
        
    }
}
