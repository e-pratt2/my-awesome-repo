import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Table<E> {
    private class TableColumn {
        public String name;
        public Function<E, Object> getter;
        public TableColumn(String name, Function<E, Object> getter){
            this.name = name;
            this.getter = getter;
        }
        public String evaluateOn(E object) {
            return getter.apply(object).toString();
        }
    }
    private ArrayList<TableColumn> columns;

    public Table() {
        this.columns = new ArrayList<>();
    }

    public void addColumn(String name, Function<E, Object> methodReference) {
        this.columns.add(new TableColumn(name, methodReference));
    }
    public String[] getColumnNames() {
        String[] names = new String[this.columns.size()];

        for(int i = 0; i < this.columns.size(); ++i)
            names[i] = this.columns.get(i).name;

        return names;
    }
    private String[] evaluateRow(E obj){
        String[] columnValues = new String[this.columns.size()];

        for(int column = 0; column < this.columns.size(); ++column) {
            columnValues[column] = this.columns.get(column).evaluateOn(obj);
        }

        return columnValues;
    }

    public String tabulate(E[] objects) {
        String[][] rowData = new String[objects.length][];

        for(int row =0; row < objects.length; ++row){
            rowData[row] = evaluateRow(objects[row]);
        }

        //TODO: actually calculate the length lol
        int[] columnWidths = new int[this.columns.size()];
        for(int column = 0; column < this.columns.size(); ++column){
            columnWidths[column] = 10;
        }

        for(int row = 0; row < rowData.length; ++row) {
            for(int column = 0; column < rowData[0].length; ++column) {
                rowData[row][column] = trimOrPad(rowData[row][column], columnWidths[column], ' ');
            }
        }

        int tableWidth = Arrays.stream(columnWidths).sum() + this.columns.size() - 1;

        String rowFormatString = getRowFormatString(this.columns.size());

        StringBuilder tableString = new StringBuilder();

        tableString.append(String.format(rowFormatString, trimOrPad(getColumnNames(), columnWidths, ' ')));
        tableString.append(trimOrPad("", tableWidth, '='))
                .append('\n');
        for(int row = 0; row < objects.length; ++row) {
            tableString.append(String.format(rowFormatString, trimOrPad(rowData[row], columnWidths, ' ')));
        }

        return tableString.toString();
    }

    private static int longestStringSize(String[] strings) {
        int max = 0;
        for(String i : strings)
            max = Math.max(max, i.length());
        return max;
    }
    private static String trimOrPad(String s, int length, char pad) {
        if(s.length() > length) {
            return s.substring(0, length);
        } else {
            StringBuilder sb = new StringBuilder(s);
            sb.ensureCapacity(length);

            for(int i = 0; i < (length-s.length()); ++i)
                sb.append(pad);

            return sb.toString();
        }
    }
    private static String[] trimOrPad(String[] strings, int[] lengths, char pad) {
        for(int i = 0; i < strings.length; ++i)
            strings[i] = trimOrPad(strings[i], lengths[i], pad);

        return strings;
    }
    private static String getRowFormatString(int numColumns) {
        StringBuilder sb = new StringBuilder();

        if(numColumns > 0) {
            sb.append(("%s|").repeat(numColumns-1));
            sb.append("%s\n");
        }

        return sb.toString();
    }
}
