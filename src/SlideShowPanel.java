import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/** SlideShowPanel is the GUI for the slide show application
 * Implements ActionListener
 * @author nganhoang
 *
 */
public class SlideShowPanel extends JPanel implements ActionListener
{
	/****** INSTANCE VARIABLES ******/
	
	/** Panel showing intro about the app **/
	private JPanel introPanel;
	private JTextArea introText;

	/** Panel storing image handling and slide show starting operations **/
	private JPanel imgHandler;
	private JButton addFileBtn;
	private JLabel fileLoaded;
	private JLabel enterTime;
	private JTextField timeField;
	private JLabel timeEntered;
	private JButton setImgTimeBtn;

	/** Panel displaying slides **/
	private JPanel slideshowPane;
	// JLabel to store slideshow images
	private JLabel label;

	/** Panel displaying timer and start slideshow button **/
	private JPanel timerPanel;
	private JButton startShowBtn;
	private JLabel timerLabel;
	private JTextArea timeOfSlide;
	
	/** Non-GUI data **/
	private Timer timer;
	private ImageIcon currentImg;
	private int currentTime;
	private SlideShowData slideshow;
	private int timeLasted;
	private static final Integer SLIDE_INTERVAL = 5000;

	
	/**** CONSTRUCTOR ****/
	public SlideShowPanel()
	{
		// Set border layout and add panels
		setLayout(new BorderLayout());

		add(createIntroPanel(), BorderLayout.NORTH);

		// Add panel with image choosing and handling operations
		add(createImgHandler(), BorderLayout.WEST);

		add(createTimerPanel(), BorderLayout.SOUTH);

		// Add panel storing slides
		createSlideshowPanel();
		add(slideshowPane, BorderLayout.EAST);

		// Instantiate slideshow backend
		slideshow = new SlideShowData();

		// Set current time default as time 0
		currentTime = 0;
		timeLasted = 0;

		// Set default welcome slide
		label.setIcon(slideshow.getPqSlide().maximum().getImage());

	}


	/** Return panel containing file browsing button, text field for duration specification,
	 * and button to add an image to slide show
	 * @return
	 */
	public JPanel createImgHandler()
	{
		imgHandler = new JPanel(new GridLayout(6,1));

		// Buttons
		addFileBtn = new JButton("Browse an image");
		addFileBtn.addActionListener(this);

		setImgTimeBtn = new JButton("Add to slideshow!");
		setImgTimeBtn.setSize(40, 20);
		setImgTimeBtn.addActionListener(this);
		
		// Labels
		enterTime = new JLabel("Enter the display order for the image, eg. 1 for 1st slide:");
		fileLoaded = new JLabel();
		timeEntered = new JLabel();
		
		// Text field
		timeField = new JTextField();

		imgHandler.add(addFileBtn);
		imgHandler.add(fileLoaded);
		imgHandler.add(enterTime);
		imgHandler.add(timeField);
		imgHandler.add(setImgTimeBtn);
		imgHandler.add(timeEntered);

		return imgHandler;
	}


	/**
	 * Return the panel that displays introduction about slide show app
	 * @return JPanel
	 */
	public JPanel createIntroPanel()
	{
		introPanel = new JPanel( new BorderLayout() );

		introText = new JTextArea();
		introText.setText("To add a slide, please choose a photo from your local directories."
				+ "\n Then enter the order at which you want the photo to appear on the slideshow."
				+ "\n For example: 2 if the slide appears second, 3 if third etc.");
		introPanel.add(introText, BorderLayout.WEST);

		return introPanel;
	}


	/** Customize the panel that shows the slide show
	 * 
	 */
	public void createSlideshowPanel()
	{
		slideshowPane = new JPanel();

		// Set label to store a slide
		label = new JLabel();
		label.setPreferredSize(new Dimension(600, 500));

		slideshowPane.add(label);
	}

	
	/**
	 * Return the panel that displays button to start slide show and timer
	 * @return JPanel
	 */
	public JPanel createTimerPanel()
	{
		timerPanel = new JPanel(new GridLayout(1,3));
		
		// Button that starts the slide show
		startShowBtn = new JButton("START SLIDESHOW");
		startShowBtn.addActionListener(this);
		
		// Timer section: Showing the time duration (in seconds) at which each slide is at
		// label
		timerLabel = new JLabel("Time: ");
		timerLabel.setHorizontalAlignment(JLabel.RIGHT);
		// text area displays timer
		timeOfSlide = new JTextArea(1, 1);
		timeOfSlide.setEditable(false);
		
		timerPanel.add(startShowBtn);
		timerPanel.add(timerLabel);
		timerPanel.add(timeOfSlide);
		
		return timerPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Make sure that an event occurs to a JButton
		if (e.getSource() instanceof JButton)
		{
			JButton btnPressed = (JButton) e.getSource();

			// If user wants to add an image
			if (btnPressed.equals(addFileBtn))
			{
				// Get chosen file's path
				String path = SlideShowData.browseImage();
				if (path != "")
				{
					// Set notification
					fileLoaded.setText("Image loaded! Now:");
					timeEntered.setText("");
					
					// Resize the image in the path
					currentImg = resizeImage(path);
				}
				else
					System.err.println("No image is chosen");			
			}
			
			// If user wants to set time of display for an image
			else if (btnPressed.equals(setImgTimeBtn))
			{
				// Execute only if there's already an image
				if (currentImg != null)
				{
					// Get current time from the text field
					currentTime = Integer.parseInt(timeField.getText());
					currentTime = currentTime * 1000;
					slideshow.insertSlide(currentTime, currentImg);
					
					// Set notification label
					timeEntered.setText("Slide added! Please choose another image or start the show.");
					fileLoaded.setText("");
				}
			}

			// If user wants to start the show
			else if (btnPressed.equals(startShowBtn))
			{			
				// Set new timer and timer task
				timer = new Timer();

				TimerTask timerTask = new TimerTask() {
					@Override
					public void run() 
					{	
						// Extract current max slide from priority queue
						Slide currentSlide = slideshow.extractMaxSlideFromPQ();
						// Cancel timer task if reaches end of queue
						if (currentSlide == null)
							this.cancel();
						else // Ow
						{
							// Display image in the current slide
							label.setIcon(currentSlide.getImage());
							
							// Display time lasted since the beginning of slide show
							timeOfSlide.setText(Integer.toString(timeLasted/1000));
							
							// Recalculate time lasted
							timeLasted = timeLasted + SLIDE_INTERVAL;
						
						}
					}
				};	
				// Schedule timer to recur timer task
				timer.schedule(timerTask, 0, SLIDE_INTERVAL);
			}
		}
	}


	/** Helper method for resizing image to fit the panel
	 * 
	 * @param ImagePath
	 * @return resized image
	 */
	private ImageIcon resizeImage(String ImagePath) 
	{ 
		ImageIcon MyImage = new ImageIcon(ImagePath); 
		Image img = MyImage.getImage(); 
		Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH); 
		ImageIcon image = new ImageIcon(newImg); 
		return image; 
	}

}
