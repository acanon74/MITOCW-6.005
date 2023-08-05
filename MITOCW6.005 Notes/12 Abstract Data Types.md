Abstract data types enable us to separate how we use a data structure in a program from the particular form of the data structure itself.

#### Abstract data types address a particularly dangerous problem: clients making assumptions about the type’s internal representation.

# What Abstraction Means

Abstract data types are an instance of a general principle in software engineering, which goes by many names with slightly different meanings. Here are some of the names that are used for this idea:

- **Abstraction.** Omitting or hiding low-level details with a simpler, higher-level idea.
- **Modularity.** Dividing a system into components or modules, each of which can be designed, implemented, tested, reasoned about, and reused separately from the rest of the system.
- **Encapsulation.** Building walls around a module (a hard shell or capsule) so that the module is responsible for its own internal behavior, and bugs in other parts of the system can’t damage its integrity.
- **Information hiding.** Hiding details of a module’s implementation from the rest of the system, so that those details can be changed later without changing the rest of the system.
- **Separation of concerns.** Making a feature (or “concern”) the responsibility of a single module, rather than spreading it across multiple modules.

# User-Defined Types

In the early days of computing, a programming language came with built-in types (such as integers, booleans, strings, etc.) and built-in procedures, e.g., for input and output. Users could define **their own procedures**: that’s how large programs were built. A major advance in software development was the idea of abstract types: that one could design a programming language to allow **user-defined types, too.**

**The key idea of data abstraction is that a type is characterized by the operations you can perform on it.** A number is something you can add and multiply; a string is something you can concatenate and take substrings of; and so on. But what made abstract types new and different was the focus on operations: the user of the type would not need to worry about how its values were actually stored. **All that matters is the operations.**

# Classifying Types and Operations

Types, whether built-in or user-defined, can be classified as mutable or immutable. The objects of a mutable type can be changed: that is, they provide operations which when executed affect the object itself.

The operations of an abstract type are classified as follows:

- **Creators** create new objects of the type. A creator may take an object as an argument, but not an object of the type being constructed.
- **Producers** create new objects from old objects of the type. The `concat` method of `String` , for example, is a producer: it takes two strings and produces a new one representing their concatenation.
- **Observers** take objects of the abstract type and return objects of a different type. The `size` method of `List` , for example, returns an `int` .
- **Mutators** change objects. The `add` method of `List` , for example, mutates a list by adding an element to the end.

A creator operation is often implemented as a constructor, like new ArrayList(). But a creator can simply be a static method instead, like Arrays.asList(). A creator implemented as a static method is often called a **factory method**.

Mutators are often signaled by a void return type. A method that returns void must be called for some kind of side-effect, since otherwise it doesn’t return anything. But not all mutators return void. For example, we can return a boolean value to communicate whether the mutation was successful or we encountered an error.

## Abstract Data Type Examples

**`int`** is Java’s primitive integer type. `int` is immutable, so it has no mutators.

- creators: the numeric literals `0` , `1` , `2` , …
- producers: arithmetic operators `+` , `-` , `*` , `/`
- observers: comparison operators `==` , `!=` , `<` , `>`
- mutators: none (it’s immutable)

**`List`** is Java’s list type. `List` is mutable. `List` is also an interface, which means that other classes provide the actual implementation of the data type. These classes include `ArrayList` and `LinkedList` .

- creators: `ArrayList` and `LinkedList` constructors, `Collections.singletonList`
- producers: `Collections.unmodifiableList`
- observers: `size` , `get`
- mutators: `add` , `remove` , `addAll` , `Collections.sort`

This classification it’s not perfect. In complicated data types, an operator could be both, a producer and a mutator.

# Designing an Abstract Type

Designing an abstract type involves choosing good operations and determining how they should behave. Here are listed some guidelines.

1.  It’s better to have a **few, simple operations** that can be combined in powerful ways, rather than lots of complex operations.
    
2.  Each operation should have a **well-defined purpose**, and should have a **coherent behavior** rather than be full of special cases. You shouldn't add a sum operation to list. It might be useful if we work with list of integers but not with lists of strings.
    
3.  The set of operations should be **adequate**. There must be enough to do the kinds of computations clients probably want to do. A good test is to check that every property of an object of the type can be extracted.
    
4.  The type may be generic (a list or graph). Or it may be domain-specific: a street map, an employee database, etc. **But it should not mix generic and domain-specific features.** Do not add dealCards into the generic type List. The average client of List will not use it to represent cards.
    

# Representation Independence

Critically, a good abstract data type should be **representation independent**. This means that the use of an abstract type is independent of its representation (the actual data structure or data fields used to implement it), so that changes in representation have no effect on code outside the abstract type itself. For example, the operations offered by List are independent of whether the list is represented as a linked list or as an array.

When changing the underlying implementation, specifications help you to keep the contract intact.

## Example: Different Representation for Strings

### Here are the specs for the ADT:

```
/** MyString represents an immutable sequence of characters. */
public class MyString { 

    //////////////////// Example of a creator operation ///////////////
    /** @param b a boolean value
     *  @return string representation of b, either "true" or "false" */
    public static MyString valueOf(boolean b) { ... }

    //////////////////// Examples of observer operations ///////////////
    /** @return number of characters in this string */
    public int length() { ... }

    /** @param i character position (requires 0 <= i < string length)
     *  @return character at position i */
    public char charAt(int i) { ... }

    //////////////////// Example of a producer operation ///////////////    
    /** Get the substring between start (inclusive) and end (exclusive).
     *  @param start starting index
     *  @param end ending index.  Requires 0 <= start <= end <= string length.
     *  @return string consisting of charAt(start)...charAt(end-1) */
    public MyString substring(int start, int end) { ... }
}
```

These public operations and their specifications are the only information that a client of this data type is allowed to know.

Following the test-first programming paradigm. We create a test suite to exercises the specifications. *Note: We do not use assertEquals directly on MyString because this code doesn't implement operators overloading.*

```
MyString s = MyString.valueOf(true);
assertEquals(4, s.length());
assertEquals('t', s.charAt(0));
assertEquals('r', s.charAt(1));
assertEquals('u', s.charAt(2));
assertEquals('e', s.charAt(3));
```

### We now decide to implement MyString as an array of characters.

```
private char[] a;
```

Therefore, the operations would be implemented this way:

```
public static MyString valueOf(boolean b) {
    MyString s = new MyString();
    s.a = b ? new char[] { 't', 'r', 'u', 'e' } 
            : new char[] { 'f', 'a', 'l', 's', 'e' };
    return s;
}

public int length() {
    return a.length;
}

public char charAt(int i) {
    return a[i];
}

public MyString substring(int start, int end) {
    MyString that = new MyString();
    that.a = new char[end - start];
    System.arraycopy(this.a, start, that.a, 0, end - start);
    return that;
}
```

However, we could implement a performance improvement. Since the original object is immutable, we could get substring to point to the substring in the original object rather than copying contents in a new array.

### To implement this optimization, we change the internal representation to:

```
private char[] a;
private int start;
private int end;
```

With this new representation, the operations are now implemented like this:

```
public static MyString valueOf(boolean b) {
    MyString s = new MyString();
    s.a = b ? new char[] { 't', 'r', 'u', 'e' } 
            : new char[] { 'f', 'a', 'l', 's', 'e' };
    s.start = 0;
    s.end = s.a.length;
    return s;
}

public int length() {
    return end - start;
}

public char charAt(int i) {
  return a[start + i];
}

public MyString substring(int start, int end) {
    MyString that = new MyString();
    that.a = this.a;
    that.start = this.start + start;
    that.end = this.start + end;
    return that;
}
```

Because MyString ’s existing clients depend only on the specs of its public methods, not on its private fields, we can make this change without having to inspect and change all that client code.

# Testing an Abstract Data Type

We build a test suite for an abstract data type by creating tests for each of its operations.

### We partition the input space of the four operation as:

```
// testing strategy for each operation of MyString:
//
// valueOf():
//    true, false
// length(): 
//    string len = 0, 1, n
//    string = produced by valueOf(), produced by substring()
// charAt(): 
//    string len = 1, n
//    i = 0, middle, len-1
//    string = produced by valueOf(), produced by substring()
// substring():
//    string len = 0, 1, n
//    start = 0, middle, len
//    end = 0, middle, len
//    end-start = 0, n
//    string = produced by valueOf(), produced by substring()
```

### Then a compact test suite that covers all these partitions might look like:

```
@Test public void testValueOfTrue() {
    MyString s = MyString.valueOf(true);
    assertEquals(4, s.length());
    assertEquals('t', s.charAt(0));
    assertEquals('r', s.charAt(1));
    assertEquals('u', s.charAt(2));
    assertEquals('e', s.charAt(3));
}

@Test public void testValueOfFalse() {
    MyString s = MyString.valueOf(false);
    assertEquals(5, s.length());
    assertEquals('f', s.charAt(0));
    assertEquals('a', s.charAt(1));
    assertEquals('l', s.charAt(2));
    assertEquals('s', s.charAt(3));
    assertEquals('e', s.charAt(4));
}

@Test public void testEndSubstring() {
    MyString s = MyString.valueOf(true).substring(2, 4);
    assertEquals(2, s.length());
    assertEquals('u', s.charAt(0));
    assertEquals('e', s.charAt(1));
}

@Test public void testMiddleSubstring() {
    MyString s = MyString.valueOf(false).substring(1, 2);
    assertEquals(1, s.length());
    assertEquals('a', s.charAt(0));
}

@Test public void testSubstringIsWholeString() {
    MyString s = MyString.valueOf(false).substring(0, 5);
    assertEquals(5, s.length());
    assertEquals('f', s.charAt(0));
    assertEquals('a', s.charAt(1));
    assertEquals('l', s.charAt(2));
    assertEquals('s', s.charAt(3));
    assertEquals('e', s.charAt(4));
}

@Test public void testSubstringOfEmptySubstring() {
    MyString s = MyString.valueOf(false).substring(1, 1).substring(0, 0);
    assertEquals(0, s.length());
}
```

Notice that each test case typically calls a few operations that *make* or *modify* objects of the type (creators, producers, mutators) and some operations that *inspect* objects of the type (observers). As a result, each test case covers parts of several operations.