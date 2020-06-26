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

    public ArrayList<String> splitTokens(String accumulator) { // perhaps a misleading name, this function returns indices to all token delimiters within the accumulated string
        ArrayList<String> toks = new ArrayList<>();

        StringBuilder b = new StringBuilder();

        for (char i : accumulator.toCharArray()) {
            b.append(i);

            if (b.length() > 0) {
                String _b = b.substring(0, b.length() - 1);

                if (this.resolve(b.toString().trim()) == null && this.resolve(_b.trim()) != null) {
                    toks.add(_b.trim());

                    String s = b.substring(b.length() - 1);
                    b = new StringBuilder();
                    b.append(s);
                }
            }
        }

        toks.add(b.substring(0, b.length() - 1) .trim());

        return toks;
    }
}
