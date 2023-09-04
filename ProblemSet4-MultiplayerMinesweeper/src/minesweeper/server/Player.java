package minesweeper.server;
import java.util.Objects;

/**
 * Player stores the player's information. It stores the player's name and score.
 * We implement equals() and hashCode() computing using only the name field.
 */

public class Player {

    /**
     * Abstraction function:
     * It relates a name and a score.
     *
     * Rep invariant:
     * Name is set at the beginning of the connection and is immutable. Cannot be null.
     * Score can be any integer.
     *
     * Safety from rep exposure:
     * Score is private and must be accessed through getters and setters.
     * String is an immutable String.
     *
     * Thread safety:
     * Getters and setters for the score field are synchronized.
     * String is immutable and therefore doesn't pose a risk of interleaving.
     * Reads of the object through equals() and hashCode() don't need
     * to be synchronized, since they only compute using the field name, which
     * we defined thread safe.
     */

    private int score;
    public final String name;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        checkRep();
    }

    private void checkRep() {
        assert this.name != null;
    }

    public synchronized int getScore() {
        return this.score;
    }
    public synchronized void setScore(int score) {
        this.score = score;
    }

    /**
     * Override of equals(), checks for equality of objects and then compares the name field.
     *
     * @param obj Another Player to compared with
     * @return true if object are the same type and have the same name, otherwise false.
     */
    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(obj.getClass() != this.getClass()){
            return false;
        }

        final Player other = (Player) obj;

        return (Objects.equals(this.name, other.name));
    }

    /**
     * Override of hashCode() to be able to use Player with Collections which implement hash encoding.
     * <p>
     *
     * @return hash of the field name using the generic hash function.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
