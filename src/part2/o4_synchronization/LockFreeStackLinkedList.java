package part2.o4_synchronization;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStackLinkedList<T> implements LockFreeStack<T>
{
  private static class Node<T>
  {
    private final T item;
    public Node<T> next;
    
    private Node(T item)
    {
      this.item = item;
    }
  }
  
  AtomicReference<Node<T>> head = new AtomicReference<>();


  @Override
  public void push(T item)
  {
    Node<T> newHead = new Node<>(item);
    Node<T> oldHead;
    do {
      oldHead = this.head.get();
      newHead.next = oldHead;
    }while( head.compareAndSet(oldHead, newHead) == false );
  }

  @Override
  public T pop()
  {
    Node<T> newHead;
    Node<T> oldHead;
    
    do
    {
      oldHead = this.head.get();
      if( oldHead == null )
        return null;
      newHead = oldHead.next;
    }while( head.compareAndSet(oldHead, newHead) == false );
    
    return oldHead.item;
  }

}
