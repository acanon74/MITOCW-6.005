package expressivo;

/**
 * An immutable data type representing a number, following the Expression specification:
 * - non-negative integers and floating-point numbers.
 */
public class NumberExpression implements Expression {

    /**
     * Abstraction function:
     * Represents a floating point number which is non-negative.
     * <p>
     * Representation invariant:
     * The number to be represents must be >= 0.
     * <p>
     * Safety from rep exposure:
     * All fields are immutable.
     */

    //TODO what happens with getValue? Does our implementation of Expression requires us to use it?
    public final double value;
    public final String type;


    public NumberExpression(double value) {
        this.value = value;
        this.type = "number";
        checkRep();
    }

    private void checkRep() {
        assert this.value >= 0;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(!(obj instanceof Expression)){
            return false;
        }

        final NumberExpression other = (NumberExpression) obj;

        return this.getValue() == other.getValue();
    }

    @Override
    public int hashCode() {
        return String.valueOf(this.value).hashCode();
    }

    public double getValue() {
        return this.value;
    }
}
