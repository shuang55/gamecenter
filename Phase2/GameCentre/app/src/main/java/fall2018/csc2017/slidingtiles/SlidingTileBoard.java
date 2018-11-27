package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The sliding tiles board.
 */
public class SlidingTileBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The board size
     */
    int boardSize;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * Initializes the SlidingTileBoard for game.
     *
     * @param tiles     list of tiles
     * @param boardSize the size of board
     */
    SlidingTileBoard(List<Tile> tiles, int boardSize) {
        this.tiles = new Tile[boardSize][boardSize];
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != boardSize; row++) {
            for (int col = 0; col != boardSize; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
        this.boardSize = boardSize;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the tiles for this slidingTileBoard.
     *
     * @return the tiles for this slidingTileBoard
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile t = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = t;
        setChanged();
        notifyObservers();
    }

    /**
     * Finds the tile with id tileID
     *
     * @param tileID the id of the tile
     * @return the position of the tile
     */
    int findTile(int tileID) {
        int position = 0;
        for (Tile[] t1 : tiles) {
            for (Tile t2 : t1) {
                if (t2.getId() == tileID) {
                    return position;
                }
                position++;
            }
        }
        return position;
    }

    @Override
    public String toString() {
        return "SlidingTileBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * An iterator that iterates through SlidingTileBoard
     */
    private class BoardIterator implements Iterator<Tile> {

        private int row = 0;
        private int col = 0;

        @Override
        public boolean hasNext() {
            return !(row == boardSize - 1 && col == boardSize - 1);
        }

        @Override
        public Tile next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more tiles");
            } else if (col == boardSize - 1) {
                Tile t = tiles[row][col];
                row++;
                col = 0;
                return t;
            } else {
                Tile t = tiles[row][col];
                col++;
                return t;
            }
        }
    }
}
