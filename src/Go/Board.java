package Go;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** An individual state of the board. */

public class Board {

	public Index koPoint;

	private int boardSize;
	private Color[][] board;
	private TerritoryMap territoryMap;

	public Board(Color[][] board) {
		boardSize = board.length;
		this.board = board;
		this.koPoint = null;
		territoryMap = new TerritoryMap(boardSize);
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
		territoryMap = new TerritoryMap(boardSize);
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

	public int getSize() {
		return boardSize;
	}

	public Index getFirstPossibleMove(Color c) {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				Index p = new Index(i, j);
				if(isValidMove(p, c)) {
					return p;
				}
			}
		}
		return null;
	}

	public TerritoryMap getTerritoryMap() {
		return territoryMap;
	}
	
	/**
	 * Process a given play. It is assumed that the play is VALID.
	 */
	public void processMove(Index I, Color c) {
		List<Index> neighbors = getNeighbors(I);
		int nbRemovedStones = 0;
		for(Index N : neighbors) {
			Chain chain = getChainAt(N, c.opposite());
			// Since the play is valid, if an adjacent chain
			// has only one liberty, then I must be it.
			if(chain.nbLiberties() == 1) {
				nbRemovedStones += chain.getSize();
				remove(chain);
			}
		}
		// If exactly one stone was removed then it is the new ko point.
		if(nbRemovedStones == 1) {
			koPoint = I;
		} else {
			koPoint = null;
		}
		board[I.i][I.j] = c;
		// The territory of a color c is the set of intersection that are
		// empty and such that all empty path from it lead to a stone of
		// color c.
		territoryMap.setTerritory(I, Color.EMPTY);
		// Update adjacent territories
		for(Index N : neighbors) {
			if(getColor(N) == Color.EMPTY) {
				EmptyZone emptyZone = getEmptyZone(N);
				Color territoryColor = Color.EMPTY;
				if(emptyZone.getBlackAdjacents().size() == 0) {
					territoryColor = Color.WHITE;
				} else if(emptyZone.getWhiteAdjacents().size() == 0) {
					territoryColor = Color.BLACK;
				}
				for(Index J : emptyZone.getZone()) {
					territoryMap.setTerritory(J, territoryColor);
				}
			}
		}
	}
	
	private void remove(Chain chain) {
		for(Index I : chain.getStones()) {
			board[I.i][I.j] = Color.EMPTY;
		}
	}

	public Chain getChainAt(Index p, Color c) {
		Chain chain = new Chain();
		if(board[p.i][p.j] == c) {
			getChainAt(p, c, chain);
		}
		return chain;
	}

	private void getChainAt(Index p, Color c, Chain chain) {
		chain.addStone(p);
		List<Index> neighbors = getNeighbors(p);
		for(Index N : neighbors) {
			if(board[N.i][N.j] == c && !chain.containsStone(N)) {
				getChainAt(N, c, chain);
			}
			if(board[N.i][N.j] == Color.EMPTY) {
				chain.addLiberty(N);
			}
		}
	}
	
	/**
	 * Given an empty intersection, computes a flood fill of
	 * all empty intersections surrounding it.
	 * It also computes the frontier, the white and black stones
	 * around that empty zone. This is returned in the form of
	 * an instance of EmptyZone.
	 */
	public EmptyZone getEmptyZone(Index I) {
		EmptyZone emptyZone = new EmptyZone();
		getEmptyZone(I, emptyZone);
		return emptyZone;
	}
	
	/**
	 * Auxiliary private method for getEmptyZone. 
	 */
	private void getEmptyZone(Index I, EmptyZone emptyZone) {
		if(getColor(I) == Color.EMPTY && !emptyZone.contains(I)) {
			emptyZone.addEmptyIntersection(I);
			for(Index N : getNeighbors(I)) {
				if(getColor(N) == Color.EMPTY) {
					getEmptyZone(N, emptyZone);
				} else if(getColor(N) == Color.BLACK) {
					emptyZone.addBlackAdjacent(N);
				} else {
					emptyZone.addWhiteAdjacent(N);
				}
			}
		}
	}

	/**
	 * Check if it is a valid move to put a stone of
	 * color c at position I. 
	 */
	public boolean isValidMove(Index I, Color c) {
		// Is the position already occupied?
		if(board[I.i][I.j] != Color.EMPTY) {
			return false;
		}
		// Is the move ko?
		if(I.equals(koPoint)) {
			return false;
		}
		// If the move suicide?
		List<Index> neighbors = getNeighbors(I);
		for(Index N : neighbors) {
			// The neighbor position is empty, so the move is valid.
			if(getColor(N) == Color.EMPTY) {
				return true;
			}
			// The neighbor is of the same color.
			if(getColor(N) == c) { 
				if(getChainAt(N, c).nbLiberties() != 1) {
					// The chain is not in atari so it is ok.
					return true;
				} else {
					// The chain is in atari so it is a chain suicide.
					return false;
				}
			}
		}
		// At this point, we know that all neighbors are of the opposite color.
		// Therefore the move is a suicide, hence invalid. 
		return false;
	}

	
	/*
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
	 */


	public List<Index> getNeighbors(Index p) {
		List<Index> neighbors = new ArrayList<Index>();

		if (p.i > 0) neighbors.add(new Index(p.i - 1, p.j));
		if (p.i < boardSize - 1) neighbors.add(new Index(p.i + 1, p.j));
		if (p.j > 0) neighbors.add(new Index(p.i, p.j - 1));
		if (p.j < boardSize - 1) neighbors.add(new Index(p.i, p.j + 1));

		return neighbors;
	}

	/*
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
	*/

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

	public String getTerritoriesStrRepresentation() {
		StringBuilder string = new StringBuilder();

		String space = boardSize < 10 ? "" : " ";

		string.append(space + ' ');
		for(int i = 0; i < boardSize; i++) {
			string.append((char)(65 + (i >= 8 ? (i + 1) : i)));
		}
		string.append(space + ' ');
		string.append('\n');

		for (int i = 0; i < boardSize; i++) {
			string.append(boardSize - i < 10 ? space + (boardSize - i) : boardSize - i);
			for (int j = 0; j < boardSize; j++) {
				if(board[board.length - i - 1][j] != Color.EMPTY) {
					string.append(board[board.length - i - 1][j].toString());
				} else {
					string.append(territoryMap.getTerritory(boardSize - i - 1, j).toString().toUpperCase());					
				}
			}
			string.append(boardSize - i < 10 ? space + (boardSize - i) : boardSize - i);
			string.append('\n');
		}

		string.append(space + ' ');
		for(int i = 0; i < boardSize; i++) {
			string.append((char)(65 + (i >= 8 ? (i + 1) : i)));
		}
		string.append(space + ' ');

		return string.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		String space = board.length < 10 ? "" : " ";

		string.append(space + ' ');
		for(int i = 0; i < board.length; i++) {
			string.append((char)(65 + (i >= 8 ? (i + 1) : i)));
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
			string.append((char)(65 + (i >= 8 ? (i + 1) : i)));
		}
		string.append(space + ' ');

		return string.toString();
	}

	public List<Board> getSuccessors() {
		// TODO Auto-generated method stub
		return null;
	}
}
