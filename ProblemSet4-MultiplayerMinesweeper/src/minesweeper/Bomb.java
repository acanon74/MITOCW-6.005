//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package minesweeper;
//TODO change docs to allow for general L, since this was adapted from Strings only
/**
 * This data structure stores String a and b, which in context should relate.
 * <p>
 * We implement equals() to compare the source and target of two Bomb object.
 * Using the String nature of the data, we implement hashCode() to produce a string hash with the form "source target".
 *
 */

public class Bomb {
    final int x;
    final int y;

    public boolean flagged;

    /**
     * Abstraction function:
     * Represents a bomb from the Minesweeper game. It stores x and y coordinates as well as a flagged state.
     * <p>
     * Representation invariant:
     * <p>
     * Safety from Rep exposure:
     * x, y are final.
     * x, y have package level exposure.
     */

    public Bomb(int a, int b) {
        this.x = a;
        this.y = b;
        this.flagged = false;
    }

    /**
     * Override of equals(), checks for equality of objects and then compares x, y fields in both objects.
     *
     * @param obj Another Bomb to compared with
     * @return true if object are the same type and have the same fields, otherwise false.
     */
    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(obj.getClass() != this.getClass()){
            return false;
        }

        final Bomb other = (Bomb) obj;

        return ((this.x == other.x) && (this.y == other.y));
    }

    /**
     * Override of hashCode() to be able to use Bomb with Collections which implement hash encoding.
     * <p>
     *
     * @return hash of the union (x + " " + y) using the generic hash function.
     */
    @Override
    public int hashCode() {
        String beforeHash = (x + "-" + y);
        return beforeHash.hashCode();
    }
}
