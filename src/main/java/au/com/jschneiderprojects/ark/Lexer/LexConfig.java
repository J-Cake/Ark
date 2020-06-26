package au.com.jschneiderprojects.ark.Lexer;

public interface LexConfig {
    String filename = "<Inline>";
    char escapeChar = '\\';
    boolean verboseLexLog = false;
    int defaultIndent = 0;
    boolean ignoreWhiteSpaceAndComments = false;
}
