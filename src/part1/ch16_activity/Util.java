package part1.ch16_activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Util
{
   public static List<Integer> readFromFileAsByte4(String filename) throws IOException
   {
      List<Integer> inputData = new ArrayList<>();
      try (InputStream input = new FileInputStream(filename))
      {
         // read buffer (reading an int, 4 bytes)
         byte[] block = new byte[4];  

         int bytesRead = 0;
         while (bytesRead != -1)
         {
            bytesRead = input.read(block);
            if (bytesRead > 0)
            {
               int intValue = fromByteArray(block);
               inputData.add(intValue);
            }
         }
      }
      
      return inputData;
   }
   
   public static void writeToFile(String filename, List<Integer> byte4List) throws IOException
   {
      try (OutputStream output = new FileOutputStream(filename))
      {
         for (Integer value : byte4List)  
         {
            byte[] block = toByteArray(value);
            output.write(block, 0, block.length);
         }
      }
   }
   
   public static byte[] toByteArray(int value)
   {
      return ByteBuffer.allocate(4).putInt(value).array();
   }

   public static int fromByteArray(byte[] bytes)
   {
      return ByteBuffer.wrap(bytes).getInt();
   }
}
