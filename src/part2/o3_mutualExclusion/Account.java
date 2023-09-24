package part2.o3_mutualExclusion;

public class Account
{
  private final int id;  //unique
  private int balance = 0;
  
  public Account(int id) 
  { 
    this.id = id;
  }
  
  public synchronized void deposit(int sum)
  {
    this.balance += sum;
  }
  
  public synchronized int getBalance()
  {
    return this.balance;
  }
  
  public int getId()
  {
    return this.id;
  }
}
