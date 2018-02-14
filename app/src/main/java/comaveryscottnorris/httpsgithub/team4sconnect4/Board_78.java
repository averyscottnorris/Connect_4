package comaveryscottnorris.httpsgithub.team4sconnect4;

/**
 * Created by kavinarasu on 1/31/18.
 */

public class Board_78 {
    private int numCols;
    private int numRows;
    public boolean hasWinner;
    public Cell_78[][] cells_78;

    public enum Turn {
        FIRST, SECOND
    }

    public Turn turn;

    public Board_78(int cols, int rows) {
        numCols = cols;
        numRows = rows;
        cells_78 = new Cell_78[cols][rows];
        reset();
    }

    public void reset() {
        hasWinner = false;
        turn = Turn.FIRST;
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                cells_78 [col][row] = new Cell_78();
            }
        }
    }

    public int lastAvailableRow(int col) {
        for (int row = numRows - 1; row >= 0; row--) {
            if (cells_78[col][row].empty) {
                return row;
            }
        }
        return -1;
    }

    public void occupyCell(int col, int row) {
        cells_78[col][row].setPlayer(turn);
    }

    public void toggleTurn() {
        if (turn == Turn.FIRST) {
            turn = Turn.SECOND;
        } else {
            turn = Turn.FIRST;
        }
    }

    public boolean checkForWin(int c, int r) {
        for (int col = 0; col < numCols; col++) {
            if (isContiguous(turn, 0, 1, col, 0, 0) || isContiguous(turn, 1, 1, col, 0, 0) || isContiguous(turn, -1, 1, col, 0, 0)) {
                hasWinner = true;
                return true;
            }
        }
        for (int row = 0; row < numRows; row++) {
            if (isContiguous(turn, 1, 0, 0, row, 0) || isContiguous(turn, 1, 1, 0, row, 0) || isContiguous(turn, -1, 1, numCols - 1, row, 0)) {
                hasWinner = true;
                return true;
            }
        }
        return false;
    }

    private boolean isContiguous(Turn player, int dirX, int dirY, int col, int row, int count) {
        if (count >= 4) {
            return true;
        }
        if (col < 0 || col >= numCols || row < 0 || row >= numRows) {
            return false;
        }
        Cell_78 cell = cells_78[col][row];
        if (cell.player == player) {
            return isContiguous(player, dirX, dirY, col + dirX, row + dirY, count + 1);
        } else {
            return isContiguous(player, dirX, dirY, col + dirX, row + dirY, 0);
        }
    }

}
