package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Formatter.Block;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Formatter.Statement;
import au.com.jschneiderprojects.ark.Log;
import au.com.jschneiderprojects.ark.Stage;

import java.util.ArrayList;

public class Compiler extends Stage<Block, ArrayList<Instruction>> {
    public Compiler(Config<CompileConfig> preferences, Formatter parentStage) {
        super(preferences, parentStage);
    }

    @Override
    public ArrayList<Instruction> receiveInput(Block o) {
        CompileConfig config = (CompileConfig) this.preferences.options;

        ArrayList<Instruction> bytecode = new ArrayList<>();

        for (Statement statement : o.body) {
            ArrayList<Instruction> instructions = Construct.fromStatement(statement).convert();
            if (config.verboseCompileLog)
                Log.i(statement.construct == null ? "<NullConstruct>" : statement.construct.identifier.toUpperCase(), statement, instructions == null ? "<No Action>" : instructions);

            if (instructions != null)
                bytecode.addAll(instructions);
        }
//            bytecode.addAll(ConstructHandler.fromStatement(statement).convert());

        return bytecode;
    }
}
