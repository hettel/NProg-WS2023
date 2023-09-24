package part2.o5_concurrencyTools.case_study;

public enum FieldValue
{
  B('x'), W('-');
  
  private final char c;
  
  FieldValue(char c)
  {
    this.c =  c;
  }
  

  @Override
  public String toString()
  {
    return String.valueOf(c);
  }
}
