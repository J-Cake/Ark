package au.com.jschneiderprojects.ark.Lexer;

import au.com.jschneiderprojects.ark.Config;
import au.com.jschneiderprojects.ark.ErrorType;
import au.com.jschneiderprojects.ark.Error;
import au.com.jschneiderprojects.ark.Lexer.Grammar.GrammarConfig;
import au.com.jschneiderprojects.ark.Lexer.Grammar.GrammarMatcher;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Stage;

import java.util.ArrayList;

public class Lexer extends Stage<String, ArrayList<Token>> {
    GrammarMatcher matcher;

    public Lexer(Config<LexConfig> config) {
        super(config);

        this.matcher = new GrammarMatcher(new Config<>(new GrammarConfig() {
        }));
    }

    int detectIndentationLevel(String source) {
        LexConfig config = (LexConfig) (preferences.options);
        int indentationType = 0;
        ArrayList<Integer> indentLevel = new ArrayList<>();

        String[] split = source.split("\n|\r\n|\r");
        for (int a = 0; a < split.length; a++) {
            String line = split[a];

            if ((indentationType == 2 && line.charAt(0) == ' ') || (indentationType == 1 && line.charAt(0) == '\t'))
                new Error(new Origin(config.filename, a + 1, 0), ErrorType.Syntax, "Inconsistent Indentation");
            else if (indentationType == 0)
                indentationType = line.charAt(0) == ' ' ? 1 : (line.charAt(0) == '\t' ? 2 : 0);

            int indentation = 0;

            for (char i : line.toCharArray())
                if (i == ' ' || i == '\t')
                    indentation++;
                else
                    break;

            indentLevel.add(indentation);
        }

        int minIndent = Integer.MAX_VALUE;

        for (int indent : indentLevel)
            if (indent > 0 && indent < minIndent)
                minIndent = indent;

        return minIndent;
    }

    public ArrayList<Token> receiveInput(String source) {
        // lex the incoming source code
        LexConfig config = (LexConfig) (preferences.options);

        int indent = detectIndentationLevel(source);

        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder accumulator = new StringBuilder();

        matcher.setTokenList(tokens);

        int line = 1;
        int charIndex = 1;

        boolean escaped;

        for (char i : source.toCharArray()) {
            accumulator.append(i);
            escaped = i == config.escapeChar;

            if (!escaped) {
                Origin origin = new Origin(config.filename, line, charIndex);
                String token = accumulator.toString().trim();

                if (i == '\n' || i == '\r') {
                    if (token.length() == 0) {
                        tokens.add(new Token(TokenType.NewLine, "", origin, indent));
                        line++;
                        charIndex = 1;
                        accumulator = new StringBuilder();
                    } else {
                        String notBlankError = "Unexpected Token '" + token + "'";
                        super.internalError(new Error(origin, ErrorType.Syntax, notBlankError).verbose("Accumulator Not Empty"), config.verboseLexLog);
                        return null;
                    }
                } else if (matcher.isClosed(token) && matcher.resolve(token) != null || (i == ' ' && !matcher.isOpen(token))) { // is literal delimiter
                    if (token.length() > 0)
                        tokens.add(new Token(matcher.resolve(token), token, origin, indent));
                    accumulator = new StringBuilder();
                } // else accumulate
            } // else accumulate
        }

        if (accumulator.toString().trim().length() > 0)
            tokens.add(new Token(matcher.resolve(accumulator.toString().trim()), accumulator.toString().trim(), new Origin(config.filename, line, charIndex), indent));

        StringBuilder log = new StringBuilder(); // basic output

        System.out.println("Captured " + tokens.size() + " tokens:");

        for (Token t : tokens) {
            log.append(t.type);
            log.append(": ");
            log.append(t.source);
            log.append(", ");
        }
        System.out.println(log.toString());

        return tokens;
    }
}
