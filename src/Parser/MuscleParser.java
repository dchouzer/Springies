package Parser;

import java.util.HashMap;
import java.util.Map;

import jboxGlue.Muscle;


import org.w3c.dom.Element;


public class MuscleParser extends Parser {
	public static final String TAG = "muscle";
	protected static final String TYPE = "type";
	protected static final String A = "a";
	protected static final String B = "b";
	protected static final String RESTLENGTH = "restlength";
	protected static final String AMPLITUDE = "amplitude";
	protected static final String CONSTANT = "constant";
	protected static final String ID = "muscle";
	protected static final String DEFAULT_VALUE = "1";
	protected static final String COLLISION_ID = "" + Muscle.COLLISION_ID;
	protected final static String COL_ID_STRING = "collisionid";
	
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map = new HashMap<String,String>();
		map.put(TYPE, TAG);
		map.put(COL_ID_STRING, COLLISION_ID);
		map.put(A,element.getAttribute(A));
		map.put(B, element.getAttribute(B));
		map.put(RESTLENGTH , checkValue(element.getAttribute(RESTLENGTH), DEFAULT_VALUE));
		map.put(AMPLITUDE, checkValue(element.getAttribute(AMPLITUDE),  DEFAULT_VALUE));
		map.put(CONSTANT, checkValue(element.getAttribute(CONSTANT),  DEFAULT_VALUE));
		return map;
	}
	

	
	

}
