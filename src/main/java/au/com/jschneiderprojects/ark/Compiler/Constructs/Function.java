package au.com.jschneiderprojects.ark.Compiler.Constructs;

import au.com.jschneiderprojects.ark.Compiler.Construct;
import au.com.jschneiderprojects.ark.Compiler.Instruction;
import au.com.jschneiderprojects.ark.Formatter.Statement;

import java.util.ArrayList;

public class Function extends Construct {
    public Function(Statement statement) {
        super(statement, au.com.jschneiderprojects.ark.Executer.Construct.FUNCTION);
    }

    @Override
    public ArrayList<Instruction> convert() {
        return null;
    }
}
