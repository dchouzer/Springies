package jboxGlue;

import jgame.JGColor;

public class MovingMass extends Mass {

	public MovingMass(String id, int collisionId, JGColor color, double radius,
			double mass) {
		super(id, collisionId, color, radius, mass);
	}
	
	@Override
	public void move() {
		super.move();
		setPos(this.eng.getMouseX(), this.eng.getMouseY());
	}

}
