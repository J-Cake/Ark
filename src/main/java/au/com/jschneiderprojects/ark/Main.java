package au.com.jschneiderprojects.ark;

import au.com.jschneiderprojects.ark.Compiler.CompileConfig;
import au.com.jschneiderprojects.ark.Compiler.Compiler;
import au.com.jschneiderprojects.ark.Executer.ExecuteConfig;
import au.com.jschneiderprojects.ark.Executer.Scope;
import au.com.jschneiderprojects.ark.Executer.Executor;
import au.com.jschneiderprojects.ark.Formatter.Block;
import au.com.jschneiderprojects.ark.Formatter.FormatConfig;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Lexer.LexConfig;
import au.com.jschneiderprojects.ark.Lexer.Lexer;
import au.com.jschneiderprojects.ark.Lexer.Token;

import org.apache.commons.cli.*;
//import org.apache.commons.cli.*;
//import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(final String[] args) {
        try {
            CLI(args);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    public static String loadProgram(final String filename) throws FileNotFoundException {
        final StringBuilder file = new StringBuilder();

        final File index = new File(filename);

        if (index.exists()) {
            final Scanner reader = new Scanner(index);

            while (reader.hasNextLine())
                file.append(reader.nextLine()).append("\n");

            return file.toString();
        } else
            throw new FileNotFoundException();
    }

    private static void runScript(final String filename, final Config<RunConfig> config) throws FileNotFoundException {
        final String source = loadProgram(filename);

        final RunConfig preferences = (RunConfig) config.options;

        final Lexer lexer = new Lexer(new Config<>(new LexConfig() {
        }));
        final Formatter formatter = new Formatter(new Config<>(new FormatConfig() {
        }), lexer);// Cannot pass configuration parameters to Lexer.
// I'm guessing it's the same story here

        if (preferences.runAsBytecode) {
            final Compiler compiler = new Compiler(new Config<>(new CompileConfig() {
            }), formatter);

            final ArrayList<Token> LexOutput = lexer.receiveInput(source);
            final Block FormatOutput = formatter.receiveInput(LexOutput);
            compiler.receiveInput(FormatOutput);
//             ArrayList<Instruction> CompileOutput = compiler.receiveInput(FormatOutput);

//            Log.i(FormatOutput.stringify(0));

        } else {
            final Executor executor = new Executor(new Config<>(new ExecuteConfig() {
            }), formatter); // And here

            final ArrayList<Token> LexOutput = lexer.receiveInput(source);
            final Block FormatOutput = formatter.receiveInput(LexOutput);
            final Scope ExecuteOutput = executor.receiveInput(FormatOutput);

            final Scope global = ExecuteOutput;
            // Scope global =
            // executor.receiveInput(formatter.receiveInput(lexer.receiveInput(program)));
            // This should give us the global scope that we can use to make changes to the
            // runtime state of the program
        }
    }

    private static void CLI(final String[] args) throws ParseException {
        final Options options = new Options();

        options.addOption("help", "View help information");
        options.addOption("p", "path", true,
                "Make sources visible to script through imports. Separate by paths by semicolon.");

        final CommandLineParser parser = new DefaultParser();

        final CommandLine cmd = parser.parse(options, args);

        // Config Buildup

        final Config<RunConfig> config = new Config<>(new RunConfig() {
        });

        // Interrogation Stage

        if (cmd.hasOption("help") || args.length == 0) {
            final HelpFormatter formatter = new HelpFormatter();
            final PrintWriter writer = new PrintWriter(System.out);
            formatter.printUsage(writer, 80, "Ark Interpreter", options);
            formatter.printHelp("Parameters", options);
            writer.flush();
        } else if (!cmd.hasOption("help")) {
            if (args[0] != null)
                try {
                    runScript(args[0], config);
                } catch (final FileNotFoundException e) {
                    System.err.println("The file doesn't exist");
                }
        }
    }
}