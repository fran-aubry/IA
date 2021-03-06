package Go;

public class Index {

	public static final Index PASS = new Index(-1, -1);
	
	public int i, j;
	
	public Index(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public static String indexToVertex(Index I) {
		return (char)(65 + (I.j >= 8 ? (I.j + 1) : I.j)) + Integer.toString(I.i + 1);
	}
	
	public static Index vertexToIndex(String vertex) {
		int i = Integer.parseInt(vertex.substring(1)) - 1;
		int k = (int)vertex.substring(0, 1).toUpperCase().charAt(0) - 65;
		int j = k >= 8 ? k - 1 : k;
		return new Index(i, j);
	}
	
	public boolean equals(Object other) {
		if (other == this) return true;
        if (other == null || other.getClass() != this.getClass()) return false;
        Index I = (Index)other;
        return i == I.i && j == I.j;
	}
	
	/**
	 * Injective hashCode since 1 <= i, j <= 19
	 */
	public int hashCode() {
		return 100 * i + j;
	}
	
	public String toString() {
		return i + " " + j;
	}
	
}

