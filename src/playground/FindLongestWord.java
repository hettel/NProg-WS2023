package playground;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;


public class FindLongestWord
{

   public static void main(String[] args) throws IOException
   {
      Path path = Paths.get("Homer-Odyssey-UTF8-Coding.txt");

      String content = Files.readString(path, UTF_8);
      String[] words = content.split("\\s+");

      System.out.println("Number of words " + words.length);
   }

}
