package jboxGlue;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObject {
	private double myRadius;
	private String id;
	private int WALL_FORCE = 150;
	public static final int COLLISION_ID = 5;
	public Mass(String id,
			int collisionId,
			JGColor color,
			double radius,
			double mass)
	{
		super(id, collisionId, color);
		init(radius, mass);
		this.id = id;
	}
	
	
	protected void init (double radius, double mass)
    {
		 myRadius = radius;
	     int intRadius = (int)radius;
	        // make it a circle
	     CircleDef shape = new CircleDef();
	     shape.radius = (float)radius;
	     shape.density = (float) mass;

	     createBody(shape);
	     setBBox(-intRadius, -intRadius, 2 * intRadius, 2 * intRadius);
	     myBody.m_mass = (float) mass;
    }
	
	
	@Override
    public void hit (JGObject other)
    {
        // we hit something! bounce off it!
        Vec2 velocity = myBody.getLinearVelocity();
        // is it a tall wall?
        final double DAMPING_FACTOR = 0.8;
        //why does this collision occur?
        boolean isSide = other.getBBox().height > other.getBBox().width;
        if (isSide) {
            velocity.x *= -DAMPING_FACTOR;
        }
        else {
            velocity.y *= -DAMPING_FACTOR;
        }
        // apply the change
        myBody.setLinearVelocity(velocity);
        
        //Was unable to account for moving walls
		if (other.getName().startsWith("topwall")) {
			this.setForce(0, WALL_FORCE);
		} else if (other.getName().startsWith("rightwall")) {
			this.setForce(-WALL_FORCE, 0);
		} else if (other.getName().startsWith("bottomwall")) {
			this.setForce(0, -WALL_FORCE);
		} else if (other.getName().startsWith("leftwall")) {
			this.setForce(WALL_FORCE, 0);
		}
    }
	
	@Override
    public void paintShape ()
    {
        myEngine.setColor(myColor);
        myEngine.drawOval(x, y, (float)myRadius * 2, (float)myRadius * 2, true, true);
    }
	
	@Override
	public String toString() {
		return id;
	}
}
