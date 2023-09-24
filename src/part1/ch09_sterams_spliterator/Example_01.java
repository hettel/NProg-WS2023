package part1.ch09_sterams_spliterator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Example_01
{

   public static void main(String[] args) throws IOException
   {     
      Path path = Paths.get( "Homer-Odyssey-UTF8-Coding.txt");
      String content = Files.readString(path, Charset.forName("UTF8"));
      
     
      long start = System.currentTimeMillis();
      String words[] = content.split("\\s+");
      Map<Integer, Long> map = Arrays.stream(words) //.parallel()
                                             .map( String::trim )
                                             .map( str -> str.replaceAll("[\",!?.';:]", "")) 
                                             .filter( str -> str.matches("[A-Za-z]+") )
                                             .collect( Collectors.groupingBy( String::length, 
                                                       Collectors.counting() ) );
      long end = System.currentTimeMillis();
      System.out.println("Duration: " + (end-start) + "[ms]");
      
      map.entrySet().forEach( entry -> {
         System.out.println( entry.getKey() + " -> " + entry.getValue() );
      });
   }

}
