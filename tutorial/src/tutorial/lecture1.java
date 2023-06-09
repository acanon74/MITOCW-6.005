package tutorial;


public class lecture1 {
    public static void main(String[] arguments) {
        double Gravity = -9.81;
        double InitialVelocity = 3.0;
        double InitialPosition = .0;
        
        double Time = 1.0;
        
        double Position = (0.5 * Gravity * (Time*Time))
                + (InitialVelocity * Time)
                + InitialPosition;
        System.out.println("Final Position: " + Position + " meters");

    }
    

}
