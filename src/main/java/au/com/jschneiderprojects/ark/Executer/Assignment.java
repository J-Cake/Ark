package au.com.jschneiderprojects.ark.Executer;

public enum Assignment {
    Plain("="),
    Increment("++"),
    Decrement("--"),
    Square("**"),
    SquareRoot("//"),
    Add("+="),
    Subtract("-="),
    Multiply("*="),
    Divide("/="),
    Modulo("%="),
    Exponent("**="),
    Negate("negate");

    public String identifier;

    Assignment(String identifier) {
        this.identifier = identifier;
    };
}
