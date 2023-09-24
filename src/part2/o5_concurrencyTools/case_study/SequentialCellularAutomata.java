package part2.o5_concurrencyTools.case_study;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class SequentialCellularAutomata
{
  public static void main(String[] args) throws InterruptedException
  {
    final int SIZE = 100;
    FieldValue[] field = new FieldValue[SIZE];
    FieldValue[] shadowField = new FieldValue[SIZE];
    Arrays.fill(field, FieldValue.W);
    Arrays.fill(shadowField, FieldValue.W);

    Random rd = new Random();
    for (int i = 0; i < 20; i++)
    {
      field[rd.nextInt(field.length)] = FieldValue.B;
    }

    print(field);

    for (int k = 0; k < 300; k++)
    {
      for (int i = 0; i < field.length; i++)
      {
        int leftIndex = (i - 1 + field.length)%field.length;
        int rightIndex = (i + 1)%field.length;
        
        FieldValue value = Rule.get(field[leftIndex], field[i], field[rightIndex]);
        shadowField[i] = value;
      }

      FieldValue[] tmp = shadowField;
      shadowField = field;
      field = tmp;

      print(field);
      
      TimeUnit.MILLISECONDS.sleep(300);
    }
  }

  private static void print(FieldValue[] array)
  {
    for (FieldValue c : array)
    {
      System.out.print(c);
    }
    System.out.println();
  }
}
