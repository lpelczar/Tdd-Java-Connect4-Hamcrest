import java.util.Arrays;

public class Connect4 {

    public enum Color {
        RED('R'), GREEN('G'), EMPTY('E');

        private final char value;

        Color(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    private Color[][] board = new Color[COLUMNS][ROWS];

    public Connect4() {
        for (Color[] column: board) {
            Arrays.fill(column, Color.EMPTY);
        }
    }
}
