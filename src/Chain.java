
import java.util.Iterator;
import java.util.Set;

public class Chain implements Iterable<Index> {
	
	private Set<Index> stones;
	private Set<Index> liberties;
	
	public Chain(Set<Index> stones, Set<Index> liberties) {
		this.stones = stones;
		this.liberties = liberties;
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
