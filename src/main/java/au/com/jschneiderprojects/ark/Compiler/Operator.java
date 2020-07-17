package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Executer.Operators;

public abstract class Operator {
    Operators base;
    public Operator(Operators base) {
        this.base = base;
    }

    public abstract Value eval(Value[] operands);
}
