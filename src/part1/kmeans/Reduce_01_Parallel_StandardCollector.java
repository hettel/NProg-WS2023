package part1.kmeans;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import part1.kmeans.util.ColorCentroid;
import part1.kmeans.util.DistanceTools;
import part1.kmeans.util.Image;
import part1.kmeans.util.Pixel;

public class Reduce_01_Parallel_StandardCollector
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

         Map<Integer, List<Pixel>> clusterMapList = image.pixels.parallelStream()
               .collect(Collectors.groupingBy(p -> p.centroidId));

         // calculate centroids
         for(Integer id : clusterMapList.keySet())
         {
            double sumRed = clusterMapList.get(id).stream().mapToDouble(p -> p.red).sum();
            double sumGreen = clusterMapList.get(id).stream().mapToDouble(p -> p.green).sum();
            double sumBlue = clusterMapList.get(id).stream().mapToDouble(p -> p.blue).sum();
            double len = clusterMapList.get(id).size();

            centroids[id] = new ColorCentroid(sumRed / len, sumGreen / len, sumBlue / len);
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
