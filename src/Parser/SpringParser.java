package Parser;

import java.util.HashMap;
import java.util.Map;

import jboxGlue.Spring;


import org.w3c.dom.Element;


public class SpringParser extends Parser {
	public static final String TAG = "spring";
	protected static final String TYPE = "type";
	protected static final String A = "a";
	protected static final String B = "b";
	protected static final String RESTLENGTH = "restlength";
	protected static final String CONSTANT = "constant";
	protected static final String ID = "id";
	protected static final String DEFAULT_VALUE = "1";
	protected static final String COLLISION_ID = "" + Spring.COLLISION_ID;
	protected final static String COL_ID_STRING = "collisionid";
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map = new HashMap<String,String>();
		map.put(TYPE, TAG);
		map.put(A,element.getAttribute(A));
		map.put(B, element.getAttribute(B));
		map.put(COL_ID_STRING,COLLISION_ID);
		map.put(RESTLENGTH , checkValue(element.getAttribute(RESTLENGTH), DEFAULT_VALUE));
		map.put(CONSTANT, checkValue(element.getAttribute(CONSTANT),  DEFAULT_VALUE));
		return map;
	}
}
