package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.gamecentre.GameManager;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Manage a slidingTileBoard, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingTileBoardManager implements Serializable, GameManager {

    /**
     * The slidingTileBoard being managed.
     */
    private SlidingTileBoard slidingTileBoard;

    /**
     * The moves that have been made in this game.
     */
    private Stack<ArrayList<Integer>> undoStack = new Stack<>();

    /**
     * The current number of moves that the player has made.
     */
    private int move = 0;

    /**
     * Manage a slidingTileBoard that has been pre-populated.
     *
     * @param slidingTileBoard the slidingTileBoard
     */
    SlidingTileBoardManager(SlidingTileBoard slidingTileBoard) {
        this.slidingTileBoard = slidingTileBoard;
    }

    /**
     * Manage a slidingTileBoard with varies complexities.
     *
     * @param boardSize the size of the slidingTileBoard.
     */
    SlidingTileBoardManager(int boardSize) {
        List<Tile> tiles = createTiles(boardSize);
        Collections.shuffle(tiles);
        slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
        if (!checkSolvability(tiles, boardSize)) {
            makeSolvableBoard(tiles);
            slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
        }
    }

    /**
     * Create tiles with the corresponding board size.
     *
     * @param boardSize size of the board
     * @return list of tiles
     */
    @NonNull
    private List<Tile> createTiles(int boardSize) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Collections.sort(tiles);
        tiles.remove(0);
        Tile blankTile = new Tile(24);
        tiles.add(blankTile);

        return tiles;
    }

    /**
     * Checks if the current sliding tile board is solvable. If it is not solvable, make it solvable.
     *
     * @param boardSize size of the board
     * @param tiles     a list containing tiles for sliding tile game
     *                  reference: https://www.sitepoint.com/randomizing-sliding-puzzle-tiles/
     *                  reference: https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
     */
    private boolean checkSolvability(List<Tile> tiles, int boardSize) {
        int blankId = 25;
        int blankRow = slidingTileBoard.findTile(blankId) / boardSize;
        int inversionCount = getInversionCount(tiles);
        return (boardSize % 2 == 1 && inversionCount % 2 == 0 ||
                boardSize % 2 == 0 && (inversionCount + boardSize - 1 - blankRow) % 2 == 0);
    }

    /**
     * Calculates the number of inversions in sliding tile board.
     *
     * @param tiles a list containing tiles for sliding tile game
     * @return number of inversions
     */
    private int getInversionCount(List<Tile> tiles) {
        int inversionCount = 0;
        for (int i = 0; i != tiles.size() - 1; i++)
            for (int j = i + 1; j != tiles.size(); j++)
                if (tiles.get(i).getId() != 25 && tiles.get(j).getId() != 25)
                    if (tiles.get(i).getId() > tiles.get(j).getId())
                        inversionCount++;
        return inversionCount;
    }

    /**
     * Modifies the board, so the inversion count gets +/- by 1 to make the board solvable.
     *
     * @param tiles a list containing tiles for sliding tile game
     */
    private void makeSolvableBoard(List<Tile> tiles) {
        if (tiles.get(0).getId() == 25 || tiles.get(1).getId() == 25) {
            Collections.swap(tiles, tiles.size() - 1, tiles.size() - 2);
        } else {
            Collections.swap(tiles, 0, 1);
        }
    }

    /**
     * Set the board to be one move away from winning.
     */
    void setBoardOneMoveWin() {
        int boardSize = getSlidingTileBoard().getBoardSize();
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.remove(tiles.size() - 1);
        tiles.add(new Tile(24));
        slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
        slidingTileBoard.swapTiles(slidingTileBoard.getBoardSize() - 1, slidingTileBoard.getBoardSize() - 1,
                slidingTileBoard.getBoardSize() - 1, slidingTileBoard.getBoardSize() - 2);
    }

    @Override
    public boolean puzzleSolved() {
        boolean solved = true;
        int start = 1;
        for (Tile tile : slidingTileBoard) {
            if (tile.getId() != start) {
                solved = false;
            }
            start++;
        }
        return solved;
    }

    /**
     * Checks whether position is a valid tap
     * @param position the position of the tap
     * @return boolean whether position is valid
     */
    @Override
    public boolean isValidTap(int position) {
        int row = position / slidingTileBoard.getBoardSize();
        int col = position % slidingTileBoard.getBoardSize();
        int blankId = 25;

        Tile above = row == 0 ? null : slidingTileBoard.getTile(row - 1, col);
        Tile below = row == slidingTileBoard.getBoardSize() - 1 ? null : slidingTileBoard.getTile(row + 1, col);
        Tile left = col == 0 ? null : slidingTileBoard.getTile(row, col - 1);
        Tile right = col == slidingTileBoard.getBoardSize() - 1 ? null : slidingTileBoard.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Makes a move at position if position is valid
     * @param position the position of the tap
     */
    @Override
    public void touchMove(int position) {
        int row = position / slidingTileBoard.getBoardSize();
        int col = position % slidingTileBoard.getBoardSize();
        int blankId = 25;
        int blankRow = slidingTileBoard.findTile(blankId) / slidingTileBoard.getBoardSize();
        int blankCol = slidingTileBoard.findTile(blankId) % slidingTileBoard.getBoardSize();

        if (isValidTap(position)) {
            ArrayList<Integer> undoMove = new ArrayList<>();
            undoMove.add(blankRow);
            undoMove.add(blankCol);
            undoMove.add(row);
            undoMove.add(col);
            this.undoStack.add(undoMove);
            this.move++;
            slidingTileBoard.swapTiles(blankRow, blankCol, row, col);
        }
    }

    /**
     * Undo the previous move made, if possible. Return true if move is undone, otherwise false.
     *
     * @return true if move is undone, other wise false
     */
    boolean undoMove() {
        if (!undoStack.empty()) {
            ArrayList<Integer> previousMove = undoStack.pop();
            int row = previousMove.get(2);
            int col = previousMove.get(3);
            int blankRow = previousMove.get(0);
            int blankCol = previousMove.get(1);
            slidingTileBoard.swapTiles(row, col, blankRow, blankCol);
            return true;
        }
        return false;
    }

    /**
     * Return the current slidingTileBoard.
     *
     * @return return the current slidingTileBoard.
     */
    SlidingTileBoard getSlidingTileBoard() {
        return slidingTileBoard;
    }

    /**
     * Getter for move
     * @return number of moves
     */
    @Override
    public int getMove() {
        return move;
    }

    /**
     * Getter for score
     * @return the score
     */
    @Override
    public int getScore() {
        int score = 1000 - (move * 3 * (6 - slidingTileBoard.getBoardSize()));
        if (score < 0) {
            return 0;
        }
        return score;
    }

    /**
     * Getter for gameName
     * @return the gamename
     */
    @Override
    public String getGameName() {
        return "Sliding Tile";
    }

    /**
     * Getter for current time
     * @return the current time
     */
    @Override
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * Getter for game difficulty
     * @return the difficulty
     */
    @Override
    public String getGameDifficulty() {
        return String.format("%s by %s", slidingTileBoard.getBoardSize(), slidingTileBoard.getBoardSize());
    }
}
