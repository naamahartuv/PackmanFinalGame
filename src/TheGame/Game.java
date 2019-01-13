package TheGame;

import java.util.ArrayList;

import File_format.CSV2elements;
import Robot.Play;

/**
 * keeps a collection of fruits and packmans
 * @author yael hava and naama hartuv
 *
 */

public class Game {

	private ArrayList<Fruit> fruitList;
	private ArrayList<Packman> packmanList;
	private ArrayList<Ghost> ghostList;
	private ArrayList<Block> blockList;
	private Player player;


	/**
	 * constructor
	 */

	public Game() {
		fruitList = new ArrayList<Fruit>();
		packmanList = new ArrayList<Packman>();
		ghostList = new ArrayList<Ghost>();
		blockList = new ArrayList<Block>();
	}

	/**
	 * constructor
	 * @param packmanList
	 * @param fruitList
	 */

	public Game(ArrayList<Packman> packmanList, ArrayList<Fruit> fruitList) {
		this.fruitList = fruitList;
		this.packmanList = packmanList;
	}


	public void update(Play play) {
		getPackmanList().clear();
		getFruitList().clear();
		getBlockList().clear();
		getGhostList().clear();
	
		CSV2elements c = new CSV2elements(play, this);
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player newPlayer) {
		this.player = newPlayer;
	}

	public ArrayList<Fruit> getFruitList() {
		return fruitList;
	}


	public ArrayList<Packman> getPackmanList() {
		return packmanList;
	}

	public ArrayList<Ghost> getGhostList() {
		return ghostList;
	}

	public ArrayList<Block> getBlockList() {
		return blockList;
	}

}
