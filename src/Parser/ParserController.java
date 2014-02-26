package Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

public abstract class ParserController {
	protected Map<String, String> preferences = new HashMap<String, String>();
	public double preferenceVal(String key, double givenValue) {
		if (preferences.containsKey(key)) {
			return Double.parseDouble(preferences.get(key));
		}
		return givenValue;
	}
	
	public boolean containsPreference(String key) {
		return preferences.containsKey(key);
	}
	
	public String getPreference(String key) {
		return preferences.get(key);
	}
	
	public Map<String, String> getPreferences() {
		return preferences;
	}
	
	public abstract void setPreferences(List<Map<String, String>> preferences);
	
	public abstract void parseInfo(Document doc);
	
	public abstract void createObjects();
}
