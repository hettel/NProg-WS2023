package part1.kmeans;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

import part1.kmeans.util.ColorCentroid;
import part1.kmeans.util.DistanceTools;
import part1.kmeans.util.Image;

public class Reduce_04_Parallel_UserDefinedCollector_Enhanced
{
   static void reduce(Image image, final int maxCluster)
   {
      // random assignment to a cluster
      image.pixels.parallelStream().forEach(p -> p.centroidId = ThreadLocalRandom.current().nextInt(maxCluster));

      // counts reallocation of pixels to clusters
      LongAdder accum = new LongAdder();
      while (true)
      {
         accum.reset();

         ColorCentroid[] centroids = image.pixels.parallelStream().collect(new CentroidCollectorEnhanced(maxCluster));

         // assign centroids
         image.pixels.parallelStream().forEach(p -> {
            int newClusterId = DistanceTools.getNearestCentroidId(p, centroids);
            if(newClusterId != p.centroidId)
            {
               p.centroidId = newClusterId;
               accum.increment();
            }
         });

         // if there are no reallocation
         // the cluster is stable
         if(accum.sum() == 0)
         {
            // reassign the new color to the pixel
            image.pixels.parallelStream().forEach(p -> {
               p.red = (int) centroids[p.centroidId].red;
               p.green = (int) centroids[p.centroidId].green;
               p.blue = (int) centroids[p.centroidId].blue;
            });
            break;
         }
      }
   }
}
