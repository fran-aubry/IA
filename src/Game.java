
public class Game {

	private final int DEFAULT_BOARDSIZE = 9;
	private final float DEFAULT_KOMI = 0;
	
	private Board board;
	private float komi;
	
	public Game(Board board, float komi) {
		this.board = board;
		this.komi = komi;
	}
	
	public Game(int boardSize) {
		board = new Board(boardSize);
		komi = DEFAULT_KOMI;
	}
	
	public Game(float komi) {
		board = new Board(DEFAULT_BOARDSIZE);
		this.komi = komi;
	}
	
	public Game() {
		board = new Board(DEFAULT_BOARDSIZE);
		komi = DEFAULT_KOMI;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public float getKomi() {
		return komi;
	}
	
	public void setKomi(float komi) {
		this.komi = komi;
	}
	
	public void resetGame(float komi) {
		board = new Board(board.getSize());
		this.komi = komi;
	}
	
	public void resetGame(Board board) {
		this.board = board;
	}
	
}

