package klausur2020.aufgabe1;

public class Person
{
  private String name;
  private int alter;

  public Person()
  {
    super();
  }

  public String getName()
  {
    return this.name;
  }

  public int getAlter()
  {
    return this.alter;
  }

  public void setName(String newName)
  {
    this.name = newName;
  }

  public void setAlter(int newAlter)
  {
    this.alter = newAlter;
  }
}
