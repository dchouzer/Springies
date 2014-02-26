package Parser;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class PreferencesParser extends Parser {
	protected static final String AMPLITUDE = "amplitude";
	protected static final String RADIUS = "radius";
	protected static final String MASS = "mass";
	protected static final String VISCOSITY_MAGNITUDE = "viscosity_magnitude";
	protected static final String GRAVITY_DIRECTION = "gravity_direction";
	protected static final String GRAVITY_MAGNITUDE = "gravity_magnitude";
	protected static final String CENTERMASS_MAGNITUDE = "centermass_magnitude";
	protected static final String CENTERMASS_EXPONENT = "centermass_exponent";
	
	@Override
	public Map<String, String> getAttribute(Element element) {
		Map<String, String> map = new HashMap<String,String>();
		map.put(AMPLITUDE,element.getAttribute(AMPLITUDE));
		map.put(RADIUS, element.getAttribute(RADIUS));
		map.put(MASS, element.getAttribute(MASS));
		map.put(VISCOSITY_MAGNITUDE, element.getAttribute(VISCOSITY_MAGNITUDE));
		map.put(GRAVITY_DIRECTION, element.getAttribute(GRAVITY_DIRECTION));
		map.put(GRAVITY_MAGNITUDE, element.getAttribute(GRAVITY_MAGNITUDE));
		map.put(CENTERMASS_MAGNITUDE, element.getAttribute(CENTERMASS_MAGNITUDE));
		map.put(CENTERMASS_EXPONENT, element.getAttribute(CENTERMASS_EXPONENT));
		return map;
	}
	
}
