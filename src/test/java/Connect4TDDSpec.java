import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
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
}
