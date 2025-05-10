public class Board {
    int length = Constants.BOARD_WIDTH;
    int height = Constants.BOARD_HEIGHT;
    Block[][] board = new Block[height][length];

    public Board() {

    }

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

    public Block getBlock(int row, int col) {
        return board[row][col];
    }
    public void setBlock(int row, int col, Block block) {
        board[row][col] = block;
        block.setRow(row);
        block.setCol(col);
    }
    public void removeBlock(int row, int col) {
        board[row][col] = null;
    }
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
    public void clearLine(int r) {
        for (int c = 0; c < length; c++) {
            Block block = getBlock(r, c);
            if (block != null) {
                removeBlock(r, c);
            }
        }
    }
}
