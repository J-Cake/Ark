package au.com.jschneiderprojects.ark.Compiler.Constructs;

import au.com.jschneiderprojects.ark.Compiler.Construct;
import au.com.jschneiderprojects.ark.Compiler.Instruction;
import au.com.jschneiderprojects.ark.Formatter.Statement;

import java.util.ArrayList;

public class Supress extends Construct {
    public Supress(Statement statement) {
        super(statement, au.com.jschneiderprojects.ark.Executer.Construct.SUPPRESS);
    }

    @Override
    public ArrayList<Instruction> convert() {
        return null;
    }
}
