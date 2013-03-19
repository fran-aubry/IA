package Functions;

import java.util.LinkedList;
import java.util.List;

import Go.Board;
import Go.Color;
import Go.Index;

public class DefaultSuccessorFunction implements SuccessorFunctionI {

	@Override
	public List<Index> successors(Board board, Color color) {
		List<Index> successors = new LinkedList<Index>();
		for(int i = 0; i < board.getSize(); i++) {
			for(int j = 0; j < board.getSize(); j++) {
				Index I = new Index(i, j);
				if(board.isValidMove(I, color)) {
					successors.add(I);
				}
			}
		}
		return successors;
	}

	
	
}

