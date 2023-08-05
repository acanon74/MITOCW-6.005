# Reproduce the Bug

Start by finding a small, repeatable test case that produces the failure. If the bug was found by regression testing, then you’re in luck; you already have a failing test case in your test suite. If the bug was reported by a user, it may take some effort to reproduce the bug. For graphical user interfaces and multithreaded programs, a bug may be hard to reproduce consistently if it depends on timing of events or thread execution.


Furthermore, after you’ve successfully fixed the bug, you’ll want to add the test case to your regression test suite, so that the bug never crops up again. Once you have a test case for the bug, making this test work becomes your goal.

# Understand the Location and Cause of the Bug

To localize the bug and its cause, you can use the scientific method:

- **Study the data**. Look at the test input that causes the bug, and the incorrect results, failed assertions, and stack traces that result from it.

- **Hypothesize**. Propose a hypothesis, consistent with all the data, about where the bug might be, or where it cannot be. It’s good to make this hypothesis general at first.

- **Experiment**. Devise an experiment that tests your hypothesis. It’s good to make the experiment an observation at first – a probe that collects information but disturbs the system as little as possible.

- **Repeat**. Add the data you collected from your experiment to what you knew before, and make a fresh hypothesis. Hopefully you have ruled out some possibilities and narrowed the set of possible locations and reasons for the bug.


## 1. Study the Data

One important form of data is the stack trace from an exception. Practice reading the stack traces that you get, because they will give you enormous amounts of information about where and what the bug might be.

The process of isolating a small test case may also give you data that you didn’t have before. You may even have two related test cases that bracket the bug in the sense that one succeeds and one fails. For example, maybe mostCommonWords("c c, b") is broken, but mostCommonWords("c c b") is fine.

## 2. Hypothesize

It helps to think about your program as modules, or steps in an algorithm, and try to rule out whole sections of the program at once.

## 3. Experiment

A good experiment is a gentle observation of the system without disturbing it much. It might be:

- **Run a different test case**. The test case reduction process discussed above used test cases as experiments.

- Insert a **print statement or assertion** in the running program, to check something about its internal state.

- Set a **breakpoint** using a debugger, then single-step through the code and look at variable and object values.

It’s tempting to try to insert *fixes* to the hypothesized bug, instead of mere probes. This is almost always the wrong thing to do. First, it leads to a guessing game, which produces awful, hard-to-understand code. Second, your fixes may just mask the true bug without actually removing it.


# Other tips

- **Bug localization by binary search**. Debugging is a search process, and you can sometimes use binary search to speed up the process. To do a binary search, you would divide this workflow in half, perhaps guessing that the bug is found somewhere between the first helper method call and the second, and insert probes (like breakpoints, print statements, or assertions) there to check the results. From the answer to that experiment, you would further divide in half.

- **Prioritize your hypotheses**. When making your hypothesis, you may want to keep in mind that different parts of the system have different likelihoods of failure. For example, old, well-tested code is probably more trustworthy than recently-added code. Java library code is probably more trustworthy than yours. 

- **Swap components**. If you have another implementation of a module that satisfies the same interface, and you suspect the module, then one experiment you can do is to try swapping in the alternative. For example, if you suspect your binarySearch() implementation, then substitute a simpler linearSearch() instead. You can waste a lot of time swapping unfailing components, however, so don’t do this unless you have good reason to suspect a component.

- **Make sure your source code and object code are up to date**.

- **Get help**. Get a colleague or classmate to help you. 

- **Sleep on it**. If you’re too tired, you won’t be an effective debugger. **Trade latency for efficiency.**

# Fix the Bug

Once you’ve found the bug and understand its cause, the third step is to devise a fix for it. Do not slap a patch on it and move on. Ask yourself whether the bug was a coding error, like a misspelled variable or interchanged method parameters, or a design error, like an underspecified or insufficient interface. Design errors may suggest that you step back and revisit your design.

- Think also whether the bug has any relatives. If I just found a divide-by-zero error here, did I do that anywhere else in the code? 
- Also consider what effects your fix will have. Will it break any other code?


#### Finally, after you have applied your fix, add the bug’s test case to your regression test suite, and run all the tests to assure yourself that (a) the bug is fixed, and (b) no new bugs have been introduced.
