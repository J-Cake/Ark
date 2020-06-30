import java.io.FileNotFoundException;
import java.util.ArrayList;

import au.com.jschneiderprojects.ark.Formatter.Reference;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Main;

public class LexTester {
    Lexer lex;
    String program_tab;
    String program_2space;
    String program_4space;
    String source;

    @Before
    public void init() {
        this.lex = new Lexer(new Config<>(new LexConfig() {
        }));

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
                TokenType.Reference, TokenType.Reference, TokenType.Operator, TokenType.Int, TokenType.NewLine,
                TokenType.Construct, TokenType.Reference, TokenType.Operator, TokenType.Int, TokenType.NewLine,
                TokenType.Construct, TokenType.Int, TokenType.NewLine
        };

        for (int i = 0; i < output.size(); i++)
            Assert.assertEquals("Compare Lexed output to expected output", output.get(i).type, expected[i]);
    }
}