package expressivo;

import java.util.Objects;

/**
 * An immutable data type representing a variable. Following the Expression specification,
 * it allows for the representation of variables which are case-sensitive, nonempty strings of letters.
 */
public class VariableExpression implements Expression {

    /**
     * Abstraction function:
     * Represents a variable which takes part in an equation.
     * <p>
     * Representation invariant:
     * The variable is stored as a case-sensitive, nonempty String of letters.
     * It must only contain letters. Not symbols, numbers, whitespaces or other special characters.
     * <p>
     * Safety from rep exposure:
     * All fields are immutable.
     */

    public final String variable;
    public final String type;

    public VariableExpression(String variable) {
        this.variable = variable;
        this.type = "variable";
        checkRep();
    }

    private void checkRep() {
        assert variable != null;
        assert !variable.equals("");
        //TODO advanced checkrep so that variable does not contain whitespaces, symbols, numbers, etc.
    }


    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(!(obj instanceof Expression)){
            return false;
        }

        final VariableExpression other = (VariableExpression) obj;

        return Objects.equals(this.variable, other.variable);
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }



}