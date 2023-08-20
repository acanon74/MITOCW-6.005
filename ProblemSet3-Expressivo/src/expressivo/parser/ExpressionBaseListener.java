// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.


import expressivo.Expression;
import expressivo.NumberExpression;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class provides an empty implementation of {@link ExpressionListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
public class ExpressionBaseListener implements ExpressionListener {
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */



  @Override public void enterRoot(ExpressionParser.RootContext ctx) {
  }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void exitRoot(ExpressionParser.RootContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   *
   * @return
   */
  @Override public void enterSum(ExpressionParser.SumContext ctx) {
  }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void exitSum(ExpressionParser.SumContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void enterProduct(ExpressionParser.ProductContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void exitProduct(ExpressionParser.ProductContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void enterExpression(ExpressionParser.ExpressionContext ctx) {
  }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void exitExpression(ExpressionParser.ExpressionContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void enterPrimitive(ExpressionParser.PrimitiveContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void exitPrimitive(ExpressionParser.PrimitiveContext ctx) { }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void enterEveryRule(ParserRuleContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void exitEveryRule(ParserRuleContext ctx) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void visitTerminal(TerminalNode node) { }
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation does nothing.</p>
   */
  @Override public void visitErrorNode(ErrorNode node) { }
}

/**
 * I actually have no idea how to solve this. Transversing the tree is handled by the parse, so building the object
 * trying to discover the tree as we go is not possible (we would need to discover all the tree while in th very first
 * node). Second, thanks to the specification given (and the fact that the course does not say that it is allowed to
 * change it, plus some method are inherited from a parser interface rather than Expression), we cannot have any passing of
 * information or objects between node enters/exits. So, that also makes recursion impossible. Third, even if we could share
 * information, the specification of Expression requires it to be immutable, which makes it more difficult to implement recursion or
 * anything really, and I imagine it to be extremely inefficient, I do not think that is the expected solution.
 *
 * I have no idea. Kind of wish I had access to the staff members or classmates to ask for guidance.
 */