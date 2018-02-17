import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;


public class Connect4TDDSpec {

    private Connect4TDD tested;
    private OutputStream output;

    @Before
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();
        tested = new Connect4TDD(new PrintStream(output));
    }

    @Test
    public void whenTheGameIsStartedTheBoardIsEmpty() {
        assertThat(tested.getNumberOfDiscs(), is(0));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        int column = -1;
        exception.expect(RuntimeException.class);
        exception.expectMessage("Wrong column " + column);
        tested.putDiscInColumn(column);
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
        int column = 1;
        assertThat(tested.putDiscInColumn(column), is(0));
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(tested.putDiscInColumn(column), is(1));
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {
        int column = 1;
        tested.putDiscInColumn(column);
        assertThat(tested.getNumberOfDiscs(), is(1));
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {
        int column = 1;
        int maxDiscsInColumn = 6;
        for (int times = 0; times < maxDiscsInColumn; ++times) {
            tested.putDiscInColumn(column);
        }
        exception.expect(RuntimeException.class);
        exception.expectMessage("No room in column number " + column);
        tested.putDiscInColumn(column);
    }

    @Test
    public void whenGameStartsThenRedColor() {
        String color = tested.getActualColor();
        assertThat(color, is("R"));
    }

    @Test
    public void whenSecondPlayerThenGreenColor() {
        tested.putDiscInColumn(1);
        String color = tested.getActualColor();
        assertThat(color, is("G"));
    }

    @Test
    public void whenGettingActualColorThenTheOutputIsNoticed() {
        tested.getActualColor();
        assertThat(output.toString(), containsString("Red color turn"));
    }

    @Test
    public void whenFirstDiscAddedThenPrintBoard() {
        tested.putDiscInColumn(1);
        assertThat(output.toString(), containsString("| |R| | | | | |"));
    }

    @Test
    public void whenBoardIsEmptyThenGameIsActive() {
        assertThat(tested.isActive(), is(true));
    }

    @Test
    public void whenBoardIsFullThenGameIsNotActive() {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                tested.putDiscInColumn(column);
            }
        }
        assertThat(tested.isActive(), is(false));
    }

    @Test
    public void whenFourDiscsInColumnThenWin() {
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(3);
        assertThat(tested.getWinner(), isEmptyString());
        tested.putDiscInColumn(1);
        assertThat(tested.getWinner(), is("R"));
    }

    @Test
    public void whenFourDiscsInRowThenWin() {
        for (int i = 1; i < 4; i++) {
            tested.putDiscInColumn(i);
            tested.putDiscInColumn(i);
        }
        assertThat(tested.getWinner(), isEmptyString());
        tested.putDiscInColumn(4);
        assertThat(tested.getWinner(), is("R"));
    }

    @Test
    public void whenFourDiscInDiagonal1ThenWin() {
        int[] moves = new int[] {1,2,2,3,4,3,3,4,4,5,4};
        for (int m : moves) {
            tested.putDiscInColumn(m);
        }
        assertThat(tested.getWinner(), is("R"));
    }

    @Test
    public void whenFourDiscInDiagonal2ThenWin() {
        int[] moves = new int[] {3,4,2,3,2,1,1,1,1};
        for (int m : moves) {
            tested.putDiscInColumn(m);
        }
        assertThat(tested.getWinner(), is("G"));
    }
}
