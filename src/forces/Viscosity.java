/**
 * 
 * @author dpc
 * 
 */

package forces;

import java.util.List;

import jboxGlue.Mass;

public class Viscosity extends Force {

	private double myMagnitude;
	public String TAG = "viscosity";
	
	public Viscosity(double magnitude) {
		super(magnitude);
		TAG = "v";
	}
	
	public void applyForce(List<Mass> list) {
		for (Mass m : list) {
			m.setForce(-m.xspeed * myMagnitude, -m.yspeed * myMagnitude);
		}
	}
	
	public String getTag() {
		return TAG;
	}
}
