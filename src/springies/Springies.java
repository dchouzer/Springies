package springies;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import forces.*;
import Parser.*;

@SuppressWarnings("serial")
public class Springies extends JGEngine {

	List<Assembly> listOfAssembly = new ArrayList<Assembly>();
	private Wall topWall;
	private Wall rightWall;
	private Wall bottomWall;
	private Wall leftWall;
	
	private final int SCREEN_HEIGHT = 600;
	private final int WALL_COLLISION_ID = 3;
	private final int WALL_TOP = 1;
	private final int WALL_RIGHT = 2;
	private final int WALL_BOTTOM = 3;
	private final int WALL_LEFT = 4;
	private final int MOUSE_CLICK = 1;
	private final double WALL_MARGIN = 2;
	private final double WALL_THICKNESS = 2;
	private double WALL_WIDTH;
	private double WALL_HEIGHT;
	private final double WALL_POSITION_ADJUSTMENT = 0.5;
	private final double WALL_LENGTH_ADJUSTMENT = 1;
	protected final int MASS_COLLISION_ID = Mass.COLLISION_ID;
	protected final String FIXED_MASS_TYPE = "fixed";
	protected final String MASS_TYPE = "mass";
	protected final String RADIUS = "radius";
	protected final String CENTER_MASS_EXP = "exponent";
	protected final String CENTER_MASS_MAG = "magnitude";
	protected final double DEFAULT_MASS = 5.0;
	protected final double DEFAULT_RADIUS = 5.0;
	private Mass mass = null;
	private Spring spr = null;
	private List<Map<String, String>> preferences = new ArrayList<Map<String, String>>();
	private MainParser mainParser = new MainParser(preferences);
	private EnvironmentParser envParser = new EnvironmentParser(preferences);
	private PreferencesParser preferencesParser = new PreferencesParser(); 

	public Springies() {
		// set the window size
		int height = SCREEN_HEIGHT;
		double aspect = 16.0 / 9.0;
		initEngineComponent((int) (height * aspect), height);
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(1, // width of the canvas in tiles
				1, // height of the canvas in tiles
				displayWidth(), // width of one tile
				displayHeight(), // height of one tile
				null,// foreground colour -> use default colour white
				null,// background colour -> use default colour black
				null);
		// standard font -> use default font
	}

	@Override
	public void initGame() {
		setFrameRate(40, 0);
		// NOTE:
		// world coordinates have y pointing down
		// game coordinates have y pointing up
		// so gravity is up in world coords and down in game coords
		// so set all directions (e.g., forces, velocities) in world coords
		WorldManager.initWorld(this);
		WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.0f));
		addWalls();

	}

	public void getFile() {
		mainParser = new MainParser(preferences);
		envParser = new EnvironmentParser(preferences);
		JFileChooser fileChooser = createFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Document doc = getDocumentFromSelection(fileChooser);
			NodeList eNodes = doc.getElementsByTagName("environment");
			NodeList pNodes = doc.getElementsByTagName("preferences");
			if (eNodes.getLength() != 0) {
				envParser.parseInfo(doc);
			} else if (pNodes.getLength() != 0) {
				preferences = preferencesParser.getDataFromXML(doc, "preference");
				mainParser.setPreferences(preferences);
			} else {
				mainParser.parseInfo(doc);
				listOfAssembly.add(mainParser.getAssembly());
			}
		}
	}
	
	public void getPreferences() {
		JFileChooser fileChooser = createFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Document doc = getDocumentFromSelection(fileChooser);
			NodeList pNodes = doc.getElementsByTagName("preferences");
			if (pNodes.getLength() != 0) {
				preferences = preferencesParser.getDataFromXML(doc, "preference");
				mainParser.setPreferences(preferences);
			} 
		}
	}
	
	public JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		String currentFileDirectory = System.getProperty("user.dir");
		fileChooser.setCurrentDirectory(new File(currentFileDirectory));
		return fileChooser;
	}
	
	public Document getDocumentFromSelection(JFileChooser fileChooser) {
		File file = fileChooser.getSelectedFile();
		String fileName = file.getPath().toString();
		Document doc = initDocument(fileName);
		return doc;
	}
	
	

	public Document initDocument(String fileName) {
		File xmlFile = new File(fileName);
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	private void addWalls() {
		WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
		WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
		topWall = new Wall("topwall", WALL_COLLISION_ID, JGColor.green, WALL_WIDTH, WALL_THICKNESS);
		topWall.setPos(displayWidth() / 2, WALL_MARGIN);
		rightWall = new Wall("rightwall", WALL_COLLISION_ID, JGColor.green, WALL_THICKNESS, WALL_HEIGHT);
		rightWall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
		bottomWall = new Wall("bottomwall", WALL_COLLISION_ID, JGColor.green, WALL_WIDTH, WALL_THICKNESS);
		bottomWall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		leftWall = new Wall("leftwall", WALL_COLLISION_ID, JGColor.green, WALL_THICKNESS, WALL_HEIGHT);
		leftWall.setPos(WALL_MARGIN, displayHeight() / 2);
	}
	
	public void expandWalls() {
		//first reset positions
		//topWall should shift to the left and length should increase
		//then expand the lengths
		if (topWall.y-1 >= WALL_MARGIN) {
			topWall.setPos(topWall.x, topWall.y-WALL_POSITION_ADJUSTMENT);
			topWall.setMyWidth(topWall.getMyWidth()+WALL_LENGTH_ADJUSTMENT);
		}
		if (rightWall.x+1 <= displayWidth() - WALL_MARGIN ) {
			rightWall.setPos(rightWall.x+WALL_POSITION_ADJUSTMENT, rightWall.y);
			rightWall.setMyHeight(rightWall.getMyHeight()+WALL_LENGTH_ADJUSTMENT);
		}
		if (bottomWall.y+1 <= displayHeight() - WALL_MARGIN) {
			bottomWall.setPos(bottomWall.x, bottomWall.y+WALL_POSITION_ADJUSTMENT);
			bottomWall.setMyWidth(bottomWall.getMyWidth()+WALL_LENGTH_ADJUSTMENT);
		}
		if (leftWall.x-1 >= WALL_MARGIN) {
			leftWall.setPos(leftWall.x-WALL_POSITION_ADJUSTMENT, leftWall.y);
			leftWall.setMyHeight(leftWall.getMyHeight()+WALL_LENGTH_ADJUSTMENT);
		}
	}
	//Problems - contraction of walls doesn't push the objects away.
	public void contractWalls() {
		topWall.setPos(topWall.x, topWall.y+WALL_POSITION_ADJUSTMENT);
		topWall.setMyWidth(topWall.getMyWidth()-WALL_LENGTH_ADJUSTMENT);
		rightWall.setPos(rightWall.x-WALL_POSITION_ADJUSTMENT, rightWall.y);
		rightWall.setMyHeight(rightWall.getMyHeight()-WALL_LENGTH_ADJUSTMENT);
		bottomWall.setPos(bottomWall.x, bottomWall.y-WALL_POSITION_ADJUSTMENT);
		bottomWall.setMyWidth(bottomWall.getMyWidth()-WALL_LENGTH_ADJUSTMENT);
		leftWall.setPos(leftWall.x+WALL_POSITION_ADJUSTMENT, leftWall.y);
		leftWall.setMyHeight(leftWall.getMyHeight()-WALL_LENGTH_ADJUSTMENT);
	}
	//TODO
	public void updateWallRepulsionPositions() {
		if (envParser.getWalls(1) != null) {
			envParser.getWalls(WALL_TOP).setXY(topWall.x, topWall.y);
			envParser.getWalls(WALL_RIGHT).setXY(rightWall.x, rightWall.y);
			envParser.getWalls(WALL_BOTTOM).setXY(bottomWall.x, bottomWall.y);
			envParser.getWalls(WALL_LEFT).setXY(leftWall.x, leftWall.y);
			
		}
	}
	
	@Override
	public void doFrame() {
		WorldManager.getWorld().step(1f, 1);
		mainParser.createObjects();
		envParser.createObjects();
		moveObjects();
		applyForces();
		drawForces();
		doForceListening();
		doAreaListening();
		doWallListening();
		doCheckForMouse();
		doChangeAmplitude();
		checkCollision(MASS_COLLISION_ID, WALL_COLLISION_ID);
		checkCollision(WALL_COLLISION_ID, MASS_COLLISION_ID);
		paintFrame();
		
		if (getKey('C')) {
			clearKey('C');
			doRemoveObjects();
		}
		
		if (getKey('N')) {
			clearKey('N');
			getFile();
		}
	}

	private void doWallListening() {
		if (getKey(KeyUp)) {
			expandWalls();
		}
		
		if (getKey(KeyDown)) {
			contractWalls();
		}
		
		if (envParser.getForceList() != null) {
			updateWallRepulsionPositions();
		}
	}
	
	public void doChangeAmplitude() {
		if (getKey('A')) {
			clearKey('A');
			increaseAmplitude();
		}
		if (getKey('D')) {
			clearKey('D');
			decreaseAmplitude();
		}
	}
	
	public void doCheckForMouse() {
		if (getMouseButton(MOUSE_CLICK)) {
			if (mass == null) {
				createBallOnScreen();
				Mass m = findClosestMass(getMouseX(), getMouseY());
				createConnection(m);
			}
		} else {
			if (mass != null) {
				mass.remove();
				mass = null;
			}
			if (spr != null) {
				spr.remove();
				spr = null;
			}
		}
	}
	
	public Mass findClosestMass(double x, double y) {
		if (listOfAssembly.size() == 0) return null;
		List<Mass> fm = new ArrayList<Mass>();
		for (Assembly am : listOfAssembly) {
			List<Mass> massList = am.getMassList();
			fm.add(Collections.min(massList, new CompareMasses(x , y)));
		}
		return Collections.min(fm, new CompareMasses(x, y));
	}
	
	public void createBallOnScreen() {
		double mass_val = DEFAULT_MASS;
		double radius = DEFAULT_RADIUS;
		if (mainParser.containsPreference(MASS_TYPE)) mass_val = Double.parseDouble(mainParser.getPreference(MASS_TYPE));
		if (mainParser.containsPreference(RADIUS)) radius = Double.parseDouble(mainParser.getPreference(RADIUS));
		mass = new MovingMass("mass", MASS_COLLISION_ID, JGColor.blue, mass_val, radius);
	}
	
	public void createConnection(Mass m) {
		if (m == null) return;
		double distance = m.getBody().getPosition().sub(mass.getBody().getPosition()).normalize();
		spr = new Spring("spring", 9, JGColor.cyan, mass, m, distance, 1);
	}
	
	public void doRemoveObjects() {
		for (Assembly am : listOfAssembly){
			am.removeObjects();
		}
		listOfAssembly = new ArrayList<Assembly>();
	}
	
	private void doAreaListening() {
		
	}
	
	private void doForceListening() {
		//force listeners
		if (getKey('G')) {
			clearKey('G');
			envParser.getG().switchToggle();
		}
		
		if (getKey('V')) {
			envParser.getV().switchToggle();
			clearKey('V');
		}
		
		if (getKey('M')) {
			envParser.getCM().switchToggle();
			clearKey('M');
		}
		
		if (getKey('1')) {
			envParser.getWalls(WALL_TOP).switchToggle();
			clearKey('1');
		}
		
		if (getKey('2')) {
			envParser.getWalls(WALL_RIGHT).switchToggle();
			clearKey('2');
		}
		
		if (getKey('3')) {
			envParser.getWalls(WALL_BOTTOM).switchToggle();
			clearKey('3');
		}
		
		if (getKey('4')) {
			envParser.getWalls(WALL_LEFT).switchToggle();
			clearKey('4');
		}
	}

	private void applyForces() {
		for (Assembly a : listOfAssembly) {
			for (Force f : envParser.getForceList()) {
				if (f.isToggledOn()) {
					f.applyForce(a.getMassList());
				}
			}
		}
	}

	private void increaseAmplitude() {
		for (Assembly am : listOfAssembly) {
			am.increaseAmplitude();
		}
	}

	private void decreaseAmplitude() {
		for (Assembly am : listOfAssembly) {
			am.decreaseAmplitude();
		}
	}

	public String drawForces() {
		String s = "";
		for (Force f : envParser.getForceList()) {
			if (f.isToggledOn())
				s += f.getTag();
		}
		return s;
	}
	
	@Override
	public void paintFrame() {
		drawString(drawForces(), displayWidth()-30, displayHeight()-30, 40);
	}

	public void resetEnvironment() {

	}


}
