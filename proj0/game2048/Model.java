package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author WANG, Ruida
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        // TODO: Fill in this constructor.
        score = 0;
        int[][] a = new int[size][size];
        board = new Board(a,score);
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        // TODO: Fill in this constructor.
        if(! gameOver) {
            board = new Board(rawValues, score);
        }
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

       /* for (int col = 0; col< board.size();col++){
            for (int row = 0; row< board.size(); row++){
                Tile t = board.tile(col,row);
                if (t != null){
                    board.move(col, 3, t);
                    score = score +7;
                    changed = true;
                }
            }
        }

        */




        if (side == Side.NORTH) {

            for (int col = board.size() - 1; col >= 0; col--) {
                for (int row = board.size() - 1; row > 0; row--) {
                    if (board.tile(col, row) != null) {
                        int lowerCloestNonEmptyBox = row - 1;
                        for (int row1 = row - 1; row1 >= 0; row1--) {
                            if (board.tile(col, row1) != null) {
                                lowerCloestNonEmptyBox = row1;
                                break;
                            }
                        }
                        int upperFarestEmptyBox = row;
                        for (int row2 = board.size() - 1; row2 > row; row2--) {
                            if (board.tile(col, row2) == null) {
                                upperFarestEmptyBox = row2;
                                break;
                            }
                        }
                        Tile thisTile = board.tile(col, row);
                        if (board.tile(col, lowerCloestNonEmptyBox) != null) {
                            Tile lowerTile = board.tile(col, lowerCloestNonEmptyBox);
                            if (thisTile.value() == lowerTile.value()) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                board.move(col, upperFarestEmptyBox, lowerTile);
                                changed = true;
                            } else if (upperFarestEmptyBox != row) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                changed = true;
                            }
                        } else if (upperFarestEmptyBox != row) {
                            board.move(col, upperFarestEmptyBox, thisTile);
                            changed = true;
                        }
                    }
                }           //expected the final row that row = 0
                if (board.tile(col, 0) != null) {
                    int upperFarestEmptyBox = 0;
                    for (int row2 = board.size() - 1; row2 > 0; row2--) {
                        if (board.tile(col, row2) == null) {
                            upperFarestEmptyBox = row2;
                            break;
                        }
                    }
                    if (upperFarestEmptyBox != 0) {
                        board.move(col, upperFarestEmptyBox, board.tile(col, 0));
                        changed = true;
                    }
                }
            }
        }


        if (side == Side.SOUTH){
            board.setViewingPerspective(Side.SOUTH);
            for (int col = board.size() - 1; col >= 0; col--) {
                for (int row = board.size() - 1; row > 0; row--) {
                    if (board.tile(col, row) != null) {
                        int lowerCloestNonEmptyBox = row - 1;
                        for (int row1 = row - 1; row1 >= 0; row1--) {
                            if (board.tile(col, row1) != null) {
                                lowerCloestNonEmptyBox = row1;
                                break;
                            }
                        }
                        int upperFarestEmptyBox = row;
                        for (int row2 = board.size() - 1; row2 > row; row2--) {
                            if (board.tile(col, row2) == null) {
                                upperFarestEmptyBox = row2;
                                break;
                            }
                        }
                        Tile thisTile = board.tile(col, row);
                        if (board.tile(col, lowerCloestNonEmptyBox) != null) {
                            Tile lowerTile = board.tile(col, lowerCloestNonEmptyBox);
                            if (thisTile.value() == lowerTile.value()) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                board.move(col, upperFarestEmptyBox, lowerTile);
                                changed = true;
                            } else if (upperFarestEmptyBox != row) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                changed = true;
                            }
                        } else if (upperFarestEmptyBox != row) {
                            board.move(col, upperFarestEmptyBox, thisTile);
                            changed = true;
                        }
                    }
                }           //expected the final row that row = 0
                if (board.tile(col, 0) != null) {
                    int upperFarestEmptyBox = 0;
                    for (int row2 = board.size() - 1; row2 > 0; row2--) {
                        if (board.tile(col, row2) == null) {
                            upperFarestEmptyBox = row2;
                            break;
                        }
                    }
                    if (upperFarestEmptyBox != 0) {
                        board.move(col, upperFarestEmptyBox, board.tile(col, 0));
                        changed = true;
                    }
                }
            }
            board.setViewingPerspective(Side.NORTH);

        }

        if (side == Side.WEST){
            board.setViewingPerspective(Side.WEST);
            for (int col = board.size() - 1; col >= 0; col--) {
                for (int row = board.size() - 1; row > 0; row--) {
                    if (board.tile(col, row) != null) {
                        int lowerCloestNonEmptyBox = row - 1;
                        for (int row1 = row - 1; row1 >= 0; row1--) {
                            if (board.tile(col, row1) != null) {
                                lowerCloestNonEmptyBox = row1;
                                break;
                            }
                        }
                        int upperFarestEmptyBox = row;
                        for (int row2 = board.size() - 1; row2 > row; row2--) {
                            if (board.tile(col, row2) == null) {
                                upperFarestEmptyBox = row2;
                                break;
                            }
                        }
                        Tile thisTile = board.tile(col, row);
                        if (board.tile(col, lowerCloestNonEmptyBox) != null) {
                            Tile lowerTile = board.tile(col, lowerCloestNonEmptyBox);
                            if (thisTile.value() == lowerTile.value()) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                board.move(col, upperFarestEmptyBox, lowerTile);
                                changed = true;
                            } else if (upperFarestEmptyBox != row) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                changed = true;
                            }
                        } else if (upperFarestEmptyBox != row) {
                            board.move(col, upperFarestEmptyBox, thisTile);
                            changed = true;
                        }
                    }
                }           //expected the final row that row = 0
                if (board.tile(col, 0) != null) {
                    int upperFarestEmptyBox = 0;
                    for (int row2 = board.size() - 1; row2 > 0; row2--) {
                        if (board.tile(col, row2) == null) {
                            upperFarestEmptyBox = row2;
                            break;
                        }
                    }
                    if (upperFarestEmptyBox != 0) {
                        board.move(col, upperFarestEmptyBox, board.tile(col, 0));
                        changed = true;
                    }
                }
            }
            board.setViewingPerspective(side.NORTH);
        }

        if (side == Side.EAST){
            board.setViewingPerspective(Side.EAST);
            for (int col = board.size() - 1; col >= 0; col--) {
                for (int row = board.size() - 1; row > 0; row--) {
                    if (board.tile(col, row) != null) {
                        int lowerCloestNonEmptyBox = row - 1;
                        for (int row1 = row - 1; row1 >= 0; row1--) {
                            if (board.tile(col, row1) != null) {
                                lowerCloestNonEmptyBox = row1;
                                break;
                            }
                        }
                        int upperFarestEmptyBox = row;
                        for (int row2 = board.size() - 1; row2 > row; row2--) {
                            if (board.tile(col, row2) == null) {
                                upperFarestEmptyBox = row2;
                                break;
                            }
                        }
                        Tile thisTile = board.tile(col, row);
                        if (board.tile(col, lowerCloestNonEmptyBox) != null) {
                            Tile lowerTile = board.tile(col, lowerCloestNonEmptyBox);
                            if (thisTile.value() == lowerTile.value()) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                board.move(col, upperFarestEmptyBox, lowerTile);
                                changed = true;
                            } else if (upperFarestEmptyBox != row) {
                                board.move(col, upperFarestEmptyBox, thisTile);
                                changed = true;
                            }
                        } else if (upperFarestEmptyBox != row) {
                            board.move(col, upperFarestEmptyBox, thisTile);
                            changed = true;
                        }
                    }
                }           //expected the final row that row = 0
                if (board.tile(col, 0) != null) {
                    int upperFarestEmptyBox = 0;
                    for (int row2 = board.size() - 1; row2 > 0; row2--) {
                        if (board.tile(col, row2) == null) {
                            upperFarestEmptyBox = row2;
                            break;
                        }
                    }
                    if (upperFarestEmptyBox != 0) {
                        board.move(col, upperFarestEmptyBox, board.tile(col, 0));
                        changed = true;
                    }
                }
            }
            board.setViewingPerspective(Side.NORTH);
        }



/*
        if (side == Side.NORTH) {
            for (int col = board.size()-1; col >= 0; col--) {
                for (int row = board.size()-1; row>=0; row--){
                    if (board.tile(col,row) != null){

                        if(row-1>=0) {
                            if (board.tile(col, row - 1) != null) {
                                if (board.tile(col, row).value() == board.tile(col, row - 1).value()) {

                                    //way to find the top empty space
                                    int topEmptySpace = row;
                                    for (int space = board.size() - 1; space > row; space--) {
                                        if (board.tile(col, space) == null) {
                                            topEmptySpace = space;
                                            break;
                                        }
                                    }

                                    Tile upper = board.tile(col, row);
                                    Tile lower = board.tile(col, row - 1);

                                    board.move(col, topEmptySpace, upper);
                                    board.move(col, topEmptySpace, lower);
                                    changed = true;

                                } else {

                                    int topEmptySpace = row;
                                    for (int space = board.size() - 1; space > row; space--) {
                                        if (board.tile(col, space) == null) {
                                            topEmptySpace = space;
                                            break;
                                        }
                                    }

                                    Tile thisSpace = board.tile(col, row);
                                    board.move(col, topEmptySpace, thisSpace);
                                    changed = true;
                                }
                            }
                        }

                            else{

                            int topEmptySpace = row;
                            for (int space = board.size()-1; space>row; space--){
                                if (board.tile(col,space) == null){
                                    topEmptySpace = space;
                                    break;
                                }
                            }

                            Tile thisSpace = board.tile(col,row);
                            board.move(col,topEmptySpace, thisSpace);
                            changed = true;
                        }
                    }
                }
            }
        }
*/

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        boolean a = false;
        for(int col = 0; col<=b.size()-1; col++){
            for (int row = 0; row<=b.size()-1; row++){
                if(b.tile(col,row) == null)
                    a = true;
            }
        }
        return a;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        boolean a = false;
        for (int row = 0;row <= b.size()-1;row++){
            for(int col = 0;col <= b.size()-1; col++){
                if (b.tile(row,col)!=null) {
                    Tile thisTile = b.tile(row, col);
                    if (thisTile.value() == MAX_PIECE) {
                        a = true;
                    }
                }
            }
        }
        return a;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        boolean a = false;
        for (int row = 0; row <= b.size()-1; row++){
            for (int col = 0; col <= b.size()-1; col++){
                if (b.tile(col,row) == null){
                    a = true;
                } else if (b.tile(col,row)!=null) {
                    if (row - 1 >= 0) {
                        if(b.tile(col,row-1) != null) {
                            if (b.tile(col, row).value() == b.tile(col, row - 1).value()) {
                                a = true;
                            }
                        }
                    }
                    if (row + 1 <= b.size() - 1) {
                        if(b.tile(col,row+1) != null) {
                            if (b.tile(col, row).value() == b.tile(col, row + 1).value()) {
                                a = true;
                            }
                        }
                    }
                    if (col - 1 >= 0) {
                        if(b.tile(col-1,row) != null && b.tile(row,col) != null) {
                            if (b.tile(row, col).value() == b.tile(col - 1, row).value()) {
                                a = true;
                            }
                        }
                    }
                    if (col + 1 <= b.size() - 1) {
                        if(b.tile(col+1,row)!=null && b.tile(row,col) != null){
                            if (b.tile(row, col).value() == b.tile(col + 1, row).value()) {
                                a = true;
                            }
                        }
                    }
                }
            }
        }

        return a;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
