package GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FileDialog;

import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Algorithms.RunAlgo;
import Coords.MyCoords;
import File_format.CSV2elements;
import File_format.CSVWriter;
import Geom.Point3D;
import Robot.Play;
import SQL.SQLTable;
import TheGame.Block;
import TheGame.Fruit;
import TheGame.Game;
import TheGame.Ghost;
import TheGame.Map;
import TheGame.Packman;
import TheGame.Player;
import Threads.RoadThread;
import javafx.scene.shape.Box;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

/**
 * this class is the window that runs the whole game
 * @author yael hava and naama hartuv
 *
 */

public class MyFrame extends JFrame implements MouseListener, MenuListener, ActionListener {


	public class DrawFrame extends JPanel{

		private BufferedImage strawberry, packmanImage, ghost, playerImage;
		//	private Map map2;

		public DrawFrame() {
			map2 = new Map( "Ariel1.png");

			try { 
				strawberry = ImageIO.read(new File("strawberry.png"));
				packmanImage = ImageIO.read(new File("player.png"));
				ghost = ImageIO.read(new File("ghost.png"));
				playerImage = ImageIO.read(new File("pacman.png"));


			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * displays on the screen the whole game 
		 * @param g - the event
		 */

		public void paint(Graphics g){
			g.drawImage(map2.getMap(), 0, 0, getWidth(), getHeight(), this);



			if(game.getPackmanList() != null)  {
				for (int i = 0; i < game.getPackmanList().size(); i++) {
					Packman p = game.getPackmanList().get(i);
					Point3D temp = map2.GPS2Pixel(p.getPoint3D(), getWidth(), getHeight());

					g.drawImage(packmanImage,(int)temp.x(), (int)temp.y(), 25, 30, this);

				}		
			}

			if(game.getFruitList() != null) {
				for (int i = 0; i < game.getFruitList().size(); i++) {
					Fruit f = game.getFruitList().get(i);
					Point3D temp = map2.GPS2Pixel(f.getPoint3D(), getWidth(), getHeight());

					g.drawImage(strawberry,(int)temp.x(), (int)temp.y(), 15, 20, this);
				} 
			}

			if(game.getGhostList() != null)  {
				for (int i = 0; i < game.getGhostList().size(); i++) {
					Ghost gh = game.getGhostList().get(i);
					Point3D temp = map2.GPS2Pixel(gh.getPoint(), getWidth(), getHeight());

					g.drawImage(ghost,(int)temp.x(), (int)temp.y(), 27, 31, this);

				}		
			}

			if(game.getBlockList() != null)  {
			for (int i = 0; i < game.getBlockList().size(); i++) {
				Block b = game.getBlockList().get(i);
					Point3D startTemp = map2.GPS2Pixel(b.getPointStart(), getWidth(), getHeight());
					Point3D endTemp = map2.GPS2Pixel(b.getPointEnd(), getWidth(), getHeight());
					int width = (int)(endTemp.x() - startTemp.x());
					int height = (int)(startTemp.y() - endTemp.y());
					g.fillRect((int)startTemp.x(), (int)endTemp.y(), width, height);

				}		
			}

			if(game.getPlayer() != null) {
				if(game.getPlayer().getPoint().ix() + 1 != 0) {//.................
					Point3D temp = map2.GPS2Pixel(game.getPlayer().getPoint(), getWidth(), getHeight());
					g.drawImage(playerImage, (int)temp.x(), (int)temp.y(), 25, 33, this);
				}
			}

			if(type == 3) {
				play.rotate(angle);
			}


		}
	}


	private JMenuBar menuBar;
	private JMenu fileMenu, typeMenu;
	private JMenuItem save, load, clear, exit, export, run, player, runAlgo ,sql;
	private Game game;
	private int type;
	private Map map2 ;
	private CSVWriter csvWriter;
	private Play play;
	private double angle;
	private DrawFrame draw;


	/**
	 * constructor
	 */

	public MyFrame() 
	{
		initGUI();		
		this.addMouseListener(this);

	}

	/**
	 * the gui initialize
	 */

	private void initGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		draw = new DrawFrame();
		add(draw);

		game = new Game();
		csvWriter = new CSVWriter(game);
		menuBar = new JMenuBar();


		fileMenu = new JMenu("File"); 
		load = new JMenuItem("Load");
		sql = new JMenuItem("MySQL");
		clear = new JMenuItem("Clear");
		exit = new JMenuItem("Exit");
		export = new JMenuItem("Export to KML");
		
		sql.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,  ActionEvent.CTRL_MASK));
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,  ActionEvent.CTRL_MASK));
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,  ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,  ActionEvent.CTRL_MASK));
		export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,  ActionEvent.CTRL_MASK));


		menuBar.add(fileMenu);
		fileMenu.add(load);
		fileMenu.add(sql);
		fileMenu.add(export);
		fileMenu.add(clear);
		fileMenu.add(exit);


		typeMenu = new JMenu("Play");
		player = new JMenuItem("Player");
		run = new JMenuItem("Run");
		runAlgo = new JMenuItem("Run algorithm");



		menuBar.add(typeMenu);
		setJMenuBar(menuBar);
		typeMenu.add(player);
		typeMenu.add(run);
		typeMenu.add(runAlgo);




		player.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				type = 4;
			}
		});


		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				runGame();
			}
		});

		runAlgo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				runAlgoGame();
			}
		});

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadFile();

			}
		});

		sql.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new SQLTable();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getPackmanList().clear();
				game.getFruitList().clear();
				game.getBlockList().clear();
				game.getGhostList().clear();
				game.getPlayer().setPoint(new Point3D(0,0));
				repaint();
			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});

		//		export.addActionListener(new ActionListener() {
		//
		//			@Override
		//			public void actionPerformed(ActionEvent arg0) {
		//				Path2KML p2k = new Path2KML(game);
		//				p2k.exportKmlFile();
		//				p2k.export();
		//			}
		//		});


		setJMenuBar(menuBar);
	}



	/**
	 * operates the algorithm 
	 * @param e - the event
	 */

	public void  runGame() { 
		type = 3;
		play.setIDs(315745828, 313417420);
		play.start();
		Thread thread = new Thread() {
			public void run(){
				while(play.isRuning()) {
					game.update(play);
					try {
						this.sleep(50);
						repaint();

					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}	
				}
			}
		};
		thread.start();
	}


	public void runAlgoGame() {
		type = 0;
		play.setIDs(315745828, 313417420, 123456);
		play.setInitLocation(game.getPlayer().getPoint().x(), game.getPlayer().getPoint().y());

//RunAlgo algo = new RunAlgo(game);
		play.start();
		//while(game.getFruitList().size() != 0) {
		//algo.run();	
		
		RoadThread thread = new RoadThread(play, game, draw);
		thread.start();
		//	}
	}


	/**
	 * every mouse click
	 * @param arg - the  event
	 */

	@Override
	public void mouseClicked(MouseEvent arg) {

		if(type == 3) {		
			Point3D point = new Point3D(map2.pixel2GPS(new Point3D(arg.getX(), arg.getY()), getWidth(), getHeight()));
			MyCoords c = new MyCoords(0, 0, 0);
			angle = c.azimuth_elevation_dist( game.getPlayer().getPoint(),point)[0];

		}

		if(type == 4) {
			Point3D newPoint = map2.pixel2GPS(new Point3D(arg.getX(), arg.getY()), getWidth(), getHeight());
			game.getPlayer().setPoint(newPoint);
			play.setInitLocation(newPoint.x(), newPoint.y());

			repaint();
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void menuCanceled(MenuEvent arg0) {}

	@Override
	public void menuDeselected(MenuEvent arg0) {}

	@Override
	public void menuSelected(MenuEvent arg0) {}


	public void actionPerformed(ActionEvent e) {}

	/**
	 * load file to the screen
	 */

	public void loadFile() {
		//		try read from the file
		FileDialog fileDialog = new FileDialog(this, "Open CSV file", FileDialog.LOAD);
		fileDialog.setFile("*.csv");
		fileDialog.setDirectory("C:\\Users");
		fileDialog.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});
		fileDialog.setVisible(true);
		String dir = fileDialog.getDirectory();
		String fileName = fileDialog.getFile();
		try {
			FileReader fileReader = new FileReader(dir + fileName);
			BufferedReader bReader = new BufferedReader(fileReader);

			play = new Play(dir + fileName);
			CSV2elements c = new CSV2elements(play, this.game);
			this.game = c.getGame();
			this.play = c.getPlay();

			repaint();

			bReader.close();
			fileReader.close();
		} catch (IOException ex) {

		}
	}


	public static void main(String[] args) {

		MyFrame window = new MyFrame();
		window.setVisible(true);
		window.setSize(1000, 550);
		//window.setSize(window.map.getMap().getWidth(),window.map.getMap().getHeight());
		
		//new SQLTable();
	}



}
