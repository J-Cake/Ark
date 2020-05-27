import java.io.FileNotFoundException;

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

    @Before
    public void init() {
        this.lex = new Lexer(new Config<>(new LexConfig() {
        }));

        // this.program = loadProgram();
        this.program_tab = "int x = 3\n\nif x == 3\n\treturn true";
        this.program_2space = "int x = 3\n\nif x == 3\n  return true";
        this.program_4space = "int x = 3\n\nif x == 3\n    return true";
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
    }
}