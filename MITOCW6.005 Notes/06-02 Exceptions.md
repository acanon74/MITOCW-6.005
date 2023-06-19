A method’s signature — its name, parameter types, return type — is a core part of its specification, and the signature may also include exceptions that the method may trigger.

#### What is an exception?
An exceptions generally indicate bugs in your code, and the information displayed by Java when the exception is thrown can help you find and fix the bug. ArrayIndex, OutOfBounds and NullPointerException are probably the most common exceptions of this sort.


# Exceptions for special results
Exceptions are not just for signaling bugs. They can be used to improve the structure of code that involves procedures with special results.

A common way to handle special results is to return special values. Lookup operations in the Java library are often designed like this: you get an index of -1 when expecting a positive integer, or a null reference when expecting an object. This approach is OK if used sparingly, but it has two problems:

- First, it’s tedious to check the return value. This adds complexity to the test design for a given method.
- Second, it’s easy to forget to do it. We might ignore it and never handle this value, giving room to more bugs down the line.
 
 # Checked and unchecked exceptions
 We’ve seen two different purposes for exceptions: special results and bug detection. **As a general rule, you’ll want to use checked exceptions to signal special results and unchecked exceptions to signal bugs.**
 
 Terminology:
 
 - **Checked exceptions** are called that because they are checked by the compiler. This is very useful, because it ensures that exceptions that are expected to occur will be handled.
	- If a method might throw a checked exception, the possibility must be declared in its signature. NotFoundException would be a checked exception, and that’s why the signature ends throws NotFoundException.
	- If a method calls another method that may throw a checked exception, it must either handle it, or declare the exception itself, since if it isn’t caught locally it will be propagated up to callers.

- **Unchecked exceptions**, in contrast, are used to signal bugs. These exceptions are not expected to be handled by the code except perhaps at the top level. We wouldn’t want every method up the call chain to have to declare that it (might) throw all the kinds of bug-related exceptions that can happen at lower call levels.

# Throwable hierarchy

```Throwable``` is the class of objects that can be thrown or caught. Throwable ’s implementation records a stack trace at the point where the exception was thrown, along with an optional string describing the exception. Any object used in a throw or catch statement, or declared in the throws clause of a method, must be a subclass of Throwable .

```Error``` is a subclass of Throwable that is reserved for errors produced by the Java runtime system, such as StackOverflowError and OutOfMemoryError. **Errors should be considered unrecoverable, and are generally not caught.**

- RuntimeException , Error , and their subclasses are unchecked exceptions. The compiler doesn't require them to be declared in the throws clause, nor caught or declared by the caller of such a method.
- All other throwables — Throwable , Exception , and all of their subclasses except for those of the RuntimeException and Error lineage — are checked exceptions. The compiler requires these to be caught or declared by the caller method.
 
When you define your own exceptions, you should either subclass RuntimeException (to make it an unchecked exception) or Exception (to make it checked). 

# Exception design considerations

The rule we have given — use checked exceptions for special results (i.e., anticipated situations), and unchecked exceptions to signal bugs (unexpected failures) — makes sense, but it isn’t the end of the story. The snag is that exceptions in Java aren’t as lightweight as they might be.

Aside from the performance penalty, exceptions in Java incur another (more serious) cost: they’re a pain to use, in both method design and method use. If you design a method to have its own (new) exception, you have to create a new class for the exception. If you call a method that can throw a checked exception, you have to wrap it in a try - catch statement (even if you know the exception will never be thrown). This latter stipulation creates a dilemma. 

Suppose, you’re designing a queue abstraction. Should popping the queue throw a checked exception when the queue is empty? Suppose you want to support a style of programming in the client in which the queue is popped until the exception is thrown. So you choose a checked exception. Now some client wants to use the method in a context in which, immediately prior to popping, the client tests whether the queue is empty and only pops if it isn’t. Maddeningly, that client will still need to wrap the call in a try - catch statement.


## Takeaways from exceptions

1. You should use an unchecked exception only to signal an unexpected failure.
2. Otherwise you should use a checked exception.

# Summary
- **Safe from bugs** A good specification clearly documents the mutual assumptions that a client and implementor are relying on. Bugs often come from disagreements at the interfaces, and the presence of a specification reduces that. 
- **Easy to understand** A short, simple spec is easier to understand than the implementation itself, and saves other people from having to read the code.
- **Ready for change** Specs establish contracts between different parts of your code, allowing those parts to change independently as long as they continue to satisfy the requirements of the contract.