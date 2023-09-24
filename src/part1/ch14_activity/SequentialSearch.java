package part1.ch14_activity;

public class SequentialSearch
{
   public static void main(String[] args)
   {
      System.out.println("Start searching");
      long startTime = System.currentTimeMillis();

      long counter = 0;
      String str = "";
      while (true)
      {
         str = Util.getRandomString(100);
         int hashValue = str.hashCode();
         counter++;

         if (0 < hashValue && hashValue < 50)
         {
            break;
         }
      }
      
      long endTime = System.currentTimeMillis();
      System.out.println("Found hash value : " + str.hashCode() );
      System.out.println("Number of tries  : " + counter);

      System.out.println("Time elapsed     : " + (endTime - startTime) + " [ms]");
   }
}
