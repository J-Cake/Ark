package au.com.jschneiderprojects.ark;

import au.com.jschneiderprojects.ark.Executer.ExecuteConfig;
import au.com.jschneiderprojects.ark.Executer.Executor;
import au.com.jschneiderprojects.ark.Executer.Scope;
import au.com.jschneiderprojects.ark.Formatter.Block;
import au.com.jschneiderprojects.ark.Formatter.FormatConfig;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Lexer.Token;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            CLI(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String loadProgram(String filename) throws FileNotFoundException {
        StringBuilder file = new StringBuilder();

        File index = new File(filename);

        if (index.exists()) {
            Scanner reader = new Scanner(index);

            while (reader.hasNextLine())
                file.append(reader.nextLine()).append("\n");

            return file.toString();
        } else
            throw new FileNotFoundException();
    }

    private static void runScript(String filename, RunConfig config) throws FileNotFoundException {
        String program = loadProgram(filename);

        Lexer lexer = new Lexer(new LexConfig("<Inline>", '\\', config.debugLog(), 0, false)); // Cannot pass configuration parameters to Lexer.
        Formatter formatter = new Formatter(new FormatConfig(config.debugLog()), lexer);
        Executor executor = new Executor(new ExecuteConfig(false), formatter); // And here

        ArrayList<Token> LexOutput = lexer.receiveInput(program);
        Block FormatOutput = formatter.receiveInput(LexOutput);
        Scope ExecuteOutput = executor.receiveInput(FormatOutput);

        if (formatter.preferences.verboseFormatLog())
            FormatOutput.print();

//        Scope global = ExecuteOutput;
//        Scope global = executor.receiveInput(formatter.receiveInput(lexer.receiveInput(program)));
        // This should give us the global scope that we can use to make changes to the runtime state of the program
    }

    private static void CLI(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption("help", "View help information");
        options.addOption("p", "path", true, "Make sources visible to script through imports. Separate by paths by semicolon.");
        options.addOption("", "omit-bytecode", false, "Do not compile result to bytecode - return format tree");
        options.addOption("d", "debug", false, "Print additional compilation information - useful only for debugging scenarios");

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = parser.parse(options, args);

        // Config Buildup

        RunConfig config = new RunConfig(cmd.hasOption("omit-bytecode"), cmd.hasOption("debug"));

        // Interrogation Stage

        if (cmd.hasOption("help") || args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            final PrintWriter writer = new PrintWriter(System.out);
            formatter.printUsage(writer, 80, "Ark Interpreter", options);
            formatter.printHelp("Parameters", options);
            writer.flush();
        } else if (!cmd.hasOption("help")) {
            if (args[0] != null)
                try {
                    runScript(args[0], config);
                } catch (FileNotFoundException e) {
                    System.err.println("The file doesn't exist");
                }
        }
    }
}