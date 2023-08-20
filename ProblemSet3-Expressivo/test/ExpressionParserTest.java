import static org.junit.Assert.*;

import expressivo.*;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;

public class ExpressionParserTest {

    /**
     * 
     * "((1.0 + 2.0) + (3.0 * 4.0))"
     * "(1 + 2 * 3)"
     * "3 + 2.4"
     * "3 * x + 2.4"
     * "3 * (x + 2.4)"
     * "((3 + 4) * x * x)"
     * "foo + bar+baz"
     * "(2*x    )+    (    y*x    )"
     * "4 + 3 * x + 2 * x * x + 1 * x * x * (((x)))"
     */
    @Test
    public void parserTest1 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(1.0);
        NumberExpression n2 = new NumberExpression(2.0);
        NumberExpression n3 = new NumberExpression(3.0);
        NumberExpression n4 = new NumberExpression(4.0);

        //operands:
        SumOperator left = new SumOperator(n1, n2);
        MultiplicationOperator right = new MultiplicationOperator(n3, n4);

        SumOperator expr = new SumOperator(left, right);

        assertEquals(expr.toString(), Expression.parse("((1.0 + 2.0) + (3.0 * 4.0))").toString());
    }
    @Test
    public void parserTest2 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(1.0);
        NumberExpression n2 = new NumberExpression(2.0);
        NumberExpression n3 = new NumberExpression(3.0);

        //operands:
        MultiplicationOperator right = new MultiplicationOperator(n2, n3);

        Expression expr = new SumOperator(n1, right);

        assertEquals(expr, Expression.parse("(1 + 2 * 3)"));

    }
    @Test
    public void parserTest3 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(3.0);
        NumberExpression n2 = new NumberExpression(2.4);

        Expression expr = new SumOperator(n1, n2);

        assertEquals(expr, Expression.parse("3 + 2.4"));

    }

    @Test
    public void parserTest4 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(3.0);
        VariableExpression v2 = new VariableExpression("x");
        NumberExpression n3 = new NumberExpression(2.4);

        //operands:
        MultiplicationOperator left = new MultiplicationOperator(n1, v2);

        Expression expr = new SumOperator(left, n3);

        assertEquals(expr, Expression.parse("3 * x + 2.4"));

    }
    @Test
    public void parserTest5 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(3.0);
        VariableExpression v2 = new VariableExpression("x");
        NumberExpression n3 = new NumberExpression(2.4);

        //operands:
        SumOperator right = new SumOperator(v2, n3);

        Expression expr = new MultiplicationOperator(n1, right);

        assertEquals(expr, Expression.parse("3 * (x + 2.4)"));
    }
    @Test
    public void parserTest6 () {

        CharStream stream = new ANTLRInputStream("((3 + 4) * x * x)");
        //primitive values:
        NumberExpression n1 = new NumberExpression(3.0);
        NumberExpression n2 = new NumberExpression(4.0);
        VariableExpression v3 = new VariableExpression("x");

        //operands:
        MultiplicationOperator left = new MultiplicationOperator(new SumOperator(n1, n2), v3);

        Expression expr = new MultiplicationOperator(left, v3);

        assertEquals(expr, Expression.parse("((3 + 4) * x * x)"));
    }
    @Test
    public void parserTest7 () {

        //primitive values:
        VariableExpression v1 = new VariableExpression("foo");
        VariableExpression v2 = new VariableExpression("bar");
        VariableExpression v3 = new VariableExpression("baz");

        //operands:
        SumOperator left = new SumOperator(v1, v2);

        Expression expr = new SumOperator(left, v3);

        assertEquals(expr, Expression.parse("foo + bar+baz"));

    }
    @Test
    public void parserTest8 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(2.0);
        VariableExpression v2 = new VariableExpression("x");
        VariableExpression v3 = new VariableExpression("y");

        //operands:
        MultiplicationOperator left = new MultiplicationOperator(n1, v2);
        MultiplicationOperator right = new MultiplicationOperator(v3, v2);

        Expression expr = new SumOperator(left, right);

        assertEquals(expr, Expression.parse("(2*x    )+    (    y*x    )"));

    }


    @Test
    public void parserTest9 () {

        //primitive values:
        NumberExpression n1 = new NumberExpression(4.0);
        NumberExpression n2 = new NumberExpression(3.0);
        VariableExpression v3 = new VariableExpression("x");
        NumberExpression n4 = new NumberExpression(2.0);
        NumberExpression n5 = new NumberExpression(1.0);
        //operands:
        MultiplicationOperator e1 = new MultiplicationOperator(n2, v3);
        MultiplicationOperator e2 = new MultiplicationOperator(n5, v3);
        SumOperator e3 = new SumOperator(n1, e1);

        SumOperator expr = new SumOperator(e3, e2);

        assertEquals(expr, Expression.parse("4 + 3 * x + 1 * (((x)))"));
    }
}
