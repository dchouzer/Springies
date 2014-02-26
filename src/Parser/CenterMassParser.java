package Parser;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class CenterMassParser extends Parser {
	protected static final String TAG = "centermass";
	protected static final String TYPE = "type";
	protected static final String MAGNITUDE = "magnitude";
	protected static final String EXPONENT = "exponent";
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map  = new HashMap<String, String>();
		map.put(TYPE, TAG);
		map.put(MAGNITUDE, element.getAttribute(MAGNITUDE));
		map.put(EXPONENT, element.getAttribute(EXPONENT));
		return map;
	}
}
