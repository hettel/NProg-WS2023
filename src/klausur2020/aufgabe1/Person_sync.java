package klausur2020.aufgabe1;

public class Person_sync
{
  private String name;
  private int alter;

  public Person_sync()
  {
    super();
  }

  public synchronized String getName()
  {
    return this.name;
  }

  public synchronized int getAlter()
  {
    return this.alter;
  }

  public synchronized void setName(String newName)
  {
    this.name = newName;
  }

  public synchronized void setAlter(int newAlter)
  {
    this.alter = newAlter;
  }
}
