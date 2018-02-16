import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Connect4TDD {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final String EMPTY = " ";
    private static final String DELIMITER = "|";
    private String[][] board = new String[ROWS][COLUMNS];
    private String actualColor = "R";
    private PrintStream output;
    private String winner = "";

    Connect4TDD(PrintStream out) {
        output = out;
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    public int getNumberOfDiscs() {
        return IntStream.range(0, COLUMNS).map(this::getNumberOfDiscsInColumn).sum();
    }

    private int getNumberOfDiscsInColumn(int column) {
        return (int) IntStream.range(0, ROWS)
                .filter(row -> !EMPTY.equals(board[row][column]))
                .count();
    }

    public int putDiscInColumn(int column) {
        checkColumn(column);
        int row = getNumberOfDiscsInColumn(column);
        checkInsertPosition(row, column);
        board[row][column] = actualColor;
        changePlayerColor();
        printBoard();
        checkWinner(row, column);
        return row;
    }

    private void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            StringJoiner stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
            for (String column : board[row]) {
                stringJoiner.add(column);
            }
            output.println(stringJoiner.toString());
        }
    }

    private void changePlayerColor() {
        if (actualColor.equals("R")) {
            actualColor = "G";
        } else {
            actualColor = "R";
        }
    }

    private void checkInsertPosition(int row, int column) {
        if (row == ROWS) {
            throw new RuntimeException("No room in column number " + column);
        }
    }

    private void checkColumn(int column) {
        if (column < 0 || column > COLUMNS) {
            throw new RuntimeException("Wrong column " + column);
        }
    }

    public String getActualColor() {
        output.println(actualColor.equals("R") ? "Red color turn" : "Green color turn");
        return actualColor;
    }

    public boolean isActive() {
        return getNumberOfDiscs() != ROWS * COLUMNS;
    }

    public String getWinner() {
        return winner;
    }

    private void checkWinner(int row, int column) {
        final int WIN = 4;
        String color = board[row][column];
        Pattern winPattern = Pattern.compile(".*" + color + "{" + WIN + "}.*");
        StringBuilder vertical = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            vertical.append(board[i][column]);
        }
        System.out.println(vertical);
        if (winPattern.matcher(vertical.toString()).matches()) {
            winner = color;
        }
    }
}
