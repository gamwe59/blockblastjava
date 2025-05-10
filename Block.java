/**
 * This class holds info for each block. It is very simple. It also returns a string for printing.
 * 
 * @epicgamwe59
 * @1.0
 */

public class Block {
    private int row;
    private int col;
    private boolean selected;
    /**
     * Constructor for Block class. Stores the block's row, column, and whether or not it is selected
     * @param row What row the block is at
     * @param col What column the block is at
     */
    public Block(int row, int col) {
        this.row = row;
        this.col = col;
        this.selected = false;
    }
    /**
     * Alternate constructor. Sets row and column to -1 because it will not be in play until it is given a position
     */
    public Block() {
        row = -1;
        col = -1;
        selected = false;
    }
    /*
     * Sets the block's row to the given row
     * @param row Row to be set
     */
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * Sets the block's column to the given column
     * @param col Column to be set
     */
    public void setCol(int col) {
        this.col = col;
    }
    /**
     * Retrieves the block's row
     * @return The block's row
     */
    public int getRow() {
        return row;
    }
    /**
     * Retrieves the block's column
     * @return The block's column
     */
    public int getCol() {
        return col;
    }
    /**
     * Sets the selected boolean to true. This changes the appearance of the block
     */
    public void select() {
        selected = true;
    }
    /**
     * Sets the selected boolean to false. This reverts the appearance of the block to normal
     */
    public void deselect() {
        selected = false;
    }
    /**
     * Retrives the block's selected status. This method is never used
     * @return The block's selected status
     */
    public boolean isSelected() {
        return selected;
    }
    /**
     * Retrieves the block's size
     * @return The block's size
     */
    public int getSize() {
        return 1;
    }

    /**
     * This returns a string of the block that will fit into the board.
     * The string changes depending on if the block is selected
     * In retrospect, this should have been a toString method, but I don't feel like changing all of the code I already did
     * @return String containing the block's appearance on the board
     */
    public String printBlock() {
        if (selected) {
       return Constants.BLOCK_START_SELECT+Constants.BLOCK_END_SELECT;
        }
       return Constants.BLOCK_START+Constants.BLOCK_END;
    }
}
