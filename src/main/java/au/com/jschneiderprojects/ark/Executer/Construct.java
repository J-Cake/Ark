package au.com.jschneiderprojects.ark.Executer;

public enum Construct {
    IF ("if"), // If Block
    ELSE ("else"), // Else Block
    SWITCH ("switch"), // Enhanced Condition Matcher
    EACH ("each"), // For Each Loop
    WHILE ("while"), // Standard While Loop
    REPEAT ("repeat"), // Repeat fixed amount of times
    BLOCK ("block"), // Closures
    FUNCTION ("function"), // Function Definition
    CLASS ("class"), // Class Definition
    NAMESPACE ("namespace"), // Namespace Definition
    SUPPRESS ("suppress"), // Error Suppressor
    CATCH ("catch"), // Error Catch Handler
    RETURN ("return", false), // Return Sequence
    PASS ("pass"), // Placeholder For Empty Blocks
    KILL ("kill", false), // Terminate Program
    EXIT ("exit", false); // Terminate Loop

    public String identifier;
    public boolean takesBlock;

    Construct(String identifier) {
        this.identifier = identifier;
        this.takesBlock = true;
    }
    Construct(String identifier, boolean takesBlock) {
        this.identifier = identifier;
        this.takesBlock = takesBlock;
    }
}