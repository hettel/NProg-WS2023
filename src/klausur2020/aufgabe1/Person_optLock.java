package klausur2020.aufgabe1;

import java.util.concurrent.locks.StampedLock;

public class Person_optLock
{
  private final StampedLock lock = new StampedLock();
  private String name;
  private int alter;

  public Person_optLock()
  {
    super();
  }

  public String getName()
  {
    long stamp = lock.tryReadLock();

    String tmp = this.name;

    if( lock.validate(stamp) )
    {
      return tmp;
    }
    else
    {
      long readStamp = lock.readLock();
      try{
        return this.name;
      }
      finally {
        lock.unlockRead( readStamp );
      }
    }
  }

  public int getAlter()
  {
    return this.alter;
  }

  public void setName(String newName)
  {
      long stamp = lock.writeLock();
      try{
        this.name = newName;
      }
      finally {
        lock.unlockWrite( stamp );
      }
  }

  public void setAlter(int newAlter)
  {
    long stamp = lock.writeLock();
    try{
      this.alter = newAlter;
    }
    finally {
      lock.unlockWrite( stamp );
    }
  }
}
