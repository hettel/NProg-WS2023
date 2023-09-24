package part1.kmeans.util;

import java.util.List;
import java.util.stream.Collectors;

public class Image
{
  public final int width;
  public final int height;
  public final List<Pixel> pixels;
  public final List<Integer> colorList;
  
  public Image(int width, int height, List<Pixel> pixels)
  {
    this.width = width;
    this.height = height;
    this.pixels = pixels;
    
    this.colorList = pixels.stream()
                      .map(dp -> dp.getRgbColor() )
                      .distinct()
                      .sorted()
                      .collect( Collectors.toUnmodifiableList() );
  }
  
  public List<Integer> getSortedRgbColors()
  {
    return this.colorList;
  }
  
  public int getColorCount()
  {
    return this.colorList.size();
  }
  
  public int getPixelCount()
  {
    return this.pixels.size();
  }
}
