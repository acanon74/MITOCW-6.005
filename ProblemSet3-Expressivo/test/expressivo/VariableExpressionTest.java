package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

public class VariableExpressionTest {

    /**
     * Preconditions:
     * VariableExpression stores a case-sensitive, nonempty String of letters.
     * <p>
     * Test strategy:
     * We test the returns of all methods when they are provided a VariableExpression
     * initialized to values corresponding to upper and lower case, as well as a single-character, and multi-character string.
     * <p>
     * Breakdown of every test used:
     * toStringTest()
     * A VariableExpression object should return the String of letters it represents.
     * equalsTest()
     * Objects, must report as equal if they represent the same String of letters. Otherwise, false.
     * We test equality with lower, upper and capitalization scenearios.
     * hashCodeTest()
     * hashCode() for two objects must return the same hash whenever we deem equals() true. Otherwise, false.
     */

    @Test
    public void toStringTest() {
        VariableExpression lowerVariable = new VariableExpression("x");
        VariableExpression upperVariable = new VariableExpression("X");

        VariableExpression lowerWord = new VariableExpression("word");
        VariableExpression capiWord = new VariableExpression("Word");

        assertEquals("x", lowerVariable.toString());
        assertEquals("X", upperVariable.toString());

        assertEquals("word", lowerWord.toString());
        assertEquals("Word", capiWord.toString());
    }

    @Test
    public void equalsTest() {

        VariableExpression lowerVariable = new VariableExpression("x");
        VariableExpression equalLowerVariable = new VariableExpression("x");
        VariableExpression diffVariable = new VariableExpression("y");

        assertTrue(lowerVariable.equals(equalLowerVariable));
        assertFalse(lowerVariable.equals(diffVariable));

        VariableExpression upperVariable = new VariableExpression("X");

        assertFalse(lowerVariable.equals(upperVariable));

        VariableExpression lowerWord = new VariableExpression("word");
        VariableExpression equalLowerWord = new VariableExpression("word");
        VariableExpression capiWord = new VariableExpression("Word");

        assertTrue(lowerWord.equals(equalLowerWord));
        assertFalse(lowerWord.equals(capiWord));
    }

    @Test
    public void hashCodeTest() {

        VariableExpression lowerVariable = new VariableExpression("x");
        VariableExpression equalLowerVariable = new VariableExpression("x");
        VariableExpression diffVariable = new VariableExpression("y");

        assertTrue(lowerVariable.hashCode() == equalLowerVariable.hashCode());
        assertFalse(lowerVariable.hashCode() == diffVariable.hashCode());

        VariableExpression upperVariable = new VariableExpression("X");

        assertFalse(lowerVariable.hashCode() == upperVariable.hashCode());

        VariableExpression lowerWord = new VariableExpression("word");
        VariableExpression equalLowerWord = new VariableExpression("word");
        VariableExpression capiWord = new VariableExpression("Word");

        assertTrue(lowerWord.hashCode() == equalLowerWord.hashCode());
        assertFalse(lowerWord.hashCode() == capiWord.hashCode());
    }
}
