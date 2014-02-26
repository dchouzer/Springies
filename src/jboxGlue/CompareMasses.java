package jboxGlue;

import java.util.Comparator;

import org.jbox2d.common.Vec2;

public class CompareMasses implements Comparator<Mass>{
	Vec2 position;
	public CompareMasses(double x, double y) {
		position = new Vec2();
		position.x = (float) x;
		position.y = (float) y;
	}
	
	public int compare(Mass a, Mass b) {
		Vec2 distA = a.getBody().getPosition().sub(position);
		double distanceA = distA.normalize();
		Vec2 distB = b.getBody().getPosition().sub(position);
		double distanceB = distB.normalize();
		if (distanceA > distanceB) return 1;
		else return -1;
	}
}
