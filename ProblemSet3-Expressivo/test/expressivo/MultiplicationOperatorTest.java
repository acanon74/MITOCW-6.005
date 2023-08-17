package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultiplicationOperatorTest {

    /**
     * Preconditions:
     * MultiplicationOperator contains other implementations of Expression as right and left members.
     * <p>
     * Test strategy:
     * We test the returns of all methods when they are provided a MultiplicationOperator
     * initialized to values corresponding to the input space.
     * <p>
     * Breakdown of every test used:
     * toStringTest()
     * A MultiplicationOperator object must format its contents as (right hand * left hand).
     * We test multiple, different nested representations. This is important to test the recursive nature of the interface.
     * equalsTest()
     * Objects, after simplification, must report as equal if they are mathematically the same. Otherwise, false.
     * hashCodeTest()
     * hashCode() for two objects must return the same hash whenever we deem equals() true. Otherwise, false.
     */

    @Test
    public void toStringTest() {

        //same type objects
        //number
        NumberExpression numberLeft = new NumberExpression(1);
        NumberExpression numberRight = new NumberExpression(2);
        NumberExpression anotherNumberLeft = new NumberExpression(3);
        NumberExpression anotherNumberRight = new NumberExpression(4);

        assertEquals("(1.0 * 2.0)", new MultiplicationOperator(numberLeft, numberRight).toString());

        //sum
        SumOperator sumLeft = new SumOperator(numberLeft, numberRight);
        SumOperator sumRight = new SumOperator(anotherNumberLeft, anotherNumberRight);

        assertEquals("((1.0 + 2.0) * (3.0 + 4.0))", new MultiplicationOperator(sumLeft, sumRight).toString());

        //multi
        MultiplicationOperator multiLeft = new MultiplicationOperator(numberLeft, numberRight);
        MultiplicationOperator multiRight = new MultiplicationOperator(anotherNumberLeft, anotherNumberRight);

        assertEquals("((1.0 * 2.0) * (3.0 * 4.0))", new MultiplicationOperator(multiLeft, multiRight).toString());

        //var
        VariableExpression varLeft = new VariableExpression("L");
        VariableExpression varRight = new VariableExpression("R");

        assertEquals("(L * R)", new MultiplicationOperator(varLeft, varRight).toString());


        //diff type objects
        //sum * var
        assertEquals("((1.0 + 2.0) * R)", new MultiplicationOperator(sumLeft, varRight).toString());

        //var * multi
        assertEquals("(L * (3.0 * 4.0))", new MultiplicationOperator(varLeft, multiRight).toString());

        //number * sum
        assertEquals("(1.0 * (3.0 + 4.0))", new MultiplicationOperator(numberLeft, sumRight).toString());

        //sum * multi
        assertEquals("((1.0 + 2.0) * (3.0 * 4.0))", new MultiplicationOperator(sumLeft, multiRight).toString());


        //Complex nested operation
        MultiplicationOperator complexLeft = new MultiplicationOperator(sumLeft, varRight);
        SumOperator complexRight = new SumOperator(numberLeft, new MultiplicationOperator(varLeft, varRight));
        assertEquals("(((1.0 + 2.0) * R) * (1.0 + (L * R)))", new MultiplicationOperator(complexLeft, complexRight).toString());
    }

    @Test
    public void equalsTest() {

        NumberExpression numberLeft = new NumberExpression(1);
        NumberExpression numberRight = new NumberExpression(2);
        NumberExpression anotherNumberLeft = new NumberExpression(3);
        NumberExpression anotherNumberRight = new NumberExpression(4);

        MultiplicationOperator multi = new MultiplicationOperator(numberLeft, numberRight);
        MultiplicationOperator equalMulti = new MultiplicationOperator(numberLeft, numberRight);
        MultiplicationOperator diffMulti = new MultiplicationOperator(anotherNumberLeft, anotherNumberRight);

        //diff order but mathematically the same
        MultiplicationOperator reversedMulti = new MultiplicationOperator(numberRight, numberLeft);


        assertTrue(multi.equals(equalMulti));
        assertTrue(multi.equals(reversedMulti));
        assertFalse(multi.equals(diffMulti));
    }

    @Test
    public void hashCodeTest() {

        NumberExpression numberLeft = new NumberExpression(1);
        NumberExpression numberRight = new NumberExpression(2);
        NumberExpression anotherNumberLeft = new NumberExpression(3);
        NumberExpression anotherNumberRight = new NumberExpression(4);

        MultiplicationOperator multi = new MultiplicationOperator(numberLeft, numberRight);
        MultiplicationOperator equalMulti = new MultiplicationOperator(numberLeft, numberRight);
        MultiplicationOperator diffMulti = new MultiplicationOperator(anotherNumberLeft, anotherNumberRight);

        //diff order but mathematically the same
        MultiplicationOperator reversedMulti = new MultiplicationOperator(numberRight, numberLeft);


        assertTrue(multi.hashCode() == equalMulti.hashCode());
        assertTrue(multi.hashCode() == reversedMulti.hashCode());
        assertFalse(multi.hashCode() == diffMulti.hashCode());
    }
}