public class ColorTest {
    public static void main(String[] args) {
        String wide = "\u2500".repeat(10);
        String content = "\u2502\u001b[33m  Super!  \u001b[32m\u2502\n";
        System.out.println("\u001b[32m\u256D" + wide + "\u256E\n" + content + "\u2570" + wide + "\u256F");

        char[][] table = new char[3][3];
        table[0][1] = FancyTable.getHorizontal(FancyTable.BOLD);
        table[0][2] = FancyTable.getCorner(FancyTable.BOLD, 0, FancyTable.TOP_RIGHT);
        table[0][0] = FancyTable.getCorner(FancyTable.BOLD, 0, FancyTable.TOP_LEFT);
        table[1][0] = FancyTable.getVertical(0);
        table[1][1] = FancyTable.getCrossing(FancyTable.BOLD, FancyTable.DOUBLED);
        table[1][2] = table[1][0];

        for(char[] row : table) {
            System.out.println(new String(row));
        }
    }
}
