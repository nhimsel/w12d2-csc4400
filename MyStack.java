interface Stack<T>{
    void push(T obj);
    T pop();
    T peek();
}

//T must extend Comparable <? super T> to be compatible with DoublyLinkedList
class MyStack<T extends Comparable <? super T>> 
    extends DoublyLinkedList<T> implements Stack<T> {
    //push to tail of DLL
    public void push(T obj){
        append(obj);
    }
    //pop from tail of DLL
    public T pop(){
        if (tail==null)return null;
        T tmp = tail.obj;
        //remove tail node
        removeBefore(null);
        return tmp;
    }
    //peek at tail of DLL
    public T peek(){
        if (tail==null)return null;
        return tail.obj;
    }
}
