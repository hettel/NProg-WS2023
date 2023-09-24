package part1.ch09_sterams_spliterator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class Example_02
{
   static class WordSpliterator implements Spliterator<String[]>
   { 
	  private static AtomicInteger counter = new AtomicInteger(); 
	   
      private final int CHUNK_DEFAULT_SIZE = 16*1024;
      private final int chunkSize;
      private final String content;
      
      private int currentPos = 0;
      
      WordSpliterator(String content)
      {
    	 System.out.println( counter.incrementAndGet() );
         this.content = content;   
         this.chunkSize = CHUNK_DEFAULT_SIZE;
      }
      
      WordSpliterator(String content, int chunkSize)
      {
         this.content = content;
         this.chunkSize = chunkSize;
      }

      @Override
      public boolean tryAdvance(Consumer<? super String[]> action)
      {        
         if( currentPos >= content.length() ) return false;
         
         String subContent;
         if( currentPos + chunkSize > content.length() )
         {
            subContent = content.substring(currentPos, content.length());
            currentPos = content.length(); 
         }
         else
         {
            int padding = 0;
            for(int i=currentPos + chunkSize; i < content.length(); i++, padding++ )
            {
               char c = content.charAt(i);
               if( Character.isWhitespace(c) )
               {
                  break;
               }
            }
            
            subContent = content.substring(currentPos, currentPos + chunkSize + padding);
            currentPos += (chunkSize + padding);
         }
         
         String words[] = subContent.split("\\s+");
         action.accept( words );
        
         return true;
      }

      @Override
      public Spliterator<String[]> trySplit()
      {
         int currentSize = content.length() - currentPos;
         
         if( currentSize < 2*chunkSize ) return null;
         
         int splitPos = currentSize/2 + currentPos;
         int padding = 0;
         for(int i=0; i < content.length(); i++, padding++ )
         {
            char c = content.charAt(splitPos +i);
            if( Character.isWhitespace(c) )
            {
               break;
            }
         }
         
         splitPos += padding;
         Spliterator<String[]> newSpliterator = new WordSpliterator( content.substring(currentPos, splitPos) );
         currentPos = splitPos;
         
         return newSpliterator;
      }

      @Override
      public long estimateSize()
      {
         return (content.length() - currentPos)/chunkSize;
      }

      @Override
      public int characteristics()
      {
         return SIZED + SUBSIZED + NONNULL + IMMUTABLE;
      }
      
   }
   
   
   public static void main(String[] args) throws IOException
   {     
      Path path = Paths.get( "Homer-Odyssey-UTF8-Coding.txt");
      String content = Files.readString(path, Charset.forName("UTF8"));
      
     
      long start = System.currentTimeMillis();
      WordSpliterator spliterator = new WordSpliterator(content); //, content.length()/48);
      
      Map<Integer, Long> map = 
      StreamSupport.stream(spliterator, true)
                   .flatMap( array -> Arrays.stream(array))
                   .map( String::trim )
                   .map( str -> str.replaceAll("[\",!?.';:]", "")) 
                   .filter( str -> str.matches("[A-Za-z]+") )
                   .collect( Collectors.groupingBy( String::length,
                             Collectors.counting()) );
      
      long end = System.currentTimeMillis();
      System.out.println("Duration: " + (end-start) + "[ms]");
      
      map.entrySet().forEach( entry -> {
         System.out.println( entry.getKey() + " -> " + entry.getValue() );
      });
   }

}
