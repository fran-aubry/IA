
public abstract class Player {

	protected Game game;
	
	public Player(Game game) {
		this.game = game;
	}
	
	public void putStone(Index position, Color color) {
		game.getBoard().setColor(position, color);
	}
	
	/**
	 * @param color
	 * @return null if pass and a point to play otherwise
	 */
	public abstract Index makeMove(Color color);
	
	public abstract void resetPlayer();
	
	public abstract void updateKomi();
	
	public Board getBoard() {
		return game.getBoard();
	}

	public void resetGame(int boardsize) {
		game = new Game(boardsize);
		resetPlayer();
	}
	
	public void setKomi(float komi) {
		game.setKomi(komi);
		updateKomi();
	}
	
	public void clearBoard() {
		game = new Game(game.getBoard().getSize());
		resetPlayer();
	}
	
}

