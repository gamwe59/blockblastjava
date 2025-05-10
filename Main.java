import java.util.Scanner;
    /**
     * The Main class is where the code is run from. It has a loop that loops through the gamecycle until the game ends.
     * It has the directions for the Board class. It properly adds blocks to and updates the board at each point in the loop.
     * It takes input from the user on what action to do in the game.
     * 
     * @epicgamwe59
     * @1.0 
    */

public class Main {

    private static int state = Constants.STATE_START;
    static Scanner input = new Scanner(System.in);
    static Board board = new Board();

    /**
    * This loops through the game until the state is set to exit.
    * There is currently no way to exit without losing, but that's okay.
    * This method is very long. I am considering each case to be its own method.
    */

    public static void run() throws InterruptedException {
        int sRow = -1;
        int sCol = -1;
        while (state != Constants.STATE_EXIT) {
            switch (state) {
                /**
                 * When you first start the program, this will run.
                 * It shows a basic title card and asks the player to type start when they are ready to play.
                 * After it gets the start command, it switches the case to the ADDLINE case.
                 */
                case Constants.STATE_START -> {
                    System.out.println();
                    System.out.println(Constants.START_MESSAGE);
                    String cmd = input.nextLine();
                    if (cmd.equals(Constants.START_COMMAND)) {
                        state = Constants.STATE_ADDLINE;
                    }
                }
                /**
                 * This is the first event of the game loop.
                 * At the start of every turn, the game adds a randomly generated row to the bottom, and pushes all pieces up one row.
                 * This case also checks if the game is lost. The game is lost when a row hits the top of the board.
                 * To generate a row, the case picks a number between 1 and 2 to be the size of the gap.
                 * Then, the case makes a block from random size MIN to MAX
                 * The case will then skip spaces according to the gap, then fill the rest of the spaces with new blocks
                 * The blocks are then added to the board after all the other blocks are pushed up
                 * If there is only one line on the board, this case will run again. Otherwise it will change the state to UPDATE
                 */
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
                /**
                 * This case handles the game updates
                 * After the line is added from ADDLINE, the case checks if each piece can fall
                 * If a piece can fall, it drops the piece down one row and prints the board
                 * After all the pieces have fallen, the case checks if any lines are full
                 * If there are any full lines, it clears the line and checks for falling pieces again
                 * Once there are no lines to be cleared, nor pieces to fall, the state is changed to SHOWBOARD
                 * If there is only one line left, ADDLINE will be called instead
                 */
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
                /**
                 * This doesn't need to be its own case.
                 * But, it helps me seperate the two chunks of the code, so I kept it
                 * This case just prints the board and changes the state to GETBLOCK
                 */
                case Constants.STATE_SHOWBOARD -> {
                    board.printBoard();
                    state = Constants.STATE_GETBLOCK;
                }
                /**
                 * This case asks the user to select a piece
                 * It prompts the user for a row and column, and repeats asking until the user selects a valid piece
                 * After a piece is selected, the state is changed to GETMOVE
                 */
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
                /**
                 * This case prompts the user for a column to move the selected piece to
                 * Once the user selects a valid column, the case moves the piece
                 * It then changes the state to ADDLINE, repeating the loop
                 */
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

    /**
     * Main method
     * It calls the run method
     */
    public static void main(String[] args) throws InterruptedException {
        run();
    }
}
