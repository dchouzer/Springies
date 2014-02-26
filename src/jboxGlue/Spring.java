	package jboxGlue;

import org.jbox2d.common.Vec2;


import jgame.JGColor;

public class Spring extends PhysicalObject {
	protected double restLength;
	protected Mass mass1 ;
	protected Mass mass2;
	protected double currentLength;
	protected double k;
	protected JGColor color;
	public static final int COLLISION_ID = 4;
	public static final String TYPE = "spring";
	public Spring(String id, int collisionid, JGColor color, Mass m1, Mass m2, double restLength, double k) {
		super(id, collisionid, color);
		this.color = color;
		this.mass1= m1;
		this.mass2 = m2; 
		this.restLength = restLength;
		this.k = k;
		this.color = color; 
	}
	
	public void applyForce() {
		Vec2 m1V = mass1.myBody.getPosition();
		Vec2 m2V = mass2.myBody.getPosition();
		Vec2 d = m1V.sub(m2V);
		double distance = d.normalize();
		currentLength = distance;
		Vec2 forceVector = d.mul((float)Math.abs(k * (restLength - currentLength)));
		if (distance < restLength) {
			//pull
			mass1.setForce(forceVector.x, forceVector.y);
			forceVector = forceVector.negate();
			mass2.setForce(forceVector.x, forceVector.y); 
		} else {
			mass2.setForce(forceVector.x, forceVector.y);
			forceVector = forceVector.negate();
			mass1.setForce(forceVector.x, forceVector.y); 
		}
	}
	public void paintShape() {
		myEngine.setColor(currentColor());
        myEngine.drawLine(mass1.x, mass1.y, mass2.x, mass2.y);	
		
	}
	
	@Override 
	public void destroy() {
		// do nothing since it does not have a body
	}
	
	public String toString() {
		return "Connecting " + mass1.toString() + " and " + mass2.toString();
	}
	
	public JGColor currentColor() {
		return (currentLength <= restLength) ? JGColor.green : JGColor.red;
	}
	
	@Override
	public void move() {
		applyForce();
	}
	
	public void increaseAmplitude() {
	}
	
	public void decreaseAmplitude() {
	}
	
	public void setAmplitude(double d) {
	}


	
	

}
