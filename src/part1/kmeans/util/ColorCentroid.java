package part1.kmeans.util;

public class ColorCentroid
{
  public double red,green,blue;
  
  public ColorCentroid( double red, double green, double blue)
  {
    super();
    this.red = red;
    this.green = green;
    this.blue = blue;
  }
  
  
  
  public int getRgbColor()
  {
    return ((int)this.red << 16) | ((int)this.green << 8) | (int)this.blue;
  }
  
  
  @Override
  public String toString()
  {
    return "(rgb) = (" + red + " / " + green + " / " + blue +")";
  }
}
