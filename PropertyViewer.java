import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. 
 * 
 * 
 * @author Michael Kölling and Josh Murphy
 * @version 1.0
 */
public class PropertyViewer
{    
    private PropertyViewerGUI gui;     // the Graphical User Interface
    private Portfolio portfolio;
    private int currentNumberProperty;
    private int numberOfPropertiesViewed;
    private int totalPrice;
    private int averagePrice;
    private double currentLatitude;
    private double currentLongitude;
    
    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public PropertyViewer()
    {
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        
        //Initialise all the variables
        currentNumberProperty = 0;
        numberOfPropertiesViewed = 1;
        totalPrice = portfolio.getProperty(0).getPrice();
        averagePrice = portfolio.getProperty(0).getPrice();
        currentLatitude = portfolio.getProperty(0).getLatitude();
        currentLongitude = portfolio.getProperty(0).getLongitude();
        
        //Display the first Property
        gui.showID(portfolio.getProperty(0));
        gui.showProperty(portfolio.getProperty(0));
    }
    
    /**
     * Display the next property in the portfolio with its data.
     */
    public void nextProperty()
    {
        if (portfolio.numberOfProperties()> 0){
            currentNumberProperty = (currentNumberProperty + 1) % portfolio.numberOfProperties();
            Property nextProperty = portfolio.getProperty(currentNumberProperty);
            gui.showProperty(nextProperty);
            gui.showID(nextProperty);
            gui.showFavourite(nextProperty);
            
            //Update the variables for the other methods
            numberOfPropertiesViewed = numberOfPropertiesViewed + 1;
            totalPrice = totalPrice + nextProperty.getPrice();
            currentLatitude = nextProperty.getLatitude();
            currentLongitude = nextProperty.getLongitude();
        }
    }

    /**
     * Display the previous property in the portfolio.
     */
    public void previousProperty()
    {
        if (portfolio.numberOfProperties() > 0) {
            if (currentNumberProperty > 0) {
            currentNumberProperty = currentNumberProperty - 1;
            } else {
                currentNumberProperty = portfolio.numberOfProperties() - 1;
            }
            Property previousProperty = portfolio.getProperty(currentNumberProperty);
            gui.showProperty(previousProperty);
            gui.showID(previousProperty);
            gui.showFavourite(previousProperty);
            
            //Update the variables for the other methods
            numberOfPropertiesViewed = numberOfPropertiesViewed + 1;
            totalPrice = totalPrice + previousProperty.getPrice();
            currentLatitude = previousProperty.getLatitude();
            currentLongitude = previousProperty.getLongitude();
        }    
    }
    
    /**
     *  Toggle the favourite status of the currently displayed property.
     */
    public void toggleFavourite()
    {
        Property currentProperty = portfolio.getProperty(currentNumberProperty);
        currentProperty.toggleFavourite();
        gui.showFavourite(currentProperty);        
    }
    

    //----- methods for challenge tasks -----
    
    /**
     * This method opens the system's default internet browser
     * The Google maps page shows the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
       double latitude = currentLatitude;
       double longitude = currentLongitude;
       
       URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
       java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * Return the number of properties viewed by the user.
     */
    public int getNumberOfPropertiesViewed()
    {
        return numberOfPropertiesViewed;
    }
    
    /**
     * Return the average property price among viewed properties.
     */
    public int averagePropertyPrice()
    {
        averagePrice = totalPrice / numberOfPropertiesViewed;
        return averagePrice;
    }
    
    /**
     * This method opens a new window
     * The window displays the number of properties viewed and the average property price among viewed properties.
     */
    public void statisticsProperties()
    {
        //Create a new window
        JFrame frame = new JFrame("Statistics");
        frame.setSize(400,100);
        frame.setVisible(true);
        
        //Add a text with the statistics on the new window
        JTextArea text = new JTextArea();
        text.setText("Number of properties viewed :" + getNumberOfPropertiesViewed() + "\nAverage property price among viewed properties : £" + averagePropertyPrice());
        frame.add(text);
    }
}
