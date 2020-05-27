package au.com.jschneiderprojects.ark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.cli.*;

import au.com.jschneiderprojects.ark.Executer.ExecuteConfig;
import au.com.jschneiderprojects.ark.Executer.Executer;
import au.com.jschneiderprojects.ark.Executer.Scope;
import au.com.jschneiderprojects.ark.Formatter.FormatConfig;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;

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
                file.append(reader.nextLine()).append( "\n");

            return file.toString();
        } else
            throw new FileNotFoundException();
    }

    private static void runScript(String filename, Config<RunConfig> config) throws FileNotFoundException {
        String program = loadProgram(filename);

        Lexer lexer = new Lexer(new Config<>(new LexConfig() {
        })); // Cannot pass configuration parameters to Lexer.
        Formatter formatter = new Formatter(new Config<>(new FormatConfig() {
        })); // I'm guessing it's the same story here
        Executer executer = new Executer(new Config<>(new ExecuteConfig() {
        })); // And here

        Scope global = executer.receiveInput(formatter.receiveInput(lexer.receiveInput(program)));
        // This should give us the global scope that we can use to make changes to the runtime state of the program
    }

    private static void CLI(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption("help", "View help information");
        options.addOption("p", "path", true, "Make sources visible to script through imports. Separate by paths by semicolon.");

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = parser.parse(options, args);

        // Config Buildup

        Config<RunConfig> config = new Config<>(new RunConfig() {
        });

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