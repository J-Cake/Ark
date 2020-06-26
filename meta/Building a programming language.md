# Building a programming language

## Overview

1. **What is a programming language**

   1. A set of characters whose arrangement causes work to be done
   2. Importantly, allows work to be done under certain conditions.
2. **What defines a programming language (what is / isn’t one)**

   1. Turing completeness
3. **Different types of programming languages (compiled/interpreted/transpiled)**

   1. Interpreted (scripting) language uses a virtual machine and is memory managed
   2. Compiled (programming) language runs on hardware 
4. **How does a computer understand source code?**

   1. Source Code - A group of characters
   2. Lex
      1. Find and label groups of characters
      2. Category map
   3. Format
      1. Arrange characters into single-level lists of instructions embedded in other lists. (Flatten code)
         1. Blocks are just instructions containing executable / parsable code. Executed conditionally.
      2. Executing instructions while obeying an instructional order is difficult. Hence statement needs to be rearranged to make executing an ordered list easier - Shunting Yard
      3. Group tokens into executable statements
   4. Execute
      1. Define and declare scopes
      2. Memory is managed on said scopes.
      3. To find variable value, move up scope until it is found.
      4. If a block is to be executed, its code will pass through the entire interpreter once again and execute it conditionally, creating a recursive, tree-like system.
5. **Why this works**

   1. Code can be arbitrarily deep and needs stack management. Recursive systems allow us to only have to manage current scope. 
   2. Much cleaner code base
   3. More extensible
6. **Implementing**

   1. **Emphasise planning** - use flow chars

      1. can become highly complex and recursive functions are by nature very difficult to understand.
      2. keeps you on track. 
      3. allow early catching of mistakes
      4. allow other components to be designed around existing structure
      5. don’t have to think up APIs and interfaces as the program is being built

   2. **Construct separate flow charts for each execution stage**

   3. **Explain language of choice and best language to use**

      1. Using JavaScript for readability reasons smaller code base size.
      2. Preferably use Java, C or C++ for speed and compiled nature (run on hardware, no interface meaning as little interruption as possible)

   4. **Define language grammar**

      1. Static typing
      2. functional first class citizens 
      3. operators
      4. indent based
      5. Object oriented language
      6. comment scheme
      7. i/o system (imports / exports etc)

   5. **Define rudimentary standard library** (language should work without stdlib)

      1. Stdio system
      2. Threading

   6. **Implement testing suite**

      1. Needs to be able to check different stages

   7. – next video –

   8. **Writing Code**

      1. **Lexer**
         1. Create grammar object
            1. Categorised
            2. Token name maps to array of syntactic values
         2. Create Token class
            1. Token Category (Enum)
            2. Token Function (Enum)
            3. File path
            4. Line number
            5. Char number
            6. Stack history
         3. Iteratively analyse accumulated character list
         4. Look up grammar
         5. Add token to token list
         6. If token is a block, copy its contents into separate `block` token
         7. return token list.
      2. – next video –
      3. **Formatter**
         1. Iterate over token list again and attempt to extract syntactic patterns (expression, declaration, invocation, etc.)
         2. If block token is encountered, construct new block class with contents from lex, then parse and insert it into token list.
         3. Convert statements to postfix
         4. return list of statements
      4. – next video –
      5. **Execution**
         1. Function takes Parent scope (`null` if root) token list and traceback object (containing information including current execution point’s source origin)
         2. Create new Scope object whose parent is received scope
         3. **Iteratively** (to retain token order) execute tasks. 
         4. If current task is block, observe condition, execute according to condition by passing block’s contents through interpreter once again. (Origin of Executer’s traceback object)
7. – next video –
8. **Announce Std lib**
9. **Extra notes on implementing  custom functionality in parent language**
   1. Objects can be generated in parent language and added to program source (as all tokens are dealt with in parent language, adding it to the token array is not that difficult)
10. **Write simple tests for each stage**
11. **Demonstrate language with stdlib**
12. **Conclusion**
    1. Reiterate interpreter structure
    2. Emphasise planning