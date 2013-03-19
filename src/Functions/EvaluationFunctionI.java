package Functions;

import Go.Board;
import Go.Color;


public interface EvaluationFunctionI {

	public int evaluate(Board board, Color color);
	
}

