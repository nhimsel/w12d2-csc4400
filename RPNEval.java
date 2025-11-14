class RPNEval{
    //expects a list formatted as outputted by RevPolish.toRevPolish
    //operates directly on the nodes to avoid modifying the list
    static double Eval(MyQueue<String> queue)
        throws NonExistentOperatorException{
        MyStack<Double> stack = new MyStack<Double>();

        DoublyLinkedList.Node curNode = queue.tail;
        while (curNode!=null) {
            //java interprets the objects as Comparables for some reason.
            //casting to String is necessary, even if its MyQueue<String>
            if (isInt((String)curNode.obj)) {
                stack.push((double)toInt((String)curNode.obj));
            } else {
                //must be an operator
                double num2 = stack.pop();
                double num1 = stack.pop();
                stack.push(performCalc(num1, num2, (String)curNode.obj));
            }
            curNode=curNode.prv;
        }
        return stack.pop();
    }

    private static double performCalc(double n1, double n2, String op)
        throws NonExistentOperatorException{
        switch(op){
            case "+":
                return n1+n2;
            case "-":
                return n1-n2;
            case "*":
                return n1*n2;
            case "/":
                return n1/n2;
            case "%":
                return n1%n2;
            case "^":
                return pow(n1,(int)n2);
            default:
                throw new NonExistentOperatorException(op+" is not valid.");
        }
    }

    private static boolean isInt(String s){
        //special handling of - because of negatives 
        if (s.equals("-")) return false;

        char[] c = s.toCharArray();
        for (int i=0; i<c.length; i++) {
            // '-' included to allow negative values
            if (!(c[i]=='-'||(c[i]>='0'&&c[i]<='9'))){
                return false;
            }
        }
        return true;
    }


    private static int toInt(String s){
        char[] c = s.toCharArray();
        int n = 0;
        int count = 0;

        for (int i=c.length-1; i>=0; i--) {
            if (c[i]>='0'&&c[i]<='9'){
                n+=(c[i]-'0')*pow(10,count++);
            }
        }
        if(c[0]=='-') n*=-1;
    return n;
    }

    private static double pow(double n, int p){
        //negative exponents are not currently supported
        if (p<=0) return 1;

        boolean isNeg = false;
        if (n<0){
            n*=-1;
            isNeg=true;
        }
        double sol = n;
        for (int i=p-1; i>0; i--){
            sol*=n;
        }
        return isNeg ? sol*-1 : sol;
    }
}
