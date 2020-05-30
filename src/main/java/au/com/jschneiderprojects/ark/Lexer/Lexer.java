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
    String indent;

    boolean isSpaceBasedIndent = true;

    public Lexer(Config<LexConfig> config) {
        super(config);

        this.matcher = new GrammarMatcher(new Config<>(new GrammarConfig() {
        }));

        this.indent = "";
    }

    public int getLevel(String line) {
        int index = line.indexOf('\n');

        if (this.indent.length() > 0)
            if (index > -1)
                return line.substring(0, index).split(this.indent).length - 1;
            else
                return line.split(this.indent).length - 1;
        return 0;
    }

    public String getIndent(String source) {
        ArrayList<String> lineIndents = new ArrayList<>();

        StringBuilder lineWhitespace = new StringBuilder();
        for (String line : source.split("\n")) {
            for (char c : line.toCharArray())
                if (c == '\t' || c == ' ')
                    lineWhitespace.append(c);
                else
                    break;

            if (lineWhitespace.length() > 0)
                lineIndents.add(lineWhitespace.toString());
            lineWhitespace = new StringBuilder();
        }

        String indent = "";

        for (String i : lineIndents)
            if (i.length() > indent.length())
                indent = i;

        for (String i : lineIndents)
            if (i.length() < indent.length())
                indent = i;

        this.indent = indent;

        return indent;
    }

    public ArrayList<Token> receiveInput(String source) {
        // lex the incoming source code
        LexConfig config = (LexConfig) (preferences.options);

        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder accumulator = new StringBuilder();

        matcher.setTokenList(tokens);

        int line = 1;
        int charIndex = 1;

        boolean escaped;

        StringBuilder lineContainer = new StringBuilder();
        int indentLevel = 0;

        Integer initBlockIndent = null;

        this.getIndent(source);

        int prevIndentLevel = 0;

        for (char i : source.toCharArray()) {
            escaped = i == config.escapeChar;
            accumulator.append(i);

            lineContainer.append(i);

            if (this.containsNonIndentCharacter(lineContainer.toString()) && lineContainer.length() > 0)
                indentLevel = this.getLevel(lineContainer.toString());

            if (!escaped) {
                if (indentLevel <= prevIndentLevel) {
                    Origin origin = new Origin(config.filename, line, charIndex);

                    TokenType type = matcher.resolve(accumulator.toString().trim());

                    Token accumulated = null;

                    if (type != null)
                        accumulated = new Token(type, accumulator.toString().trim(), origin, indentLevel);

                    if (i == '\n') {
                        if (accumulated != null)
                            tokens.add(accumulated);
                        if (this.containsNonIndentCharacter(accumulator.toString()))
                            tokens.add(new Token(TokenType.NewLine, "", origin, indentLevel));
                        lineContainer = new StringBuilder();
                        prevIndentLevel = indentLevel;
                    } else if (matcher.isClosed(accumulator.toString())) { // important: don't trim
                        if (accumulated != null)
                            tokens.add(accumulated);
                    } else if (!matcher.isOpen(accumulator.toString()) && i == ' ' && this.containsNonIndentCharacter(lineContainer.toString())) {
                        if (accumulated != null)
                            tokens.add(accumulated);
                    } else
                        continue; // skip clear

                    accumulator = new StringBuilder();
                } else if (initBlockIndent == null)
                    initBlockIndent = indentLevel;
            }
        }

        System.out.println(accumulator.toString());
        System.out.println(initBlockIndent + ", " + indentLevel + ", " + (initBlockIndent != null && initBlockIndent == indentLevel && this.containsNonIndentCharacter(accumulator.toString())));

        if (accumulator.toString().trim().length() > 0)
            if (initBlockIndent != null && initBlockIndent == indentLevel && this.containsNonIndentCharacter(accumulator.toString()))
                tokens.add(new Token(TokenType.Block, accumulator.toString(), new Origin(config.filename, line, charIndex), indentLevel));
            else
                tokens.add(new Token(matcher.resolve(accumulator.toString().trim()), accumulator.toString().trim(), new Origin(config.filename, line, charIndex), indentLevel));

        StringBuilder log = new StringBuilder(); // basic output

        System.out.println("Captured " + tokens.size() + " tokens:");

        for (Token t : tokens) {
            log.append(t.type)
                    .append("(>")
                    .append(t.indentationLevel)
                    .append("): ")
                    .append(t.source)
                    .append(", ");
        }
        System.out.println(log.toString());

        return tokens;
    }

    private boolean containsNonIndentCharacter(String line) {
        for (char c : line.toCharArray())
            if (c != ' ' && c != '\t')
                return true;
        return false;
    }
}
