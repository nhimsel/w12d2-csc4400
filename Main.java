class Main{
    public static void main(String[] args){
        MyQueue<String> tests = new MyQueue<String>();

        tests.enqueue("(3+4)*2/(1-5)");
        //34+2*15-/     =-3.5

        tests.enqueue("((1+2)+21)*31+2");
        //12+21+31*2+   =746

        tests.enqueue("1+2+(3*4)/5");
        //12+34*5/+     =5.4

        tests.enqueue("1%(2+3)/4*5-3");
        //123+%4/5*3-   =-1.75

        tests.enqueue("5");
        //5             =5

        tests.enqueue("-5+-4*2");
        //(-5)(-4)+2*   =-13

        tests.enqueue("-1+34*(5%3)/-4+(8*-7/7+3)");
        //(-1)(34)53%*(-4)/+8(-7)*7/3++
        //              =-23

        tests.enqueue("-7^32/43");
        //(-7)(32)^43/ ~=-2.568

        try{
            while (tests.front()!=null){
                String test=tests.dequeue();
                System.out.println(test);
                MyQueue<String> out = RevPolish.toRevPolish(test);
                System.out.println(RevPolish.queueToString(out, true));
                double ans = RPNEval.Eval(out);
                System.out.println(ans+"\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
