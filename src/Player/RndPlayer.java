package Player;
import Go.Board;
import Go.Color;
import Go.Game;
import Go.Index;


public class RndPlayer extends Player {

	public RndPlayer(float komi) {
		super(komi);
	}

	@Override
	public Index makeMove(Board board, Color color) {
		Index p = board.getFirstPossibleMove(color);
		if(p != null) {
			return p;
		}
		return Index.PASS;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateKomi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RND";
	}
	
}

