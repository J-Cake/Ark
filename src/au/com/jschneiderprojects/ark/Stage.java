package au.com.jschneiderprojects.ark;

import javax.lang.model.type.NullType;

public abstract class Stage<Input, Output> {
    public Config preferences;

    public Stage(Config preferences) {
        this.preferences = preferences;
    }

    public abstract Output receiveInput(Input input);

    public void internalError(Error err, boolean verbose) {
        err.throwError(null, verbose);
    }
}
