package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Stage;

import java.util.ArrayList;

public class Formatter extends Stage<ArrayList<Token>, ArrayList<Statement>> {
    public Formatter(Config<FormatConfig> config) {
        super(config);
    }

    public ArrayList<Statement> receiveInput(ArrayList<Token> input) {
        // format the incoming tokens
        return null;
    }
}
