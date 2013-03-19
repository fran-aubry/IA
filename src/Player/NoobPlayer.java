package Player;

import Go.Board;
import Go.Color;
import Go.Game;
import Go.Index;

public class NoobPlayer extends Player {

	public NoobPlayer(float komi) {
		super(komi);
	}

	@Override
	public Index makeMove(Board board, Color color) {
		int n = board.getSize();
		Index bestI = null;
		int maxSize = Integer.MIN_VALUE;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				Index I = new Index(i, j);
				if(board.isValidMove(I, color)) {
					Board boardCopy = board.clone();
					boardCopy.processMove(I, color);
					if(boardCopy.getTerritoryMap().getTerritorySize(color) >= maxSize) {
						bestI = I;
						maxSize =  boardCopy.getTerritoryMap().getTerritorySize(color);
					}
				}
			}
		}
		if(bestI == null) {
			return Index.PASS;
		}
		return bestI;
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
		return "NOOB";
	}

}

