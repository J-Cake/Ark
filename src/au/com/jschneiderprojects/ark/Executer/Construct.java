package au.com.jschneiderprojects.ark.Executer;

public enum Construct {
    IF ("if"),
    ELSE ("else"),
    SWITCH ("switch"),
    EACH ("each"),
    WHILE ("while"),
    REPEAT ("repeat"),
    BLOCK ("block"),
    FUNCTION ("function"),
    CLASS ("class"),
    NAMESPACE ("namespace"),
    SUPPRESS ("suppress"),
    CATCH ("catch");

    public String identifier;
    Construct(String identifier) {
        this.identifier = identifier;
    }
}