package Go;

public class TerritoryMap {

	private Color[][] territories;
	private int nbWhite, nbBlack, size;
	
	public TerritoryMap(int size) {
		this.size = size;
		territories = new Color[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				territories[i][j] = Color.EMPTY;
			}
		}
		nbWhite = 0;
		nbBlack = 0;
	}
	
	public Color getTerriory(Index I) {
		return territories[I.i][I.j];
	}
	
	public Color getTerritory(int i, int j) {
		return territories[i][j];
	}
	
	public int getTerritorySize(Color c) {
		if(c == Color.WHITE) {
			return nbWhite;
		} else if(c == Color.BLACK) {
			return nbBlack;
		}
		return size * size - nbWhite - nbBlack;
	}
	
	public int getWhiteTerritorySize() {
		return nbWhite;
	}
	
	public int getBlackTerritorySize() {
		return nbBlack;
	}
	
	public void setTerritory(Index I, Color owner) {
		if(territories[I.i][I.j] == Color.BLACK && owner != Color.BLACK) {
			nbBlack--;
		} else if(territories[I.i][I.j] == Color.WHITE && owner != Color.WHITE) {
			nbWhite--;
		}
		if(territories[I.i][I.j] != Color.BLACK && owner == Color.BLACK) {
			nbBlack++;
		} else if(territories[I.i][I.j] != Color.WHITE && owner == Color.WHITE) {
			nbWhite++;
		}
		territories[I.i][I.j] = owner;
	}
	
	public Color getTerritory(Index I) {
		return territories[I.i][I.j];
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();

		String space = territories.length < 10 ? "" : " ";

		string.append(space + ' ');
		for(int i = 0; i < territories.length; i++) {
			string.append((char)(65 + i >= 8 ? (i + 1) : i));
		}
		string.append(space + ' ');
		string.append('\n');

		for (int i = 0; i < territories.length; i++) {
			string.append(territories.length - i < 10 ? space + (territories.length - i) : territories.length - i);
			for (int j = 0; j < territories[i].length; j++) {
				string.append(territories[territories.length - i - 1][j].toString());
			}
			string.append(territories.length - i < 10 ? space + (territories.length - i) : territories.length - i);
			string.append('\n');
		}

		string.append(space + ' ');
		for(int i = 0; i < territories.length; i++) {
			string.append((char)(65 + i >= 8 ? (i + 1) : i));
		}
		string.append(space + ' ');

		return string.toString();
	}
	
}

