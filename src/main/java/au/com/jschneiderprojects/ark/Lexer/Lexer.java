package au.com.jschneiderprojects.ark.Lexer;

import au.com.jschneiderprojects.ark.*;
import au.com.jschneiderprojects.ark.Error;
import au.com.jschneiderprojects.ark.Lexer.Grammar.GrammarConfig;
import au.com.jschneiderprojects.ark.Lexer.Grammar.GrammarMatcher;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;

import java.util.ArrayList;

public class Lexer extends Stage<String, ArrayList<Token>> {
    public GrammarMatcher matcher;
    public String indent;

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

        StringBuilder blockContent = new StringBuilder();
        StringBuilder line = new StringBuilder();

        this.indent = this.getIndent(source);

        int lineCounter = 0;

        boolean isEscaped = false;
        boolean wasEscaped = false;

        for (char i : source.toCharArray()) {
            if (i == config.escapeChar)
                isEscaped = true;
            else
                line.append(i);

            if (!wasEscaped && i == '\n') {
                if (this.containsNonWhitespace(line.toString())) {
                    int indent = getLevel(line.toString());

                    if (indent <= 0) {
                        if (blockContent.length() > 0) {
                            tokens.add(new Token(TokenType.Block, blockContent.toString(), new Origin(config.filename, lineCounter, indent)));
                            blockContent = new StringBuilder();
                        }

                        tokens.addAll(this.resolveTokens(config, line, lineCounter, indent));
                    } else
                        blockContent.append(line);

                    line = new StringBuilder();
                }
            }

            if (i == '\n')
                lineCounter++;

            wasEscaped = isEscaped;
            isEscaped = false;
        }

        int indent = getLevel(line.toString());
        if (blockContent.length() > 0)
            tokens.add(new Token(TokenType.Block, blockContent.toString(), new Origin(config.filename, lineCounter, indent)));
        if (line.length() > 0)
            tokens.addAll(this.resolveTokens(config, line, lineCounter, indent));

        StringBuilder log = new StringBuilder(); // basic output

        int totalTokens = 0;
        for (Token t : tokens)
            if ((t.type != TokenType.Comment && t.type != TokenType.NewLine) || !config.ignoreWhiteSpaceAndComments)
                totalTokens++;

        if (config.verboseLexLog) {
            if (totalTokens > 0)
                Log.i("Captured " + totalTokens + " tokens:");

            for (Token t : tokens)
                if ((t.type != TokenType.Comment && t.type != TokenType.NewLine) || !config.ignoreWhiteSpaceAndComments)
                    log.append(t.toString());

            if (totalTokens > 0)
                Log.i(log.toString());
        }

        return tokens;
    }

    private ArrayList<Token> resolveTokens(LexConfig config, StringBuilder line, int lineCounter, int indent) {
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<String> toks = matcher.splitTokens(line.toString());
        int _char = 0;

        for (String s : toks) {
            _char += s.length();

            if (matcher.resolve(s) != null)
                tokens.add(new Token(matcher.resolve(s), s, new Origin(config.filename, lineCounter, _char)));
        }

        tokens.add(new Token(TokenType.NewLine, "", new Origin(config.filename, lineCounter, _char + 1)));

        return tokens;
    }

    public boolean containsNonWhitespace(String line) {
        if (line.length() > 0)
            for (char c : line.toCharArray())
                if (c != ' ' && c != '\t' && c != '\n')
                    return true;
        return false;
    }

    public String reduceIndent(String source, Origin origin) {
        String[] lines = source.split("\n");

        StringBuilder reduced = new StringBuilder();

        for (String line : lines) {
            if (getLevel(line) == 0 && reduced.length() == 0) // make exception for first line. It's a ridiculous solution but hey
                reduced.append(line.trim()).append('\n');
            else if (getLevel(line) > 0) {
                reduced.append(line.substring(this.indent.length()));
                reduced.append('\n');
            } else {
                if (((LexConfig) this.preferences.options).verboseLexLog)
                    Log.e(source);
                this.internalError(new Error(origin, ErrorType.Syntax, "Cannot reduce 0 indentation", true).verbose(line), true);
                return null;
            }
        }

        reduced.deleteCharAt(reduced.length() - 1);

        return reduced.toString();
    }
}
