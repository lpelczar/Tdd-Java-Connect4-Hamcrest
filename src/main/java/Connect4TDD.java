import java.util.Arrays;
import java.util.stream.IntStream;

public class Connect4TDD {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final String EMPTY = " ";
    private String[][] board = new String[ROWS][COLUMNS];

    Connect4TDD() {
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    public int getNumberOfDiscs() {
        return IntStream.range(0, COLUMNS).map(this::getNumberOfDiscsInColumn).sum();
    }

    public int putDiscInColumn(int column) {
        int row;
        if (column < 0 || column > 6) {
            throw new RuntimeException("Wrong column " + column);
        } else {
            row = getNumberOfDiscsInColumn(column);
            checkInsertPosition(row, column);
            board[row][column] = "X";
        }
        return row;
    }

    private void checkInsertPosition(int row, int column) {
        if (row == ROWS) {
            throw new RuntimeException("No room in column number " + column);
        }
    }

    private int getNumberOfDiscsInColumn(int column) {
        int numOfDiscs = 0;
        for (String[] row : board) {
            if (row[column].equals("X")) {
                numOfDiscs++;
            }
        }
        return numOfDiscs;
    }
}
