/**
 * 
 * @author dpc22
 * 
 */

package forces;

import java.util.List;

import jboxGlue.*;

public class Force {

	protected double myMagnitude;
	private boolean toggledOn = true;
	protected String TAG;

	public double[] calculateForceVector(Mass m) {
		double[] checker = new double[2];
		return checker;
	}
	

	public void turnOn() {
		toggledOn = true;
	}

	public void turnOff() {
		toggledOn = false;
	}

	public void switchToggle() {
		if (isToggledOn()) {
			turnOff();
		} else {
			turnOn();
		}
	}

	public Force(double magnitude) {
		this.myMagnitude = magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.myMagnitude = magnitude;
	}

	public double getMagnitude() {
		return myMagnitude;
	}

	public String getTag() {
		return TAG;
	}

	public void applyForce(List<Mass> massList) {

	}

	public boolean isToggledOn() {
		return toggledOn;
	}

}
