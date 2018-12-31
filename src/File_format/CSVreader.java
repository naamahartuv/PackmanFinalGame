package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class read a csv file and inserts every line to array list.
 * @author yael hava and naama hartuv
 */

public class CSVreader {
	
	private String path;
	
	/**
	 * constructor
	 * @param path - the file locate
	 */

	public CSVreader(String path) {
		this.path = path;
	}

	/**
	 * read a csv file and inserts every line to an array list.
	 * @return list - the list with all the lines.
	 */

	public ArrayList<String[]> CSVReader()  {
		ArrayList<String[]> list = new ArrayList<String[]>();
		String csvFile = path;
		String line = "";
		String cvsSplitBy = ",";


		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
		{

			int counter =0;
			while ((line = br.readLine()) != null) {
				if(counter>0) {
					String[] userInfo = line.split(cvsSplitBy);
					list.add(userInfo);
				}
				counter++;
			}

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return list;
	}
}