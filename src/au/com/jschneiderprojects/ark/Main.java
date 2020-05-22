package au.com.jschneiderprojects.ark;

import au.com.jschneiderprojects.ark.Executer.ExecuteConfig;
import au.com.jschneiderprojects.ark.Executer.Executer;
import au.com.jschneiderprojects.ark.Executer.Scope;
import au.com.jschneiderprojects.ark.Formatter.FormatConfig;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;

public class Main {

    public static void main(String[] args) {
        String fileName = "Test";
        String program = loadProgram(fileName);

        Lexer lexer = new Lexer(new Config<>(new LexConfig() {
            final String filename = fileName;
        }));
        Formatter formatter = new Formatter(new Config<>(new FormatConfig() {}));
        Executer executer = new Executer(new Config<>(new ExecuteConfig() {}));

        Scope global = executer.receiveInput(formatter.receiveInput(lexer.receiveInput(program)));
        // This should give us the global scope that we can use to make changes to the runtime state of the program
    }

    private static String loadProgram(String filename) {
        if (filename.equals("Test"))
            return "\"Hello\" 12.4 * 365";
        else
            return "";
    }
}