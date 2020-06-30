package au.com.jschneiderprojects.ark.Executer;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Formatter.Block;
import au.com.jschneiderprojects.ark.Formatter.Formatter;
import au.com.jschneiderprojects.ark.Stage;

public class Executer extends Stage<Block, Scope> {
    Formatter origin;
    public Executer(Config<ExecuteConfig> config, Formatter parent) {
        super(config, parent);
    }

    public Scope receiveInput(Block input) {
        // execute the incoming statements
        return null;
    }
}
