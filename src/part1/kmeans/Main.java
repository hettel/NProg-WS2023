package part1.kmeans;

import part1.kmeans.util.IOTools;
import part1.kmeans.util.Image;

public class Main
{
   public static void main(String[] args) throws Exception
   {
      final String inputFileName = "Schmetterling.jpg";
      final String outputFileName = "reduced_" + inputFileName;
      final int k = 5;
      
      System.out.println("Color k-Means");
      System.out.println("Load image: " + inputFileName);
      long startLoading = System.currentTimeMillis();
      Image image = IOTools.load(inputFileName);
      long endLoading = System.currentTimeMillis();
      System.out.println("Loading image : " + (endLoading - startLoading) + "[ms]");
      System.out.println("Num of pixels : " + image.getPixelCount());
      System.out.println("Num of colors : " + image.getColorCount());
      
      
      
      System.out.println("Reduce image (k = " + k + ")");
      long startTime = System.currentTimeMillis();
      //Reduce_00_Sequential.reduce(image, k);
      //Reduce_01_Parallel_StandardCollector.reduce(image, k);
      //Reduce_02_Parallel_UserDefinedCollector.reduce(image, k);
      //Reduce_03_Parallel_UserDefinedCollector_Downstream.reduce(image, k);
      Reduce_04_Parallel_UserDefinedCollector_Enhanced.reduce(image, k);
      long endTime = System.currentTimeMillis();
      System.out.println("Duration: " + (endTime - startTime) + "[ms]");
      
      
      
      System.out.println("Store image: " + outputFileName);
      IOTools.store(image, outputFileName);
      System.out.println("done");
   }
}
