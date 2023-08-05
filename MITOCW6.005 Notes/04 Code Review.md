Code review has two purposes:

- **Improving the code.** Finding bugs, anticipating possible bugs, checking the clarity of the code, and checking for consistency with the project’s style standards.
- **Improving the programmer.** Code review is an important way that programmers learn and teach each other, about new language features, changes in the design of the project or its coding standards, and new techniques. In open source projects, particularly, much conversation happens in the context of code reviews.

# Style Standards

As a single programmer, as long as you maintain consistency and readability, it is only a matter of taste. However, when working on a team (company or open-source project) it is important to follow the pre-established conventions.

# Don’t Repeat Yourself

#### DRY - Don't Repeat Yourself

- Duplicated code is a risk to safety. If you have identical or very similar code in two places, then the fundamental risk is that there’s a bug in both copies, and some maintainer fixes the bug in one place but not the other.
- For the same reason it affects maintainability. Since changing the behavior in a part of the code won't affect the other "implementations" of the same code somewhere else.

# Comments Where Needed

Good software developers write comments in their code, and do it judiciously. Good comments should make the code easier to understand, safer from bugs (because important assumptions have been documented), and ready for change.

One kind of crucial comment is a specification, which appears above a method or above a class and documents the behavior of the method or class. In Java, this is conventionally written as a Javadoc comment, meaning that it starts with /** and includes @ -syntax, like @param and @return for methods.

Another crucial comment is one that specifies the provenance or source of a piece of code that was copied or adapted from elsewhere. One reason for documenting sources is to avoid violations of copyright.

##### Some comments are bad and unnecessary. Direct transliterations of code into English, for example, do nothing to improve understanding.

# Fail Fast

Failing fast means that code should reveal its bugs as early as possible. The earlier a problem is observed (the closer to its cause), the easier it is to find and fix.

# Avoid Magic Numbers

The only two commonly used constants are 0 and 1. **All other constants are called magic because they appear as if out of thin air with no explanation.** This can be resolved either by defining the constant with a well named variable or using a comment to explain the meaning of the value.

# One Purpose For Each Variable

Don’t reuse parameters, and don’t reuse variables. Instead, introduce them freely, give them good names, and just stop using them when you stop needing them.

Method parameters, in particular, should generally be left unmodified. (This is important for being ready-for-change — in the future, some other part of the method may want to know what the original parameters of the method were, so you shouldn’t blow them away while you’re computing.)

Use keywords to define constants wherever possible. (final in Java, UPPERCASE naming in Python).

Do not use variables named temp, tmp, etc. Their naming doesn't mean anything since, once outside scope, they will be deleted anyways. If you must use temporal variables use proper names such as counter, total, etc.

# Use Good Names

Good method and variable names are long and self-descriptive. Comments can often be avoided entirely by making the code itself more readable, with better names that describe the methods and variables.

# Use Whitespace to Help the Reader

- Use consistent indentation.
- Put spaces within code lines to make them easy to read.
- Never use tab characters for indentation, only space characters. Different software treat tab characters different, this might hurt displaying your code.

# Don’t Use Global Variables

Abusing of global variables arises problems, some of them are:

- Non-locality - Source code is easiest to understand when the scope of its individual elements are limited. Global variables are modifiable everywhere, which makes it difficult to remember every use of them.
- No Access Control or Constraint Checking - A global variable can be get or set by any part of the program, and any rules regarding its use can be easily broken or forgotten.
- Concurrency issues - If global variables can be accessed by multiple threads of execution, synchronization is necessary (and too-often neglected).

Read more at: http://wiki.c2.com/?GlobalVariablesAreBad

# Methods Should Return Results, not Print Them

- Only high-level parts of the program should ever interact with the user directly.
- You might want to use a given method somewhere else where you need the actual output and the information printed.