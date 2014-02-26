 import springies.Springies;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Parser.MainParser;


/**
 * Creates window that can be moved, resized, and closed by the user.
 * 
 * @author Robert C. Duvall
 */
public class Main 
{
    // constants
    public static final Dimension SIZE = new Dimension(800, 600);
    public static final String TITLE = "Springies!";

    /**
     * main --- where the program starts
     * 
     * @param args
     */
    
    public static MainParser parser; 
    
    public static void main (String args[])
    {
        // view of user's content
        final Springies sp = new Springies();

        JButton loadFiles = new JButton("Load XML File");
        loadFiles.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed (ActionEvent arg0) {
        		sp.getFile();
            }
        });
        
       
        JButton removeObjects = new JButton("Clear Objects");
        removeObjects.addActionListener (new ActionListener() {
        	@Override
        	public void actionPerformed (ActionEvent arg0) {
        		sp.resetEnvironment();	
        		
        	}
        });
        
        JButton init = new JButton("Reinitialize");
        init.addActionListener (new ActionListener() {
        	@Override
        	public void actionPerformed (ActionEvent arg0) {
        		System.out.println("removing");
        		sp.doRemoveObjects();
        	}
        });
        
        // container that will work with user's OS
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components
        frame.getContentPane().add(sp, BorderLayout.CENTER);
        frame.getContentPane().add(loadFiles, BorderLayout.EAST);
        frame.getContentPane().add(init, BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
        
        //ask if user would like to load preferences
        int n = JOptionPane.showConfirmDialog(sp,
                "Do you want to load preferences?", "Load Preferences",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
        	sp.getPreferences();
        }
    }

	

	
}
