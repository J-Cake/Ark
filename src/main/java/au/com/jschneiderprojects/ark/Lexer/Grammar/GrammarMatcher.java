package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;

import java.util.ArrayList;

public class GrammarMatcher {
    Config<GrammarConfig> config;
    Grammar grammar;

    ArrayList<Token> tokenList;

    public StringBuilder blockContent = new StringBuilder();

    public GrammarMatcher(Config<GrammarConfig> config) {
        this.config = config;
        this.grammar = new Grammar();
    }

    public boolean isOpen(String token) {
        if (token.length() > 0)
            for (TokenRule rule : grammar.rules)
                if (rule.isDelimiterOpen(token))
                    return true;
        return false;
    }

    public boolean isClosed(String token) {
        if (token.length() > 0)
            for (TokenRule rule : grammar.rules)
                if (rule.isDelimiterClosed(token))
                    return true;
        return false;
    }

    public TokenType resolve(String token) {
        if (token.length() > 0)
            for (TokenRule rule : grammar.rules)
                if (rule.matches(token, tokenList))
                    return rule.type;
        return null;
    }

    public static String trimStart(String string) {
        StringBuilder b = new StringBuilder();

        boolean hasNonWhiteSpaceCharOccurred = false;
        for (char i : string.toCharArray())
            if (!hasNonWhiteSpaceCharOccurred && (i == '\n' || i == '\t' || i == '\r' || i == ' '))
                hasNonWhiteSpaceCharOccurred = true;
            else
                b.append(i);

        return b.toString();
    }

    public ArrayList<String> splitTokens(String accumulator) {
        ArrayList<String> toks = new ArrayList<>();

        StringBuilder b = new StringBuilder();

        for (char i : accumulator.toCharArray()) {
            if ((b.length() == 0 && i != '\t' && i != '\n' && i != ' ' && i != '\r') || b.length() > 0)
                b.append(i);

            if (b.length() > 0) {
                String _b = b.substring(0, b.length() - 1);

                boolean shouldClear = (this.resolve(b.toString()) == null && this.resolve(_b) != null);

                if (shouldClear) {
                    toks.add(_b.trim());

                    b = new StringBuilder();
                    if (i != '\t' && i != '\n' && i != ' ' && i != '\r')
                        b.append(i);
                }
            }
        }

        toks.add(b.toString().trim());

        return toks;
    }
}
