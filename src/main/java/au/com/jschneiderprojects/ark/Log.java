package au.com.jschneiderprojects.ark;

import java.util.Date;

public class Log {
    private static void print(String colour, Object... message) {
        StringBuilder logMessage = new StringBuilder();

        String[] caller = Thread.currentThread().getStackTrace()[3].getClassName().split("\\.");

        logMessage
                .append(colour)
                .append("+")
                .append(caller[caller.length - 1])
                .append(ConsoleColours.BLACK_BRIGHT)
                .append(" ")
                .append(new Date().toString())
                .append(ConsoleColours.RESET);

        for (Object s : message)
            if (s != null)
                for (String ss : s.toString().split("\n"))
                    logMessage.append(ConsoleColours.PURPLE_BOLD).append("\n\t").append(ConsoleColours.RESET).append(ss != null ? ss : "null");
            else
                logMessage.append(ConsoleColours.PURPLE_BOLD).append("\n\t").append(ConsoleColours.RESET).append("null");

        String whitespace = " \t\n\b-";

        while (whitespace.indexOf(logMessage.charAt(logMessage.length() - 1)) > -1)
            logMessage.deleteCharAt(logMessage.length() - 1);

        System.out.println(logMessage + "\n");
    }

    public static void i(Object... message) {
        Log.print(ConsoleColours.CYAN, message);
    }

    public static void e(Object... message) {
        Log.print(ConsoleColours.RED, message);
    }
}
