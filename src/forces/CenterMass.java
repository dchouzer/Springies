/**
 * 
 * @author dpc22
 * 
 */

package forces;

import java.util.List;

import jboxGlue.Mass;

public class CenterMass extends Force {

	private double myExponent = 0;
	private double myCenterX = 0;
	private double myCenterY = 0;
	private String TAG = "centermass";

	public CenterMass(double magnitude, double exponent) {
		super(magnitude);
		myExponent = exponent;
		TAG = "m";
	}

	/*
	 * This method should return the type of force that will be taken in for
	 * ApplyForce
	 */
	public double[] calculateForceVector(Mass m) {
		double[] directions = calculateDirection(m);
		// distance formula - this also gives magnitude to create unit vector
		double distance = Math.sqrt(Math.pow((directions[0]), 2) + Math.pow((directions[1]), 2));
		double magnitude = myMagnitude * Math.pow((1 / distance), myExponent);
		double[] unitVector = { directions[0] * magnitude, directions[1] * magnitude };
		double[] forceVector = { unitVector[0], unitVector[1] };
		return forceVector;
	}
	
	// This method calculates the direction that the force should be directed in
	public double[] calculateDirection(Mass m) {
		double[] directions = new double[2];
		directions[0] = myCenterX - m.x;
		directions[1] = myCenterY - m.y;
		return directions;
	}

	// This method takes all of the masses in the environment and creates the
	// center of mass location
	public void setCenter(List<Mass> massList) {
		double myX = 0;
		double myY = 0;
		for (Mass mass : massList) {
			myX += mass.x;
			myY += mass.y;
		}
		myCenterX = myX / massList.size();
		myCenterY = myY / massList.size();
	}

	public void setExponent(double exponent) {
		myExponent = exponent;
	}



	public void applyForce(List<Mass> list) {
		setCenter(list);
		for (Mass m : list) {
			double[] forceVector = calculateForceVector(m);
			m.setForce(forceVector[0], forceVector[1]);
		}
	}
	
	public String getTag() {
		return TAG;
	}

}
