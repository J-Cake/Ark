package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Lexer.Origin;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;

import java.util.ArrayList;

public class Block {
    ArrayList<Statement> body;
    Origin origin;

    public Block(Origin origin, ArrayList<Statement> body) {
        this.origin = origin;
        this.body = body;
    }

    public String stringify(int depth) {
        StringBuilder string = new StringBuilder();

        for (Statement statement : this.body) {
            if (statement.construct != null)
                string.append(statement.construct.toString()).append(" - ");

            StringBuilder indent = new StringBuilder();
            indent.append("  ".repeat(Math.max(0, depth)));

            for (Expression condition : statement.conditions) {
                string.append(condition.toString()).append(", ");
            }

            if (statement.block != null)
                string
                        .append("\n")
                        .append(indent)
                        .append(" - ")
                        .append(statement.block.stringify(depth + 1));
            else if (statement.construct != null && statement.construct.takesBlock)
                string
                        .append("\n")
                        .append(indent)
                        .append(" - NO_BLOCK ");
            else if (statement.construct == null)
                string
                        .append("\n")
                        .append(indent)
                        .append(" - NO_CONSTRUCT ");
        }

        return string.toString();
    }

    public void print() {
        Log.i(this.stringify(0));
    }
}
