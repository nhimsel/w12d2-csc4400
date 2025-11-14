class RevPolish{
    static MyQueue<String> toRevPolish(String inString)
            throws NonExistentOperatorException, UnbalancedParentheseesException{
        char[] in = inString.toCharArray();

        MyQueue<String> out = new MyQueue<String>();
        MyStack<Character> op = new MyStack<Character>();

        int numStart = 0;
        int curNum = 0;
        boolean wasInt = false;
        boolean isNegative = false;

        while (curNum < in.length) {
            if (isInt(in[curNum]) || (isNegative && in[curNum] == '0')) {
                //it's a number
                curNum++;
                wasInt = true;
            } else if (in[curNum] == '-' && (curNum == 0 || isOperator(in[curNum - 1]) || in[curNum - 1] == '(')) {
                //handle negatives 
                isNegative = true;
                curNum++;
            } else {
                //parse last number
                if (wasInt) {
                    out.enqueue(parseInt(in, numStart, curNum, isNegative));
                    wasInt = false;
                    isNegative = false;
                }

                //parse operator
                char opr;
                try {
                    opr = parseOpr(in, curNum);
                } catch (NonExistentOperatorException e) {
                    throw e;
                }

                //ignore space
                if (opr != ' '){ 
                    //handle operator
                    try {
                        handleOpr(opr, op, out);
                    } catch (UnbalancedParentheseesException e) {
                        throw e;
                    }
                }

                curNum++;
                numStart = curNum;
            }
        }

        //enqueue if last num was int
        if (wasInt) {
            out.enqueue(parseInt(in, numStart, curNum, isNegative));
        }

        //ensure all operators are popped
        while (op.peek() != null) {
            if (op.peek() != '(') {
                out.enqueue(op.pop() + "");
            } else {
                throw new UnbalancedParentheseesException("unmatched " + ')');
            }
        }

        return out;
    }

    private static String parseInt(char[] in, int start, int end, boolean isNegative) {
        String s = new String();
        while (start < end) {
            s += in[start++];
        }
        return s;
    }

    private static boolean isInt(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')' || c=='^';
    }

    private static char parseOpr(char[] in, int idx)
            throws NonExistentOperatorException {
        char op;
        try {
            op = getOperator(in[idx]);
        } catch (NonExistentOperatorException e) {
            throw e;
        }

        return op;
    }

    private static void handleOpr(char opr, MyStack<Character> stack,
            MyQueue<String> queue) throws UnbalancedParentheseesException {
        if (opr != ')') {
            //push if not ')'
            while (stack.peek() != null && getPrecidence(opr, stack.peek())) {
                queue.enqueue(stack.pop() + "");
            }
            stack.push(opr);
        } else {
            //pop everything before '(' into queue
            while (stack.peek() != '(') {
                queue.enqueue(stack.pop() + "");
            }
            if (stack.peek() == null) {
                //there was no '(' to match the ')'
                throw new UnbalancedParentheseesException("unmatched " + ')');
            }
            //next val should be '('
            stack.pop();
        }
    }

    private static char getOperator(char c) throws NonExistentOperatorException {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            case '(':
            case ')':
            case '^':
            //need space so we can ignore it later
            case ' ':
                return c;
            default:
                throw new NonExistentOperatorException(c + " is not a valid operator.");
        }
    }

    //return true if prev has higher precedence, false if not
    private static boolean getPrecidence(char c, char prev) {
        if (prev == '(') return false;
        return (precidence(prev) >= precidence(c));
    }

    private static int precidence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
            default:
                return 99;
        }
    }

    //if spaces, a space is placed between arguments. else, parens around args>1 char
    public static String queueToString(MyQueue<String> queue, boolean spaces) {
        if (queue.front() == null) return "";
        String s = "";
        String tmp = "";
        DoublyLinkedList.Node curNode = queue.tail;
        if (spaces) {
            s = curNode.obj+"";
            curNode=curNode.prv;
            while (curNode!=null) {
                s+=" "+curNode.obj;
                curNode=curNode.prv;
            }
        } else {
            while (curNode != null) {
                tmp = curNode.obj+"";
                if (tmp.length() > 1) {
                    tmp = '('+tmp+')';
                }
                s+=tmp;
                curNode = curNode.prv;
            }
        }
        return s;
    }
}
