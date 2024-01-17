package klausur2020.aufgabe4;

import java.util.Date;

public class Main
{

  public static void main(String[] args)
  {
    LongProducer    longProd = new LongProducer();
    DateProducer    dateProd = new DateProducer();
    OutputConsumer  outCons  = new OutputConsumer();

    System.out.println("start");
    for( int i=1; i <=10; i++)
    {
      long time = longProd.getNext();
      Date date = dateProd.getDate(time);
      outCons.printDate(date);
    }
    System.out.println("done");
  }

}
