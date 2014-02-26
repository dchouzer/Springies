/**
 * 
 * @author dpc
 * 
 */
package forces;

import java.util.List;

import jboxGlue.Mass;

public class WallRepulsion extends Force {

	protected double myExponent;
	protected double myX;
	protected double myY;
	protected double myID;
	protected String TAG;
	protected double WALL_MAGNIFICATION = 1000000;
	protected int TOP_WALL_ID = 1;
	protected int RIGHT_WALL_ID = 2;
	protected int BOTTOM_WALL_ID = 3;
	protected int LEFT_WALL_ID = 4;

	public WallRepulsion(double magnitude) {
		super(magnitude);
	}

	public WallRepulsion(double id, double magnitude, double exponent) {
		super(magnitude);
		myID = id;
		myExponent = exponent;
		if (id == TOP_WALL_ID)
			TAG = "1";
		if (id == RIGHT_WALL_ID)
			TAG = "2";
		if (id == BOTTOM_WALL_ID)
			TAG = "3";
		if (id == LEFT_WALL_ID)
			TAG = "4";
	}

	public void setExponent(double exponent) {
		myExponent = exponent;
	}

	public void setXY(double x, double y) {
		myX = x;
		myY = y;
	}

	//We recognize that this is poor design but under the given time constraints,
	//we were unable to generalize the code to prevent if statements.
	public double[] calculateForceVectorAndSetWalls(Mass m) {

		double[] forces = new double[2];
			// TopWall
		if (myID == TOP_WALL_ID) {
			forces[0] = 0;
			forces[1] = (myMagnitude * WALL_MAGNIFICATION / Math.pow(m.y, 2));
			// Right Wall
		} else if (myID == RIGHT_WALL_ID) {
			myX = 1066;
			forces[0] = (-myMagnitude * WALL_MAGNIFICATION / Math.pow(myX - m.x, 2));
			forces[1] = 0;
			// BottomWall
		} else if (myID == BOTTOM_WALL_ID) {
			myY = 600;
			forces[0] = 0;
			forces[1] = (-myMagnitude * WALL_MAGNIFICATION / Math.pow(myY - m.y, 2));
			// LeftWall
		} else if (myID == LEFT_WALL_ID) {
			forces[0] = (myMagnitude * WALL_MAGNIFICATION / Math.pow(m.x, 2));
			forces[1] = 0;
		}
		return forces;
	}

	public void applyForce(List<Mass> list) {
		for (Mass m : list) {
			m.setForce(calculateForceVector(m)[0], calculateForceVector(m)[1]);
		}
	}
	
	public String getTag() {
		return TAG;
	}
}
