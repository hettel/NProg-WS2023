package part1.kmeans.util;

public class DistanceTools
{
  public static int getNearestCentroidId(Pixel pixel, ColorCentroid[] centroids)
  {
    int centroidId = 0;  // default
    double minDistance = distanceSquard(pixel, centroids[0]);
    
    for(int i=1; i < centroids.length; i++)
    {
      double distance = distanceSquard(pixel, centroids[i]);
      if( distance < minDistance )
      {
        minDistance = distance;
        centroidId = i;
      }
    }
    
    return centroidId;
  }
  
  public static double distanceSquard(Pixel pixel, ColorCentroid cc)
  {
    return (pixel.red-cc.red)*(pixel.red-cc.red) 
         + (pixel.green-cc.green)*(pixel.green-cc.green) 
         + (pixel.blue-cc.blue)*(pixel.blue-cc.blue);
  }
}
