package au.com.jschneiderprojects.ark.Compiler.Constructs;

import au.com.jschneiderprojects.ark.Compiler.Construct;
import au.com.jschneiderprojects.ark.Compiler.Instruction;
import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Formatter.Statement;

import java.util.ArrayList;

public class Null extends Construct {
    public Null(Statement statement) {
        super(statement, null);
    }

    @Override
    public ArrayList<Instruction> convert() {
        ArrayList<Instruction> declaration = new ArrayList<>();

        for (Expression expression : this.statement.conditions) {
            // Convert expressions into values - declare if undeclared
//            if (expression.tokens.size() > 1 && expression.tokens.get(0).type == TokenType.Reference && expression.tokens.get(1).type == TokenType.Reference) // is declaration
        }

        return declaration;
    }

    public ArrayList<Instruction> convertExpression(Expression expression) {
        ArrayList<Instruction> expr = new ArrayList<>();



        return expr;
    }
}
