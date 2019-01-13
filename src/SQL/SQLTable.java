package SQL;

import java.sql.*;
import java.text.DecimalFormat;

import javax.swing.JTextArea;

/**
 * this class saves the DB of all the game
 * @author yael hava and naama hartuv
 *
 */

public class SQLTable {

	private JTextArea textArea;
	private ResultSet resultSet;

	/**
	 * constructor
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public SQLTable() throws ClassNotFoundException, SQLException {
		SQLFrame frame = new SQLFrame();
		textArea = frame.getTextArea();

		initSql();
		calcStatisticts();
	}

	/**
	 * calculate the average and difference
	 * @throws SQLException
	 */
	
	private void calcStatisticts() throws SQLException {

		final int firstId = 313417420;
		final int secondId = 315745828;
		final int thirdAlgoId = 123456;


		double ourScore[] = new double[9];
		double ourNumOfGames[] = new double[9];


		double algoScore[] = new double[9];
		double algoNumOfGames[] = new double[9];


		double studentsScore[] = new double[9];
		double studentsNumOfGames[] = new double[9];


		while (resultSet.next()) {

			int mapNumber = getMapNumber(resultSet.getString("SomeDouble"));

			if (mapNumber != -1) {

				// our game
				if ((resultSet.getInt("FirstID") == firstId && resultSet.getInt("SecondID") == secondId && resultSet.getInt("ThirdID") == 0)
						||  (resultSet.getInt("firstId") == secondId && resultSet.getInt("SecondID") == firstId && resultSet.getInt("ThirdID") == 0)) {

					ourNumOfGames[mapNumber - 1]++;
					ourScore[mapNumber - 1] += resultSet.getDouble("Point");
				}

				//algorithm game
				else if ((resultSet.getInt("FirstID") == firstId && resultSet.getInt("SecondID") == secondId && resultSet.getInt("ThirdID") == thirdAlgoId)
						||  (resultSet.getInt("firstId") == secondId && resultSet.getInt("SecondID") == firstId && resultSet.getInt("ThirdID") == thirdAlgoId)) {

					algoNumOfGames[mapNumber - 1]++;
					algoScore[mapNumber - 1] += resultSet.getDouble("Point");
				}

				//other students game
				else {

					studentsNumOfGames[mapNumber - 1]++;
					studentsScore[mapNumber - 1] += resultSet.getDouble("Point");
				}	
			}
		}	

		textArea.append("Map No.\tNo. Of Games\tOur Average\tAlgo No. Of Game\t"
				+ "Algo Average\tStudents Average\tdifference\n\n");
		for (int i = 0; i < 9; i++) {
			double ourAvrg = 0, algoAvrg = 0, studentsAvrg = 0;
			textArea.append("     " + (i + 1) + "\t\t");
			if (ourNumOfGames[i] != 0) {
				ourAvrg = ourScore[i] / ourNumOfGames[i];
				textArea.append(ourNumOfGames[i] + "\t                 " + new DecimalFormat("##.##").format(ourAvrg) + "\t             ");
			}
			if(algoNumOfGames[i] != 0) {
				algoAvrg = algoScore[i] / algoNumOfGames[i];
				textArea.append(algoNumOfGames[i] + "\t                    " + new DecimalFormat("##.##").format(algoAvrg) + "\t\t"); 
			}
			if(studentsNumOfGames[i] != 0) {
				studentsAvrg = studentsScore[i] / studentsNumOfGames[i];
				textArea.append(new DecimalFormat("##.##").format(studentsAvrg) + "\t           ");
			}
			double differ = ourAvrg + algoAvrg - studentsAvrg;
			textArea.append(new DecimalFormat("##.##").format(differ) + "\n\n");
		
		}
	}

	/**
	 * brings the table and saves it
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	private void initSql() throws ClassNotFoundException, SQLException {

		String jdbcUrl = "jdbc:mysql://ariel-oop.xyz:3306/oop";
		String jdbcUser = "student";
		String jdbcPassword = "student";

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		Statement statement = connection.createStatement();

		String query = "SELECT * FROM logs";
		resultSet = statement.executeQuery(query);
	}

	/**
	 * classified the number of the maps 
	 * @param map - the map number
	 * @return - the map current number
	 */
	
	private int getMapNumber(String map) {
		switch (map) {
		case "2128259830":
			return 1;
		case "1149748017":
			return 2;
		case "-683317070":
			return 3;
		case "1193961129":
			return 4;
		case "1577914705":
			return 5;
		case "-1315066918":
			return 6;
		case "-1377331871":
			return 7;
		case "306711633":
			return 8;
		case "919248096":
			return 9;
		default:
			return -1;
		}
	}
}
