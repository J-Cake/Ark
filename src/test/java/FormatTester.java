import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Formatter.Block;
import au.com.jschneiderprojects.ark.Formatter.FormatConfig;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Formatter.Invocation;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class FormatTester {
    Lexer lexer;
    Formatter formatter;
    String source;

    @Before
    public void init() {
        this.lexer = new Lexer(new Config<>(new LexConfig() {
        }));
        this.formatter = new Formatter(new Config<>(new FormatConfig() {
        }), lexer);

        this.source = "int x = 1\nif x == 1\n    return 2";
    }

    @Test
    public void format() {
        // TODO: format expression by construct
        ArrayList<Token> lexOutput = lexer.receiveInput(source);
        Block formatOutput = formatter.receiveInput(lexOutput);

        formatOutput.print();
//        Log.i(formatOutput);
    }

    @Test
    public void mergeSelectors() {
        ArrayList<Token> sourceWithReferences = lexer.receiveInput("a.b(\"c\")");

        Log.i(sourceWithReferences);

        ArrayList<Token> merged = formatter.mergeSelectors(sourceWithReferences);
        ArrayList<TokenType> actual = new ArrayList<>();

        for (Token t : merged)
            actual.add(t.type);

        ArrayList<TokenType> expected = new ArrayList<>(Arrays.asList(TokenType.Reference, TokenType.LeftParenthesis, TokenType.String, TokenType.RightParenthesis, TokenType.NewLine));
        // Note: Newlines are added by lexer to aid in analysis

        Assert.assertEquals("The references present within the source string should be collapsed into a single selector entity", actual, expected);
    }

    @Test
    public void findInvocations() {
        ArrayList<Token> sourceWithInvocation = lexer.receiveInput("a.b(\"c\")");

        Log.i(sourceWithInvocation);

        ArrayList<Token> invoked = formatter.findInvocations(formatter.mergeSelectors(sourceWithInvocation)); // need to collapse references first
        ArrayList<TokenType> actual = new ArrayList<>();

        for (Token t : invoked)
            actual.add(t.type);

        ArrayList<TokenType> expected = new ArrayList<>(Arrays.asList(TokenType.Invocation, TokenType.NewLine));
        // Note: Newlines are added by lexer to aid in analysis

        Assert.assertEquals("The invocation signature should be detected and collapsed into a single invocation container", actual, expected);
    }
}
