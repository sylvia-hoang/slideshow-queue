import javax.swing.JFrame;

public class SlideShowMainApp 
{
	public static void main(String[] args) 
	{
		// create a JFrame
		JFrame slideshowFrame = new JFrame("Make your own slideshow.");
		
		// set size
		slideshowFrame.setSize(1000,500);
		
		// set normal exiting upon closing the window
		slideshowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add sorting panel to frame
		SlideShowPanel slideshowPanel = new SlideShowPanel();
		
		slideshowFrame.add(slideshowPanel);
		
		// set visible
		slideshowFrame.setVisible(true);
	}

}
