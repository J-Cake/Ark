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

        ArrayList<Instruction> bytecode = new ArrayList<>();

        for (Statement statement : o.body)
            Log.i(statement.construct == null ? "<NullConstruct>" : statement.construct.identifier, statement, Construct.fromStatement(statement).convert());
//            bytecode.addAll(ConstructHandler.fromStatement(statement).convert());

        return bytecode;
    }
}
