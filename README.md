# MITOCW-6.005
My solutions and notes for the MITOCW course 6.005 "Software Construction".


My notes can be found inside the "MITOCW6.005 Notes" folder. I am aware that they are mostly poorly summarized or straight up truncated copies of the original notes available at "https://ocw.mit.edu/ans7870/6/6.005/s16/". I did not intend for them to serve more than a quick refresher for the future, so if you are planning to take this course, I strongly advise you to read the original notes instead.

Before starting:

In the version of the course I am following, the first problem set relies on a now depricated (thanks Elon) Twitter API. So although, it is still possible to send the request to MIT's server, we do not receive any information. I thought about caching the tweets in my machine, and just giving them as input simulating an actual input as much as possible, however, I could not find any information regarding the format of the input. Neither on the original MIT website nor in solutions found online (actually I couldn't find a repository that covered the same course edition as mine). 

So, instead of skipping the problem set, I decided to solve a different PS from an older version of the course. Such PS proposed the following problem:

## Problem Set 1: Test-first programming

Write black-box tests based on specifications, prior to writing code. Wrote classes to convert digits of pi between bases, express digits in base-26, convert base-26 digits into characters, and ultimately find English words in the resulting string of characters.

http://mit.usiu.ac.ke/courses/electrical-engineering-and-computer-science/6-005-elements-of-software-construction-fall-2011/assignments/MIT6_005F11_ps1.pdf

Pretty fun project. It serves the purpose of forcing you into thinking the Java way. Kind of challenging at first, since you might be able to think dozens of ways to write a piece of code in Python or C++, taking advantage of some features in those languages (without static typing in Python, or pointers and vectors in C++). Not because this are not present in Java (in fact they probably are), but because you are still a beginner and have no idea of similar or better features present in Java. 
