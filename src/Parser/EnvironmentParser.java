package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import forces.*;

public class EnvironmentParser extends ParserController {
	
	private List<Map<String, String>> wallRepulsionList;
	private List<Map<String, String>> gravityList;
	private List<Map<String, String>> viscosityList;
	private List<Map<String, String>> centerMassList;
	private List<Force> forceList = new ArrayList<Force>();
	
	private Gravity g;
	private Viscosity v;
	private CenterMass cm;
	private WallRepulsion[] walls = new WallRepulsion[4];
	
	public EnvironmentParser(List<Map<String, String>> preferenceList) {
		setPreferences(preferenceList);
	}
	
	public void setPreferences(List<Map<String, String>> preferenceList) {
		for (Map<String, String> map : preferenceList) {
			String str = "";
			if (!(str = map.get(PreferencesParser.VISCOSITY_MAGNITUDE)).equals("")) preferences.put(PreferencesParser.VISCOSITY_MAGNITUDE, str);
			if (!(str = map.get(PreferencesParser.GRAVITY_DIRECTION)).equals("")) preferences.put(PreferencesParser.GRAVITY_DIRECTION, str);
			if (!(str = map.get(PreferencesParser.GRAVITY_MAGNITUDE)).equals("")) preferences.put(PreferencesParser.GRAVITY_MAGNITUDE, str);
			if (!(str = map.get(PreferencesParser.CENTERMASS_MAGNITUDE)).equals("")) preferences.put(PreferencesParser.CENTERMASS_MAGNITUDE, str);
			if (!(str = map.get(PreferencesParser.CENTERMASS_EXPONENT)).equals("")) preferences.put(PreferencesParser.CENTERMASS_EXPONENT, str);
		}
	}
	public void parseInfo(Document doc) {
		Parser wallParser = ParserFactory.createParser("wall");
		Parser viscosityParser = ParserFactory.createParser("viscosity");
		Parser centerMassParser = ParserFactory.createParser("centermass");
		Parser gravityParser = ParserFactory.createParser("gravity");
		
		wallRepulsionList = wallParser.getDataFromXML(doc, WallParser.TAG);
		gravityList = gravityParser.getDataFromXML(doc, GravityParser.TAG);
		viscosityList = viscosityParser.getDataFromXML(doc, ViscosityParser.TAG);
		centerMassList = centerMassParser.getDataFromXML(doc, CenterMassParser.TAG);
	}
	
	public void createObjects() {
		if (gravityList != null && !gravityList.isEmpty()) createGravity();
		if (viscosityList != null && !viscosityList.isEmpty()) createViscosity();
		if (centerMassList  != null && !centerMassList.isEmpty()) createCenterMass();
		while (wallRepulsionList  != null && !wallRepulsionList.isEmpty()) createWall();
	}
	
	private void createGravity() {
		Map<String, String> gravityMap = gravityList.remove(0);
		double magnitude = Double.parseDouble(gravityMap.get(GravityParser.MAGNITUDE));
		double direction = Double.parseDouble(gravityMap.get(GravityParser.DIRECTION));
		setG(new Gravity(preferenceVal(PreferencesParser.GRAVITY_MAGNITUDE,magnitude), preferenceVal(PreferencesParser.GRAVITY_DIRECTION,direction)));
		forceList.add(getG());
	}
	
	private void createViscosity() {
		Map<String, String> viscosityMap = viscosityList.remove(0);
		double magnitude = Double.parseDouble(viscosityMap.get(ViscosityParser.MAGNITUDE));
		setV(new Viscosity(preferenceVal(PreferencesParser.VISCOSITY_MAGNITUDE,magnitude)));
		forceList.add(getV());
	}
	
	private void createCenterMass() {
		Map<String, String> centerMassMap = centerMassList.remove(0);
		double magnitude = Double.parseDouble(centerMassMap.get(CenterMassParser.MAGNITUDE));
		double exponent = Double.parseDouble(centerMassMap.get(CenterMassParser.EXPONENT));
		setCM(new CenterMass(preferenceVal(PreferencesParser.CENTERMASS_MAGNITUDE,magnitude),preferenceVal(PreferencesParser.CENTERMASS_EXPONENT,exponent)));
		forceList.add(getCM());
	}
	
	private void createWall() {
		Map<String, String> wallRepulsionMap = wallRepulsionList.remove(0);
		double id = Double.parseDouble(wallRepulsionMap.get(WallParser.ID));
		double magnitude = Double.parseDouble(wallRepulsionMap.get(WallParser.MAGNITUDE));
		double exponent = Double.parseDouble(wallRepulsionMap.get(WallParser.EXPONENT)); 
		int wallIndex = Integer.parseInt(wallRepulsionMap.get(WallParser.ID)) - 1;
		WallRepulsion wr = new WallRepulsion(id, magnitude,exponent);
		walls[wallIndex] = wr;
		forceList.add(wr);
	}

	public List<Force> getForceList() {
		return forceList;
	}

	public void setForceList(List<Force> forceList) {
		this.forceList = forceList;
	}

	public Gravity getG() {
		return g;
	}

	private void setG(Gravity g) {
		this.g = g;
	}

	public Viscosity getV() {
		return v;
	}

	private void setV(Viscosity v) {
		this.v = v;
	}

	public CenterMass getCM() {
		return cm;
	}

	private void setCM(CenterMass cm) {
		this.cm = cm;
	}

	public WallRepulsion getWalls(int id) {
		return walls[id-1];
	}

	public void setWalls(WallRepulsion[] walls) {
		this.walls = walls;
	}
}
