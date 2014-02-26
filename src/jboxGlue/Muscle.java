package jboxGlue;

import jgame.JGColor;

public class Muscle extends Spring {
	protected static final double MUSCLE_CHANGE = 0.2;
	private double timer = 0;
	private double amplitude; 
	private final double fixedLength;
	public static final int COLLISION_ID = 4;
	public Muscle(String id, int collisionid, JGColor color,Mass m1, Mass m2,
			double restLength, double k, double amplitude) {
		super(id, collisionid, color, m1, m2, restLength, k);
		this.amplitude = amplitude;
		this.fixedLength = restLength;
	}
	
	@Override
	public void move() {
		timer += 0.05;
		double change = amplitude * Math.sin(Math.PI/2 * timer);
		restLength = fixedLength - change; 
		applyForce();
	}
	
	@Override
	public JGColor currentColor() {
		return (Math.sin(Math.PI/2 * timer) <= 0) ? JGColor.white : JGColor.orange;
	}
	
	@Override
	public String toString() {
		return "Muscle";
	}
	
	@Override
	public void increaseAmplitude(){
		amplitude += MUSCLE_CHANGE;
	}
	
	@Override
	public void decreaseAmplitude(){
		amplitude -= MUSCLE_CHANGE;
	}
	
	@Override 
	public void setAmplitude(double d) {
		amplitude = d; 
	}
	

}
