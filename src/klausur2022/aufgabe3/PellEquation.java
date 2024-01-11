package klausur2022.aufgabe3;

import java.util.ArrayList;
import java.util.List;

public class PellEquation
{

  public static void main(String[] args)
  {
    List<int[]> loesungen = new ArrayList<int[]>();
    
    final int range = 10_000;
    for(int x=-range; x <= range; x++ )
    {
      for(int y=-range; y <= range; y++ )
      {
       
        if( gleichung(x, y) == 1 )
        {
          loesungen.add( new int[]{x,y});
        }
      }
    }

    for( int[] p : loesungen )
    {
      System.out.println(" x = " + p[0] + ", y = " + p[1]);
    }
    
    System.out.println("Anzahl: " + loesungen.size() );
  }
  
  public static int gleichung(int x, int y)
  {
    return x*x - 2*y*y;
  }

}
