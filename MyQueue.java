interface Queue<T>{
    void enqueue(T obj);
    T dequeue();
    T front();
}

//T must extend Comparable <? super T> to be compatible with DoublyLinkedList
class MyQueue<T extends Comparable <? super T>> 
    extends DoublyLinkedList<T> implements Queue<T> {
    //enqueue to tail of DLL
    public void enqueue(T obj){
        prepend(obj);
    }
    //dequeue from tail of DLL
    public T dequeue(){
        if (tail==null)return null;
        T tmp = tail.obj;
        //remove tail node
        removeBefore(null);
        return tmp;
    }
    //show object at tail of DLL
    public T front(){
        if (tail==null)return null;
        return tail.obj;
    }
}
