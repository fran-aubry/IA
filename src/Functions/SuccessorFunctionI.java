package Functions;

import java.util.List;
import Go.Board;
import Go.Color;
import Go.Index;

public interface SuccessorFunctionI {

	public List<Index> successors(Board board, Color color);
	
}

