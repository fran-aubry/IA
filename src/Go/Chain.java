package Go;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Chain implements Iterable<Index> {
	
	private Set<Index> stones;
	private Set<Index> liberties;
	
	public Chain(Set<Index> stones, Set<Index> liberties) {
		this.stones = stones;
		this.liberties = liberties;
	}
	
	public Chain() {
		stones = new HashSet<Index>();
		liberties = new HashSet<Index>();
	}
	
	public void addStone(Index s) {
		stones.add(s);
	}
	
	public void addLiberty(Index l) {
		liberties.add(l);
	}
	
	public int getSize() {
		return stones.size();
	}
	
	public boolean containsStone(Index s) {
		return stones.contains(s);
	}
	
	public int nbLiberties() {
		return liberties.size();
	}
	
	public Set<Index> getLiberties() {
		return liberties;
	}
	
	public boolean inAtari() {
		return liberties.size() == 1;
	}

	public Set<Index> getStones() {
		return stones;
	}
	
	public Iterator<Index> iterator() {
		return stones.iterator();
	}
}
