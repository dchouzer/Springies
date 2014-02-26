package forces;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;
import forces.*;
import jgame.impl.JGEngineInterface;
import org.junit.Test;

public class ForceTest extends JGEngine{
	@Test
	public void makeGravity() {
		Gravity g = new Gravity(3.0, 90);
		assertEquals( g.getMagnitude(),3.0, 0.0);
	}
	public ForceTest() {
		
	}
	//check to see if creating gravity works
	
	
	@Test
	public void makeForces() {
		CenterMass cm = new CenterMass(3.0, 4.0);
	}


	@Override
	public void initCanvas() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		
	}
	
	

}
