package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.gamecentre.GameManager;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable, GameManager {

    /**
     * The board being managed.
     */
    private Board board;
    /**
     * the moves that have been made in this game
     */
    private Stack<ArrayList<Integer>> undoStack = new Stack<>();
    /**
     * The current number of moves that the player has made.
     */
    private int move = 0;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a board with varies complexities.
     *
     * @param boardSize the size of the board.
     */
    BoardManager(int boardSize) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        if (boardSize == 3) {
            Collections.sort(tiles);
            tiles.remove(0);
            Tile blankTile = new Tile(24);
            tiles.add(blankTile);
        }
        if (boardSize == 4) {
            Collections.sort(tiles);
            tiles.remove(0);
            Tile blankTile = new Tile(24);
            tiles.add(blankTile);
        }

        Collections.shuffle(tiles);



        int inv_count = 0;
        for (int i = 0; i < tiles.size() - 1; i++)
            for (int j = i + 1; j < tiles.size(); j++)
                if (tiles.get(i).getId() > tiles.get(j).getId())
                    inv_count ++;





        this.board = new Board(tiles, boardSize);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        int start = 1;
        for (Tile tile : board) {
            if (tile.getId() != start) {
                solved = false;
            }
            start++;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / board.boardSize;
        int col = position % board.boardSize;
        int blankId = 25;

        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.boardSize - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.boardSize - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * Increment the number of moves.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / board.boardSize;
        int col = position % board.boardSize;
        int blankId = 25;
        int blankRow = board.findTile(blankId) / board.boardSize;
        int blankCol = board.findTile(blankId) % board.boardSize;

        if (isValidTap(position)) {
            ArrayList<Integer> undoMove = new ArrayList<>();
            undoMove.add(blankRow);
            undoMove.add(blankCol);
            undoMove.add(row);
            undoMove.add(col);
            this.undoStack.add(undoMove);
            this.move++;
            board.swapTiles(blankRow, blankCol, row, col);

        }

    }

    /**
     * Return the player's move.
     */
    int getMove() {
        return move;
    }

    /**
     * Return the player's score.
     */
    public int getScore() {
        int score = 1000 - (move * 3 * (6 - board.boardSize));
        if (score < 0) {
            return 0;
        }
        return score;

    }

    /**
     * Return the game's name.
     */
    public String getGameName() {
        return "Sliding Tile";
    }

    /**
     * Return the date and time of the game being played.
     */
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * return the game difficulty.
     */
    public String getGameDifficulty() {
        return String.format("%s by %s", board.boardSize, board.boardSize);
    }

    /**
     * Undo the previous move made, if possible. Return true if move is undone, otherwise false.
     */
    boolean undoMove() {
        if (!undoStack.empty()) {
            ArrayList<Integer> previousMove = undoStack.pop();
            int row = previousMove.get(2);
            int col = previousMove.get(3);
            int blankRow = previousMove.get(0);
            int blankCol = previousMove.get(1);
            board.swapTiles(row, col, blankRow, blankCol);
            return true;
        }
        return false;
    }
}
