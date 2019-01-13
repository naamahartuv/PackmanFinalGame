package File_format;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.html.HTMLDocument.Iterator;

import Geom.Point3D;
import Robot.Play;
import TheGame.Block;
import TheGame.Fruit;
import TheGame.FruitMetaData;
import TheGame.Game;
import TheGame.Ghost;
import TheGame.Packman;
import TheGame.PackmanMetaData;
import TheGame.Player;

/**
 * this class takes a csv file and inserts every line to an packman, fruit, ghost, block and player object.
 * @author yael hava and naama hartuv
 */

public class CSV2elements 
{
	private Game game;
	private Play play;


	/**
	 * constructor
	 * @param path - the given cvs file's path
	 * @param game - the game
	 */

	public CSV2elements(Play play, Game game) {
		this.game = game;
		this.play = play;
		toElem(game, play);
	}


	/**
	 * create an array list with elements and add any element to an array list
	 * of packmans or fruits
	 * @param game - the game
	 * @param play - the server
	 */

	public void toElem(Game game, Play play) {
		ArrayList<String> temp = play.getBoard();
		ArrayList<String[]> arr = new ArrayList<String[]>();

		for (int i = 0; i < temp.size(); i++) {
			arr.add(temp.get(i).split(","));
		}

		for(int i = 0; i < arr.size(); i++) {
			if(arr.get(i)[0].equals("P")) {
				game.getPackmanList().add(toPackmanElem(arr.get(i)));
			}
			else if(arr.get(i)[0].equals("F")) {
				game.getFruitList().add(toFruitElem(arr.get(i)));
			}
			else if(arr.get(i)[0].equals("G")) {
				game.getGhostList().add(toGhostElem(arr.get(i)));
			}
			else if(arr.get(i)[0].equals("B")) {
				game.getBlockList().add(toBlockElem(arr.get(i)));
			}
			else if(arr.get(i)[0].equals("M")) {
				Player player = new Player(arr.get(i));
				game.setPlayer(player);

			}
		}
	}

	/**
	 * enters data and coordinate to a created packman.
	 * @param arr - the line
	 * @return p - the packman with the data and the coordinate
	 */

	private Packman toPackmanElem(String arr[]) {
		PackmanMetaData data = new PackmanMetaData(arr);
		Point3D point = new Point3D(arr);
		Packman p = new Packman(point, data.getMoveAbility(),data.getRadius(), data.getID());
		return p;
	}

	/**
	 * enters data and coordinate to a created fruit.
	 * @param arr - the line
	 * @return f - the fruit with the data and the coordinate
	 */

	private Fruit toFruitElem(String arr[]) {
		FruitMetaData data = new FruitMetaData(arr);
		Point3D point = new Point3D(arr);
		Fruit f = new Fruit(point, data);
		return f;
	}


	public Ghost toGhostElem(String[] arr) {
		return new Ghost(arr);
	}

	public Block toBlockElem(String[] arr) {
		return new Block(arr);
	}

	public Game getGame() {
		return game;
	}

	public Play getPlay() {
		return play;
	}

}




