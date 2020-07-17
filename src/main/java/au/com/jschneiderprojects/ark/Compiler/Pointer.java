package au.com.jschneiderprojects.ark.Compiler;

public class Pointer extends Primitive {
    String varName;
    public Pointer(String varName) {
        super(PrimitiveType.POINTER);
        this.varName = varName;
    }
}
