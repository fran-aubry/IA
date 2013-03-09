import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** An individual state of the board. */

public class Board {

	public Index koPoint;

	private int boardSize;
	private Color[][] board;

	public Board(Color[][] board) {
		boardSize = board.length;
		this.board = board;
		this.koPoint = null;
	}
	
	public Board(int size) {
		boardSize = size;
		board = new Color[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				board[i][j] = Color.EMPTY;
			}
		}
		this.koPoint = null;
	}
	
	public Board(Color[][] board, Index koPoint) {
		boardSize = board.length;
		this.board = board;
		this.koPoint = koPoint;
	}

	public Color getColor(Index p) {
		return board[p.i][p.j];
	}

	public void setColor(Index p, Color color) {
		board[p.i][p.j] = color;
	}

	public boolean canMove(Index p, Color c) {
		return processMove(p, c) != null;
	}
	
	public int getSize() {
		return boardSize;
	}
	
	public Index getFirstPossibleMove(Color c) {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				Index p = new Index(i, j);
				if(canMove(p, c)) {
					return p;
				}
			}
		}
		return null;
	}
	
	public Board makeFirstPossibleMove(Color c) {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				Index p = new Index(i, j);
				if(canMove(p, c)) {
					return processMove(p, c);
				}
			}
		}
		return null;
	}
	
	public Board processMove(Index p, Color c) {
		Board result = clone();
		Set<Index> capturedStones = new HashSet<Index>();

		// Is the point already occupied?
		if (result.getColor(p) != Color.EMPTY) {
			return null;
		}

		// Is the move ko?
		if (p.equals(result.koPoint)) {
			return null;
		}

		// Is the move suicide?
		boolean suicide = true;
		List<Index> neighbors = result.getNeighbors(p);
		for (Index neighbor: neighbors) {
			if (result.getColor(neighbor) == Color.EMPTY) {
				// if any neighbor is VACANT, suicide = false
				suicide = false;
			} else if (result.getColor(neighbor) == c) {
				// if any neighbor is an ally
				// that isn't in atari
				if (!result.getChain(neighbor).inAtari()) {
					suicide = false;
				}
			} else if (result.getColor(neighbor) == c.opposite()) {
				// if any neighbor is an enemy
				// and that enemy is in atari
				Chain enemy = result.getChain(neighbor);
				if (enemy.inAtari()) {
					suicide = false;

					// remove the enemy stones from the board
					for (Index point : enemy) {
						result.setColor(point, Color.EMPTY);
						capturedStones.add(point);
					}
				}
			}
		}

		if (neighbors.size() > 0 && suicide) {
			return null;
		}

		// If the point is not occupied, the move is not ko, and not suicide
		// it is a legal move.
		result.setColor(p, c);

		// If this move captured exactly one stone, that stone is the new ko point
		if (capturedStones.size() == 1) {
			result.koPoint = capturedStones.iterator().next();
		} else {
			result.koPoint = null;
		}

		return result;
	}


	public List<Index> getNeighbors(Index p) {
		List<Index> neighbors = new ArrayList<Index>();

		if (p.i > 0) neighbors.add(new Index(p.i - 1, p.j));
		if (p.i < boardSize - 1) neighbors.add(new Index(p.i + 1, p.j));
		if (p.j > 0) neighbors.add(new Index(p.i, p.j - 1));
		if (p.j < boardSize - 1) neighbors.add(new Index(p.i, p.j + 1));

		return neighbors;
	}

	private Chain getChain(Index p) {
		Set<Index> stones = getChainPoints(clone(), p);

		Set<Index> liberties = new HashSet<Index>();
		for (Index stone : stones) {
			for (Index point : getNeighbors(stone)) {
				if (getColor(point) == Color.EMPTY) {
					liberties.add(point);
				}
			}
		}

		return new Chain(stones, liberties);
	}

	private Set<Index> getChainPoints(Board b, Index p) {
		Set<Index> stones = new HashSet<Index>();
		stones.add(p);

		Color myColor = b.getColor(p);
		Color floodfillColor = myColor.opposite();
		b.setColor(p, floodfillColor);

		List<Index> neighbors = b.getNeighbors(p);
		for (Index neighbor : neighbors) {
			if (b.getColor(neighbor) == myColor) {
				if (!stones.contains(neighbor)) {
					stones.addAll(getChainPoints(b, neighbor));
				}
			}
		}

		return stones;
	}


	@Override
	public Board clone() {
		int n = board.length;
		Color[][] stones = new Color[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				stones[i][j] = board[i][j];
			}
		}
		return new Board(stones);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		String space = board.length < 10 ? "" : " ";
		
		string.append(space + ' ');
		for(int i = 0; i < board.length; i++) {
			string.append(GTP.LABELS.charAt(i));
		}
		string.append(space + ' ');
		string.append('\n');
		
		for (int i = 0; i < board.length; i++) {
			string.append(board.length - i < 10 ? space + (board.length - i) : board.length - i);
			for (int j = 0; j < board[i].length; j++) {
				string.append(board[board.length - i - 1][j].toString());
			}
			string.append(board.length - i < 10 ? space + (board.length - i) : board.length - i);
			string.append('\n');
		}
		
		string.append(space + ' ');
		for(int i = 0; i < board.length; i++) {
			string.append(GTP.LABELS.charAt(i));
		}
		string.append(space + ' ');

		return string.toString();
	}
}
