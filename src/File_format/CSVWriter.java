package File_format;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import TheGame.Fruit;
import TheGame.Game;
import TheGame.Packman;

/**
 * this class writes a csv file
 * @author yael hava and naama hartuv
 *
 */

public class CSVWriter {

	private Game game;
	private int id = 0;

	/**
	 * constructor
	 * @param game - the game
	 */

	public CSVWriter(Game game) {
		this.game = game;
	}

	/**
	 * writes the csv file
	 * @param fileName - the file's path
	 * @param pw  - the file
	 */

	public void writeCSV(String fileName, PrintWriter pw) {

		StringBuilder sb = new StringBuilder();
		sb.append("Type");
		sb.append(',');
		sb.append("id");
		sb.append(",");
		sb.append("Lat");
		sb.append(",");
		sb.append("Lon");
		sb.append(",");
		sb.append("Alt");
		sb.append(",");
		sb.append("Speed/Weight");
		sb.append(",");
		sb.append("Radius");
		sb.append('\n');



		packman2String(game.getPackmanList(),  sb);
		fruit2String(game.getFruitList(), sb);

		pw.write(sb.toString());
		pw.close();
	}

	/**
	 * takes every packman and turn it to a string line
	 * @param list - packman list
	 * @param sb - the StringBuilder
	 * @return - the StringBuilder
	 */

	public StringBuilder packman2String(ArrayList<Packman> list, StringBuilder sb) {
		for(Packman p : list) {
			sb.append("P,");
			sb.append("" + id + ",");
			sb.append("" + p.getPoint3D().x() + ",");
			sb.append("" + p.getPoint3D().y() + ",");
			sb.append("" + 0 + ",");
			sb.append("" + 1 + ",");
			sb.append("" + 1);
			sb.append('\n');
			id++;
		}

		return sb;
	}

	/**
	 * takes every fruit and turn it to a string line
	 * @param list - fruit list
	 * @param sb - the StringBuilder
	 * @return - the StringBuilder
	 */
	
	public StringBuilder fruit2String(ArrayList<Fruit> list, StringBuilder sb) {
		for(Fruit f : list) {
			sb.append("F,");
			sb.append("" + id + ",");
			sb.append("" + f.getPoint3D().x() + ",");
			sb.append("" + f.getPoint3D().y() + ",");
			sb.append("" + 0 + ",");
			sb.append("" + 1);
			sb.append('\n');
			id++;
		}

		return sb;
	}



}




