package au.com.jschneiderprojects.ark.Compiler.Constructs;

import au.com.jschneiderprojects.ark.Compiler.Construct;
import au.com.jschneiderprojects.ark.Compiler.Instruction;
import au.com.jschneiderprojects.ark.Formatter.Statement;

import java.util.ArrayList;

public class While extends Construct {
    public While(Statement statement) {
        super(statement, au.com.jschneiderprojects.ark.Executer.Construct.WHILE);
    }

    @Override
    public ArrayList<Instruction> convert() {
        return null;
    }
}
