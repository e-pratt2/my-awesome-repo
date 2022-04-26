

public class TabulateTest {
    private static class TestData{
        int anInt;
        double aDouble;
        String aString;
        public TestData(int anInt, double aDouble, String aString) {
            this.anInt = anInt;
            this.aDouble = aDouble;
            this.aString = aString;
        }
        public int getAnInt() {
            return anInt;
        }
        public double getaDouble(){
            return aDouble;
        }
        public String getaString() {
            return aString;
        }
    }
    public static void main(String[] args) {
        Table<TestData> table = new Table<>();

        table.addColumn("An int!", TestData::getAnInt);
        table.addColumn("A double!", TestData::getaDouble);
        table.addColumn("A string!", TestData::getaString);
        table.addColumn("Get Fancy!!", (TestData data) -> data.getAnInt() * 5);
        table.addColumn("Long name!! too big!!!!", (TestData data) -> 1);

        TestData[] datas = new TestData[] {
                new TestData(5,10.3,"Yay!"),
                new TestData(78, -1e9, "Wow!"),
                new TestData(1,1.0,"1.0")
        };

        System.out.print(table.tabulate(datas));
    }
}
