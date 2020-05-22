package au.com.jschneiderprojects.ark.Executer;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Formatter.Statement;
import au.com.jschneiderprojects.ark.Stage;

import java.util.ArrayList;

public class Executer extends Stage<ArrayList<Statement>, Scope> {
    public Executer(Config<ExecuteConfig> config) {
        super(config);
    }

    public Scope receiveInput(ArrayList<Statement> input) {
        // execute the incoming statements
        return null;
    }
}
