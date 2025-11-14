class Main{
    public static void main(String[] args){
        String test = "((1+2)+21)*31+2";
        //12+21+31*2+

        //String test = "1+2+(3*4)/5";
        //12+34*5/+

        //String test = "1%(2+3)/4*5-3";
        //123+%4/5*3-
        try{
            System.out.println(RevPolish.queueToString(RevPolish.toRevPolish(test)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
