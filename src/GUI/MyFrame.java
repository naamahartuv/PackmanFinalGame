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
import TheGame.Block;
import TheGame.Fruit;
import TheGame.Game;
import TheGame.Ghost;
import TheGame.Map;
import TheGame.Packman;
import TheGame.Player;
import Threads.PackmanThread;

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
				for(Packman p : game.getPackmanList()) {
					Point3D temp = map2.GPS2Pixel(p.getPoint3D(), getWidth(), getHeight());

					g.drawImage(packmanImage,(int)temp.x(), (int)temp.y(), 25, 30, this);

				}		
			}

			if(game.getFruitList() != null) {
				for(Fruit f : game.getFruitList()) {
					Point3D temp = map2.GPS2Pixel(f.getPoint3D(), getWidth(), getHeight());

					g.drawImage(strawberry,(int)temp.x(), (int)temp.y(), 15, 20, this);
				} 
			}

			if(game.getGhostList() != null)  {
				for(Ghost gh : game.getGhostList()) {
					Point3D temp = map2.GPS2Pixel(gh.getPoint(), getWidth(), getHeight());

					g.drawImage(ghost,(int)temp.x(), (int)temp.y(), 27, 31, this);

				}		
			}

			if(game.getBlockList() != null)  {
				for(Block b : game.getBlockList()) {
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
	private JMenuItem save, load, clear, exit, export, run, player, runAlgo;
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
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		clear = new JMenuItem("Clear");
		exit = new JMenuItem("Exit");
		export = new JMenuItem("Export to KML");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,  ActionEvent.CTRL_MASK));
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,  ActionEvent.CTRL_MASK));
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,  ActionEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,  ActionEvent.CTRL_MASK));
		export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,  ActionEvent.CTRL_MASK));


		menuBar.add(fileMenu);
		fileMenu.add(save);
		fileMenu.add(load);
		fileMenu.add(export);
		fileMenu.add(clear);
		fileMenu.add(exit);


		typeMenu = new JMenu("Play");
		player = new JMenuItem("Player");
		run = new JMenuItem("Run");
		runAlgo = new JMenuItem("Run algorithms");



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
				type = 0;
				while(game.getFruitList().size() != 0) {
				RunAlgo algo = new RunAlgo(game);
				algo.run(play);
				repaint();
				}
			}
		});

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadFile();

			}
		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveFile();
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

	


	/**
	 * the synchronized between the packman threads
	 */

	public synchronized void synchronizedPaint() {
		repaint();
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
			play.setIDs(315745828, 313417420);
			CSV2elements c = new CSV2elements(play, this.game);
			this.game = c.getGame();
			this.play = c.getPlay();

			repaint();

			bReader.close();
			fileReader.close();
		} catch (IOException ex) {

		}
	}

	/**
	 * save file from the screen
	 */

	public void saveFile() {
		//		try read from the file
		FileDialog fileDialog = new FileDialog(this, "Save CSV file", FileDialog.SAVE);
		fileDialog.setFile("*.csv");
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
			FileWriter fileWriter = new FileWriter(dir + fileName);
			PrintWriter pWriter = new PrintWriter(fileWriter);
			csvWriter.writeCSV(fileName, pWriter);
			fileWriter.close();

		} catch (IOException ex) {
			System.out.print("Error writing file  " + ex);
		}
	}

	/**
	 * calculates the distance between 2 point between packman and fruit
	 * @param packman - the packman
	 * @return - an array list of points
	 */

	public ArrayList<Point3D> packmanSteps(Packman packman) {
		ArrayList<Point3D> stepsList = new ArrayList<Point3D>();
		for (int i = 0; i < packman.getPackmanRoad().size() - 1; i++) {
			double stepSize = 0.5;
			MyCoords coord = new MyCoords(0, 0, 0);
			double dis = coord.distance3d(packman.getPackmanRoad().get(i).getPoint3D(), 
					packman.getPackmanRoad().get(i + 1).getPoint3D());
			double x = (stepSize/dis) * ( packman.getPackmanRoad().get(i + 1).getPoint3D().x() -
					packman.getPackmanRoad().get(i).getPoint3D().x())+
					packman.getPackmanRoad().get(i + 1).getPoint3D().x();
			double y = (stepSize/dis) * ( packman.getPackmanRoad().get(i + 1).getPoint3D().y() -
					packman.getPackmanRoad().get(i).getPoint3D().y()) +
					packman.getPackmanRoad().get(i + 1).getPoint3D().y();

			Point3D stepPoint = new Point3D(x, y);
			stepsList.add(stepPoint);

		} 
		return stepsList;
	}

	public static void main(String[] args){

		MyFrame window = new MyFrame();
		window.setVisible(true);
		window.setSize(1000, 550);
		//window.setSize(window.map.getMap().getWidth(),window.map.getMap().getHeight());
	}



}
