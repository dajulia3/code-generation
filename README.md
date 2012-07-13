code-generation
===============

Code generation excercise. Generates Java Byte code for the decaffeinated java language. Scanner (lexical analyzer) was generated using JFLEX. Parser/Grammar was written using CUP (which was used to generate an AST in Java). I wrote a VERY complete test harness for this project using JUnit and used a TDD approach. I didn't use mock objects or anything to stub out dependencies in the tests, but they were all still EXTREMELY helpful. Apparently this final project was one of the few that actually didn't crash, and one of even fewer that generated correct output.

Some Notes on the AWESOME tests and why they're really quite clever:
I actually used JUnit both to test and log the output of tests into test/testASTLog.txt.
I used a customized TestWatchman to assist with both of these tasks.

The real cleverness lies in the helper methods. 
First, I create a temporary test file from the decaffeinated java test input code in the JUnit Tests using a helper method.Then I generated reference output using the reference compiler (redirecting its output string from stdout to my input stream, and captured that input in a String. 

Similarly, I captured my compiler's output by redirecting my compiler's output from stdout to my input stream.
I then had a few functions that helped Log the AST structure into a test file, 
and to run each individual test, comparing the reference output to my compiler's output.

In that way, I was able to test small chunks of decaffeinated java code, corresponding to single Nodes in the AST,
and make sure that for each Node, my compiler's output matched the test compiler's output EXACTLY.
I was using eclipse, so eclipse shows you a nice diff when you assert that an input should match some string (the reference compiler's output) but failed to do so. By using the diffs, I was able to develop my CORRECT solution in a fraction of the time that the other students took to develop mostly incorrect solutions.

Why did I write this rant? Because people allllwwwayyysss doubt TDD when they see the time that it takes up-front to write an AWESOME test harness... but when you have a great testing framework in place, it speeds up your development like mad (and you can be a lot more confident in your code's correctness).
