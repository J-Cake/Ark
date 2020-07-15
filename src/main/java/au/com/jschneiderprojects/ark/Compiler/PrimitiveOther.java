package au.com.jschneiderprojects.ark.Compiler;

public class PrimitiveOther extends Primitive {
    public String data;
    public PrimitiveOther(String data) { // meta-data holder
        super(PrimitiveType.OTHER);
        this.data = data;
    }
}
