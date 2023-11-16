package playground.completion_service;

import java.util.concurrent.ThreadLocalRandom;

public class Util
{
  static private final char[] CharSet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  static private final int CharSet_Len = CharSet.length;

  static public String getRandomString(final int length)
  {
    StringBuilder strBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++)
    {
      int index = ThreadLocalRandom.current().nextInt(CharSet_Len);
      strBuilder.append(CharSet[index]);
    }

    return strBuilder.toString();
  }
}
