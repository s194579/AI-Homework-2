# Introduction to Artificial Intelligence - 2180
*AI-Homework-2*

**Installation:**
1. Compile the project source code (we used JDK version 13)
2. Run the main class, Application.java
3. You will know that it works if your terminal prints "Welcome..."
4. We used the IntelliJ IDE. If you have this IDE you can open the project and run it from there.

**Instructions of Use:**

Once the program is running you will be able to add propositions to your belief base.
It is important that you follow our syntax carefully. Below is a list of our syntax of our logical connectives:

1. AND:             &
2. OR:              |
3. NOT:             !
4. IMPLICATION:     =>
5. BI-IMPLICATION:  <=>

It is important that you provide these logical connectives with other propositions, and give them some literals.
Literals can just be things like letters, but any string should work.

A simple example of use would be: A | B => A

If you want to specify that two propositions should be connected you can use parentheses.

Example of the use of parentheses: (A <=> (B & C)) => !C

Once you have written your proposition, simply press Enter. 
This will store the proposition in the belief base if it was valid. 
The terminal will let you know if you made an error such as writing a syntactically incorrect proposition.
However, we did not spend too much time on this, so the syntax checker is not perfect.
Try not to write syntactically incorrect stuff on purpose. 

When you have entered one or more propositions, you can ask the belief base to print it on CNF form by inputting "cnf".
This will give you a CNF version of all your propositions individually. 
They are written to CNF form individually for ease of readability, but you may imagine them being connected as one.
Once you are done with the program, you may close it by inputting "exit".

**Project Members:**
- Kasper Jørgensen
- Peter Revsbech
- Tobias van Deurs Lundsgaard
- Connor Wall
