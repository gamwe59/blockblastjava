/**
 * This class holds the logic for the board. It stores the blocks in the board.
 * This class prints the board, and checks different possible movements for the blocks.
 * 
 * @epicgamwe59
 * @1.0
 */

public class Board {
    int length;
    int height;
    Block[][] board;

    /**
     * Constructor. Initalizes the board
    */
    public Board() {
        length = Constants.BOARD_WIDTH;
        height = Constants.BOARD_HEIGHT;
        board = new Block[height][length];
    }

    /**
     * This method prints the board
     * It prints the top first, with column indexes
     * Then it prints each row, with row index on the left
     * Then it prints the bottom
     */
    public void printBoard() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
        System.out.print(Constants.BOARD_CORNER);
        for (int i = 0; i < length; i++) {
            System.out.print(i+Constants.BOARD_TOP);
        }
        System.out.println(Constants.BOARD_CORNER);
        for (int row = height-1; row >= 0; row--) {
            System.out.print(row);
            for (int col = 0; col < length; col++) {
                if (board[row][col] == null) {
                    System.out.print(Constants.BOARD_FILL);
                } else {
                    String piece = board[row][col].printBlock();
                    System.out.print(piece);
                    col += piece.length()/2 - 1;
                }
            }
            System.out.println(Constants.BOARD_RIGHT);
        }
        System.out.print(Constants.BOARD_CORNER);
        for (int i = 0; i < length; i++) {
            System.out.print(Constants.BOARD_BOTTOM);
        }
        System.out.println(Constants.BOARD_CORNER);
    }
    /**
     * @param row The row of the block requested
     * @param col The column of the block requested
     * @return The block requested
     */
    public Block getBlock(int row, int col) {
        return board[row][col];
    }
    /**
     * This puts the block sent onto the board, and updates the block's internal location
     * @param row Requested row for block to be placed
     * @param col Requested column for block to be placed
     * @param block Block requested to be placed
     */
    public void setBlock(int row, int col, Block block) {
        board[row][col] = block;
        block.setRow(row);
        block.setCol(col);
    }
    /**
     * Sets the requested block to null, removing it from the board
     * @param row Requested row of block to remove
     * @param col Requested column of block to remove
     */
    public void removeBlock(int row, int col) {
        board[row][col] = null;
    }
    /**
     * Checks if the given move is valid by comparing the selected block to each block in the same row
     * @param sR Selected block's row
     * @param sC Selected block's column
     * @param r Selected block's row again for some reason
     * @param c Column selected to be sent to
     * @return Returns a boolean dictating if the given move is valid (true) or not (false)
     */
    public boolean isValidMove(int sR, int sC, int r, int c) {
        Block block = getBlock(sR, sC);
        //claimed spaces = c to c+size-1
        if (block != null) {
            if (c+block.getSize()-1 < Constants.BOARD_WIDTH) {
                for (int i = 0; i < length; i++) {
                    Block block2 = getBlock(sR,i);
                    int c2 = c+block.getSize()-1;
                    if (block2 != null && block2 != block) {
                        int a = block2.getCol();
                        int a2 = block2.getCol()+block2.getSize()-1;
                        if (
                            (c >= a && c <= a2) ||
                            (c2 >= a && c <= a2) ||
                            (a >= c && a <= c2) ||
                            (a2 >= c && a2 <= c2)
                            ) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the given block is able to fall using the same process as isValidMove, just for the row below the block instead of the same row
     * @param r Requested block's row
     * @param c Requested block's column
     * @return Returns a boolean dicating if the given block can fall
     */
    public boolean canFall(int r, int c) {
        Block block = getBlock(r, c);
        if (block != null && r != 0) {
            for (int i = 0; i < length; i++) {
                Block block2 = getBlock(r-1, i);
                int c2 = c+block.getSize()-1;
                if (block2 != null) {
                    int a = block2.getCol();
                    int a2 = block2.getCol()+block2.getSize()-1;
                    if (
                        (c >= a && c <= a2) ||
                        (c2 >= a && c <= a2) ||
                        (a >= c && a <= c2) ||
                        (a2 >= c && a2 <= c2)
                        ) {
                            return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    /**
     * Checks if a line has no empty spaces
     * @param r Row requested to be checked
     * @return Returns a boolean dictating if the given row is full
     */
    public boolean isLineClear(int r) {
        for (int c = 0; c < length; c++) {
            Block block = getBlock(r, c);
            if (block != null) {
                c+=block.getSize()-1;
            } else {
                return false;
            }
        }
        return true;
    }
    /**
     * Loops through all blocks in given row and removes them
     * @param r Row requested to be cleared
     */
    public void clearLine(int r) {
        for (int c = 0; c < length; c++) {
            Block block = getBlock(r, c);
            if (block != null) {
                removeBlock(r, c);
            }
        }
    }
}
