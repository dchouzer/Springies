package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class Parser {

	public List<Map<String, String>> getDataFromXML(Document doc, String tagName) {
		List<Map<String, String>> listOfElements = new ArrayList<Map<String, String>>();
		NodeList list = doc.getElementsByTagName(tagName);
		if (doc != null) {
			for (int i = 0; i < list.getLength(); i++) {
				Element element = (Element) list.item(i);
				listOfElements.add(getAttribute(element));
			}
		}
		
		return listOfElements;
	}
	
	protected String checkValue(String value, String defaultValue) {
		return (!value.equals("")) ? value : defaultValue;
	}
	
	public abstract Map<String, String> getAttribute(Element element);
}
