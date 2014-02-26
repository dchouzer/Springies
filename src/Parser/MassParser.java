package Parser;


import java.util.HashMap;
import java.util.Map;

import jboxGlue.Mass;


import org.w3c.dom.Element;

public class MassParser extends Parser{
	public static final String TAG = "mass";
	protected final static String TYPE = "type";
	protected final static String ID = "id";
	protected final static String X = "x";
	protected final static String Y = "y";
	protected final static String VX = "vx";
	protected final static String VY = "vy";
	protected final static String MASS = "mass";
	protected final static String DEFAULT_MASS = "1";
	protected final static String DEFAULT_SPEED = "0";
	protected final static String COLLISION_ID = "" + Mass.COLLISION_ID;
	protected final static String COL_ID_STRING = "collisionid";
	protected final static String RADIUS = "radius";
	protected final static String RADIUS_VALUE = "5";
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TYPE, TAG);
		map.put(RADIUS, RADIUS_VALUE);
		map.put(ID, element.getAttribute(ID));
		map.put(X, element.getAttribute(X));
		map.put(Y, element.getAttribute(Y));
		map.put(VX, checkValue(element.getAttribute(VX), DEFAULT_SPEED));
		map.put(VY,   checkValue(element.getAttribute(VY), DEFAULT_SPEED));
		map.put(MASS, checkValue(element.getAttribute(MASS), DEFAULT_MASS));
		map.put(COL_ID_STRING, COLLISION_ID);
		return map;
	}
	

}
