import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;
import au.com.jschneiderprojects.ark.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class LexTester {
    Lexer lex;
    String program_tab;
    String program_2space;
    String program_4space;
    String source;

    @Before
    public void init() {
        this.lex = new Lexer(new LexConfig("<Inline>", '\\', false, 0, false));

        // this.program = loadProgram();
        this.program_tab = "int x = 3\n\nif x == 3\n\treturn true";
        this.program_2space = "int x = 3\n\nif x == 3\n  return true";
        this.program_4space = "int x = 3\n\nif x == 3\n    return true";

        this.source = "int x = 1\nif x == 1\n\n    return 2";
    }

    String loadProgram() throws FileNotFoundException {
        return Main.loadProgram("/home/jcake/Code/Language/Ark/TestProject/test.ark");
    }

    @Test
    public void testIndentation() {
        Assert.assertEquals("Check the indentation type", "\t", lex.getIndent(program_tab));
        Assert.assertEquals("Check indentation level", 1, lex.getLevel("\treturn true"));
        Assert.assertEquals("Check indentation level", 2, lex.getLevel("\t\treturn true"));

        Assert.assertEquals("Check the indentation type", "  ", lex.getIndent(program_2space));
        Assert.assertEquals("Check indentation level", 1, lex.getLevel("  return true"));
        Assert.assertEquals("Check indentation level", 2, lex.getLevel("    return true"));

        Assert.assertEquals("Check the indentation type", "    ", lex.getIndent(program_4space));
        Assert.assertEquals("Check indentation level", 1, lex.getLevel("    return true"));
        Assert.assertEquals("Check indentation level", 2, lex.getLevel("        return true"));
    }

    @Test
    public void testLexer() {
        // TODO: Implement lexer test
        ArrayList<Token> output = this.lex.receiveInput(source);

        TokenType[] expected = new TokenType[]{
                TokenType.Reference, TokenType.Reference, TokenType.Assignment, TokenType.Int, TokenType.NewLine,
                TokenType.Construct, TokenType.Reference, TokenType.Operator, TokenType.Int, TokenType.NewLine,
                TokenType.Construct, TokenType.Int, TokenType.NewLine
        };

        for (int i = 0; i < output.size(); i++)
            Assert.assertEquals("Compare Lexed output to expected output", expected[i], output.get(i).type);
    }

    @Test
    public void testSplitter() {
        ArrayList<String> split = this.lex.matcher.splitTokens("int a = 1 + 2");

        Log.i(split);

        ArrayList<String> expected = new ArrayList<>(Arrays.asList("int", "a", "=", "1", "+", "2"));

        Assert.assertEquals("Confirm the tokeniser splits the string into tokens correctly", expected, split);
    }
}