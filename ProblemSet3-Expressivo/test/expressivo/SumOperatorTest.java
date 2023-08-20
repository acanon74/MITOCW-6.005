package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class SumOperatorTest {

    /**
     * Preconditions:
     * SumOperator contains other implementations of Expression as right and left members.
     * <p>
     * Test strategy:
     * We test the returns of all methods when they are provided a SumOperator
     * initialized to values corresponding to the input space.
     * <p>
     * Breakdown of every test used:
     * toStringTest()
     * A SumOperator object must format its contents as (right hand + left hand).
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

        assertEquals("(1.0 + 2.0)", new SumOperator(numberLeft, numberRight).toString());

        //sum
        SumOperator sumLeft = new SumOperator(numberLeft, numberRight);
        SumOperator sumRight = new SumOperator(anotherNumberLeft, anotherNumberRight);

        assertEquals("((1.0 + 2.0) + (3.0 + 4.0))", new SumOperator(sumLeft, sumRight).toString());

        //multi
        MultiplicationOperator multiLeft = new MultiplicationOperator(numberLeft, numberRight);
        MultiplicationOperator multiRight = new MultiplicationOperator(anotherNumberLeft, anotherNumberRight);

        assertEquals("((1.0 * 2.0) + (3.0 * 4.0))", new SumOperator(multiLeft, multiRight).toString());

        //var
        VariableExpression varLeft = new VariableExpression("L");
        VariableExpression varRight = new VariableExpression("R");

        assertEquals("(L + R)", new SumOperator(varLeft, varRight).toString());


        //diff type objects
        //sum + var
        assertEquals("((1.0 + 2.0) + R)", new SumOperator(sumLeft, varRight).toString());

        //var + multi
        assertEquals("(L + (3.0 * 4.0))", new SumOperator(varLeft, multiRight).toString());

        //number + sum
        assertEquals("(1.0 + (3.0 + 4.0))", new SumOperator(numberLeft, sumRight).toString());

        //sum + multi
        assertEquals("((1.0 + 2.0) + (3.0 * 4.0))", new SumOperator(sumLeft, multiRight).toString());


        //Complex nested operation
        MultiplicationOperator complexLeft = new MultiplicationOperator(sumLeft, varRight);
        SumOperator complexRight = new SumOperator(numberLeft, new MultiplicationOperator(varLeft, varRight));
        assertEquals("(((1.0 + 2.0) * R) + (1.0 + (L * R)))", new SumOperator(complexLeft, complexRight).toString());
    }

    @Test
    public void equalsTest() {

        NumberExpression numberLeft = new NumberExpression(1);
        NumberExpression numberRight = new NumberExpression(2);
        NumberExpression anotherNumberLeft = new NumberExpression(3);
        NumberExpression anotherNumberRight = new NumberExpression(4);

        SumOperator sum = new SumOperator(numberLeft, numberRight);
        SumOperator equalSum = new SumOperator(numberLeft, numberRight);
        SumOperator diffSum = new SumOperator(anotherNumberLeft, anotherNumberRight);

        //diff order but mathematically the same
        SumOperator reverseSum = new SumOperator(numberRight, numberLeft);


        assertTrue(sum.equals(equalSum));
        assertFalse(sum.equals(reverseSum));
        assertFalse(sum.equals(diffSum));
    }

    @Test
    public void hashCodeTest() {

        NumberExpression numberLeft = new NumberExpression(1);
        NumberExpression numberRight = new NumberExpression(2);
        NumberExpression anotherNumberLeft = new NumberExpression(3);
        NumberExpression anotherNumberRight = new NumberExpression(4);

        SumOperator sum = new SumOperator(numberLeft, numberRight);
        SumOperator equalSum = new SumOperator(numberLeft, numberRight);
        SumOperator diffSum = new SumOperator(anotherNumberLeft, anotherNumberRight);

        //diff order but mathematically the same
        SumOperator reversedSum = new SumOperator(numberRight, numberLeft);


        assertTrue(sum.hashCode() == equalSum.hashCode());
        assertTrue(sum.hashCode() == reversedSum.hashCode());
        assertFalse(sum.hashCode() == diffSum.hashCode());
    }
}
