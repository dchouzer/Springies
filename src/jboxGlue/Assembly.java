package jboxGlue;

import java.util.List;

public class Assembly {
	private List<Mass> massList;
	private List<Spring> springList;
	public Assembly(List<Mass> massList, List<Spring> springList) {
		this.massList = massList;
		this.springList = springList;
	}
	
	public String toString() {
		return "Number of Masses : " + getMassList().size() + " Number of Springs : " + springList.size();
	}
	
	public void increaseAmplitude() {
		for (Spring sp : springList) {
			sp.increaseAmplitude();
		}
	}
	
	public void decreaseAmplitude() {
		for (Spring sp : springList) {
			sp.decreaseAmplitude();
		}
	}
	
	public void removeObjects() {
		for (Mass m : massList) {
			m.remove();
		}
		for (Spring spr : springList) {
			spr.remove();
		}

	}
	
	public void setAmplitude(double d) {
		for (Spring spr : springList) {
			spr.setAmplitude(d);
		}
	}

	public List<Mass> getMassList() {
		return massList;
	}
	
	protected List<Spring> getSpringList() {
		return springList;
	}
	
}
