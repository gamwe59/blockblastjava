public class BlockGroup extends Block {
    private int size;

    public BlockGroup(int size) {
        super();
        this.size = size;
    }
    public BlockGroup(int row, int col, int size) {
        super(row, col);
        this.size = size;
    }
    @Override
    public int getSize() {
        return size;
    }

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
