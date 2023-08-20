/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

/**
 * This file is the grammar file used by ANTLR.
 *
 * In order to compile this file, navigate to this folder
 * (src/expressivo/parser) and run the following command:
 * 
 * java -jar ../../../lib/antlr.jar Expression.g4
 *
 * PS3 instructions: you are free to change this grammar.
 */

grammar Expression;
import Configuration;

/*
 * Nonterminal rules (a.k.a. parser rules) must be lowercase, e.g. "root" and "sum" below.
 *
 * Terminal rules (a.k.a. lexical rules) must be UPPERCASE, e.g. NUMBER below.
 * Terminals can be defined with quoted strings or regular expressions.
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". The start rule should end with the special token
 * EOF so that it describes the entire input. Below, "root" is the start rule.
 *
 * For more information, see reading 18 about parser generators, which explains
 * how to use Antlr and has links to reference information.
 * java -jar ..\..\..\lib\antlr.jar .\Expression.g4


/*

I tried my hardest but I was unable to obtain a parser that worked the way I wanted.
I want it to group the operands in pairs (around the operator), just as the parser that was implemented in scheme on
the course SICP 6.001. I simply could not find a solution to the fact that operands can be both, inside and outside parenthesis.
And after a couple of days, all I can picture generates a left-recursive error. Which is imho, kind of stupid. Parsers should allow for
recursion as long as base cases have been defined. And it should to up to the client to provide proper base and recursion cases.
Here, my base cases are numbers and variables. So, Expression types should be able to do recursion.

Finally, I understand the importance and usefulness of grammar, parsers and whatnot. But, I am not interested by them and I have no
use whatsoever for them right now. I just copied someone else's grammar and call it a day. NVM that implementation was not clear enough,
I had to tweak it. I will still give the author its credit.

Anyway, there is my last attempt:
 SPACES : [ \t\r\n]+ -> skip;
 root : expression EOF;
 expression : (primitive | '(' (sum | multiplication) ')' | ('+' expression) | ('*' expression) | ('+' '('* expression ')'*) | ('*' '('* expression ')'*) )+;
 primitive : NUMBER | VARIABLE;
 sum : expression ('+' expression)+;
 multiplication : expression ('*' expression)+;
 NUMBER : INTEGER ('.' DIGIT+)?;
 INTEGER : DIGIT+;
 DIGIT : [0-9];
 VARIABLE : [a-z]+;


This implementation is a tweak from https://github.com/Riksi/Expressivo/blob/master/src/expressivo/parser/Expression.g4
*/
SPACES : [ \t\r\n]+ -> skip;
root : expression EOF;
sum : expression (ADD expression)*;
product : expression (MULT expression)*;
expression : primitive | '(' (product | sum) ')' | WS expression | expression WS | WS expression WS ;
primitive : NUMBER | VAR;
NUMBER : ([0-9]+ ('.' [0-9]*)? | '.' [0-9]+)(('e'|'E')('-'|'+')?[0-9]+)?;
VAR : [a-zA-Z]+;
WS : [ \t\r\n]+;
ADD: '+';
MULT: '*';
