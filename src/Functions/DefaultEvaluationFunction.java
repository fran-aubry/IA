package Functions;

import Go.Board;
import Go.Color;

public class DefaultEvaluationFunction implements EvaluationFunctionI {

	@Override
	public int evaluate(Board board, Color color) {
		return board.getTerritoryMap().getTerritorySize(color);
	}

}

