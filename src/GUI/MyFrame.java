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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FileDialog;

import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import Coords.MyCoords;
import File_format.CSV2elements;
import File_format.CSVWriter;
import Geom.Point3D;
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


	private BufferedImage strawberry, packmanImage, ghost, playerImage;
	private JMenuBar menuBar;
	private JMenu fileMenu, typeMenu;
	private JMenuItem save, load, clear, exit, export, packman, fruit, run, player;
	private Game game;
	private ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
	private int type, ID = 0;
	private Map map ;
	private CSVWriter csvWriter;
	//	private ShortestPathAlgo algo;


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

		map = new Map( "Ariel1.png");
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
		packman = new JMenuItem("Packman");
		fruit = new JMenuItem("Fruit");
		player = new JMenuItem("Player");
		run = new JMenuItem("Run");



		menuBar.add(typeMenu);
		setJMenuBar(menuBar);
		typeMenu.add(packman);
		typeMenu.add(fruit);
		typeMenu.add(player);
		typeMenu.add(run);

		try {
			strawberry = ImageIO.read(new File("strawberry.png"));
			packmanImage = ImageIO.read(new File("player.png"));
			ghost = ImageIO.read(new File("ghost.png"));
			playerImage = ImageIO.read(new File("pacman.png"));


		} catch (IOException e) {
			e.printStackTrace();
		}

		player.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				type = 4;
			}
		});

		fruit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				type = 1;
			}
		});

		packman.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				type = 2;
			}
		});

		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				add(e);
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
				ID = 0;
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

	public void  add(ActionEvent e) {
		type = 3;
		//		algo = new ShortestPathAlgo(game);
		//		algo.ShortestPath();

		repaint();

		for (Packman P : game.getPackmanList()) {
			PackmanThread thread = new PackmanThread(P,this);

			thread.start();

		}
	}

	/**
	 * displays on the screen the whole game 
	 * @param g - the event
	 */

	public void paint(Graphics g){
		g.drawImage(map.getMap(), 0, 0, getWidth() - 8, getHeight() - 8, this);
		setJMenuBar(menuBar);


		if(game.getPackmanList() != null)  {
			for(Packman p : game.getPackmanList()) {
				Point3D temp = map.GPS2Pixel(p.getPoint3D(), getWidth(), getHeight());

				g.drawImage(packmanImage,(int)temp.x(), (int)temp.y(), 25, 30, this);

			}		
		}

		if(game.getFruitList() != null) {
			for(Fruit f : game.getFruitList()) {
				Point3D temp = map.GPS2Pixel(f.getPoint3D(), getWidth(), getHeight());

				g.drawImage(strawberry,(int)temp.x(), (int)temp.y(), 15, 20, this);
			} 
		}

		if(game.getGhostList() != null)  {
			for(Ghost gh : game.getGhostList()) {
				Point3D temp = map.GPS2Pixel(gh.getPoint(), getWidth(), getHeight());

				g.drawImage(ghost,(int)temp.x(), (int)temp.y(), 27, 31, this);

			}		
		}

		if(game.getBlockList() != null)  {
			for(Block b : game.getBlockList()) {
				Point3D startTemp = map.GPS2Pixel(b.getPointStart(), getWidth(), getHeight());
				Point3D endTemp = map.GPS2Pixel(b.getPointEnd(), getWidth(), getHeight());
				int width = (int)(endTemp.x() - startTemp.x());
				int height = (int)(startTemp.y() - endTemp.y());
				g.fillRect((int)startTemp.x(), (int)endTemp.y(), width, height);

			}		
		}

		if(game.getPlayer() != null) {
			if(game.getPlayer().getPoint().ix() + 1 != 0) {//.................
				Point3D temp = map.GPS2Pixel(game.getPlayer().getPoint(), getWidth(), getHeight());
				g.drawImage(playerImage, (int)temp.x(), (int)temp.y(), 34, 34, this);
			}
		}
		if(type == 3) {
			for(Packman p : game.getPackmanList()) {
				Point3D right1 = p.getPoint3D();
				Point3D right = map.GPS2Pixel(p.getPackmanRoad().get(0).getPoint3D(), getWidth(), getHeight());
				for(Fruit f: p.getPackmanRoad()) {

					Point3D left = map.GPS2Pixel(f.getPoint3D(), getWidth(), getHeight());

					Graphics2D g2 = (Graphics2D) g;
					g.setColor(Color.PINK);
					g2.setStroke(new BasicStroke(4));
					g2.drawLine((int)right.x(),(int) right.y(),
							(int)left.x(), (int)left.y());
					right = map.GPS2Pixel(f.getPoint3D(), getWidth(), getHeight());


				}
				p.setPoint3D(right1);
			}
		}
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

		if(type == 1) {
			Point3D newPoint = map.pixel2GPS(new Point3D(arg.getX(), arg.getY() ), getWidth(), getHeight());
			Fruit newFruit = new Fruit(newPoint, ID);
			game.getFruitList().add(newFruit);
			repaint();
			ID++;
		}

		if(type == 2) {
			Point3D newPoint = map.pixel2GPS(new Point3D(arg.getX(), arg.getY()), getWidth(), getHeight());
			Packman newPackman = new Packman(newPoint,1,1, ID);
			game.getPackmanList().add(newPackman);
			repaint();
			ID++;
		}




		if(type == 4) {
			Point3D newPoint = map.pixel2GPS(new Point3D(arg.getX(), arg.getY()), getWidth(), getHeight());

			game.getPlayer().setPoint(newPoint);
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

			CSV2elements c = new CSV2elements(dir + fileName, this.game);
			this.game = c.getGame();

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
		//window.setSize(1000, 550);
			window.setSize(window.map.getMap().getWidth(),window.map.getMap().getHeight());
	}

}
