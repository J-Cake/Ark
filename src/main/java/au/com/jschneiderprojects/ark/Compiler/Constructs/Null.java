package au.com.jschneiderprojects.ark.Compiler.Constructs;

import au.com.jschneiderprojects.ark.Compiler.*;
import au.com.jschneiderprojects.ark.Formatter.Assignment;
import au.com.jschneiderprojects.ark.Formatter.Statement;

import java.util.ArrayList;
import java.util.Arrays;

public class Null extends Construct {
    public Null(Statement statement) {
        super(statement, null);
    }

    @Override
    public ArrayList<Instruction> convert() {
        ArrayList<Instruction> declaration = new ArrayList<>();

        Pointer variable = new Pointer(((Assignment)this.statement).lhs.get(1).source);

        if (this.statement instanceof Assignment && ((Assignment)this.statement).shouldDeclare())
            declaration.add(new Instruction(InstructionType.DECLARE, Arrays.asList(variable, new PrimitiveOther(((Assignment)this.statement).lhs.get(0).source))));

        declaration.addAll(new PrimitiveExpression(((Assignment) this.statement).rhs).convert());

        return declaration;
    }
}
