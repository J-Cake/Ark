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

        Lexer lexer = new Lexer(new Config<>(new LexConfig() {})); // Cannot pass configuration parameters to Lexer.
        Formatter formatter = new Formatter(new Config<>(new FormatConfig() {})); // I'm guessing it's the same story here
        Executer executer = new Executer(new Config<>(new ExecuteConfig() {})); // And here

        Scope global = executer.receiveInput(formatter.receiveInput(lexer.receiveInput(program)));
        // This should give us the global scope that we can use to make changes to the runtime state of the program
    }

    private static String loadProgram(String filename) {
        if (filename.equals("Test"))
//            return "int a = 2";
//            return "string str = \"multiline\\\nstring\"";
            return "if x == 3 \n\treturn true";
        else
            return "";
    }
}