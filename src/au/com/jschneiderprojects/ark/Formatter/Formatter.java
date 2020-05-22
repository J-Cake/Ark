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

        /* Todo:
            1. Tokenise statements into constructs and expressions.
            2. Postfixify expressions and mark accordingly in specialised containers
            3. Lookup construct in a dictionary and check if the parameters fit. (Parameters should be comma separated, like in functions)
            4. Ensure order is retained on Statements.

            Constructs are lists of expressions used to determine how a block given to it should be executed.
            A construct in itself (the syntactical keyword) is not a literal token. Hence the term `literal`. Instead the statement is an umbrella term for all constructs.
            An empty construct, containing a single expression as an argument with no blocks must also exist.
         */

        return null;
    }
}
