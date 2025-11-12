class Main{
    public static void main(String[] args){
        String test = "(1+2)+21";
        try{
            System.out.println(RevPolish.queueToString(RevPolish.toRevPolish(test)));
        } catch (NonExistentOperator e){
            System.out.println(e);
        }
    }
}
