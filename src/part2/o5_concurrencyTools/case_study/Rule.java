package part2.o5_concurrencyTools.case_study;

public final class Rule
{
  private Rule() { }
  
  public static FieldValue get(FieldValue c1, FieldValue c2, FieldValue c3)
  {
    if (c1 == FieldValue.B && c2 == FieldValue.B && c3 == FieldValue.B)
      return FieldValue.W;
    if (c1 == FieldValue.B && c2 == FieldValue.B && c3 == FieldValue.W)
      return FieldValue.B;
    if (c1 == FieldValue.B && c2 == FieldValue.W && c3 == FieldValue.B)
      return FieldValue.B;
    if (c1 == FieldValue.B && c2 == FieldValue.W && c3 == FieldValue.W)
      return FieldValue.W;
    if (c1 == FieldValue.W && c2 == FieldValue.B && c3 == FieldValue.B)
      return FieldValue.B;
    if (c1 == FieldValue.W && c2 == FieldValue.B && c3 == FieldValue.W)
      return FieldValue.B;
    if (c1 == FieldValue.W && c2 == FieldValue.W && c3 == FieldValue.B)
      return FieldValue.B;
    if (c1 == FieldValue.W && c2 == FieldValue.W && c3 == FieldValue.W)
      return FieldValue.W;

    throw new IllegalArgumentException();
  }
}
