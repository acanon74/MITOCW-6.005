// Generated from Expression.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionParser extends Parser {
  static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache =
    new PredictionContextCache();
  public static final int
    T__0=1, T__1=2, SPACES=3, NUMBER=4, VAR=5, WS=6, ADD=7, MULT=8;
  public static final int
    RULE_root = 0, RULE_sum = 1, RULE_product = 2, RULE_expression = 3, 
    RULE_primitive = 4;
  public static final String[] ruleNames = {
    "root", "sum", "product", "expression", "primitive"
  };

  private static final String[] _LITERAL_NAMES = {
    null, "'('", "')'", null, null, null, null, "'+'", "'*'"
  };
  private static final String[] _SYMBOLIC_NAMES = {
    null, null, null, "SPACES", "NUMBER", "VAR", "WS", "ADD", "MULT"
  };
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated
  public static final String[] tokenNames;
  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override

  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() { return "Expression.g4"; }

  @Override
  public String[] getRuleNames() { return ruleNames; }

  @Override
  public String getSerializedATN() { return _serializedATN; }

  @Override
  public ATN getATN() { return _ATN; }


      // This method makes the lexer or parser stop running if it encounters
      // invalid input and throw a ParseCancellationException.
      public void reportErrorsAsExceptions() {
          // To prevent any reports to standard error, add this line:
          //removeErrorListeners();
          
          addErrorListener(new BaseErrorListener() {
              public void syntaxError(Recognizer<?, ?> recognizer,
                                      Object offendingSymbol,
                                      int line, int charPositionInLine,
                                      String msg, RecognitionException e) {
                  throw new ParseCancellationException(msg, e);
              }
          });
      }

  public ExpressionParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
  }
  public static class RootContext extends ParserRuleContext {
    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class,0);
    }
    public TerminalNode EOF() { return getToken(ExpressionParser.EOF, 0); }
    public RootContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_root; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterRoot(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitRoot(this);
    }
  }

  public final RootContext root() throws RecognitionException {
    RootContext _localctx = new RootContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_root);
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(10);
      expression(0);
      setState(11);
      match(EOF);
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class SumContext extends ParserRuleContext {
    public List<ExpressionContext> expression() {
      return getRuleContexts(ExpressionContext.class);
    }
    public ExpressionContext expression(int i) {
      return getRuleContext(ExpressionContext.class,i);
    }
    public List<TerminalNode> ADD() { return getTokens(ExpressionParser.ADD); }
    public TerminalNode ADD(int i) {
      return getToken(ExpressionParser.ADD, i);
    }
    public SumContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_sum; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterSum(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitSum(this);
    }
  }

  public final SumContext sum() throws RecognitionException {
    SumContext _localctx = new SumContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_sum);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(13);
      expression(0);
      setState(18);
      _errHandler.sync(this);
      _la = _input.LA(1);
      while (_la==ADD) {
        {
        {
        setState(14);
        match(ADD);
        setState(15);
        expression(0);
        }
        }
        setState(20);
        _errHandler.sync(this);
        _la = _input.LA(1);
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ProductContext extends ParserRuleContext {
    public List<ExpressionContext> expression() {
      return getRuleContexts(ExpressionContext.class);
    }
    public ExpressionContext expression(int i) {
      return getRuleContext(ExpressionContext.class,i);
    }
    public List<TerminalNode> MULT() { return getTokens(ExpressionParser.MULT); }
    public TerminalNode MULT(int i) {
      return getToken(ExpressionParser.MULT, i);
    }
    public ProductContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_product; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterProduct(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitProduct(this);
    }
  }

  public final ProductContext product() throws RecognitionException {
    ProductContext _localctx = new ProductContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_product);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(21);
      expression(0);
      setState(26);
      _errHandler.sync(this);
      _la = _input.LA(1);
      while (_la==MULT) {
        {
        {
        setState(22);
        match(MULT);
        setState(23);
        expression(0);
        }
        }
        setState(28);
        _errHandler.sync(this);
        _la = _input.LA(1);
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ExpressionContext extends ParserRuleContext {
    public List<TerminalNode> WS() { return getTokens(ExpressionParser.WS); }
    public TerminalNode WS(int i) {
      return getToken(ExpressionParser.WS, i);
    }
    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class,0);
    }
    public PrimitiveContext primitive() {
      return getRuleContext(PrimitiveContext.class,0);
    }
    public ProductContext product() {
      return getRuleContext(ProductContext.class,0);
    }
    public SumContext sum() {
      return getRuleContext(SumContext.class,0);
    }
    public ExpressionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_expression; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpression(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpression(this);
    }
  }

  public final ExpressionContext expression() throws RecognitionException {
    return expression(0);
  }

  private ExpressionContext expression(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
    ExpressionContext _prevctx = _localctx;
    int _startState = 6;
    enterRecursionRule(_localctx, 6, RULE_expression, _p);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
      setState(44);
      switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
      case 1:
        {
        setState(30);
        match(WS);
        setState(31);
        expression(3);
        }
        break;
      case 2:
        {
        setState(32);
        primitive();
        }
        break;
      case 3:
        {
        setState(33);
        match(T__0);
        setState(36);
        switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
        case 1:
          {
          setState(34);
          product();
          }
          break;
        case 2:
          {
          setState(35);
          sum();
          }
          break;
        }
        setState(38);
        match(T__1);
        }
        break;
      case 4:
        {
        setState(40);
        match(WS);
        setState(41);
        expression(0);
        setState(42);
        match(WS);
        }
        break;
      }
      _ctx.stop = _input.LT(-1);
      setState(50);
      _errHandler.sync(this);
      _alt = getInterpreter().adaptivePredict(_input,4,_ctx);
      while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
        if ( _alt==1 ) {
          if ( _parseListeners!=null ) triggerExitRuleEvent();
          _prevctx = _localctx;
          {
          {
          _localctx = new ExpressionContext(_parentctx, _parentState);
          pushNewRecursionContext(_localctx, _startState, RULE_expression);
          setState(46);
          if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
          setState(47);
          match(WS);
          }
          } 
        }
        setState(52);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input,4,_ctx);
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  public static class PrimitiveContext extends ParserRuleContext {
    public TerminalNode NUMBER() { return getToken(ExpressionParser.NUMBER, 0); }
    public TerminalNode VAR() { return getToken(ExpressionParser.VAR, 0); }
    public PrimitiveContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }
    @Override public int getRuleIndex() { return RULE_primitive; }
    @Override
    public void enterRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterPrimitive(this);
    }
    @Override
    public void exitRule(ParseTreeListener listener) {
      if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitPrimitive(this);
    }
  }

  public final PrimitiveContext primitive() throws RecognitionException {
    PrimitiveContext _localctx = new PrimitiveContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_primitive);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
      setState(53);
      _la = _input.LA(1);
      if ( !(_la==NUMBER || _la==VAR) ) {
      _errHandler.recoverInline(this);
      } else {
        consume();
      }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
    case 3:
      return expression_sempred((ExpressionContext)_localctx, predIndex);
    }
    return true;
  }
  private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
    switch (predIndex) {
    case 0:
      return precpred(_ctx, 2);
    }
    return true;
  }

  public static final String _serializedATN =
    "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\n:\4\2\t\2\4\3"+
      "\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\3\3\3\3\3\7\3\23\n\3\f"+
      "\3\16\3\26\13\3\3\4\3\4\3\4\7\4\33\n\4\f\4\16\4\36\13\4\3\5\3\5\3"+
      "\5\3\5\3\5\3\5\3\5\5\5\'\n\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5/\n\5\3\5"+
      "\3\5\7\5\63\n\5\f\5\16\5\66\13\5\3\6\3\6\3\6\2\3\b\7\2\4\6\b\n\2\3"+
      "\3\2\6\7;\2\f\3\2\2\2\4\17\3\2\2\2\6\27\3\2\2\2\b.\3\2\2\2\n\67\3"+
      "\2\2\2\f\r\5\b\5\2\r\16\7\2\2\3\16\3\3\2\2\2\17\24\5\b\5\2\20\21\7"+
      "\t\2\2\21\23\5\b\5\2\22\20\3\2\2\2\23\26\3\2\2\2\24\22\3\2\2\2\24"+
      "\25\3\2\2\2\25\5\3\2\2\2\26\24\3\2\2\2\27\34\5\b\5\2\30\31\7\n\2\2"+
      "\31\33\5\b\5\2\32\30\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2\2\34\35\3\2"+
      "\2\2\35\7\3\2\2\2\36\34\3\2\2\2\37 \b\5\1\2 !\7\b\2\2!/\5\b\5\5\""+
      "/\5\n\6\2#&\7\3\2\2$\'\5\6\4\2%\'\5\4\3\2&$\3\2\2\2&%\3\2\2\2\'(\3"+
      "\2\2\2()\7\4\2\2)/\3\2\2\2*+\7\b\2\2+,\5\b\5\2,-\7\b\2\2-/\3\2\2\2"+
      ".\37\3\2\2\2.\"\3\2\2\2.#\3\2\2\2.*\3\2\2\2/\64\3\2\2\2\60\61\f\4"+
      "\2\2\61\63\7\b\2\2\62\60\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65"+
      "\3\2\2\2\65\t\3\2\2\2\66\64\3\2\2\2\678\t\2\2\28\13\3\2\2\2\7\24\34"+
      "&.\64";
  public static final ATN _ATN =
    new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}