# Mutability

Some objects are immutable: once created, they always represent the same value. Other objects are mutable: they have methods that change the value of the object.

String is an example of an immutable type. A String object always represents the same string. StringBuilder is an example of a mutable type. It has methods to delete parts of the string, insert or replace characters, etc.

Since String is immutable, once created, a String object always has the same value. To add something to the end of a String, you have to create a new String object. By contrast, StringBuilder objects are mutable. This class has methods that change the value of the object, rather than just returning new values.

The difference between mutability and immutability doesn’t matter much when there’s only one reference but there are big differences in how they behave when there are other references to the object. For example, when another variable t points to the same String object as s, and another variable tb points to the same StringBuilder as sb, then the differences between the immutable and mutable objects become more evident:
![Screenshot 2023-07-09 035117.png](../_resources/Screenshot%202023-07-09%20035117.png)

Why do we need such difference? Consider the case of concatenate a large number of strings together. Then:

Using immutable strings, this makes a lot of temporary copies — the first number of the string ( "0" ) is actually copied n times in the course of building up the final string, the second number is copied n-1 times, and so on. It actually costs O(n^2) time just to do all that copying, even though we only concatenated n elements.

StringBuilder is designed to minimize this copying. It uses a simple but clever internal data structure to avoid doing any copying at all until the very end, when you ask for the final String with a toString() call:

#### Getting good performance is one reason why we use mutable objects. Another is convenient sharing: two parts of your program can communicate more conveniently by sharing a common mutable data structure.

# Risk of mutation

Why do we even use immutable object then? The answer is that immutable types are safer from bugs, easier to understand, and more ready for change. Mutability makes it harder to understand what your program is doing, and much harder to enforce contracts.

### Passing or returning mutable values

- Passing mutable objects around is a latent bug. One part of the program might change the variable, while other part expected it to be consistent.
- Not easy to understand. Imagine the first point happens in a large codebase, and the callers are far apart.

When returning a variable, we can use defensive copying. We return a copy of our result, and keep the original reference for our own, or later use. So that the callers can do whatever they need with the result while not affecting us. But defensive copying forces the method to do extra work and use extra space for every client — even if 99% of the clients never mutate the data it returns. We may end up with lots of copies of the result throughout memory.

## Aliasing is what makes mutable types risky

Actually, using mutable objects is just fine if you are using them entirely locally within a method, and with only one reference to the object. What leads to aforementioned problems is having multiple references, for the same mutable object.

# Specifications for mutating methods

At this point it should be clear that when a method performs mutation, it is crucial to include that mutation in the method’s spec.

**If the effects do not explicitly say that an input can be mutated, then in 6.005 we assume mutation of the input is implicitly disallowed.** Here’s an example of a mutating method:

```
static void sort(List<String> lst)
  requires: nothing
  effects:  puts lst in sorted order, i.e. lst[i] <= lst[j]
              for all 0 <= i < j < lst.size()
```

And an example of a method that does not mutate its argument:

```
static List<String> toLowerCase(List<String> lst)
  requires: nothing
  effects:  returns a new list t where t[i] = lst[i].toLowerCase()
```

# Mutation and contracts

## Mutable objects can make simple contracts very complex

This is a fundamental issue with mutable data structures. Multiple references to the same mutable object may mean that multiple places in your program — possibly widely separated — are relying on that object to remain consistent.

**To put it in terms of specifications, contracts can’t be enforced in just one place anymore.** Contracts involving mutable objects now depend on the good behavior of everyone who has a reference to the mutable object.

## Mutable objects reduce changeability

Mutable objects make the contracts between clients and implementers more complicated, and reduce the freedom of the client and implementer to change. In other words, using objects that are allowed to change makes the code harder to change.