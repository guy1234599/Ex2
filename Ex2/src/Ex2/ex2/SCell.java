package Ex2.ex2;
// Add your documentation below:

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import static Ex2.ex2.Ex2Utils.M_OPS;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;
    // Add your code here

    public SCell(String s) {
        // Add your code here
        setData(s);
    }

    @Override
    public int getOrder() {
        int max=0;
        if (isNumber(line)||isText(line)){
            return max;
        }

        return 1+max;

        // /////////////////// צריך להוסיף התייחסות לתאים
    }

    //@Override
    @Override
    public String toString() {
        return getData();
        //להחזיר את הנוסחא המקורית
    }

    @Override
    public void setData(String s) {
        this.line = s;
    }

    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
        this.order=t;
// cd
    }

    public static boolean isNumber(String line) {
        if (line == null) {
            return false;
        }
        try {
            Double.parseDouble(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isText(String line) {

        return !isNumber(line) && !isForm(line) && line != null;


    }

    public static boolean isForm(String text) {


        if (text == null || text.isEmpty()) {
            return false;
        }
        // בדיקה אם המחרוזת מתאימה לרגקס
        return Pattern.matches(FORMULA_REGEX, text);
    }

    private static final String FORMULA_REGEX =
            "^=\\s*(\\((?:[A-Z]+\\d+|\\d+(\\.\\d+)?|\\((?:[A-Z]+\\d+|\\d+(\\.\\d+)?)(?:\\s*[-+*/]\\s*(?:[A-Z]+\\d+|\\d+(\\.\\d+)?))*\\))*\\)|[A-Z]+\\d+|\\d+(\\.\\d+)?)(?:\\s*[-+*/]\\s*(?:\\((?:[A-Z]+\\d+|\\d+(\\.\\d+)?)(?:\\s*[-+*/]\\s*(?:[A-Z]+\\d+|\\d+(\\.\\d+)?))*\\)|[A-Z]+\\d+|\\d+(\\.\\d+)?))*$";

    public static Double computeFormula(String formula) {
        // יוצר מערך של כל האינדקסים של האופרטורים ובודק מה האינדקס הראשון מבין ה אינדקסים הראשונים של כפל
        // וחילוק לוקח את האינדקס הראשון ומחשב את זה ביחס לאופורטרים אחרים מכניס את זה מחדש לפורמולה
        int indexStart=0;
        int indexAnd=0;
        int index1=0;
        int index2=0;
        String outcome;
        ArrayList<Double> allValue = new ArrayList<Double>();
        double value =0 ;
        double number1 =0, number2 =0;
        String bracket1="(";
        String bracket2=")";
         indexStart = formula.indexOf(bracket1);
        indexAnd= formula.lastIndexOf(bracket2);
        String subFormula=formula.substring(indexStart, indexAnd);
        computeFormula(subFormula);
        char[] charArray = formula.toCharArray();
        int[] index= new int[charArray.length];


        for (int k=0 ; k<charArray.length;k++){
            for(int n=0; n<=4;n++){
            if(charArray[k]==M_OPS[n]){index[k]=(charArray[k]);
        }}}

        for (int i=0 ; i<2*formula.length();i++);
        {
            index1=formula.indexOf('*');
            index2=formula.indexOf('/');
            if(index2>index1){
                 String compponit1 =formula.substring(charArray[index2-1],charArray[index2]);
                String compponit2 =formula.substring(charArray[index2],charArray[index2+1]);
                number1 = Double.parseDouble(compponit1);
                number2 = Double.parseDouble(compponit2);
                value=number1/number2;
                outcome= String.valueOf(value);
                StringBuilder sb = new StringBuilder(formula);
                sb.replace(charArray[index2-1],charArray[index2],outcome);
                formula=sb.toString();
            }
            else {
                String compponit1 =formula.substring(charArray[index1-1],charArray[index1]);
                String compponit2 =formula.substring(charArray[index1],charArray[index1+1]);
                number1 = Double.parseDouble(compponit1);
                number2 = Double.parseDouble(compponit2);
                value=number1*number2;
                outcome= String.valueOf(value);
                StringBuilder sb = new StringBuilder(formula);
                sb.replace(charArray[index1-1],charArray[index1],outcome);
                formula=sb.toString();

            }
        }
        for (int i=0 ; i<2*formula.length();i++);
        {
            index1=formula.indexOf('+');
            index2=formula.indexOf('-');
            if(index2>index1){
                String compponit1 =formula.substring(charArray[index2-1],charArray[index2]);
                String compponit2 =formula.substring(charArray[index2],charArray[index2+1]);
                number1 = Double.parseDouble(compponit1);
                number2 = Double.parseDouble(compponit2);
                value=number1/number2;
                outcome= String.valueOf(value);
                StringBuilder sb = new StringBuilder(formula);
                sb.replace(charArray[index2-1],charArray[index2],outcome);
                formula=sb.toString();
            }
            else {
                String compponit1 =formula.substring(charArray[index1-1],charArray[index1]);
                String compponit2 =formula.substring(charArray[index1],charArray[index1+1]);
                number1 = Double.parseDouble(compponit1);
                number2 = Double.parseDouble(compponit2);
                value=number1*number2;
                outcome= String.valueOf(value);
                StringBuilder sb = new StringBuilder(formula);
                sb.replace(charArray[index1-1],charArray[index1],outcome);
                formula=sb.toString();

            }
        }
        return Double.parseDouble(formula);

    }


    }

