package part2.o2_executors.case_study;

public class SequentialSearch
{
  public static void main(String[] args)
  {
    System.out.println("Start searching");
    long startTime = System.currentTimeMillis();
     
    long counter = 0;
    while(true)
    {
        String str = Util.getRandomString(100);
        int hashValue = str.hashCode();
        counter++;
        
        if( 0 < hashValue && hashValue < 500 )
        {
          System.out.println("Found " + hashValue );
          System.out.println("Number of tries " + counter );
          long endTime = System.currentTimeMillis();
          System.out.println("Duration " + (endTime - startTime) + " [ms]");
          break;
        }
    }
  }
}
