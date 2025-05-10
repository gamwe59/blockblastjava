/**
 * This class is an extension of Block. It is block but it has a dedicated size.
 * It is important that this class is an subclass because of the way it turns itself into a string.
 * Having all of this in the original Block class would be too complicated. This makes it easier.
 * 
 * @epicgamwe59
 * @1.0
 */

public class BlockGroup extends Block {
    private int size;

    /**
     * Alternate Constructor. Uses the original constructor, but also takes in a size value
     * @param size Size of the block
     */
    public BlockGroup(int size) {
        super();
        this.size = size;
    }
    /**
     * Constructor. Initializes the block's row, column, and size
     * @param row The block's row
     * @param col The block's column
     * @param size The block's size
     */
    public BlockGroup(int row, int col, int size) {
        super(row, col);
        this.size = size;
    }
    /**
     * Retrieves the size of the block
     * @return The size of the block
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Again, this would work better as a toString method
     * This method returns the block's appearance on the board
     * This time, it encorporates the size of the block into the string
     * @return The block's appearance on the board
     */
    @Override
    public String printBlock() {
        String text = "";
        if (super.isSelected()) {
            text += Constants.BLOCK_START_SELECT+Constants.BLOCK_FILL_SELECT;
            for (int i = 0; i < size-2; i++) {
                text += Constants.BLOCK_FILL_SELECT+Constants.BLOCK_FILL_SELECT;
            }
            text += Constants.BLOCK_FILL_SELECT+Constants.BLOCK_END_SELECT;
        } else {
            text += Constants.BLOCK_START+Constants.BLOCK_FILL;
            for (int i = 0; i < size-2; i++) {
                text += Constants.BLOCK_FILL+Constants.BLOCK_FILL;
            }
            text += Constants.BLOCK_FILL+Constants.BLOCK_END;
        }
       return text;
    }
}
