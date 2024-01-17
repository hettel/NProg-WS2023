package klausur2020.aufgabe4;

import java.text.DateFormat;
import java.util.Date;

public class OutputConsumer
{
  private DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

  public void printDate(Date date)
  {
    System.out.println( formatter.format(date) );
  }
}
