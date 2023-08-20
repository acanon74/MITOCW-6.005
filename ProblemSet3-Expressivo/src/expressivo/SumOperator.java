package expressivo;

/**
 * An immutable data type representing a sum operation. Following the Expression specification,
 * it allows for the use of numeric values, variables as well as other subclasses of Expression.
 */
public class SumOperator implements Expression {

    /**
     * Abstraction function:
     * Represents an addition operation regarding the left and right operands.
     * <p>
     * Representation invariant:
     * Left and right operands must be subclasses of the Expression interface.
     * <p>
     * Safety from rep exposure:
     * All fields are immutable.
     */

    public final Expression leftHand;
    public final Expression rightHand;
    public final String type;

    public SumOperator(Expression leftHand, Expression rightHand) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.type = "Sum";
        checkRep();
    }

    private void checkRep() {
        assert ( this.leftHand instanceof Expression && this.rightHand instanceof Expression);
    }


    @Override
    public String toString() {
        return "(" + leftHand.toString() + " + " + rightHand.toString() + ")";
    }

    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(!(obj instanceof Expression)){
            return false;
        }

        final SumOperator other = (SumOperator) obj;

        return (this.leftHand.equals(other.leftHand) && this.rightHand.equals(other.rightHand));
    }

    @Override
    public int hashCode() {
        //Due to the commutative property, we want to return the same hash for two objects
        //with the same operands, regardless of the position of these (l or r).
        //Adding both hashes allows us to do that.
        return leftHand.hashCode() + rightHand.hashCode();
    }

}
