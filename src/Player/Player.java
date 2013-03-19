package Player;
import Go.Board;
import Go.Color;
import Go.Index;

public abstract class Player {
	
	private float komi;
	private String version;
	
	public Player(float komi) {
		this.komi = komi;
	}
	
	public void setKomi(float komi) {
		this.komi = komi;
	}
	
	public String getVersion() {
		return version;
	}

	public abstract Index makeMove(Board board, Color color);
	
	public abstract void reset();
	
	public abstract void updateKomi();
	
	public abstract String getName();
	
}

