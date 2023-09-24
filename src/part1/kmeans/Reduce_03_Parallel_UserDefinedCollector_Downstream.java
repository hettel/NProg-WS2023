package part1.kmeans;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import part1.kmeans.util.ColorCentroid;
import part1.kmeans.util.DistanceTools;
import part1.kmeans.util.Image;

public class Reduce_03_Parallel_UserDefinedCollector_Downstream
{
   static void reduce(Image image, final int maxCluster)
   {
      // random assignment of color pixels to a cluster
      image.pixels.parallelStream().forEach(p -> p.centroidId = ThreadLocalRandom.current().nextInt(maxCluster));

      ColorCentroid[] centroids = new ColorCentroid[maxCluster];

      // counts reallocations
      LongAdder accum = new LongAdder();
      while (true)
      {
         accum.reset();

         Map<Integer, ColorCentroid> clusterMap = image.pixels.parallelStream()
               .collect(Collectors.groupingBy(p -> p.centroidId, new CentroidCollector()));

         // calculate centroids
         for(Integer id : clusterMap.keySet())
         {
            centroids[id] = clusterMap.get(id);
         }

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
            // reassign the new color (centroid) to the pixel
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
