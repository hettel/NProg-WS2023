package playground;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


public class CollectionExamples
{

   public static void main(String[] args) throws IOException
   {
      Path path = Paths.get("Homer-Odyssey-UTF8-Coding.txt");

      String content = Files.readString(path, UTF_8);
      content = content.replaceAll("[\",;!?\\.]"," ");
      String[] words = content.split("\\s+");
      System.out.println("Number of words " + words.length);


      List<String> list1 = Arrays.stream(words).filter( s -> s.length() == 15 ).toList();
      System.out.println( list1.size() );
      System.out.println();


      List<String> list2 = Arrays.stream(words).filter( s -> s.length() == 15 ).collect( Collectors.toList() );
      System.out.println( list2.size() );
      System.out.println();



      List<String> list3 = Arrays.stream(words)
                                 .filter( s -> s.length() == 15 )
                                 .collect( () -> new LinkedList<>(),(list, s) -> list.add(s) , (l1, l2) -> l1.addAll(l2) );
      System.out.println( list3.size() );
      System.out.println();


      List<String> list4 = Arrays.stream(words)
              .filter( s -> s.length() == 15 )
              .collect( ArrayList::new, ArrayList::add, ArrayList::addAll );
      System.out.println( list4.size() );
      System.out.println();



      Map<Integer,List<String>> map1 = Arrays.stream(words).collect( Collectors.groupingBy( String::length ) );
      for( Map.Entry<Integer,List<String>> entry : map1.entrySet() )
      {
         System.out.println( entry.getKey() + " : " + entry.getValue());
      }
      System.out.println();


      Map<Integer,Long> map2 = Arrays.stream(words).collect( Collectors.groupingBy( s -> s.length(), Collectors.counting() ) );
      for( Map.Entry<Integer,Long> entry : map2.entrySet() )
      {
         System.out.println( entry.getKey() + " : " + entry.getValue() );
      }
   }

}
