package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.w3c.dom.Document;


import jboxGlue.Assembly;
import jboxGlue.Mass;
import jboxGlue.Muscle;
import jboxGlue.Spring;
import jgame.JGColor;


public class MainParser extends ParserController {
	
	private Queue<Map<String, String>> massQueue = new LinkedList<Map<String,String>>();
	private Queue<Map<String, String>> springQueue = new LinkedList<Map<String,String>>();
	private List<Mass> massList = new ArrayList<Mass>();
	private List<Spring> springList = new ArrayList<Spring>();
	private Map<String, Mass> massMap = new HashMap<String, Mass>();
	private Assembly myAssembly;
	private JGColor[] COLOR_ARRAY = {JGColor.blue, JGColor.cyan, JGColor.orange, JGColor.white};
	private JGColor currentColor; 
	
	/**
	 * @param args
	 */
	public MainParser() {
		myAssembly = new Assembly(massList, springList);
	}
	
	public MainParser(List<Map<String, String>> preferenceList) {
		
		myAssembly = new Assembly(massList, springList);
		setPreferences(preferenceList);
	}
	
	public void setPreferences(List<Map<String, String>> preferenceList) {
		for (Map<String, String> map : preferenceList) {
			String str = "";
			if (!(str = map.get(PreferencesParser.AMPLITUDE)).equals("")) preferences.put(PreferencesParser.AMPLITUDE, str);
			if (!(str = map.get(PreferencesParser.RADIUS)).equals("")) preferences.put(PreferencesParser.RADIUS, str);
			if (!(str = map.get(PreferencesParser.MASS)).equals("")) preferences.put(PreferencesParser.MASS, str);
		}
	}
	
	public void parseInfo(Document doc) {
		Parser springParser = ParserFactory.createParser("spring");
		Parser massParser = ParserFactory.createParser("mass");
		Parser muscleParser = ParserFactory.createParser("muscle");
		Parser fixedMassParser = ParserFactory.createParser("fixed");
		
		pickMassColor();
		
		List<Map<String, String>> massList = massParser.getDataFromXML(doc, MassParser.TAG);
		massQueue.addAll(massList);
		List<Map<String, String>> fixedMassList = fixedMassParser.getDataFromXML(doc, FixedMassParser.TAG);
		massQueue.addAll(fixedMassList);
		List<Map<String, String>> springList = springParser.getDataFromXML(doc, SpringParser.TAG);
		springQueue.addAll(springList);
		List<Map<String, String>> muscleList = muscleParser.getDataFromXML(doc, MuscleParser.TAG);
		springQueue.addAll(muscleList);
	}
	
	private void pickMassColor() {
		Random r = new Random();
		int index = r.nextInt(COLOR_ARRAY.length);
		currentColor = COLOR_ARRAY[index];
	}
	
	

	private void createMass() {
		Map<String, String> fixedMassMap = massQueue.remove();
		String id = fixedMassMap.get(MassParser.ID);
		String type = fixedMassMap.get(MassParser.TYPE);
		double x = Double.parseDouble(fixedMassMap.get(MassParser.X));
		double y = Double.parseDouble(fixedMassMap.get(MassParser.Y));
		double xspeed = Double.parseDouble(fixedMassMap.get(MassParser.VX));
		double yspeed = Double.parseDouble(fixedMassMap.get(MassParser.VY));
		double mass = Double.parseDouble(fixedMassMap.get(MassParser.MASS));
		double radius = Double.parseDouble(fixedMassMap.get(MassParser.RADIUS));
		int collisionid = (int) Integer.parseInt(fixedMassMap.get("collisionid"));
		Mass m;
		if (type.equals("mass")) { 
			m = new Mass(id, collisionid, currentColor, preferenceVal(MassParser.RADIUS, radius), preferenceVal(MassParser.MASS, mass));
		} else {
			m = new Mass(id, collisionid, currentColor, preferenceVal(MassParser.RADIUS, radius), 0);
		}
		m.setPos(x, y);
		m.setForce(xspeed,yspeed);
		massMap.put(id, m);
		massList.add(m);
	}

	private void createSpring() {
		Map<String, String> springMap = springQueue.remove();
		Mass m1 = massMap.get(springMap.get(SpringParser.A));
		Mass m2 = massMap.get(springMap.get(SpringParser.B));
		double restLength = Double.parseDouble(springMap.get(SpringParser.RESTLENGTH));
		double constant = Double.parseDouble(springMap.get(SpringParser.CONSTANT));
		int collisionid = (int) Integer.parseInt(springMap.get("collisionid"));
		String type = springMap.get("type");
		Spring sp;
		if (type.equals("spring")) {
			sp = new Spring(type, collisionid, JGColor.cyan, m1, m2, restLength, constant);
		} else {
			double amplitude = Double.parseDouble(springMap.get(MuscleParser.AMPLITUDE));
			sp = new Muscle(type, collisionid, JGColor.cyan, m1, m2, restLength, constant, preferenceVal(MuscleParser.AMPLITUDE, amplitude));
		}
		springList.add(sp);
	}

	public void createObjects() {
		while (!massQueue.isEmpty()) {
			createMass();
		}
		while (!springQueue.isEmpty()) {
			createSpring();
		}
	}
	
	public Assembly getAssembly() {
		return myAssembly;
	}

}
