package au.com.jschneiderprojects.ark.Compiler;

public abstract class Primitive { // empty class
    PrimitiveType primitiveType;
    PrimitiveVar var;

    Primitive(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
    }
    Primitive(PrimitiveVar var) {
        this.primitiveType = PrimitiveType.LITERAL;
        this.var = var;
    }

    public String toString() {
        if (this.var != null)
            return "<" + var.toString().toLowerCase() + ">";
        return new StringBuilder().append("").toString();
    }
}
