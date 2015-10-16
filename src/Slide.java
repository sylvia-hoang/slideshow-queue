import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/** Slide implements the Comparable<> interface
 * Contains information about the time order and the picture of a slide
 * 
 * @author nganhoang
 *
 */
public class Slide implements Comparable<Slide> 
{
	/** Time and image properties **/
	private Integer time;
	
	private ImageIcon image;
	
	/** Constructor taking in time and an image as params
	 * 
	 * @param time
	 * @param image
	 */
	public Slide(Integer time, ImageIcon image)
	{
		this.time = time;
		this.image = image;
	}
	
	/** CompareTo method instructs slides with smaller time order number
	 * to be at the top of the queue
	 * @param other slide 
	 */
	@Override
	public int compareTo(Slide otherSlide) 
	{
		if (this.getTime() < otherSlide.getTime())
			return 1;
		else if (this.getTime() == otherSlide.getTime())
			return 0;
		else 
			return -1;
	}
	
	public Integer getTime()
	{
		return time;
	}
	
	public ImageIcon getImage()
	{
		return image;
	}

}
