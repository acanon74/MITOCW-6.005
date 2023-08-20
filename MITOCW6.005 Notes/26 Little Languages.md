## Representing code as data

Recall the  [`Formula` datatype from  *Recursive Data Types*](https://ocw.mit.edu/ans7870/6/6.005/s16/classes/16-recursive-data-types/recursive/#another_example_boolean_formulas) :

```
Formula = Variable(name:String)
          + Not(formula:Formula)
          + And(left:Formula, right:Formula)
          + Or(left:Formula, right:Formula)
```

We used instances of  `Formula` to take propositional logic formulas, e.g.  ***(p ∨ q) ∧ (¬p ∨ r)*** , and represent them in a data structure, e.g.:

```
And(Or(Variable("p"), Variable("q")),
    Or(Not(Variable("p")), Variable("r")))
```

In the parlance of grammars and parsers, formulas are a  *language* , and  `Formula` is an  [*abstract syntax tree*](https://ocw.mit.edu/ans7870/6/6.005/s16/classes/18-parser-generators/) .

But why did we define a  `Formula` type? Java already has a way to represent expressions of Boolean variables with  *logical and* ,  *or* , and  *not* . For example, given  `boolean` variables  `p` ,  `q` , and  `r` :

```
(p || q) && ((!p) || r) 
```

Done!

The answer is that the Java code expression  `(p || q) && ((!p) || r)` is evaluated as soon as we encounter it in our running program. The  `Formula` value  `And(Or(...), Or(...))` is a  **first-class value** that can be stored, passed and returned from one method to another, manipulated, and evaluated now or later (or more than once) as needed.

The  `Formula` type is an example of  **representing code as data** , and we’ve seen many more.

Consider this  [functional object](https://ocw.mit.edu/ans7870/6/6.005/s16/classes/25-map-filter-reduce/#first-class_functions_in_java) :

```
class VariableNameComparator implements Comparator<Variable> {
    public int compare(Variable v1, Variable v2) {
        return v1.name().compareTo(v2.name());
    }
}
```

An instance of  `VariableNameComparator` is a value that can be passed around, returned, and stored. But at any time, the function that it represents can be invoked by calling its  `compare` method with a couple of  `Variable` arguments:

```
Variable v1, v2;
Comparator<Variable> c = new VariableNameComparator();
...
int a = c.compare(v1, v2);
int b = c.compare(v2, v1);
SortedSet<Variable> vars = new TreeSet<>(c); // vars is sorted by name
```

Lambda expressions allow us to create functional objects with a compact syntax:

```
Comparator<Variable> c = (v1, v2) -> v1.name().compareTo(v2.name());
```

## Building languages to solve problems

When we define an  [abstract data type](https://ocw.mit.edu/ans7870/6/6.005/s16/classes/12-abstract-data-types/) , we’re extending the universe of built-in types provided by Java to include a new type, with new operations, appropriate to our problem domain. This new type is like a new language: a new set of nouns (values) and verbs (operations) we can manipulate. Of course, those nouns and verbs are abstractions built on top the existing nouns and verbs which were themselves already abstractions.

A  *language* has greater flexibility than a mere  *program* , because we can use a language to solve a large class of related problems, instead of just a single problem.

- That’s the difference between writing  `(p || q) && ((!p) || r)` and devising a  `Formula` type to represent the semantically-equivalent Boolean formula.
    
- And it’s the difference between writing a matrix multiplication function and devising a  [`MatrixExpression` type](https://ocw.mit.edu/ans7870/6/6.005/s16/classes/16-recursive-data-types/matexpr/) to represent matrix multiplications — and store them, manipulate them, optimize them, evaluate them, and so on.
    

First-class functions and functional objects enable us to create particularly powerful languages because we can capture patterns of computation as reusable abstractions.