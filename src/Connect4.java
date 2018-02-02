import java.util.Arrays;
import java.util.StringJoiner;

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

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final String DELIMITER = "|";
    private Color[][] board = new Color[COLUMNS][ROWS];
    private Color currentPlayer = Color.RED;

    public Connect4() {
        for (Color[] column: board) {
            Arrays.fill(column, Color.EMPTY);
        }
    }

    public void putDisc(int column) {
        if (column > 0 && column <= COLUMNS) {
            int numOfDiscs = getNumberOfDiscsInColumn(column - 1);
            if (numOfDiscs < ROWS) {
                board[column - 1][numOfDiscs] = currentPlayer;
                printBoard();
                switchPlayer();
            } else {
                System.out.println(numOfDiscs);
                System.out.println("There is no empty place in this column");
                printBoard();
            }
        } else {
            System.out.println("This column is not in the board");
            printBoard();
        }
    }

    public int getNumberOfDiscsInColumn(int column) {
        if (column >= 0 && column < COLUMNS) {
            int row;
            for (row = 0; row < ROWS; row++) {
                if (Color.EMPTY == board[column][row]) {
                    return row;
                }
            }
            return row;
        }
        return -1;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
        System.out.println("Current player: " + currentPlayer);
    }

    public void printBoard() {
        for (int row = ROWS - 1; row >= 0; --row) {
            StringJoiner stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
            for (int col = 0; col < COLUMNS; ++col) {
                stringJoiner.add(board[col][row].toString());
            }
            System.out.println(stringJoiner.toString());
        }
    }

    public boolean isFinished() {
        int numOfDiscs = 0;
        for (int col = 0; col < COLUMNS; ++col) {
            numOfDiscs += getNumberOfDiscsInColumn(col);
        }
        if (numOfDiscs >= COLUMNS * ROWS) {
            System.out.println("It is a draw");
            return true;
        }
        return false;
    }
}
