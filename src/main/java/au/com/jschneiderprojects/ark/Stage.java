package au.com.jschneiderprojects.ark;

public abstract class Stage<Input, Output> {
    public Config preferences;
    public Stage parentStage;

    public Stage(Config preferences) {
        this.preferences = preferences;
    }

    public Stage(Config preferences, Stage parent) {
        this.preferences = preferences;

//        System.out.println(parent.getClass().getCanonicalName() + " Received in " + this.getClass().getCanonicalName());

        this.parentStage = parent;
    }

    public abstract Output receiveInput(Input input);

    public void internalError(Error err, boolean verbose) {
        err.throwError(null, verbose);
    }

    public void internalError(Error err) {
        err.throwError(null, false);
    }
}
