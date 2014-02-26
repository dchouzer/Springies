/**
 * @author dpc22
 */

package forces;

import java.util.List;

import jboxGlue.Mass;

public class Gravity extends Force {

	public Gravity(double magnitude) {
		super(magnitude);
		// TODO Auto-generated constructor stub
	}

	private double myXDirection;
	private double myYDirection;
	private double myMagnitude;
	private double myAngle;
	private String TAG;
	
	

	public Gravity(double magnitude, double direction) {
		super(magnitude);
		myMagnitude = magnitude;
		myAngle = direction;
		convertAngleToXY(myAngle);
		multiplyForceVector();
		TAG = "g";
	}

	public Gravity(double xdirection, double ydirection, double magnitude) {
		super(magnitude);
		this.myXDirection = xdirection;
		this.myYDirection = ydirection;
	}

	public void convertAngleToXY(double angle) {
		myXDirection = Math.cos(angle / 180 * Math.PI);
		myYDirection = Math.sin(angle / 180 * Math.PI);
	}
	
	public double[] calculateForceVector() {
		double distance = Math.sqrt(Math.pow(myXDirection, 2)
				+ Math.pow(myYDirection, 2));
		double[] unitVector = { myXDirection / distance,
				myYDirection / distance };
		double[] forceVector = { unitVector[0] * myMagnitude,
				unitVector[1] * myMagnitude };
		return forceVector;
	}

	public void multiplyForceVector() {
		myXDirection = myXDirection * myMagnitude;
		myYDirection = myYDirection * myMagnitude;
	}

	public double getXDirection() {
		return myXDirection;
	}

	public double getYDirection() {
		return myYDirection;
	}

	public String toString() {
		return "X force : " + myXDirection + " Y force : ";
	}

	public void applyForce(List<Mass> list) {
		for (Mass m : list) {
			m.setForce(myXDirection, myYDirection);
		}
	}
	
	public String getTag() {
		return TAG;
	}
}
