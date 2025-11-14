class Main{
    public static void main(String[] args){
        //String test = "((1+2)+21)*31+2";
        //12+21+31*2+

        //String test = "1+2+(3*4)/5";
        //12+34*5/+

        //String test = "1%(2+3)/4*5-3";
        //123+%4/5*3-

        //String test = "5";
        //5

        //String test = "-5+-4*2";
        //(-5)(-4)+2*

        String test = "-1+34*(5%3)/-4+(8*-7/7+3)";
        //(-1)(34)53%*(-4)/+8(-7)*7/3++

        try{
            System.out.println(RevPolish.queueToString(RevPolish.toRevPolish(test)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
