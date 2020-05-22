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

    boolean isSpaceBasedIndent = true;

    public Lexer(Config<LexConfig> config) {
        super(config);

        this.matcher = new GrammarMatcher(new Config<>(new GrammarConfig() {
        }));
    }

    int detectIndentationLevel(String source) {
        LexConfig config = (LexConfig) (preferences.options);

        String[] lines = source.split("\n");

        ArrayList<Integer> leadingSpaces = new ArrayList<>();
        ArrayList<Integer> leadingTabs = new ArrayList<>();

        for (String line : lines) {
            int leadingSpace = 0;
            int leadingTab = 0;

            for (char c : line.toCharArray())
                if (c == ' ')
                    leadingSpace++;
                else if (c == '\t')
                    leadingTab++;
                else
                    break;

            if (leadingSpace > 0)
                leadingSpaces.add(leadingSpace);
            if (leadingTab > 0)
                leadingTabs.add(leadingTab);
        }

        if ((leadingSpaces.size() > 0 && leadingTabs.size() > 0))
            super.internalError(new Error(new Origin(config.filename, 0, 0), ErrorType.Syntax, "Inconsistent Indentation").verbose("Detecting indentation failed due to inconsistent indentation"), true);
        else if (leadingSpaces.size() == 0 && leadingTabs.size() == 0)
            return -1;
        else {
            int min = Integer.MAX_VALUE;
            for (int line : leadingSpaces.size() > leadingTabs.size() ? leadingSpaces : leadingTabs)
                if (line < min) min = line;

            isSpaceBasedIndent = leadingSpaces.size() > leadingTabs.size();

            return min;
        }

        return -1;
    }

    public ArrayList<Token> receiveInput(String source) {
        // lex the incoming source code
        LexConfig config = (LexConfig) (preferences.options);

        int indentCharacterRepeat = detectIndentationLevel(source);
        if (indentCharacterRepeat == -1)
            indentCharacterRepeat = config.defaultIndent;

        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder accumulator = new StringBuilder();

        matcher.setTokenList(tokens);

        int line = 1;
        int charIndex = 1;

        boolean escaped = false;

        StringBuilder lineContainer = new StringBuilder();
        int lineIndent = 0;

        boolean hasBlockCollected = false;

        for (char i : source.toCharArray()) {
            if (i == config.escapeChar) {
                escaped = true;
            } else {
                accumulator.append(i);
                lineContainer.append(i);

                if (i != '\t' && i != ' ') {
                    String lineString = lineContainer.toString();
                    int nonIndentIndex = lineString.indexOf(isSpaceBasedIndent ? ' ' : '\t');
                    if (nonIndentIndex > -1 && indentCharacterRepeat > 0)
                        lineIndent = lineString.substring(0, nonIndentIndex + 1).length() / indentCharacterRepeat;
                }
                if (i == '\n')
                    lineContainer = new StringBuilder();

                if (!escaped) {
                    if (lineIndent <= 0) {
                        if (hasBlockCollected) {
                            tokens.add(new Token(TokenType.Block, accumulator.toString(), new Origin(config.filename, line, charIndex), lineIndent));
                            accumulator = new StringBuilder();
                            hasBlockCollected = false;
                        } else {
                            Origin origin = new Origin(config.filename, line, charIndex);
                            String token = accumulator.toString().trim();

                            if ((i == '\n' || i == '\r') && !matcher.isOpen(token)) {
                                if (token.length() == 0) {
                                    tokens.add(new Token(TokenType.NewLine, "", origin, lineIndent));
                                    line++;
                                    charIndex = 1;
                                } else
                                    tokens.add(new Token(matcher.resolve(token), token, origin, lineIndent));
                                accumulator = new StringBuilder();
                            } else if (matcher.isClosed(token) && matcher.resolve(token) != null || (i == ' ' && !matcher.isOpen(token))) { // is literal delimiter
                                if (token.length() > 0)
                                    tokens.add(new Token(matcher.resolve(token), token, origin, lineIndent));
                                accumulator = new StringBuilder();
                            }
                        }
                    } else
                        hasBlockCollected = true;
                }
                escaped = false;
            }
        }

        if (accumulator.toString().trim().length() > 0)
            if (hasBlockCollected)
                tokens.add(new Token(TokenType.Block, accumulator.toString(), new Origin(config.filename, line, charIndex), lineIndent));
            else
                tokens.add(new Token(matcher.resolve(accumulator.toString().trim()), accumulator.toString().trim(), new Origin(config.filename, line, charIndex), lineIndent));

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
}
