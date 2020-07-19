# Ark
Simple Statically typed, Functional, Object-Oriented Scripting Language

## What is Ark?

Ark is a scripting language designed to replace JavaScript and subsequently PWA. 
Ark uses an execution method similar in nature to Java. The code is lexically understood, then compiled into Bytecode where a Virtual Machine executes Assembly-like instructions.

Ark is designed around similar philosophies as python - simplicity and friendliness. 
Unlike JavaScript, Ark is designed from the ground up to create entire applications without relying on developers to implement styles, themes and conventions. 
The philosophy originates from KDE - a signle theming engine can customise appearances of entire applications. 
It is not up to developers to design applications to look a certain way, rather users are encouraged to customise their experience to suit their liking. 
Ark applications should respect a user's preference for uniformity in design across any application without requiring extensive theming libraries to attempt to match a user's environment.

Instead Ark provides a Graphical library such that all applications that use it can be created completely and consistently. 
This mentality extends beyond localised applications however, and as previously mentioned, should replace PWAs in the future. 
Due to its Assembly-like bytecode, Ark will be able to be compiled indirectly to machine code such that native level performance can be achieved. 

## Ark

Ark is statically typed meaning the programmer must specify any and all information used throughout a program. However, due to its compiled nature, cannot be runtime-garbage-collected. 
Instead data is managed lexically. Take the following snippet:

```

int x = 1

function b(int)
  return x + 1

block
  int x = 2
  stdout.write(b())
  
stdout.write(b())

```

This would produce the following output:

```
3
2
```

In Garbage-Collected languages such as JavaScript, both instances of variables would remain existant until they are no longer in use. 
In the above example, the variable `x` exists in two scopes: `global` and `block`. If this snippet were translated into JavaScript, it would yield the output 

```
2
2
```

as `b` is defined on the `global` scope, and hence any reference to `x` is read as such.
In contrary, a variable declared within a scope will *override* variables declared outside it, as a result of how the bytecode is interpreted. 
The variable `x` declared on the `global` scope is overridden by that declared on `block` as the body of the `block` owns a scope of its own, where a variable is declared. 
A variable's value is that which is first found when marching up the scope tree. 
A walkthrough: `b` is invoked, references `x`. The lookup searches for `x` on scope `b`. It is not found. 
`b` was called within scope `block`. `x` exists on scope `block`, its value is returned.

This allows the omission of a Garbage-Collector as any variables declared on a given scope become dereferenced, the instance the last statement has finished executing on that scope. 
scope `block`'s instance of `x` no longer exists after the `stdout.write(x)` has finished. 
This allows a program to focus solely on the execution of the code it has been fed, rather than on meta tasks, so slow it down.

### Syntax

Ark uses a syntax similar to Ruby's. It is indent-based rather than brace-based, however by the design of the Lexer, can easily be configured to use alternate synacies.
The indent-based nature of the code allows a program to be layed out far more cleanly that programs in Java or JavaScript - where several lines are wasted on single characters of closing braces.
Reading such code requires one to follow the matching brace, when simply indenting the code will suffice.

Ark is also designed to function similarly to JavaScript - as it is designed to replace it, it should be as easy as possible to migrate to it. 
To some's annoyance, JavaScript provides no type safety and hacky solutions such as TypeScript (although brilliant in implementation), do not completely resolve the issue.
Instead, rather than using Compile-Time type checking which cannot catch runtime type mismatches, Ark requires a programmer to specify a variable's type. This however can be omitted when a variable's type is declared as `any`. 
In such situations, a variable's type will *snap* to the type its first assignment is and will throw an error for any assignment following which does not comply with that type. 

For completely variable types, a programmer can use `var` in place of a type to signify a variable's type can *vary*.

### Paradigm

Ark is Object-oriented meaning everything except for primitive values are objects. 
Objects themselves are primitive values, and unlike JavaScript, where each object has a prototype object (thus an infinite loop), objects' properties remain fixed once they have been declared.
In other words, objects cannot gain or lose properties (or methods) once they have been declared. This does not mean an object's properties cannot change value or type. 

Again, similar in nature to ECMAScript 8, is Ark's moduling system. Ark by nature is designed to be easy to use and encourage good practices, meaning individual files and classes should be kept separate. To allow this