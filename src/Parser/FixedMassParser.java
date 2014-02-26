package Parser;

import java.util.HashMap;
import java.util.Map;

import jboxGlue.Mass;

import org.w3c.dom.Element;


public class FixedMassParser extends Parser {
	public static final String TAG = "fixed";
	protected static final String TYPE = "type";
	protected static final String ID = "id";
	protected static final String X = "x";
	protected static final String Y = "y";
	protected static final String MASS = "mass";
	protected static final String DEFAULT_MASS = "0";
	protected static final String DEFAULT_SPEED = "0";
	protected static final String DEFAULT_RADIUS = "5";
	protected static final String COLLISION_ID = "" + Mass.COLLISION_ID;
	protected static final String RADIUS = "radius";
	protected static final String VX = "vx";
	protected static final String VY = "vy";
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(TYPE, TAG);
		map.put(ID, element.getAttribute(ID));
		map.put(X, element.getAttribute(X));
		map.put(Y, element.getAttribute(Y));
		map.put(MASS, DEFAULT_MASS);	
		map.put(RADIUS, checkValue(element.getAttribute(RADIUS),DEFAULT_RADIUS));
		map.put("collisionid", COLLISION_ID);
		map.put(VX, DEFAULT_SPEED);
		map.put(VY, DEFAULT_SPEED);
		return map;
	}


}
