public class FancyTable {
    public static final int BOLD = 0b1, DASHED = 0b10, DOUBLED = 0b100;
    public static final int TOP_LEFT = 0, TOP_RIGHT = 1, BOTTOM_LEFT = 2, BOTTOM_RIGHT = 3;
    public static char getHorizontal(int style) {
        if ((style & DOUBLED) != 0)
            return 0x2550;
        if ((style & DASHED) != 0)
            return ((style & BOLD) != 0) ? (char) 0x2505 : 0x2504;
        if ((style & BOLD) != 0)
            return 0x2501;
        return 0x2500;
    }
    public static char getVertical(int style) {
        if ((style & DOUBLED) != 0)
            return 0x2551;
        return (char)(getHorizontal(style) + 2);
    }
    public static char getCorner(int horizontalStyle, int verticalStyle, int rotation) {
        boolean flipHorizontal = rotation == TOP_RIGHT || rotation == BOTTOM_RIGHT;
        boolean flipVertical = rotation == BOTTOM_LEFT || rotation == BOTTOM_RIGHT;

        char baseCorner = 0x250c;

        if(flipHorizontal && flipVertical){
            int temp = verticalStyle;
            verticalStyle = horizontalStyle;
            horizontalStyle = temp;
        }

        if((verticalStyle & BOLD) != 0)
            baseCorner += 2;
        if((horizontalStyle & BOLD) !=0)
            baseCorner += 1;

        baseCorner += flipHorizontal ? 4 : 0;
        baseCorner += flipVertical ? 8 : 0;

        return baseCorner;
    }
    public static char getCrossing(int horizontalStyle, int verticalStyle) {
        if((verticalStyle & DOUBLED) != 0){
            return (horizontalStyle & DOUBLED)!= 0 ? (char) 0x256c : 0x256b;
        }
        if((horizontalStyle & DOUBLED) != 0){
            return 0x256b;
        }
        if((verticalStyle & BOLD) != 0)
            return (horizontalStyle & BOLD) != 0 ? (char)0x254b : 0x2543;
        if((horizontalStyle & BOLD) != 0)
            return 0x253f;
        return 0x253c;
    }
    public static char getT(int horizontalStyle, int verticalStyle){
        return ' ';
    }
}
