package klausur2020.aufgabe1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Person_rwLock
{
  private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
  private final Lock wLock = rwLock.writeLock();

  private final Lock rLock = rwLock.readLock();


  private String name;
  private int alter;

  public Person_rwLock()
  {
    super();
  }

  public String getName()
  {
    rLock.lock();
    try{
      return this.name;
    }
    finally {
      rLock.unlock();
    }
  }

  public int getAlter()
  {
    rLock.lock();
    try{
      return this.alter;
    }
    finally {
      rLock.unlock();
    }
  }

  public void setName(String newName)
  {
    wLock.lock();
    try{
      this.name = newName;
    }
    finally {
      wLock.unlock();
    }
  }

  public void setAlter(int newAlter)
  {
    wLock.lock();
    try{
      this.alter = newAlter;
    }
    finally {
      wLock.unlock();
    }
  }
}
