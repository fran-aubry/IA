package Go;

import java.util.HashSet;
import java.util.Set;

public class EmptyZone {

	private Set<Index> zone;
	private Set<Index> whiteAdj;
	private Set<Index> blackAdj;
	
	public EmptyZone() {
		zone = new HashSet<Index>();
		whiteAdj = new HashSet<Index>();
		blackAdj = new HashSet<Index>();
	}
	
	public boolean contains(Index I) {
		return zone.contains(I);
	}
	
	public void addEmptyIntersection(Index I) {
		zone.add(I);
	}
	
	public void addWhiteAdjacent(Index I) {
		whiteAdj.add(I);
	}
	
	public void addBlackAdjacent(Index I) {
		blackAdj.add(I);
	}
	
	public Set<Index> getZone() {
		return zone;
	}
	
	public Set<Index> getWhiteAdjacents() {
		return whiteAdj;
	}
	
	public Set<Index> getBlackAdjacents() {
		return blackAdj;
	}
	
}

