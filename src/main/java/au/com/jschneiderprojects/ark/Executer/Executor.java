package au.com.jschneiderprojects.ark.Executer;

import au.com.jschneiderprojects.ark.Formatter.Block;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Stage;

public class Executor extends Stage<ExecuteConfig, Block, Scope> {
    Formatter origin;
    public Executor(ExecuteConfig config, Formatter parent) {
        super(config, parent);
    }

    public Scope receiveInput(Block input) {
        // execute the incoming statements
        return null;
    }
}
