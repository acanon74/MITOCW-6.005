# First Defense: Make Bugs Impossible

##### The best defense against bugs it's to make them impossible by design.

Take advantage of language features such as:

- **Static checking:** Static checking eliminates many bugs by catching them at compile time.
- **Dynamic checking:** For example, Java makes array overflow bugs impossible by catching them dynamically. If you try to use an index outside the bounds of an array or a List, then Java automatically produces an error.
- **Immutability:** An immutable type is a type whose values can never change once they have been created.

# Second Defense: Localize Bugs

If we can’t prevent bugs, we can try to localize them to a small part of the program, so that we don’t have to look too hard to find the cause of a bug.

### Fail Fast Strategy: the earlier a problem is observed (the closer to its cause), the easier it is to fix.

Checking preconditions is an example of defensive programming. Real programs are rarely bug-free. Defensive programming offers a way to mitigate the effects of bugs.

# Assertions

It is common practice to define a procedure for these kinds of defensive checks, usually called assert :

```
assert x >= 0;
```

This approach abstracts away from what exactly happens when the assertion fails. We only care about the fact that something unexpected happened at that point. In reality, the failed assert might exit, it might record an event in a log file, etc.

Assertions have the added benefit of documenting an assumption about the state of the program at that point. To somebody reading your code, assert (x >= 0) says “at this point, it should always be true that x >= 0.” Unlike a comment, however, an assertion is executable code **that enforces the assumption at runtime.**

# What to Assert

- **Method argument requirements**, for example, checking that the input of sqrt is nonnegative.
- **Method return value requirements**, this is called a self-check. For Example, sqrt might square its result to check if it close enough to the input.
- **Covering all cases.** If a conditional statement does not cover all the possible cases, it is good practice to use an assertion to block the illegal cases.

#### When should you write runtime assertions? As you write the code, not after the fact. When you’re writing the code, you have the invariants in mind. If you postpone writing assertions, you’re less likely to do it, and you’re liable to omit some important invariants.

# What Not to Assert

Runtime assertions are not free. They can clutter the code, so they must be used judiciously. Avoid trivial assertions, just as you would avoid uninformative comments. Avoid things such as:

```
x = y + 1;
assert x == y+1;
```

This assertion doesn’t find bugs in your code. It finds bugs in the compiler or Java virtual machine, which are components that you should trust.

**Never use assertions to test conditions that are external to your program**, such as the existence of files, the availability of the network. External failures are not bugs, and there is no change you can make to your program in advance that will prevent them from happening. *External failures should be handled using exceptions instead.*

Many assertion mechanisms are designed so that assertions are executed only during testing and debugging, and turned off when the program is released to users. The advantage of this approach is that you can write expensive assertions that would otherwise degrade the performance of your program.

The correctness of your program should never depend on whether or not the assertion expressions are executed. In particular, asserted expressions should not have side-effects.

# Incremental Development

A great way to localize bugs to a tiny part of the program is incremental development. Build only a bit of your program at a time, and test that bit thoroughly before you move on. This guarantees that a bug is in the newly written code rather than in old, already tested code.

Two techniques mentioned here are:

- **Unit testing:** when you test a module in isolation, you can be confident that any bug you find is in that unit – or maybe in the test cases themselves.
- **Regression testing:** when you’re adding a new feature to a big system, run the regression test suite as often as possible. If a test fails, the bug is probably in the code you just changed.

# Modularity & Encapsulation

- **Modularity.** Modularity means dividing up a system into components, or modules, each of which can be designed, implemented, tested, reasoned about, and reused separately from the rest of the system. The opposite of a modular system is a monolithic system – big and with all of its pieces tangled up and dependent on each other. Example: a big program entirely written in a main() file takes a monolithic approach.
    
- **Encapsulation.** Encapsulation means building walls around a module (a hard shell or capsule) so that the module is responsible for its own internal behavior, and bugs in other parts of the system can’t damage its integrity. Example: access control, using public and private to control de accessibility of variables and methods.
    
- **Minimizing the scope of variables.**
    
    - Declare a variable only when you first need it, and in the innermost curly-brace block that you can.
        
    - Avoid global variables.