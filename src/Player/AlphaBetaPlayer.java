package Player;

import java.util.ArrayList;

import Functions.DepthCutoffFunction;
import Go.Board;
import Go.Color;
import Go.Game;
import Go.Index;
import Search.AlphaBeta;

public class AlphaBetaPlayer extends Player {

	public AlphaBetaPlayer(float komi) {
		super(komi);
		
	}

	@Override
	public Index makeMove(Board board, Color color) {
		AlphaBeta ab = new AlphaBeta();
		ab.setCutoffFunction(new DepthCutoffFunction(4));
		ArrayList<Index> depthAction = ab.alphaBeta(board, color);
		return depthAction.get(0);
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
		return "AlphaBeta";
	}

}

