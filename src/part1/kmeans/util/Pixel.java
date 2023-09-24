package part1.kmeans.util;

public class Pixel
{
  public final int x,y;
  // public visibility for performance reasons
  public int alpha;
  public int red,green,blue;
  
  
  public int centroidId;
  
  public Pixel(int x, int y, int alpha, int red, int green, int blue)
  {
    this.x = x;
    this.y = y;
    
    this.alpha = alpha;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }
  
  public int getRgbColor()
  {
    return (this.alpha << 24) | (this.red << 16) | (this.green << 8) | this.blue;
  }
  
  
  public void setRgbColor(int rgb)
  {   
    this.alpha = (rgb >> 24) & 0xff;
    this.red = (rgb >> 16) & 0xff;
    this.green = (rgb >> 8) & 0xff;
    this.blue = (rgb) & 0xff;
  }
  
  public Pixel copy()
  {
    return new Pixel(this.x, this.y, this.alpha, this.red, this.green, this.blue);
  }
  
  @Override
  public String toString()
  {
    return "[ (x/y) = (" + x + "/" + y + ") : RGB " + this.getRgbColor() + "]";
  }
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Pixel other = (Pixel) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }
}
