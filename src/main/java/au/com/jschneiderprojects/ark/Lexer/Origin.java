package au.com.jschneiderprojects.ark.Lexer;

public class Origin {
    public String file;
    public int line;
    public int charIndex;

    Origin(String filePath, int line, int charIndex) {
        this.file = filePath;
        this.line = line;
        this.charIndex = charIndex;
    }
}
