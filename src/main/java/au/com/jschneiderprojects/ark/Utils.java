package au.com.jschneiderprojects.ark;

import java.util.ArrayList;

public class Utils {
    public static String join(ArrayList obj) {
        return join(obj, "");
    }
    public static String join(ArrayList obj, String joiner) {
        StringBuilder b = new StringBuilder();

        for (Object o : obj)
            b.append(o.toString()).append(joiner);

        return b.substring(0, joiner.length());
    }
}
