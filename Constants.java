/**
 * This class holds the constants for the game.
 * 
 * @epicgamwe59
 * @1.0
 */

public class Constants {
    public static final int STATE_START = 0;
    public static final int STATE_ADDLINE = 1;
    public static final int STATE_SHOWBOARD = 2;
    public static final int STATE_GETMOVE = 3;
    public static final int STATE_GETBLOCK = 6;
    public static final int STATE_UPDATE = 4;
    public static final int STATE_EXIT = 5;

    public static final int BLOCK_SIZE_MIN = 1;
    public static final int BLOCK_SIZE_MAX = 4;
    public static final String BLOCK_START = "[";
    public static final String BLOCK_END = "]";
    public static final String BLOCK_FILL = "+";
    public static final String BLOCK_START_SELECT = "{";
    public static final String BLOCK_END_SELECT = "}";
    public static final String BLOCK_FILL_SELECT = "=";
    
    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 10;
    public static final String BOARD_TOP = "_";
    public static final String BOARD_LEFT = "|";
    public static final String BOARD_RIGHT = "|";
    public static final String BOARD_BOTTOM = "--";
    public static final String BOARD_FILL = "  ";
    public static final String BOARD_CORNER = " ";

    public static final String START_COMMAND = "start";
    public static final String START_MESSAGE = "WELCOME TO BLOCK BLAST\n\n\nPLEASE TYPE '"+START_COMMAND+"' TO START";
    public static final String SELECT_MESSAGE_ROW = "PLEASE ENTER ROW";
    public static final String SELECT_MESSAGE_COL = "PLEASE ENTER COLUMN. SELECT FIRST SPACE THAT A PIECE APPEARS ON";
    public static final String INVALID_SPACE = "INVALID.";

    public static final int MS = 250;
}
