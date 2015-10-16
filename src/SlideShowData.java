import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SlideShowData 
{
	// Priority queue of slides
	private PriorityQueueBH<Slide> pqSlide;
	// slide array 
	private Slide[] slideArray;

	
	/*** CONSTRUCTOR ***/	
	public SlideShowData()
	{
		// Init array
		slideArray = new Slide[1];
		
		// Get default image to be inserted into slide array
		ImageIcon img = new ImageIcon("welcome.jpg");
		Slide slide1 = new Slide(0, img);
		slideArray[0] = slide1;
		
		// Init priority queue
		pqSlide = new PriorityQueueBH<Slide>(slideArray);		
	}

	
	/** Insert slide into slide array 
	 * 
	 * @param time
	 * @param img
	 */
	public void insertSlide(Integer time, ImageIcon img)
	{	
		Slide newSlide = new Slide(time, img);

		pqSlide.insert(newSlide);		
	}
	
	/**
	 * Static method that handles file 
	 * @return path to image file
	 */
	public static String browseImage()
	{
		String path = "";
		try 
		{
			// Generate file chooser and get 
			JFileChooser chooser = new JFileChooser();

			File f = new File(new File(".").getCanonicalPath());

			chooser.setCurrentDirectory( f );

			// add a filter for image files
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
			chooser.addChoosableFileFilter(filter);		

			int result = chooser.showOpenDialog(null);
			
			// If user chooses a file
			if (result == JFileChooser.APPROVE_OPTION)
			{
				// Get file and its path
				File selectedFile = chooser.getSelectedFile();
				path = selectedFile.getAbsolutePath();

			} // ow if cancelled
			else if (result == JFileChooser.CANCEL_OPTION)
			{
				System.out.println("No file was chosen");
			}
		} // Catch IO exception 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		return path;
	}
	
	
	/** 
	 * Extract max slide from the priority queue
	 * @return Slide
	 */
	public Slide extractMaxSlideFromPQ()
	{
		return pqSlide.extractMax();
	}
	
	/** Peek the max slide from the priority queue
	 * @return Slide
	 */
	public Slide peekMaxSlideFromPQ()
	{
		return pqSlide.maximum();
	}
	
	
	/** Getter for pqSlide
	 * @return the pqSlide
	 */
	public PriorityQueueBH<Slide> getPqSlide() 
	{
		return pqSlide;
	}

}
