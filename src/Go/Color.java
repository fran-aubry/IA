package Go;

/** Color of a point on the board. */
public enum Color {
	
	BLACK, WHITE, EMPTY;
	
	public static Color strToColor(String c) {
		if(c.length() == 1) {
			return charToColor(c.charAt(0));
		}
		c = c.toLowerCase();
		return c.equals("white") ? WHITE : BLACK;
	}

	public static Color charToColor(char c) {
		switch (Character.toLowerCase(c)) {
		case 'b':
			return BLACK;
		case 'w':
			return WHITE;
		default:
			return EMPTY;
		}
	}
	
	public String toString() {
		switch (this) {
		case BLACK:
			return "b";
		case WHITE:
			return "w";
		case EMPTY:
			return ".";
		default:
			return " ";
		}
	}
	
	public Color opposite() {
		if (this == EMPTY) return EMPTY;
		return (this == WHITE) ? BLACK : WHITE;
	}
	
}