# Compiling

> The process of converting abstract tokens into bytecode

* Given Lexed + Formatted code
* Each statement contains parameters in the form of expressions as well as constructs to indicate what kind of conditional operation to perform.
* Create a pointer type which holds references to one of two things: values or functions
  * Values are literally values - A primitive type. 
    * String
    * Int
    * Float
    * Data
    * Boolean
  * Functions - A signature optionally accepting information and optionally returning it
* Flatten the nested structure with jumps etc