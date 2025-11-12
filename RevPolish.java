class RevPolish{
    static MyQueue<String> toRevPolish(String inString) throws NonExistentOperator{
        char[] in = inString.toCharArray();

        MyQueue<String> out = new MyQueue<String>();
        MyStack<Character> op = new MyStack<Character>();

        int numStart = 0;
        int curNum = 0;
        boolean wasInt = false;

        String fin;
        
        while (curNum<in.length){
            System.out.println(in[curNum]+"\t"+numStart+"\t"+curNum);
            if (isInt(in[curNum])){
                //it's a number
                curNum++; 
                wasInt=true;
            } else {
                //parse last number
                if (wasInt){
                    out.enqueue(parseInt(in, numStart, curNum));
                    wasInt=false;
                }

                //parse operator
                char opr;
                try{
                    opr = parseOpr(in, curNum);
                } catch (NonExistentOperator e){
                    throw e;
                }

                //handle operator
                handleOpr(opr, op, out); 

                curNum++;
                numStart=curNum;
            }
            System.out.println("\t"+numStart+"\t"+curNum);
        }

        if (wasInt) {
            //ensure last num is caught
            out.enqueue(parseInt(in, numStart, curNum));
            //ensure all operators are popped
            while (op.head!=null){
                out.enqueue(op.pop()+"");
            }
        }
        /* is this needed?
         else {
            char opr;
            try {
                opr = parseOpr(in, numStart); 
            } catch (NonExistentOperator e) {
                throw e;
            }
            handleOpr(opr, op, out);
        }*/

        return out;
        //return toString(out);
    }

    private static String parseInt(char[] in, int start, int end){
        String s = new String();
        while (start<end){
            s+=in[start++];
        }
        return s;
    }

    private static char parseOpr(char[] in, int idx)
            throws NonExistentOperator{
        char op;
        try {
            op = getOperator(in[idx]);
        } catch (NonExistentOperator e) {
            throw e;
        }

        return op;
    }

    private static void handleOpr(char opr, MyStack<Character> stack,
            MyQueue<String> queue){
        if (opr!=')'){
            //push if not ')'
            stack.push(opr);
        } else {
            //pop everything before '(' into queue
            while (stack.peek() != '(') {
                queue.enqueue(stack.pop()+"");
            }
            //next val should be '('
            stack.pop();
        }
    }

    private static boolean isInt(char c){
        if ('0'<=c && c<='9') return true;
        return false;
    }

    private static char getOperator(char c) throws NonExistentOperator{
        switch(c){
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            case '(':
            case ')':
                return c;
            default:
                throw new NonExistentOperator(c+" is not a valid operator.");
        }
    }

    public static String queueToString(MyQueue<String> queue){
        String s=queue.dequeue();
        while(queue.head!=null){
            s+=","+queue.dequeue();
        }
        return s;
    }
}
