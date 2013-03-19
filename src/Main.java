


import java.util.HashSet;
import java.util.Scanner;

import Go.Board;
import Go.Color;
import Go.Index;
import Player.AlphaBetaPlayer;
import Player.NoobPlayer;
import Player.Player;
import Player.RndPlayer;

/**

*protocol_version
*name
*version
*known_command
*list_commands
*quit
*boardsize
*clear_board
*komi
*play
*genmove
*showboard

+territories
 */
public class Main {

	
	private static final String PROTOCOL_VERSION = "2";

	private static final int DEFAULT_BOARDSIZE = 9;
	private static final float DEFAULT_KOMI = 0;
	
	private static HashSet<String> known_commands;
	static {
		known_commands = new HashSet<String>();
		known_commands.add("protocol_version");
		known_commands.add("name");
		known_commands.add("version");
		known_commands.add("know_command");
		known_commands.add("list_commands");
		known_commands.add("quit");
		known_commands.add("boardsize");
		known_commands.add("clear_board");
		known_commands.add("komi");
		known_commands.add("play");
		known_commands.add("genmove");
		known_commands.add("showboard");
		// additional commands
		known_commands.add("territories");
	}
	
	private static Player player;
	private static Board board;
	
	public static void main(String[] args) {
		
		player = new AlphaBetaPlayer(DEFAULT_KOMI);
		board = new Board(DEFAULT_BOARDSIZE);
		
		Scanner reader = new Scanner(System.in);
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			if(!line.equals("") && !line.equals(" ")) {
				// parse line
				String[] data = line.split(" ");
				String command = null;
				String id = null;
				String[] arguments = null;
				if(data.length == 1) {
					command = data[0];
				} else {
					if(data[0].matches("[123456789][0-9]*")) {
						id = data[0];
						command = data[1];
						arguments = new String[data.length - 2];
						for(int i = 2, j = 0; i < data.length; i++, j++) {
							arguments[j] = data[i];
						}
					} else {
						command = data[0];
						arguments = new String[data.length - 1];
						for(int i = 1, j = 0; i < data.length; i++, j++) {
							arguments[j] = data[i];
						}
					}
				}
				//
				executeCommand(id, command, arguments);
				System.out.println();
			}
		}
	}
	
	static void executeCommand(String id, String command, String[] arguments) {
		switch(command) {
		case "name":
			name(id);
			break;
		case "protocol_version":
			protocol_version(id);
			break;
		case "version":
			version(id);
			break;
		case "know_command":
			know_command(id, arguments[0]);
			break;
		case "list_commands":
			list_commands(id);
			break;
		case "quit":
			quit(id);
			break;
		case "boardsize":
			boardsize(id, Integer.parseInt(arguments[0]));
			break;
		case "clear_board":
			clear_board(id);
			break;
		case "komi":
			komi(id, Float.parseFloat(arguments[0]));
			break;
		case "play":
			play(id, arguments[0], arguments[1]);
			break;
		case "genmove":
			genmove(id, arguments[0]);
			break;
		case "showboard":
			showboard(id);
			break;
		case "territories":
			show_territories(id);
			break;
		default:
			System.out.println("unknown command");
			break;
		}
	}
	
	static void showboard(String id) {
		printOk(id);
		System.out.println();
		System.out.println(board.toString());
	}
	
	static void play(String id, String color, String vertex) {
		if(!vertex.equals("PASS") && !vertex.endsWith("pass")) {
			board.processMove(Index.vertexToIndex(vertex), Color.strToColor(color));			
		}
		printOk(id);
		System.out.println();
	}
	
	static void genmove(String id, String color) {
		Index I = player.makeMove(board.clone(), Color.strToColor(color));
		printOk(id);
		if(I == null) {
			System.out.println("resign");
		} else if(I.equals(Index.PASS)) {
			System.out.println("pass");
		} else {
			board.processMove(I, Color.strToColor(color));
			System.out.println(Index.indexToVertex(I));			
		}
	}
	
	static void komi(String id, float arg) {
		player.reset();
		player.setKomi(arg);
		printOk(id);
		System.out.println();
	}
	
	static void boardsize(String id, int arg) {
		board = new Board(arg);
		player.reset();
		printOk(id);
		System.out.println();
	}
	
	static void clear_board(String id) {
		board = new Board(board.getSize());
		player.reset();
		printOk(id);
		System.out.println();
	}

	static void protocol_version(String id) {
		printOk(id);
		System.out.println(PROTOCOL_VERSION);
	}
	
	static void name(String id) {
		printOk(id);
		System.out.println(player.getName());
	}

	static void version(String id) {
		printOk(id);
		System.out.println(player.getVersion());
	}
	
	static void know_command(String id, String command_name) {
		if(known_commands.contains(command_name)) {
			printOk(id);
			System.out.println("true");
		} else {
			printKo(id);
			System.out.println("false");
		}
	}
	
	static void list_commands(String id) {
		printOk(id);
		for(String command : known_commands) {
			System.out.println(command);
		}
	}
	
	static void quit(String id) {
		System.out.print("=\n\n");
		System.exit(0);
	}

	
	private static void printOk(String id) {
		System.out.print("=");
		if(id != null) {
			System.out.print(id);
		}
		System.out.print(' ');
	}
	
	private static void printKo(String id) {
		System.out.print("?");
		if(id != null) {
			System.out.print(id);
		}
		System.out.print(' ');
	}
	
	// Additional command.
	private static void show_territories(String id) {
		printOk(id);
		System.out.println();
		System.out.println(board.getTerritoriesStrRepresentation());
	}
	
}

