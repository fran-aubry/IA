package Functions;

import Go.Board;

public class DepthCutoffFunction implements CutoffFunctionI {

	private int maxDepth;
	
	public DepthCutoffFunction(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	@Override
	public boolean cutoff(Board board, int depth) {
		return depth >= maxDepth;
	}

}

