package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Error;
import au.com.jschneiderprojects.ark.*;
import au.com.jschneiderprojects.ark.Executer.Construct;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

public class Formatter extends Stage<ArrayList<Token>, Block> {
    Lexer parentStage;

    public Formatter(Config<FormatConfig> config, Lexer parent) {
        super(config, parent);
        this.parentStage = parent;

        if (config.options.verboseFormatLog)
            Log.i("Received " + parent.getClass().getName());
    }

    public Block receiveInput(ArrayList<Token> input) {
        FormatConfig config = (FormatConfig) (preferences.options);

        if (config.verboseFormatLog)
            if (this.parentStage == null)
                Log.e("Invalid Lexer instance");
        // format the incoming tokens

        ArrayList<Statement> BlockBody = new ArrayList<>();
        ArrayList<Token> StatementBody = new ArrayList<>();

        Construct construct = null;
        Statement statement = null;

        ArrayList<Token> preFormatted = findInvocations(mergeSelectors(input));

        if (preFormatted != null) {
            for (Token t : preFormatted) {
                if (t.type == TokenType.Construct) {
                    for (Construct c : Construct.values())
                        if (c.identifier.equals(t.source)) {
                            construct = c;
                            break;
                        }
                } else if (t.type == TokenType.Block) {
                    if (construct != null && statement != null)
                        if (construct.takesBlock && statement.block == null)
                            statement.block = this.receiveInput(
                                    this.parentStage.receiveInput(this.parentStage.reduceIndent(t.source, t.origin)));
                } else if (t.type == TokenType.NewLine) {
                    if (StatementBody.size() > 0) {
                        if (Assignment.matches(StatementBody))
                            BlockBody.add(new Assignment(StatementBody, StatementBody.get(0).origin));
                        else
                            BlockBody.add(
                                    statement = new Statement(construct, StatementBody, StatementBody.get(0).origin));
                        // Log.i(BlockBody.get(BlockBody.size() - 1), BlockBody.get(BlockBody.size() - 1) instanceof Assignment);
                        StatementBody.clear();
                    }
                } else
                    StatementBody.add(t);
            }
        }

        return new Block(input.get(0).origin, BlockBody);
    }

    public ArrayList<Token> mergeSelectors(ArrayList<Token> input) {
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<Token> reference = new ArrayList<>();

        for (Token t : input) { // merge selectors
            if (t.type == TokenType.Reference) {
                if (reference.size() == 0 || reference.get(reference.size() - 1).type == TokenType.SubReference) {
                    reference.add(t);
                    continue;
                }
            } else if (t.type == TokenType.SubReference) {
                if (reference.size() > 0 && reference.get(reference.size() - 1).type == TokenType.Reference) {
                    reference.add(t);
                    continue;
                }
            }

            if (reference.size() > 0) {
                tokens.add(new Reference(reference, reference.get(0).origin));
                reference = new ArrayList<>(); // .clear() may result in changes within the object to carry over into
                                               // the stored list within the Reference instance. Create a new object
                                               // instead

                if (t.type == TokenType.Reference) {
                    reference.add(t);
                    continue;
                }
            }
            tokens.add(t);
        }

        if (reference.size() > 0) {
            tokens.add(new Reference(reference, reference.get(0).origin));
        }

        return tokens;
    }

    public ArrayList<Token> findInvocations(ArrayList<Token> input) {
        ArrayList<Token> tokens = new ArrayList<>();

        ArrayList<Token> parameters = new ArrayList<>();

        int bracketCount = 0;

        for (Token t : input) {
            if (bracketCount == 0) {
                if (t.type == TokenType.LeftParenthesis)
                    bracketCount++;
                tokens.add(t);
            } else
                parameters.add(t);

            if (t.type == TokenType.RightParenthesis) {
                bracketCount--;

                if (bracketCount == 0) {
                    if (tokens.get(tokens.size() - 2) instanceof Reference) {
                        tokens.remove(tokens.size() - 1); // remove excess parenthesis
                        Reference reference = (Reference) tokens.get(tokens.size() - 1);
                        tokens.remove(tokens.size() - 1); // remove reference (it exists in the invocation)
                        parameters.remove(parameters.size() - 1);
                        tokens.add(new Invocation(reference, parameters));
                    } else
                        tokens.addAll(parameters);
                    parameters = new ArrayList<>();
                } else if (bracketCount < 0) {
                    this.internalError(
                            new Error(t.origin, ErrorType.Syntax, "Unexpected )").verbose(parameters.toString()));
                    return null;
                }
            }
        }

        if (parameters.size() > 0) {
            this.internalError(new Error(parameters.get(0).origin, ErrorType.Syntax, "Mismatched parentheses")
                    .verbose(parameters.toString()));
            return null;
        }

        return tokens;
    }
}
