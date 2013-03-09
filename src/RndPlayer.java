
public class RndPlayer extends Player {

	public RndPlayer(Game game) {
		super(game);
	}

	@Override
	public Index makeMove(Color color) {
		Board board = game.getBoard();
		Index p = board.getFirstPossibleMove(color);
		if(p != null) {
			board.setColor(p, color);
			return p;
		}
		return Index.PASS;
	}

	@Override
	public void resetPlayer() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateKomi() {
		// TODO Auto-generated method stub
		
	}
	
}

