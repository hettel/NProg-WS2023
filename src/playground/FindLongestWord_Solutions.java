package playground;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;


public class FindLongestWord_Solutions
{

   public static void main(String[] args) throws IOException
   {
      Path path = Paths.get("Homer-Odyssey-UTF8-Coding.txt");

      String content = Files.readString(path, UTF_8);
      content = content.replaceAll("[\",;!?\\.]"," ");
      String[] words = content.split("[\\s+]");

      System.out.println("Number of words " + words.length);

      // 1. Anzahl der Worte mit Länge 15
      long anz1 = Arrays.stream( words )
                        .filter( s -> s.length() == 15 )
                        .count();
      System.out.println("Anz1 " + anz1);

      // 2. Anzahl der der Buchstaben des längsten Wortes
      long anz2a = Arrays.stream( words )
                         .mapToInt( s -> s.length() )
                         .max().getAsInt();
      System.out.println("Anz2a " + anz2a);

      int anz2b = Arrays.stream( words )
                        .reduce( (a,b) -> a.length() < b.length() ? b : a ).get().length();
      System.out.println("Anz2b " + anz2b);

      // 3. Längstes Wort
      String str1 = Arrays.stream( words )
              .reduce( (a,b) -> a.length() < b.length() ? b : a ).get();
      System.out.println("Str 1 " + str1 );

      String str2 = Arrays.stream( words )
                         .parallel()
                         .reduce( (a,b) -> a.length() < b.length() ? b : a ).get();
      System.out.println("Str 2 " + str2 );

      // 4. Anzahl der Wörter mit drei Vokalen
      long vowal3 = Arrays.stream( words )
                          .filter( s -> countVowals(s) == 3 )
                          .count();
      System.out.println("Anzahl " + vowal3);
   }


   private static final String vowals = "aeiouAEIOU";
   private static int countVowals(String word)
   {
      int count = 0;
      for( char c : word.toCharArray() )
      {
         if( vowals.indexOf(c) >= 0 )
            count++;
      }

      return count;
   }

}
