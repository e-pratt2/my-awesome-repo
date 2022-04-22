import java.util.Locale;

public class FuzzyMatch {
    public static boolean matches(String matcher, String toTest) {
        matcher = matcher.toLowerCase(Locale.US);
        toTest = toTest.toLowerCase(Locale.US);

        String[] tokens = matcher.split(" ");

        int startingIndex = 0;
        for(String token : tokens) {
            startingIndex = toTest.indexOf(token, startingIndex);
            if (startingIndex == -1)
                return false;
        }

        return true;
    }
    public static String findMatch(String matcher, String[] strings) {
        String match = null;
        for(String s : strings) {
            if(matches(matcher, s)){
                if(match != null)
                    return "Multiple matches.";
                else
                    match = s;
            }
        }
        return match;
    }

    public static void test(String matcher, String test) {
        System.out.print(matcher + " matches " + test + "? ");
        System.out.println(matches(matcher, test));
    }
    public static void testFind(String matcher, String[] strings) {
        System.out.print(matcher + " matches ");
        String match = findMatch(matcher, strings);
        System.out.println(match == null ? "none" : match);
    }
    public static void main(String args[]) {
        String[] strings = new String[]{
                "Skippy Peanut Butter 16oz",
                "Skippy Peanut Butter 32oz",
                "Peanut bar",
                "Lentils bulk"
        };
        testFind("Skippy", strings);
        testFind("skippy 16", strings);
        testFind("Skippy 32", strings);
        testFind("32 skippy", strings);
        testFind("Peanut butter 16", strings);
        testFind("bar", strings);
        testFind("p b 16oz", strings);
        testFind("soup", strings);
        testFind("bulk", strings);
    }
}