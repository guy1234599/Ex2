package Ex2.ex2;
// Add your documentation below:

public class CellEntry  implements Index2D {
    private String indexString;

    public CellEntry(String indexString) {
        this.indexString = indexString;
    }

    @Override
    public boolean isValid( ) {
        int value1 =0;
         String substring=indexString.substring(0,indexString.length()-1);
         if(SCell.isNumber(substring)){
             double value= Double.parseDouble(substring) ;
             if(value==(int) value){
                  value1 =(int) value;
             }else{
                 return false;
             }
         }
        if((indexString.matches("^[A-Za-z].*"))&&(String.valueOf(value1).matches("^([0-9]|[1-9][0-9])$"))){
            return  true;
        }
        return false;
    }
    @Override
    public int getX() {
        int counter =65;
         int counter1=1;
        char x= indexString.charAt(0);
       if(isValid()) {
           for(int i=0 ;i<26 ;i++){
               if(x== counter){
                return counter1;
               }
               if(x==counter&&counter>=97){
                   return counter1-32 ;

               }
           }

    }

        return Ex2Utils.ERR;}


    @Override
    public int getY()
    {
        String y = indexString.substring(1,indexString.length()-1);
        if(isValid()) {
            return Integer.parseInt(y);
        }
        return Ex2Utils.ERR;}
}
