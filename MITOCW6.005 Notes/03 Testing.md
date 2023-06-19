# Validation

The purpose of validation is to uncover problems in a program and thereby increase your confidence in the program’s correctness. Validation includes:

- Verification. Formal reasoning by which we construct a formal proof that a program is correct. Manual verification is unfeasible for complex problems, and automated tools don't yet exist.
- Code review. Having somebody else carefully read your code, and reason informally about it, can be a good way to uncover bugs.
- Testing. Running the program on carefully selected inputs (so as to cover the entire input range/combination) and checking the results.

Even with the best validation, it’s very hard to achieve perfect quality in software. Here are some typical residual defect rates per kloc (one thousand lines of source code):

1 - 10 defects/kloc: Typical industry software.
0.1 - 1 defects/kloc: High-quality validation. The Java libraries might achieve this level of correctness.
0.01 - 0.1 defects/kloc: The very best, safety-critical validation. NASA and similar can achieve this level.

# Why Software Testing is Hard

Exhaustive testing is infeasible. The space of possible test cases is generally too big to cover exhaustively. Imagine exhaustively testing a 32-bit floating-point multiply operation, a*b . There are 2^64 test cases!

Haphazard testing is less likely to find bugs, it also doesn’t increase our confidence in program correctness.

Random or statistical testing doesn’t work well for software. Other engineering disciplines can test small random samples (e.g. 1% of hard drives manufactured) and infer the defect rate for the whole production lot. Physical systems can use many tricks to speed up time, like opening a refrigerator 1000 times in 24 hours instead of 10 years. These tricks give known failure rates (e.g. mean lifetime of a hard drive), but they assume continuity or uniformity across the space of defects. This is true for physical artifacts.

But it’s not true for software. Software behavior varies discontinuously and discretely across the space of possible inputs. The system may seem to work fine across a broad range of inputs, and then abruptly fail at a single boundary point. Stack overflows, out of memory errors, and numeric overflow bugs tend to happen abruptly, and always in the same way, not with probabilistic variation.

# Putting on Your Testing Hat

### When you’re coding, your goal is to make the program work, but as a tester, you want to make it fail.

Be impartial when testing your code, you don't want to assume your code is perfectly written. You want the opposite, you have to be brutal. A good tester beats the program everywhere it might be vulnerable, so that those vulnerabilities can be eliminated.

# Test-first Programming

### Test early and often. Don’t leave testing until the end.

Leaving testing until the end only makes debugging longer and more painful, because bugs may be anywhere in your code. It’s far more pleasant to test your code as you develop it.

In test-first-programming, you write tests before you even write any code. The development of a single function proceeds in this order:

1.  Write a specification for the function.
2.  Write tests that exercise the specification.
3.  Write the actual code. Once your code passes the tests you wrote, you’re done.

The specification describes the input and output behavior of the function. It gives the types of the parameters and any additional constraints on them (e.g. sqrt ’s parameter must be nonnegative). It also gives the type of the return value and how the return value relates to the inputs. In code, the specification consists of the method signature and the comment above it that describes what it does.

Writing tests first is a good way to understand the specification. The specification can be buggy, too — incorrect, incomplete, ambiguous, missing corner cases. Trying to write tests can uncover these problems early.

# Choosing Test Cases by Partitioning

### We want to pick a set of test cases that is small enough to run quickly, yet large enough to validate the program.

To do this, we divide the input space into subdomains, each consisting of a set of inputs. Taken together the subdomains completely cover the input space, so that every input lies in at least one subdomain. Then we choose one test case from each subdomain, and that’s our test suite.

The idea behind subdomains is to partition the input space into sets of similar inputs on which the program has similar behavior. Then we use one representative of each set. This approach makes the best use of limited testing resources by choosing dissimilar test cases, and forcing the testing to explore parts of the input space that random testing might not reach.

### We can also partition into subdomains the output space.

If we need to ensure our tests will explore different parts of the output space. Most of the time, partitioning the input space is sufficient.

## Example: BigInteger.multiply()

BigInteger is a class build into the Java library that can represent integers of any size. The method multiply() will multiply two BigInteger together.

Our specification will be:

```
/**
* @param val another BigIntger
* @return a BigInteger whose value is (this * val).
*/
public BigInteger multiply(BigInteger val)
```

So we have a two-dimensional input space, consisting of all the pairs of integers (a,b). Now let’s partition it. Thinking about how multiplication works, we might start with these partitions:

- a and b are both positive
- a and b are both negative
- a is positive, b is negative
- a is negative, b is positive

There are also some special cases for multiplication that we should check: 0, 1, and -1.

- a or b is 0, 1, or -1

Finally, as a suspicious tester trying to find bugs, we might suspect that the implementor of BigInteger might try to make it faster by using int or long internally when possible, and only fall back to an expensive general representation when the value is too big. So we should definitely also try integers that are very big, bigger than the biggest long.

- a or b is small
- the absolute value of a or b is bigger than Long.MAX_VALUE, the biggest possible primitive integer in Java, which is roughly 2^63.

Putting all this together we have the following cases:

- 0
- 1
- -1
- small positive integer
- small negative integer
- huge positive integer
- huge negative integer

So this will produce 7 × 7 = 49 partitions that completely cover the space of pairs of integers.
To produce the test suite, we would pick an arbitrary pair (a,b) from each square of the grid, for example:

- (a,b) = (-3, 25) to cover (small negative, small positive)
- (a,b) = (0, 30) to cover (0, small positive)
- (a,b) = (2^100, 1) to cover (large positive, 1)
- etc.

The figure below shows how the two-dimensional (a,b) space is divided by this partition, and the points are test cases that we might choose to completely cover the partition.
![multiply-partition.png](../_resources/multiply-partition.png)

## Example: max()

Our specification:

```
/**
 * @param a  an argument
 * @param b  another argument
 * @return the larger of a and b.
 */
public static int max(int a, int b)
```

From the specification, it makes sense to partition this function as:

- a < b
- a = b
- a > b

Our test suit might then be:

- (a, b) = (1, 2) to cover a < b
- (a, b) = (9, 9) to cover a = b
- (a, b) = (-5, -6) to cover a > b

With the image below to illustrate.
![max-partition.png](../_resources/max-partition.png)

# Include Boundaries in the Partition

Bugs often occur at boundaries between subdomains. Some examples:

- 0 is a boundary between positive numbers and negative numbers.
- Emptiness (the empty string, empty list, empty array) for collection types.

These bugs are commonly occasioned by off-by-one mistakes, such as writing <= instead of <, or initializing a counter to 0 instead of 1.

For our max() example, we should change our suit of test cases to.

relationship between a and b

- a < b
- a = b
- a > b
    value of a
- a = 0
- a < 0
- a > 0
- a = minimum integer
- a = maximum integer
    value of b
- b = 0
- b < 0
- b > 0
- b = minimum integer
- b = maximum integer

Now let’s pick test values that cover all these classes:

- (1, 2) covers a &lt; b, a &gt; 0, b > 0
- (-1, -3) covers a > b, a < 0, b < 0
- (0, 0) covers a = b, a = 0, b = 0
- (Integer.MIN\_VALUE, Integer.MAX\_VALUE) covers a < b, a = minint, b = maxint
- (Integer.MAX\_VALUE, Integer.MIN\_VALUE) covers a > b, a = maxint, b = minint

# Two Extremes for Covering the Partition

After partitioning the input space, we can choose how exhaustive we want the test suite to be:

#### Full Cartesian product.

Every legal combination of the partition dimensions is covered by one test case. We did this for the multiply() example.

#### Cover each part.

Every part of each dimension is covered by at least one test case, but not necessarily every combination. We took this approach for the max() example.

# Blackbox and Whitebox Testing

#### Blackbox testing

Blackbox testing means choosing test cases only from the specification, not the implementation of the function. That’s what we’ve been doing in our examples so far. We partitioned and looked for boundaries in multiply and max without looking at the actual code for these functions.

#### Whitebox testing

Whitebox testing means choosing test cases with knowledge of how the function is actually implemented. For example, if the implementation selects different algorithms depending on the input, then you should partition according to those domains. If the implementation keeps an internal cache that remembers the answers to previous inputs, then you should test repeated inputs.

# Documenting Your Testing Strategy

For the example function on the left, on the right is how we can document the testing strategy we worked on in the partitioning exercises above . The strategy also addresses some boundary values we didn’t consider before.

#### Specification

```
/**
 * Reverses the end of a string.
 *
 * For example:
 *   reverseEnd("Hello, world", 5)
 *   returns "Hellodlrow ,"
 *
 * With start == 0, reverses the entire text.
 * With start == text.length(), reverses nothing.
 *
 * @param text    non-null String that will have
 *                its end reversed
 * @param start   the index at which the
 *                remainder of the input is
 *                reversed, requires 0 <=
 *                start <= text.length()
 * @return input text with the substring from
 *               start to the end of the string
 *               reversed
 */
static String reverseEnd(String text, int start)
```

#### Documenting the strategy

```
/*
 * Testing strategy
 *
 * Partition the inputs as follows:
 * text.length(): 0, 1, > 1
 * start:         0, 1, 1 < start < text.length(),
 *                text.length() - 1, text.length()
 * text.length()-start: 0, 1, even > 1, odd > 1
 *
 * Include even- and odd-length reversals because
 * only odd has a middle element that doesn't move.
 *
 * Exhaustive Cartesian coverage of partitions.
 */
```

#### Document how each test case was chosen

```
// covers test.length() = 0,
//        start = 0 = text.length(),
//        text.length()-start = 0
@Test public void testEmpty() {
    assertEquals("", reverseEnd("", 0));
}

// ... other test cases ...
```

# Coverage

One way to judge a test suite is to ask how thoroughly it exercises the program. This notion is called coverage . Here are three common kinds of coverage:

- Statement coverage: is every statement run by some test case?
- Branch coverage: for every if or while statement in the program, are both the true and the false direction taken by some test case?
- Path coverage: is every possible combination of branches — every path through the program — taken by some test case?

Branch coverage is stronger (requires more tests to achieve) than statement coverage, and path coverage is stronger than branch coverage. In industry, 100% statement coverage is a common goal, but even that is rarely achieved due to unreachable defensive code (like “should never get here” assertions). 100% branch coverage is highly desirable, and safety critical industry code has even more arduous criteria (e.g., “MCDC,” modified decision/condition coverage). Unfortunately 100% path coverage is infeasible, requiring exponential-size test suites to achieve.

A standard approach to testing is to add tests until the test suite achieves adequate statement coverage: i.e., so that every reachable statement in the program is executed by at least one test case.

# Unit Testing and Stubs

A well-tested program will have tests for every individual module that it contains. A test that tests an individual module, in isolation if possible, is called a unit test. Testing modules in isolation leads to much easier debugging. When a unit test for a module fails, you can be more confident that the bug is found in that module, rather than anywhere in the program.

The opposite of a unit test is an integration test, which tests a combination of modules, or even the entire program. If all you have are integration tests, then when a test fails, you have to hunt for the bug. It might be anywhere in the program. Integration tests are still important, because a program can fail at the connections between modules. For example, one module may be expecting different inputs than it’s actually getting from another module. But if you have a thorough set of unit tests that give you confidence in the correctness of individual modules, then you’ll have much less searching to do to find the bug.

Isolating a higher-level module is possible if we write stub versions of the modules that it calls. For example, a stub for getWebPage() wouldn’t access the internet at all, but instead would return mock web page content no matter what URL was passed to it.

# Automated Testing and Regression Testing

Once you have test automation, it’s very important to rerun your tests when you modify your code. This prevents your program from regressing — introducing other bugs when you fix new bugs or add new features. Running all your tests after every change is called regression testing .

Whenever you find and fix a bug, take the input that elicited the bug and add it to your automated test suite as a test case. This kind of test case is called a regression test . This helps to populate your test suite with good test cases. Remember that a test is good if it elicits a bug — and every regression test did in one version of your code! Saving regression tests also protects against reversions that reintroduce the bug. The bug may be an easy error to make, since it happened once already.

In practice, these two ideas, automated testing and regression testing, are almost always used in combination.
Regression testing is only practical if the tests can be run often, automatically. Conversely, if you already have automated testing in place for your project, then you might as well use it to prevent regressions. So automated regression testing is a best-practice of modern software engineering.

This idea also leads to test-first debugging . When a bug arises, immediately write a test case for it that elicits it, and immediately add it to your test suite. Once you find and fix the bug, all your test cases will be passing, and you’ll be done with debugging and have a regression test for that bug.