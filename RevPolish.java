class RevPolish{
    static MyQueue<String> toRevPolish(String inString) 
            throws NonExistentOperatorException, UnbalancedParentheseesException{
        char[] in = inString.toCharArray();

        MyQueue<String> out = new MyQueue<String>();
        MyStack<Character> op = new MyStack<Character>();

        int numStart = 0;
        int curNum = 0;
        boolean wasInt = false;

        String fin;
        
        while (curNum<in.length){
            //System.out.println(in[curNum]+"\t"+numStart+"\t"+curNum);
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
                } catch (NonExistentOperatorException e){
                    throw e;
                }

                //handle operator
                try {
                    handleOpr(opr, op, out); 
                } catch (UnbalancedParentheseesException e){
                    throw e;
                }

                curNum++;
                numStart=curNum;
            }
            //System.out.println("\t"+numStart+"\t"+curNum);
        }

        if (wasInt) {
            //ensure last num is caught
            out.enqueue(parseInt(in, numStart, curNum));
            //ensure all operators are popped
            while (op.head!=null){
                if(op.peek()!='(') {
                    out.enqueue(op.pop()+"");
                } else {
                    throw new UnbalancedParentheseesException("unmatched "+')');
                }
            }
        }

        /* is this needed?
         else {
            char opr;
            try {
                opr = parseOpr(in, numStart); 
            } catch (NonExistentOperatorException e) {
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
            throws NonExistentOperatorException{
        char op;
        try {
            op = getOperator(in[idx]);
        } catch (NonExistentOperatorException e) {
            throw e;
        }

        return op;
    }

    private static void handleOpr(char opr, MyStack<Character> stack,
            MyQueue<String> queue) throws UnbalancedParentheseesException{
        if (opr!=')'){
            //push if not ')'
            while (stack.peek()!=null && getPrecidence(opr, stack.peek())){
                queue.enqueue(stack.pop()+"");
            }
            stack.push(opr);
        } else {
            //pop everything before '(' into queue
            while (stack.peek() != '(') {
                queue.enqueue(stack.pop()+"");
            }
            if (stack.peek() == null) {
                //there was no '(' to match the ')'
                System.out.println(queueToString(queue));
                throw new UnbalancedParentheseesException("unmatched "+')');
            }
            //next val should be '('
            stack.pop();
        }
    }

    private static boolean isInt(char c){
        if ('0'<=c && c<='9') return true;
        return false;
    }

    private static char getOperator(char c) throws NonExistentOperatorException{
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
                throw new NonExistentOperatorException(c+" is not a valid operator.");
        }
    }

    //return true if prev has higher precidence, false if not
    private static boolean getPrecidence(char c, char prev){
        if (prev=='(') return false;
        return (precidence(prev)>=precidence(c));
    }

    private static int precidence(char c){
        switch(c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return 99;
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
