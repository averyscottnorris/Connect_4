package comaveryscottnorris.httpsgithub.team4sconnect4;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kavinarasu on 2/13/18.
 */

public class Board_1088 {
    private int numCols;
    private int numRows;
    public boolean hasWinner;
    public Cell_1088[][] cells_78;
    private int WIN_X = 0;
    private int WIN_Y = 0;
    private int p, q;

    public enum Turn {
        FIRST, SECOND
    }

    public Board_1088.Turn turn;

    public Board_1088(int cols, int rows) {
        numCols = cols;
        numRows = rows;
        cells_78 = new Cell_1088[cols][rows];
        reset();
    }

    public void reset() {
        hasWinner = false;
        turn = Board_1088.Turn.FIRST;
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                cells_78 [col][row] = new Cell_1088();
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
        if (turn == Board_1088.Turn.FIRST) {
            turn = Board_1088.Turn.SECOND;
        } else {
            turn = Board_1088.Turn.FIRST;
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

    @NonNull
    public ArrayList<ImageView> getWinDiscs(ImageView[][] cells) {
        ArrayList<ImageView> combination = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            combination.add(cells[p + WIN_Y * i][q + WIN_X * i]);
        }
        return combination;
    }


    private boolean isContiguous(Board_1088.Turn player, int dirX, int dirY, int col, int row, int count) {
        if (count >= 4) {
            return true;
        }
        if (col < 0 || col >= numCols || row < 0 || row >= numRows) {
            return false;
        }
        Cell_1088 cell = cells_78[col][row];
        if (cell.player == player) {
            return isContiguous(player, dirX, dirY, col + dirX, row + dirY, count + 1);
        } else {
            return isContiguous(player, dirX, dirY, col + dirX, row + dirY, 0);
        }
    }

}
