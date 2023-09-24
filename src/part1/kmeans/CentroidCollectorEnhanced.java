package part1.kmeans;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import part1.kmeans.util.ColorCentroid;
import part1.kmeans.util.Pixel;

public class CentroidCollectorEnhanced implements Collector< Pixel, double[][],  ColorCentroid[]>
{
  private final int k;
  
  public CentroidCollectorEnhanced(int cluster)
  {
    this.k = cluster;
  }
  
  
  @Override
  public Supplier<double[][]> supplier()
  {
    // array to accumulate the color values
    // first dimenson corresponds to the cluster
    // a[k][0]: red, a[k][1]: green, a[k][2]: blue
    // a[k][3]: pixel counter
    return () -> new double[k][4];
  }

  @Override
  public BiConsumer<double[][], Pixel> accumulator()
  {
    return (a,pixel) -> { 
       a[pixel.centroidId][0] += pixel.red; 
       a[pixel.centroidId][1] += pixel.green;
       a[pixel.centroidId][2] += pixel.blue;
       a[pixel.centroidId][3]++; 
     };
  }

  @Override
  public BinaryOperator<double[][]> combiner()
  {
    return (aLeft, aRight) -> {
      for(int i=0; i< aLeft.length; i++)
      {
        aLeft[i][0] += aRight[i][0];
        aLeft[i][1] += aRight[i][1];
        aLeft[i][2] += aRight[i][2];
        aLeft[i][3] += aRight[i][3];
      }
      return aLeft;
    };
  }

  @Override
  public Function<double[][], ColorCentroid[]> finisher()
  {
    return a -> { 
      ColorCentroid[] centroids = new ColorCentroid[ a.length ];
      for(int i=0; i < centroids.length; i++ )
      {
        centroids[i] = new ColorCentroid( a[i][0]/a[i][3], a[i][1]/a[i][3], a[i][2]/a[i][3]);
      }
      return centroids; 
      };
  }

  @Override
  public Set<Characteristics> characteristics()
  {
    return Set.of(Characteristics.UNORDERED);
  }
}
