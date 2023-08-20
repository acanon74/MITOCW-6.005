# Abstracting out control flow

We’ve already seen one design pattern that abstracts away from the details of iterating over a data structure: Iterator.

## Iterator abstraction

Iterator gives you a sequence of elements from a data structure, without you having to worry about whether the data structure is a set or a token stream or a list or an array — the [`Iterator`](https://docs.oracle.com/javase/8/docs/api/?java/util/Iterator.html) looks the same no matter what the data structure is.

For example, given a `List<File> files` , we can iterate using indices:

```
for (int ii = 0; ii < files.size(); ii++) {
    File f = files.get(ii);
    // ...
```

But this code depends on the `size` and `get` methods of `List` , which might be different in another data structure. Using an iterator abstracts away the details:

```
Iterator<File> iter = files.iterator();
while (iter.hasNext()) {
    File f = iter.next();
    // ...
```

Now the loop will be identical for any type that provides an `Iterator` . There is, in fact, an interface for such types: [`Iterable`](https://docs.oracle.com/javase/8/docs/api/?java/lang/Iterable.html) . Any `Iterable` can be used with Java’s [enhanced for statement](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html) — `for (File f : files)` — and under the hood, it uses an iterator.

# Map

**Map** applies a unary function to each element in the sequence and returns a new sequence containing the results, in the same order:

**map : (E → F) × Seq&lt;‍E&gt; → Seq&lt;‍F&gt;**

# Filter

Our next important sequence operation is **filter** , which tests each element with a unary predicate. Elements that satisfy the predicate are kept; those that don’t are removed. A new list is returned; filter doesn’t modify its input list.

**filter : (E → boolean) × Seq&lt;‍E&gt; → Seq&lt;‍E&gt;**

# Reduce

Our final operator, **reduce** , combines the elements of the sequence together, using a binary function. In addition to the function and the list, it also takes an *initial value* that initializes the reduction, and that ends up being the return value if the list is empty.

**reduce : (F × E → F) × Seq&lt;‍E&gt; × F → F**

## Map/filter/reduce abstraction

The map/filter/reduce patterns in this reading do something similar to Iterator, but at an even higher level: they treat the entire sequence of elements as a unit, so that the programmer doesn’t have to name and work with the elements individually. In this paradigm, the control statements disappear: specifically, the `for` statements, the `if` statements, and the `return` statements in the code from our introductory example will be gone. We’ll also be able to get rid of most of the temporary names (i.e., the local variables `files` , `f` , and `result` ).

# Back to the intro example

Going back to the example we started with, where we want to find all the words in the Java files in our project, let’s try creating a useful abstraction for filtering files by suffix:

```
def fileEndsWith(suffix):
    return lambda file: file.getName().endsWith(suffix)
```

`fileEndsWith` returns *functions* that are useful as filters: it takes a filename suffix like `.java` and dynamically generates a function that we can use with filter to test for that suffix:

```
filter(fileEndsWith(".java"), files)
```

`fileEndsWith` is a different kind of beast than our usual functions. It’s a **higher-order function** , meaning that it’s a function that takes another function as an argument, or returns another function as its result. Higher-order functions are operations on the datatype of functions; in this case, `fileEndsWith` is a *producer* of functions.

Now let’s use map, filter, and flatten (which we defined above using reduce) to recursively traverse the folder tree:

```
def allFilesIn(folder):
    children = folder.listFiles()
    subfolders = filter(File.isDirectory, children)
    descendants = flatten(map(allFilesIn, subfolders))
    return descendants + filter(File.isFile, children)
```

The first line gets all the children of the folder, which might look like this:

```
["src/client", "src/server", "src/Main.java", ...] 
```

The second line is the key bit: it filters the children for just the subfolders, and then recursively maps `allFilesIn` against this list of subfolders! The result might look like this:

```
[["src/client/MyClient.java", ...], ["src/server/MyServer.java", ...], ...] 
```

So we have to flatten it to remove the nested structure. Then we add the immediate children that are plain files (not folders), and that’s our result.

We can also do the other pieces of the problem with map/filter/reduce. Once we have the list of files we want to extract words from, we’re ready to load their contents. We can use map to get their pathnames as strings, open them, and then read in each file as a list of files:

```
pathnames = map(File.getPath, files)
streams = map(open, pathnames)
lines = map(list, streams)
```

This actually looks like a single map operation where we want to apply three functions to the elements, so let’s pause to create another useful higher-order function: composing functions together.

```
def compose(f, g):
    """Requires that f and g are functions, f:A->B and g:B->C.
    Returns a function A->C by composing f with g.""" 
    return lambda x: g(f(x))
```

Now we can use a single map:

```
lines = map(compose(compose(File.getPath, open), list), files)
```

Better, since we already have three functions to apply, let’s design a way to compose an arbitrary chain of functions:

```
def chain(funcs):
    """Requires funcs is a list of functions [A->B, B->C, ..., Y->Z]. 
    Returns a fn A->Z that is the left-to-right composition of funcs."""
    return reduce(compose, funcs)
```

So that the map operation becomes:

```
lines = map(chain([File.getPath, open, list]), files)
```

Now we see more of the power of first-class functions. We can put functions into data structures and use operations on those data structures, like map, reduce, and filter, on the functions themselves!

Since this map will produce a list of lists of lines (one list of lines for each file), let’s flatten it to get a single line list, ignoring file boundaries:

```
allLines = flatten(map(chain([File.getPath, open, list]), files))
```

Then we split each line into words similarly:

```
words = flatten(map(str.split, lines))
```

And we’re done, we have our list of all words in the project’s Java files! As promised, the control statements have disappeared.

[**→ full Python code for the example**](https://github.com/mit6005/sp16-ex25-words/blob/master/src/words/Words2.py)

# Benefits of abstracting out control

Map/filter/reduce can often make code shorter and simpler, and allow the programmer to focus on the heart of the computation rather than on the details of loops, branches, and control flow.

By arranging our program in terms of map, filter, and reduce, and in particular using immutable datatypes and pure functions (functions that do not mutate data) as much as possible, we’ve created more opportunities for safe concurrency. Maps and filters using pure functions over immutable datatypes are instantly parallelizable — invocations of the function on different elements of the sequence can be run in different threads, on different processors, even on different machines, and the result will still be the same. [MapReduce](https://en.wikipedia.org/wiki/MapReduce) is a pattern for parallelizing large computations in this way.

# First-class functions in Java

We’ve seen what first-class functions look like in Python; how does this all work in Java?

In Java, the only first-class values are primitive values (ints, booleans, characters, etc.) and object references. But objects can carry functions with them, in the form of methods. So it turns out that the way to implement a first-class function, in an object-oriented programming language like Java that doesn’t support first-class functions directly, is to use an object with a method representing the function.

We’ve actually seen this before several times already:

- The `Runnable` object that you pass to a `Thread` constructor is a first-class function, `void run()` .
- The `Comparator<T>` object that you pass to a sorted collection (e.g. `SortedSet` ) is a first-class function, `int compare(T o1, T o2)` .
- The `KeyListener` object that you register with the graphical user interface toolkit to get keyboard events is a bundle of several functions, `keyPressed(KeyEvent)` , `keyReleased(KeyEvent)` , etc.

This design pattern is called a **functional object** or **functor** , an object whose purpose is to represent a function.

## Lambda expressions in Java

Java’s lambda expression syntax provides a succinct way to create instances of functional objects. For example, instead of writing:

```
new Thread(new Runnable() {
    public void run() {
        System.out.println("Hello!");
    }
}).start();
```

we can use a lambda expression:

```
new Thread(() -> {
    System.out.println("Hello");
}).start();
```

On the Java Tutorials page for Lambda Expressions, read [Syntax of Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#syntax) .

There’s no magic here: Java still doesn’t have first-class functions. So you can only use a lambda when the Java compiler can verify two things:

1.  It must be able to determine the type of the functional object the lambda will create. In this example, the compiler sees that the `Thread` constructor takes a `Runnable` , so it will infer that the type must be `Runnable` .
2.  This inferred type must be *functional interface* : an interface with only one (abstract) method. In this example, `Runnable` indeed only has a single method — `void run()` — so the compiler knows the code in the body of the lambda belongs in the body of a `run` method of a new `Runnable` object.

Java provides some [standard functional interfaces](https://docs.oracle.com/javase/8/docs/api/?java/util/function/package-summary.html) we can use to write code in the map/filter/reduce pattern, e.g.:

- [`Function<T,R>`](https://docs.oracle.com/javase/8/docs/api/?java/util/function/Function.html) represents unary functions from `T` to `R`
- [`BiFunction<T,U,R>`](https://docs.oracle.com/javase/8/docs/api/?java/util/function/BiFunction.html) represents binary functions from `T` × `U` to `R`
- [`Predicate<T>`](https://docs.oracle.com/javase/8/docs/api/?java/util/function/Predicate.html) represents functions from `T` to boolean

So we could implement map in Java like so:

```
/**
 * Apply a function to every element of a list.
 * @param f function to apply
 * @param list list to iterate over
 * @return [f(list[0]), f(list[1]), ..., f(list[n-1])]
 */
public static <T,R> List<R> map(Function<T,R> f, List<T> list) {
    List<R> result = new ArrayList<>();
    for (T t : list) {
        result.add(f.apply(t));
    }
    return result;
}
```

And here’s an example of using map; first we’ll write it using the familiar syntax:

```
// anonymous classes like this one are effectively lambda expressions
Function<String,String> toLowerCase = new Function<>() {
    public String apply(String s) { return s.toLowerCase(); }
};
map(toLowerCase, Arrays.asList(new String[] {"A", "b", "C"}));
```

And with a lambda expression:

```
map(s -> s.toLowerCase(), Arrays.asList(new String[] {"A", "b", "C"}));
// --or--
map((s) -> s.toLowerCase(), Arrays.asList(new String[] {"A", "b", "C"}));
// --or--
map((s) -> { return s.toLowerCase(); }, Arrays.asList(new String[] {"A", "b", "C"}));
```

In this example, the lambda expression is just wrapping a call to `String` ’s `toLowerCase` . We can use a *method reference* to avoid writing the lambda, with the syntax `::` . The signature of the method we refer to must match the signature required by the functional interface for static typing to be satisfied:

```
map(String::toLowerCase, Arrays.asList(new String[] {"A", "b", "C"}));
```

In the Java Tutorials, you can read more about [**method references**](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) if you want the details.

Using a method reference (vs. calling it) in Java serves the same purpose as referring to a function by name (vs. calling it) in Python.

# Map/filter/reduce in Java

The [abstract sequence type](https://ocw.mit.edu/ans7870/6/6.005/s16/classes/25-map-filter-reduce/#sequences) we defined above exists in Java as [`Stream`](https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html) , which defines `map` , `filter` , `reduce` , and many other operations.

Collection types like `List` and `Set` provide a `stream()` operation that returns a `Stream` for the collection, and there’s an `Arrays.stream` function for creating a `Stream` from an array.

Here’s one implementation of `allFilesIn` in Java with map and filter:

```
public class Words {
    static Stream<File> allFilesIn(File folder) {
        File[] children = folder.listFiles();
        Stream<File> descendants = Arrays.stream(children)
                                         .filter(File::isDirectory)
                                         .flatMap(Words::allFilesIn);
        return Stream.concat(descendants,
                             Arrays.stream(children).filter(File::isFile));
    }
```

The map-and-flatten pattern is so common that Java provides a `flatMap` operation to do just that, and we’ve used it instead of defining `flatten` .

Here’s `endsWith` :

```
 static Predicate<File> endsWith(String suffix) {
        return f -> f.getPath().endsWith(suffix);
    }
```

Given a `Stream<File> files` , we can now write e.g. `files.filter(endsWith(".java"))` to obtain a new filtered stream.

Look at the [revised Java code for this example](https://github.com/mit6005/sp16-ex25-words/blob/master/src/words/Words3.java) .

You can compare [all three versions](https://github.com/mit6005/sp16-ex25-words/tree/master/src/words) : the familiar Java implementation, Python with map/filter/reduce, and Java with map/filter/reduce.

## Higher-order functions in Java

Map/filter/reduce are of course higher-order functions; so is `endsWith` above. Let’s look at two more that we saw before: `compose` and `chain` .

The `Function` interface provides `compose` — but the implementation is very straightforward. In particular, once you get the types of the arguments and return values correct, Java’s static typing makes it pretty much impossible to get the method body wrong:

```
/**
 * Compose two functions.
 * @param f function A->B
 * @param g function B->C
 * @return new function A->C formed by composing f with g
 */
public static <A,B,C> Function<A,C> compose(Function<A,B> f,
                                            Function<B,C> g) {
    return t -> g.apply(f.apply(t));
    // --or--
    // return new Function<A,C>() {
    //     public C apply(A t) { return g.apply(f.apply(t)); }
    // };
}
```

It turns out that we *can’t* write `chain` in strongly-typed Java, because `List` s (and other collections) must be homogeneous — we can specify a list whose elements are all of type `Function<A,B>` , but not one whose first element is a `Function<A,B>` , second is a `Function<B,C>` , and so on.

But here’s `chain` for functions of the same input/output type:

```
/**
 * Compose a chain of functions.
 * @param funcs list of functions A->A to compose
 * @return function A->A made by composing list[0] ... list[n-1]
 */
public static <A> Function<A,A> chain(List<Function<A,A>> funcs) {
    return funcs.stream().reduce(Function.identity(), Function::compose);
}
```

Our Python version didn’t use an initial value in the `reduce` , it required a non-empty list of functions. In Java, we’ve provided the identity function (that is, *f(t) = t* ) as the identity value for the reduction.