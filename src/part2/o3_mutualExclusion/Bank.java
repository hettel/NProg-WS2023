package part2.o3_mutualExclusion;

import java.util.HashMap;
import java.util.Map;

public class Bank
{
  private Map<Integer, Account> accounts = new HashMap<>();

  public Bank()
  {

  }

  public void transfer(int from, int to, int sum)
  {
    Account fromAccount = this.accounts.get(from);
    Account toAccount = this.accounts.get(to);

    Account firstLock, secondLock;
    if (fromAccount.getId() > toAccount.getId())
    {
      firstLock = fromAccount;
      secondLock = toAccount;
    }
    else
    {
      firstLock = toAccount;
      secondLock = fromAccount;
    }

    synchronized (firstLock)
    {
      synchronized (secondLock)
      {
        if (fromAccount.getBalance() >= sum)
        {
          fromAccount.deposit(-sum);
          toAccount.deposit(sum);
        }
      }
    }

  }
}
