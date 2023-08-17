package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Objects;

public class NumberExpressionTest {

    /**
     * Preconditions:
     * NumberExpression might contain 0, 1, small double or maximum double.
     *
     * <p>
     * Test strategy:
     * We test the returns of all methods when they are provided a NumberExpression
     * initialized to values corresponding to the input space.
     * <p>
     * Breakdown of every test used:`
     * toStringTest()
     * An object must return a proper String representation of its value field.
     * Objects with the same value field must return the same String representation.
     * equalsTest()
     * Objects representing the same value must report as equal. Otherwise, report false.
     * We test String representation of Double that are syntactically different, but mathematically the same.
     * For example, 1.000 == 1.0. But 2.006 != 2.0.
     * hashCodeTest()
     * hashCode() must return the same hash number for equal objects. Otherwise, the numbers must be different.
     * getValueTest()
     * An object must successfully return the value it represents in the form of a Double type.
     */

    @Test
    public void toStringTest() {

        NumberExpression zeroValue = new NumberExpression(0.0);
        NumberExpression equalZeroValue = new NumberExpression(0.0);

        assertEquals("0.0", zeroValue.toString());
        assertTrue(Objects.equals(zeroValue.toString(), equalZeroValue.toString()));

        NumberExpression value = new NumberExpression(22.1);
        NumberExpression equalValue = new NumberExpression(22.1);

        assertEquals("22.1", value.toString());
        assertTrue(Objects.equals(value.toString(), equalValue.toString()));

        Expression maxValue = new NumberExpression(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), maxValue.toString());
    }

    @Test
    public void equalsTest() {

        NumberExpression zeroValue = new NumberExpression(0);
        NumberExpression equalZeroValue = new NumberExpression(0);

        assertTrue(zeroValue.equals(equalZeroValue));


        NumberExpression value = new NumberExpression(10.5);
        NumberExpression equalValue = new NumberExpression(10.5);

        assertTrue(value.equals(equalValue));

        Expression diffValue = new NumberExpression(999);

        assertFalse(value.equals(diffValue));

        //different formatting must report being the same value.
        NumberExpression formatValue = new NumberExpression(1.2000);
        NumberExpression equalOtherFormatValue = new NumberExpression(1.2);

        assertTrue(formatValue.equals(equalOtherFormatValue));

        //but things like this must be different, since they are different mathematically.
        NumberExpression trailingValue = new NumberExpression(0.9);
        NumberExpression equalTrailingValue = new NumberExpression(0.9001);

        assertFalse(trailingValue.equals(equalTrailingValue));

    }

    @Test
    public void hashCodeTest() {

        NumberExpression zeroValue = new NumberExpression(0);
        NumberExpression equalZeroValue = new NumberExpression(0);

        assertTrue(zeroValue.hashCode() == equalZeroValue.hashCode());


        NumberExpression value = new NumberExpression(10.5);
        NumberExpression equalValue = new NumberExpression(10.5);

        assertTrue(value.hashCode() == equalValue.hashCode());

        NumberExpression diffValue = new NumberExpression(999);

        assertFalse(value.hashCode() == diffValue.hashCode());
    }

    @Test
    public void getValueTest() {

        NumberExpression zeroValue = new NumberExpression(0.0);
        assertEquals(0.0, zeroValue.getValue(), 0);

        NumberExpression oneValue = new NumberExpression(1.0);
        assertEquals(1.0, oneValue.getValue(), 0);

        NumberExpression smallValue = new NumberExpression(54.3);
        assertEquals(54.3, smallValue.getValue(), 0);

        NumberExpression maxValue = new NumberExpression(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), maxValue.toString());
    }
}
