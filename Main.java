import java.util.Scanner;

public class Main {

    private static int state = Constants.STATE_START;
    static Scanner input = new Scanner(System.in);
    static Board board = new Board();

    public static void run() throws InterruptedException {
        int sRow = -1;
        int sCol = -1;
        while (state != Constants.STATE_EXIT) {
            switch (state) {
                case Constants.STATE_START -> {
                    System.out.println(Constants.START_MESSAGE);
                    String cmd = input.nextLine();
                    if (cmd.equals(Constants.START_COMMAND)) {
                        state = Constants.STATE_ADDLINE;
                    }
                }
                case Constants.STATE_ADDLINE -> {
                    boolean lost = false;
                    for (int row = Constants.BOARD_HEIGHT-1; row >= 0; row--) {
                        for (int col = 0; col < Constants.BOARD_WIDTH; col++) {
                            Block block = board.getBlock(row, col);
                            if (block != null) {
                                if (row == Constants.BOARD_HEIGHT-2) {
                                    lost = true;
                                } else {
                                    board.setBlock(row+1, col, block);
                                    block.setRow(row+1);
                                    board.removeBlock(row, col);
                                }
                            }
                        }
                    }

                    int spaces = Constants.BOARD_WIDTH;
                    int gap = (int) ((Math.random())*2 + 1);
                    Block[] blocks = new Block[spaces];
                    while (spaces > 0) {
                        int size = (int) (Math.random()*(Constants.BLOCK_SIZE_MAX-Constants.BLOCK_SIZE_MIN+1)+Constants.BLOCK_SIZE_MIN);
                        size = Math.min(spaces, size);
                        if (size == 1) {
                            Block block = new Block();
                            blocks[Constants.BOARD_WIDTH-spaces] = block;
                        } else {
                            BlockGroup block = new BlockGroup(size);
                            blocks[Constants.BOARD_WIDTH-spaces] = block;
                        }
                        spaces -= size;
                        if (spaces > gap) {
                            spaces -= gap;
                            gap = spaces+1;
                        }
                    }
                    for (int i = 0; i < Constants.BOARD_WIDTH; i++) {
                        if (blocks[i] != null) {
                            board.setBlock(0, i, blocks[i]);
                            blocks[i].setRow(0);
                            blocks[i].setCol(i);
                        }
                    }
                    state = Constants.STATE_UPDATE;
                    if (lost == true) {
                        board.printBoard();
                        System.out.println("YOU LOST.");
                        state = Constants.STATE_EXIT;
                    }
                }
                case Constants.STATE_UPDATE -> {
                    board.printBoard();
                    Thread.sleep(Constants.MS);
                    boolean check = false;
                    boolean fell = false;
                    for (int row = 0; row < Constants.BOARD_HEIGHT; row++) {
                        for (int col = 0; col < Constants.BOARD_WIDTH; col++) {
                            Block block = board.getBlock(row, col);
                            if (block != null) {
                                if (row != 0) {
                                    check = true;
                                    if (board.canFall(row, col)) {
                                        board.setBlock(row-1, col, block);
                                        board.removeBlock(row, col);
                                        board.printBoard();
                                        Thread.sleep(Constants.MS);
                                        fell = true;
                                    }
                                }
                            }
                        }
                    }
                    if (fell == false) {
                        for (int row = Constants.BOARD_HEIGHT-1; row >= 0; row--) {
                            boolean clear = board.isLineClear(row);
                            if (clear) {
                                board.clearLine(row);
                                board.printBoard();
                                fell = true;
                                Thread.sleep(Constants.MS);
                            }
                        }
                    }
                    state = Constants.STATE_SHOWBOARD;
                    if (check == false) {
                        state = Constants.STATE_ADDLINE;
                    }
                    if (fell == true) {
                        state = Constants.STATE_UPDATE;
                    }
                }
                case Constants.STATE_SHOWBOARD -> {
                    board.printBoard();
                    state = Constants.STATE_GETBLOCK;
                }
                case Constants.STATE_GETBLOCK -> {
                    while (sCol == -1) {
                        while (sRow == -1) {
                        System.out.println(Constants.SELECT_MESSAGE_ROW);
                            sRow = input.nextInt();
                            if (sRow > Constants.BOARD_HEIGHT-1 || sRow < 0) {
                                sRow = -1;
                                System.out.println(Constants.INVALID_SPACE);
                            }
                        }
                        System.out.println(Constants.SELECT_MESSAGE_COL);
                        sCol = input.nextInt();
                        if (sCol > Constants.BOARD_WIDTH-1 || sCol < 0) {
                            sCol = -1;
                            sRow = -1;
                            System.out.println(Constants.INVALID_SPACE);
                        } else if (board.getBlock(sRow, sCol) == null) {
                            sCol = -1;
                            sRow = -1;
                            System.out.println(Constants.INVALID_SPACE);
                        }
                        
                    }
                    board.getBlock(sRow, sCol).select();
                    board.printBoard();
                    state = Constants.STATE_GETMOVE;
                }
                case Constants.STATE_GETMOVE -> {
                    int col = -1;
                    while (col == -1) {
                        System.out.println("SELECT A COLUMN TO MOVE THE PEICE TO");
                        System.out.println("TO SELECT A DIFFERENT PIECE, TYPE '-1'");
                        col = input.nextInt();
                        if (col == -1) {
                            break;
                        }
                        if (col > Constants.BOARD_WIDTH-1 || col < 0 || col == sCol) {
                            col = -1;
                            System.out.println(Constants.INVALID_SPACE);
                        } else if (board.isValidMove(sRow, sCol, sRow, col) == false) {
                            col = -1;
                            System.out.println(Constants.INVALID_SPACE);
                        }
                    }
                    Block block = board.getBlock(sRow, sCol);
                    block.deselect();
                    state = Constants.STATE_SHOWBOARD;
                    if (col != -1) {
                        board.setBlock(sRow, col, block);
                        board.removeBlock(sRow, sCol);
                        state = Constants.STATE_ADDLINE;
                    }
                    sRow = -1;
                    sCol = -1;
                }
                default -> {
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        run();
    }
}
