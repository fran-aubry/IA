
public class Index {

	public static final Index PASS = new Index(-1, -1);
	
	public int i, j;
	
	public Index(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public static String indexToVertex(Index I) {
		return GTP.LABELS.charAt(I.j) + Integer.toString(I.i + 1);
	}
	
	public static Index vertexToIndex(String vertex) {
		int i = Integer.parseInt(vertex.substring(1)) - 1;
		int j = GTP.LABELS.indexOf(vertex.substring(0, 1).toUpperCase());
		return new Index(i, j);
	}
	
	public String toString() {
		return i + " " + j;
	}
	
}

