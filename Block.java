public class Block {
    private int row;
    private int col;
    private boolean selected;

    public Block(int row, int col) {
        this.row = row;
        this.col = col;
        this.selected = false;
    }
    public Block() {
        row = -1;
        col = -1;
        selected = false;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public void select() {
        selected = true;
    }
    public void deselect() {
        selected = false;
    }
    public boolean isSelected() {
        return selected;
    }
    public int getSize() {
        return 1;
    }

    public String printBlock() {
        if (selected) {
       return Constants.BLOCK_START_SELECT+Constants.BLOCK_END_SELECT;
        }
       return Constants.BLOCK_START+Constants.BLOCK_END;
    }
}
