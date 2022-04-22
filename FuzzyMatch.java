import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

class SubstringPermuter implements Iterable<String>, Iterator<String> {
    private String toPermute;
    private int currentStart, currentLength, minLength;

    public SubstringPermuter(String toPermute, int minLength) {
        this.toPermute = toPermute;
        this.currentStart = 0;
        this.currentLength = toPermute.length();
        this.minLength = minLength;
    }
    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return currentLength > (minLength - 1);
    }

    public int getPermutationCount() {
        return toPermute.length() * (toPermute.length()-(minLength-1)) / 2;
    }

    @Override
    public String next() {
        String toReturn = toPermute.substring(currentStart, currentStart + currentLength);
        currentStart++;
        if(currentStart + currentLength > toPermute.length()) {
            currentStart = 0;
            currentLength--;
        }
        return toReturn;
    }
}
class NGramPermuter implements Iterable<String>, Iterator<String> {
    private String toPermute;
    private int currentIndex, nGramLength;

    public NGramPermuter(String toPermute, int nGramLength) {
        this.toPermute = toPermute;
        this.currentIndex = 0;
        this.nGramLength = nGramLength;
    }
    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < (toPermute.length() - (nGramLength-1));
    }

    public int getPermutationCount() {
        return Math.max(toPermute.length() - (nGramLength-1), 0);
    }

    @Override
    public String next() {
        String toReturn = toPermute.substring(currentIndex, currentIndex + nGramLength);
        currentIndex++;
        return toReturn;
    }
}

public class FuzzyMatch {
    public static boolean matches(String matcher, String toTest) {
        matcher = matcher.toLowerCase(Locale.US);
        toTest = toTest.toLowerCase(Locale.US);

        String[] tokens = matcher.split(" ");

        int startingIndex = 0;
        for (String token : tokens) {
            startingIndex = toTest.indexOf(token, startingIndex);
            if (startingIndex == -1)
                return false;
        }

        return true;
    }
    public static float matches2(String matcher, String toTest) {
        String[] tokens = matcher.toLowerCase(Locale.US).split(" ");
        toTest = toTest.toLowerCase(Locale.US);

        int totalPermutations = 0;
        int totalPassedPermutations = 0;

        for(String token : tokens) {
            SubstringPermuter sp = new SubstringPermuter(token, 2);

            totalPermutations += sp.getPermutationCount();
            for(String substring : sp) {
                if(toTest.indexOf(substring) != -1)
                    totalPassedPermutations++;
            }
        }

        return totalPassedPermutations/(float)totalPermutations;
    }
    public static float matches3(String matcher, String toTest) {
        String[] tokens = matcher.toLowerCase(Locale.US).split(" ");
        toTest = toTest.toLowerCase(Locale.US);

        int totalPermutations = 0;
        int totalPassedPermutations = 0;

        for(String token : tokens) {
            NGramPermuter sp = new NGramPermuter(token, 2);

            totalPermutations += sp.getPermutationCount();
            for(String substring : sp) {
                if(toTest.indexOf(substring) != -1)
                    totalPassedPermutations++;
            }
        }

        return totalPassedPermutations/(float)totalPermutations;
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
    public static void testFind2(String matcher, String[] strings) {
        System.out.println("Mathcing " + matcher);
        for(String s : strings) {
            System.out.println(s + ": " + matches2(matcher, s));
        }
    }
    public static void testFind3(String matcher, String[] strings) {
        System.out.println("Mathcing " + matcher);
        for(String s : strings) {
            System.out.println(s + ": " + matches3(matcher, s));
        }
    }
    public static void main(String args[]) {
        String[] strings = new String[]{
                "Skippy Peanut Butter 16oz",
                "Skippy Peanut Butter 32oz",
                "Peanut Butter bar",
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

        for(String s : new NGramPermuter("Hello, World!", 3)) {
            System.out.println(s);
        }

        Scanner s = new Scanner(System.in);

        while(true) {
            String line = s.nextLine();

            if(line.equalsIgnoreCase("exit"))
                return;



            System.out.println("=======PERMUTE=======");
            long startTime = System.nanoTime();
            testFind2(line, strings);
            long endTime = System.nanoTime();
            System.out.println((endTime - startTime)/1e+9);
            System.out.println("=======NGRAM=======");
            startTime = System.nanoTime();
            testFind3(line, strings);
            endTime = System.nanoTime();
            System.out.println((endTime - startTime)/1e+9);
        }
    }
}