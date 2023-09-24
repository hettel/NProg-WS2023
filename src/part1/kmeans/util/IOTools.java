package part1.kmeans.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class IOTools
{
   public static Image load(String filename) throws IOException
   {
      File file = new File(filename);
      BufferedImage image = ImageIO.read(file);

      int height = image.getHeight();
      int width = image.getWidth();

      List<Pixel> pixels = new ArrayList<>();
      for(int y = 0; y < height; y++)
      {
         for(int x = 0; x < width; x++)
         {
            int color = image.getRGB(x, y);
            int alpha = (color & 0xff000000) >> 24;
            int red = (color & 0x00ff0000) >> 16;
            int green = (color & 0x0000ff00) >> 8;
            int blue = (color & 0x000000ff);

            Pixel pixel = new Pixel(x, y, alpha, red, green, blue);
            pixels.add(pixel);
         }
      }

      return new Image(width, height, pixels);
   }

   public static Image load_with_streams(String filename) throws IOException
   {
      File file = new File(filename);
      BufferedImage image = ImageIO.read(file);

      int height = image.getHeight();
      int width = image.getWidth();

      List<Pixel> pixels = 
      IntStream.range(0, height).mapToObj( y -> 
      {
         return  IntStream.range(0, width).mapToObj( x ->  
         {
            int color = image.getRGB(x, y);
            int alpha = (color & 0xff000000) >> 24;
            int red = (color & 0x00ff0000) >> 16;
            int green = (color & 0x0000ff00) >> 8;
            int blue = (color & 0x000000ff);

            return new Pixel(x, y, alpha, red, green, blue);
         } ).toList();
         
      } ).flatMap( list -> list.stream() ).toList();

      return new Image(width, height, pixels);
   }

   public static void store(Image image, String filename) throws IOException
   {
      int width = image.width;
      int height = image.height;

      BufferedImage imageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      List<Pixel> pixels = image.pixels;
      for(Pixel pixel : pixels)
      {
         int rgb = pixel.getRgbColor();
         imageOut.setRGB(pixel.x, pixel.y, rgb);
      }

      File file = new File(filename);
      ImageIO.write(imageOut, "png", file);
   }
}
