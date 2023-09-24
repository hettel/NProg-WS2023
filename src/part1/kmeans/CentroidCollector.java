package part1.kmeans;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import part1.kmeans.util.ColorCentroid;
import part1.kmeans.util.Pixel;

public class CentroidCollector implements Collector< Pixel, double[],  ColorCentroid>
{

  @Override
  public Supplier<double[]> supplier()
  {
    // array to accumulate the color values
    // a[0]: red, a[1]: green, a[2]: blue
    // a[3]: pixel counter
    return () -> new double[4];
  }

  @Override
  public BiConsumer<double[], Pixel> accumulator()
  {
    return (a,pixel) -> { 
       a[0] += pixel.red; 
       a[1] += pixel.green;
       a[2] += pixel.blue;
       a[3]++; 
     };
  }

  @Override
  public BinaryOperator<double[]> combiner()
  {
    return (aLeft, aRight) -> {
      aLeft[0] += aRight[0];
      aLeft[1] += aRight[1];
      aLeft[2] += aRight[2];
      aLeft[3] += aRight[3];
      return aLeft;
    };
  }

  @Override
  public Function<double[], ColorCentroid> finisher()
  {
    return a -> new ColorCentroid( a[0]/a[3], a[1]/a[3], a[2]/a[3]);
  }

  @Override
  public Set<Characteristics> characteristics()
  {
    return Set.of(Characteristics.UNORDERED);
  }
}
