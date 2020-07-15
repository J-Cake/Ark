package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Compiler.Constructs.*;
import au.com.jschneiderprojects.ark.Compiler.Constructs.Class;
import au.com.jschneiderprojects.ark.Formatter.Statement;

import java.util.ArrayList;

public abstract class Construct {
    public Statement statement;
    public au.com.jschneiderprojects.ark.Executer.Construct construct;

    public Construct(Statement statement, au.com.jschneiderprojects.ark.Executer.Construct construct) {
        this.statement = statement;
        this.construct = construct;
    }

    public abstract ArrayList<Instruction> convert();

    static Construct fromStatement(Statement statement) {
        if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.BLOCK)
            return new Block(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.CATCH)
            return new Catch(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.CLASS)
            return new Class(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.EACH)
            return new Each(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.ELSE)
            return new Else(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.EXIT)
            return new Exit(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.FUNCTION)
            return new Function(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.IF)
            return new If(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.KILL)
            return new Kill(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.NAMESPACE)
            return new Namespace(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.PASS)
            return new Pass(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.REPEAT)
            return new Repeat(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.RETURN)
            return new Return(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.SUPPRESS)
            return new Supress(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.SWITCH)
            return new Switch(statement);
        else if (statement.construct == au.com.jschneiderprojects.ark.Executer.Construct.WHILE)
            return new While(statement);
        else
            return new Null(statement);
    }
}
