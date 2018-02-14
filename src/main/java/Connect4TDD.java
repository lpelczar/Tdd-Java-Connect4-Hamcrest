public class Connect4TDD {

    public int getNumberOfDiscs() {
        return 0;
    }

    public void putDiscInColumn(int column) {
        if (column < 0 || column > 6) {
            throw new RuntimeException("Wrong column " + column);
        }
    }
}
