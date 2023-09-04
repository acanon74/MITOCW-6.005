package minesweeper;
/**
 * This data structure stores String a and b, which in context should relate.
 * <p>
 * We implement equals() to compare the source and target of two Bomb object.
 * Using the String nature of the data, we implement hashCode() to produce a string hash with the form "source target".
 */

public class Bomb {
    private final int x;
    private final int y;
    private boolean flagged;

    /**
     * Abstraction function:
     * Represents a bomb from the Minesweeper game. It stores x and y coordinates as well as a flagged state.
     * <p>
     * Representation invariant:
     * x, y must be coordinates inside or on a boundary of the board.
     * <p>
     * Safety from Rep exposure:
     * x, y are final and private.
     * flagged is private and must be accessed through getters and setters.
     *
     * Thread safety:
     * x, y are final and therefore don't pose a risk of interleaving.
     * getters and setters for the flagged field are synchronized.
     * Reads of the object through equals() and hashCode() don't need
     * to be synchronized, since they only compute using the fields x, y, which
     * we defined thread safe.
     * toString() must be synchronized since it computes using the flagged method, which is immutable to other threads.
     */

    public Bomb(int a, int b) {
        this.x = a;
        this.y = b;
        this.flagged = false;
    }

    public synchronized void setFlag(boolean state) {
        this.flagged = state;
    }
    public synchronized boolean getFlag() {
        return this.flagged;
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
    /**
     * Override of the toString method, this returns a String containing the object's fields inside parenthesis.
     * @return A String with the object's fields formatted onto it.
     */
    @Override
    public synchronized String toString() {
        return "(" + x + ", " + y + ", " + flagged +")";
    }
}
