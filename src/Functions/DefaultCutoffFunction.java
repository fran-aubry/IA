package Functions;

import Go.Board;

public class DefaultCutoffFunction implements CutoffFunctionI{

	@Override
	public boolean cutoff(Board board, int depth) {
		return true;
	}

}

