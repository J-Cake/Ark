package au.com.jschneiderprojects.ark.Lexer;

public record LexConfig(String fileName, char escapeChar, boolean verboseLexLog, int defaultIndent, boolean ignoreWhiteSpaceAndComments) {}
