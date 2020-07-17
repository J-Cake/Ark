package au.com.jschneiderprojects.ark.Compiler.Operators;

import au.com.jschneiderprojects.ark.Compiler.Operator;
import au.com.jschneiderprojects.ark.Compiler.Types.Int;
import au.com.jschneiderprojects.ark.Compiler.Types.Float;
import au.com.jschneiderprojects.ark.Compiler.Types.Number;
import au.com.jschneiderprojects.ark.Compiler.Types.String;
import au.com.jschneiderprojects.ark.Compiler.Value;
import au.com.jschneiderprojects.ark.Executer.Operators;

public class Plus extends Operator {
    public Plus() {
        super(Operators.PLUS);
    }

    public Number eval(Number[] operands) {
        if (operands.length == 2) {
            Number result;

            if (operands[0] instanceof Float || operands[1] instanceof Float)
                result = new Float();
            else
                result = new Int();

            return result;
        } else
            return null;
    }

    public String eval(Value[] operands) {
        return new String();
    }
}
