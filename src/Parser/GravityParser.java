package Parser;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class GravityParser extends Parser{ 
	public static final String TAG = "gravity";
	protected static final String TYPE = "type";
	protected static final String DIRECTION = "direction";
	protected static final String MAGNITUDE = "magnitude";
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map  = new HashMap<String, String>();
		map.put(MAGNITUDE, element.getAttribute(MAGNITUDE));
		map.put(DIRECTION, element.getAttribute(DIRECTION));
		map.put(TYPE, TAG);
		return map;
	}


}
