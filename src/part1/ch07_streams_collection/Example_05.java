package part1.ch07_streams_collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Example_05
{

   public static void main(String[] args)
   {
      // Create a list with random strings
      List<String> strings = new ArrayList<>();
      for(int i=0; i < 1_000; i++)
      {
        strings.add( getRandomString(10) );
      }
      
      
      // Schritt 1: Erzeugung von Map<String, List<String> >
      Map<String, List<String> > groupByFirstLetter =
                   strings.stream()
                          .collect( Collectors.groupingBy( str -> (String) str.subSequence(0, 1)));
      
      groupByFirstLetter.entrySet()
                        .forEach( e -> System.out.println( e.getKey() + " -> " + e.getValue() ));
      
      System.out.println("-----------------------------------------");
      
      // Schritt 2: Erzeuge Map<String, Long>
      Map<String, Long> mapStrCount = new HashMap<>();
      for(String key : groupByFirstLetter.keySet() )
      {
          List<String> list = groupByFirstLetter.get(key);
          mapStrCount.put(key, (long) list.size());
      }
      mapStrCount.entrySet()
                 .forEach( e -> System.out.println( e.getKey() + " -> " + e.getValue() ));
        
      System.out.println("-----------------------------------------");
        
      // Using a downstream collector to count the strings
      Map<String, Long> stringMapCount = strings.stream().parallel()
                    .collect( Collectors.groupingBy( str -> str.substring(0, 1), 
                              Collectors.counting() ) );

      for( Entry<String, Long> entry : stringMapCount.entrySet() )
      {
         System.out.println( entry.getKey() + " -> " + entry.getValue() );
      }
   }
   
   private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

   public static String getRandomString(int len)
   {
     StringBuilder strBuilder = new StringBuilder();
     for (int i = 0; i < len; i++)
     {
       int idx = ThreadLocalRandom.current().nextInt(ALPHABET.length);
       strBuilder.append(ALPHABET[idx]);
     }

     return strBuilder.toString();
   }

}
