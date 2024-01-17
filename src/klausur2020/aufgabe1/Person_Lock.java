package klausur2020.aufgabe1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Person_Lock
{
  private final Lock lock = new ReentrantLock(true);
  private String name;
  private int alter;

  public Person_Lock()
  {
    super();
  }

  public String getName()
  {
    lock.lock();
    try{
      return this.name;
    }
    finally {
      lock.unlock();
    }
  }

  public int getAlter()
  {
    lock.lock();
    try{
      return this.alter;
    }
    finally {
      lock.unlock();
    }
  }

  public void setName(String newName)
  {
    lock.lock();
    try{
      this.name = newName;
    }
    finally {
      lock.unlock();
    }
  }

  public void setAlter(int newAlter)
  {
    lock.lock();
    try{
      this.alter = newAlter;
    }
    finally {
      lock.unlock();
    }
  }
}
