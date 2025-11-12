//extend Comparable <? super T> to ensure that T is comparable so we can use compareTo in sort()
class DoublyLinkedList<T extends Comparable <? super T>>{
    //node subclass
    class Node{
        T obj = null;
        Node nxt = null;
        Node prv = null;

        Node(T obj){
            this.obj=obj;
        }
        Node(T obj, Node nxt, Node prv){
            this.obj=obj;
            this.nxt=nxt;
            this.prv=prv;
        }

        @Override
        public String toString(){
            return obj.toString();
        }
    }

    Node head=null;
    Node tail=null;

    DoublyLinkedList() {}
    DoublyLinkedList(T obj){
        append(obj);
    }

    void append(T obj){
        Node n = new Node(obj);
        
        //empty
        if(head==null){
            head=n;
            tail=n;
            return;
        }

        //tail.nxt should be null, replace with n
        tail.nxt = n;
        n.prv=tail;
        tail=n;
    }

    void prepend(T obj){
        Node n = new Node(obj);

        //check empty
        if (head==null) {
            head=n;
            tail=n;
            return;
        }

        n.nxt=head;
        head.prv=n;
        head=n;
    }

    //return true if success, false if fail
    boolean insertAfter(T target, T val){
        Node n = new Node(val);

        //if adding at start of list
        if (target==null){
            prepend(val);
            return true;
        }

        //if empty and not prepend
        if (head==null){
            return false;
        }

        Node curNode=head;
        while(curNode!=null){
            if (curNode.obj.equals(target)){
                //found correct spot
                n.nxt=curNode.nxt;
                n.prv=curNode;
                //ensure curNode.nxt is a node
                if (curNode.nxt != null){
                    curNode.nxt.prv = n;
                }
                //this is after tail
                else {
                    tail=n;
                }
                curNode.nxt=n;
                return true;
            }
            curNode=curNode.nxt;
        }
        //target not found
        return false;
    }

    //return true if success, false if fail
    boolean insertBefore(T target, T val){
        Node n = new Node(val);

        //if adding at end of list
        if (target==null){
            append(val);
            return true;
        }

        //if empty and not prepend
        if (head==null){
            return false;
        }

        Node curNode=tail;
        while(curNode!=null){
            if (curNode.obj.equals(target)){
                //found correct spot
                n.prv=curNode.prv;
                n.nxt=curNode;
                //ensure curNode.nxt is a node
                if (curNode.prv!= null){
                    curNode.prv.nxt= n;
                }
                //this is before head
                else {
                    head=n;
                }
                curNode.prv=n;
                return true;
            }
            curNode=curNode.prv;
        }
        //target not found
        return false;
    }

    void remove(T target){
        if (head==null) return;

        Node curNode=head;

        //in the case of only one node
        if (head.nxt == null && head.obj.equals(target)) {
            head=null;
            tail=null;
            curNode=null;
        }

        while (curNode!=null){
            if (curNode.obj.equals(target)){
                //point around node
                if (curNode.prv!=null){
                    curNode.prv.nxt=curNode.nxt;
                } else if (curNode==head) {
                    head=curNode.nxt;
                }
                if (curNode.nxt!=null){
                    curNode.nxt.prv=curNode.prv;
                } else if (curNode==tail) {
                    tail=curNode.prv;
                }
                return;
            }
            curNode=curNode.nxt;
        }
    }
    
    //return true if success, false if not
    boolean removeAfter(T target){
        //check empty
        if (head==null) return false;

        Node curNode = head;

        //remove first value
        if (target==null){
            if (head.nxt!=null) {
                head=curNode.nxt;
                head.prv=null;
            }
            else{
                //list only had 1 value
                head=null;
                tail=null;
            }
        }

        while (curNode!=null) {
            if (curNode.obj.equals(target)) {
                curNode=curNode.nxt;
                if (curNode==null) {
                    //was tail
                    return false;
                }
                //remove node
                if (curNode.nxt != null) {
                    curNode.prv.nxt=curNode.nxt;
                    curNode.nxt.prv = curNode.prv;
                } else if (curNode==tail) {
                    tail=curNode.prv;
                    curNode.prv.nxt=null;
                }
                return true;
            }
            curNode=curNode.nxt;
        }
        // target wasn't found
        return false;
    }

    //return true if success, false if not
    boolean removeBefore(T target){
        //check empty
        if (head==null) return false;

        Node curNode = tail;

        //remove first value
        if (target==null){
            if (curNode.prv!=null) {
                tail=curNode.prv;
                tail.nxt=null;
            }
            else{
                //list only had 1 value
                head=null;
                tail=null;
            }
        }

        while (curNode!=null) {
            if (curNode.obj.equals(target)) {
                curNode=curNode.prv;
                if (curNode==null) {
                    //was head
                    return false;
                }
                //remvoe node
                if (curNode.prv!=null) {
                    curNode.nxt.prv= curNode.prv;
                    curNode.prv.nxt=curNode.nxt;
                } else if (curNode==head) {
                    head=curNode.nxt;
                    curNode.nxt.prv=null;
                }
                return true;
            }
            curNode=curNode.prv;
        }
        // target wasn't found
        return false;
    }

    //return true if success, false if fail
    boolean search(T value){
        //check empty
        if (head == null) return false;

        Node curNode = head;
        while (curNode!=null) {
            if (curNode.obj.equals(value)) {
                return true;
            }
            curNode=curNode.nxt;
        }
        return false;
    }

    //insertion sort
    void sort(){
        //check empty
        if (head==null) return;
        //check if one val
        if (head.nxt == null) return;

        Node curNode=head;
        while (curNode!=null) {
            Node nxtNode=curNode.nxt;
            Node tmpNode=curNode;
            while ((tmpNode.prv!=null) && ((curNode.obj).compareTo(tmpNode.prv.obj) <0)) {
                tmpNode=tmpNode.prv;
            }
            if (tmpNode!=curNode) {
                //ensure head and tail are updated correctly
                if (tmpNode.equals(head)) {
                    head=curNode;
                }
                if (curNode.equals(tail)) {
                    tail=tail.prv;
                }

                //fill curNode's gap
                if (curNode.prv !=null) {
                    curNode.prv.nxt=curNode.nxt;
                }
                if (curNode.nxt != null){
                    curNode.nxt.prv=curNode.prv;
                }

                //insert curNode before tmpNode
                curNode.nxt=tmpNode;
                curNode.prv=tmpNode.prv;
                if (tmpNode.prv !=null) {
                    tmpNode.prv.nxt=curNode;
                }
                tmpNode.prv=curNode;
            }
            curNode=nxtNode;
        }
    }

    @Override
    public String toString(){
        //check empty
        if (head==null) {
            return "null";
        }

        Node curNode=head;
        String out = curNode.obj.toString();
        while (curNode.nxt!=null) {
            curNode=curNode.nxt;
            out+="->"+curNode.obj.toString();
        }
        return out;
    }

    String stringReverse(){
        //check empty
        if (head==null) {
            return "null";
        }

        Node curNode=tail;
        String out=curNode.obj.toString();
        while(curNode.prv!=null) {
            curNode=curNode.prv;
            out+="<-"+curNode.obj.toString();
        }
        return out;
    }
}
