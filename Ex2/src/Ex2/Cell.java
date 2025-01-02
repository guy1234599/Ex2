package Ex2;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cell {
    private String text;

    public Cell(String text) {
        this.text = text;
}public String getText() {
        return text;
    }


  public static boolean isNumber(String text) {
        if (text==null){
            return false;
        }
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // מזהה אם הקלט הוא מספר תקין, לדוגמה: "1", "-1.1".

    public  static boolean isText(String text){

        return !isNumber(text) && !isForm(text)&&text!=null;


    }

    // מזהה אם הקלט הוא טקסט, לדוגמה: "2a", "{2}", "hello".
    public static boolean isForm(String text) {


        if (text== null || text.isEmpty()) {
            return false;
        }
        // בדיקה אם המחרוזת מתאימה לרגקס
        return Pattern.matches(FORMULA_REGEX, text);
    }
     // מזהה אם הקלט הוא נוסחה תקינה, לדוגמה: "=1", "=1+2*2".

//    public static Double computeFormula(String formula, Spreadsheet spreadsheet) {
//        if (formula == null || !formula.startsWith("=")) {
//            throw new IllegalArgumentException("Formula must start with '='");
//        }
//
//        // הסרת סימן השוויון והמרת הפניות לתאים לערכים
//        String formulaContent = formula.substring(1);
//        String translatedFormula = translateFormula(formulaContent, spreadsheet);
//
//        // חישוב הנוסחה באמצעות מנוע JavaScript
//        try {
//            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
//            return Double.valueOf(engine.eval(translatedFormula).toString());
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid formula: " + formula, e);
//        }
//    }


//    public static String translateFormula(String formulaContent, Spreadsheet spreadsheet) {
//        // יצירת Pattern לזיהוי הפניות לתאים
//        Pattern pattern = Pattern.compile("[A-Z]+\\d+");
//        Matcher matcher = pattern.matcher(formulaContent);
//
//        // אובייקט לתוצאה
//        StringBuffer result = new StringBuffer();
//
//        // מעבר על כל התאמות והחלפתן בערכי התאים
//        while (matcher.find()) {
//            String cellRef = matcher.group(); // מציאת הפניה לתא (למשל "A1")
//            String cellValue = spreadsheet.getValue(cellRef); // קבלת הערך של התא מהגיליון
//            matcher.appendReplacement(result, cellValue != null ? cellValue : "0"); // החלפה
//        }
//
//        matcher.appendTail(result); // הוספת שאר הנוסחה ללא שינוי
//        return result.toString();
//    }//            List<char[]> contentBetweenParentheses = new ArrayList<>();
    //            List<Integer> stack = new ArrayList<>(); // מחסנית לזיהוי סוגריים פתוחים
//
//            // מעבר על המערך
//            for (int i = 0; i < formulaArray.length; i++) {
//                if (formulaArray[i] == '(') {
//                    // שמירת המיקום של הסוגר הפתוח
//                    stack.add(i);
//                } else if (formulaArray[i] == ')') {
//                    // בדיקת סוגר תואם
//                    if (!stack.isEmpty()) {
//                        // מיקום הסוגר הפתוח האחרון
//                        int openIndex = stack.remove(stack.size() - 1);
//
//                        // חיתוך התווים בין הסוגריים
//                        char[] content = new char[i - openIndex - 1];
//                        System.arraycopy(formulaArray, openIndex + 1, content, 0, content.length);
//
//                        // הוספה לרשימה
//                        contentBetweenParentheses.add(content);
//                    } else {
//                        throw new IllegalArgumentException("Unmatched closing parenthesis at index " + i);
//                    }
//                }
//            }
//
//            // בדיקה אם נשארו סוגריים פתוחים לא תואמים
//            if (!stack.isEmpty()) {
//                throw new IllegalArgumentException("Unmatched opening parenthesis at index " + stack.get(0));
//            }// לולאה לעיבוד חוזר כל עוד יש סוגריים
//    while (formula.contains("(")) {
//        // מציאת הסוגריים הפנימיים ביותר
//        int closeIndex = formula.indexOf(')');
//        int openIndex = formula.lastIndexOf('(', closeIndex);
//
//        // קבלת התוכן בין הסוגריים
//        String innerFormula = formula.substring(openIndex + 1, closeIndex);
//
//        // חישוב התוכן בין הסוגריים
//        double result = calculateFromArray(innerFormula.toCharArray());
//
//        // החלפת התוכן והתוצאה בנוסחה הראשית
//        formula = formula.substring(0, openIndex) + result + formula.substring(closeIndex + 1);
//    }
//
//    // חישוב שאר הנוסחה (ללא סוגריים)
//    return calculateFromArray(formula.toCharArray());
//}
//public static Double gg (String Formula){
//    char[] formulaArray = Formula.toCharArray();
//
//
//
//
//
//        // שלב 1: המרה של המספרים והאופרטורים לרשימה
//        List<String> tokens = new ArrayList<>();
//        StringBuilder numberBuffer = new StringBuilder();
//
//        for (char c : formulaArray) {
//            if (Character.isDigit(c) || c == '.') { // מספר
//                numberBuffer.append(c);
//            } else if (c == '+' || c == '-' || c == '*' || c == '/') { // אופרטור
//                if (numberBuffer.length() > 0) {
//                    tokens.add(numberBuffer.toString());
//                    numberBuffer.setLength(0);
//                }
//                tokens.add(String.valueOf(c));
//            }
//        }
//
//        if (numberBuffer.length() > 0) {
//            tokens.add(numberBuffer.toString());
//        }
//
//        // שלב 2: חישוב לפי סדר פעולות חשבון
//        // כפל וחילוק
//        for (int i = 0; i < tokens.size(); i++) {
//            if (tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
//                double left = Double.parseDouble(tokens.get(i - 1));
//                double right = Double.parseDouble(tokens.get(i + 1));
//                double result = tokens.get(i).equals("*") ? left * right : left / right;
//
//                tokens.set(i - 1, String.valueOf(result));
//                tokens.remove(i);
//                tokens.remove(i);
//                i--;
//            }
//        }
//
//        // חיבור וחיסור
//        for (int i = 0; i < tokens.size(); i++) {
//            if (tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
//                double left = Double.parseDouble(tokens.get(i - 1));
//                double right = Double.parseDouble(tokens.get(i + 1));
//                double result = tokens.get(i).equals("+") ? left + right : left - right;
//
//                tokens.set(i - 1, String.valueOf(result));
//                tokens.remove(i);
//                tokens.remove(i);
//                i--;
//            }
//        }
//
//        // התוצאה הסופית
//        return Double.parseDouble(tokens.get(0));
//
//
//
//
//        } public static double calculateFormulaWithParentheses(String formula) {
//        // לולאה לעיבוד חוזר כל עוד יש סוגריים
//        while (formula.contains("(")) {
//            // מציאת הסוגריים הפנימיים ביותר
//            int closeIndex = formula.indexOf(')');
//            int openIndex = formula.lastIndexOf('(', closeIndex);
//
//            // קבלת התוכן בין הסוגריים
//            String innerFormula = formula.substring(openIndex + 1, closeIndex);
//
//            // חישוב התוכן בין הסוגריים
//            double result = calculateFromArray(innerFormula.toCharArray());
//
//            // החלפת התוכן והתוצאה בנוסחה הראשית
//            formula = formula.substring(0, openIndex) + result + formula.substring(closeIndex + 1);
//        }
//
//        // חישוב שאר הנוסחה (ללא סוגריים)
//        return calculateFromArray(formula.toCharArray());
//    }
//
//    public static double calculateFromArray(char[] formulaArray) {
//        // שלב 1: המרה של המספרים והאופרטורים לרשימה
//        List<String> tokens = new ArrayList<>();
//        StringBuilder numberBuffer = new StringBuilder();
//
//        for (char c : formulaArray) {
//            if (Character.isDigit(c) || c == '.') { // חלק ממספר
//                numberBuffer.append(c);
//            } else if (c == '+' || c == '-' || c == '*' || c == '/') { // אופרטור מתמטי
//                if (numberBuffer.length() > 0) {
//                    tokens.add(numberBuffer.toString());
//                    numberBuffer.setLength(0);
//                }
//                tokens.add(String.valueOf(c)); // הוספת האופרטור לרשימה
//            }
//        }
//
//        // הוספת המספר האחרון אם קיים
//        if (numberBuffer.length() > 0) {
//            tokens.add(numberBuffer.toString());
//        }
//
//        // שלב 2: חישוב לפי סדר פעולות חשבון
//        // כפל וחילוק
//        for (int i = 0; i < tokens.size(); i++) {
//            if (tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
//                double left = Double.parseDouble(tokens.get(i - 1));
//                double right = Double.parseDouble(tokens.get(i + 1));
//                double result = tokens.get(i).equals("*") ? left * right : left / right;
//
//                tokens.set(i - 1, String.valueOf(result));
//                tokens.remove(i); // הסרת האופרטור
//                tokens.remove(i); // הסרת המספר הימני
//                i--; // חזרה אחורה לבדיקה נוספת
//            }
//        }
//
//        // חיבור וחיסור
//        for (int i = 0; i < tokens.size(); i++) {
//            if (tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
//                double left = Double.parseDouble(tokens.get(i - 1));
//                double right = Double.parseDouble(tokens.get(i + 1));
//                double result = tokens.get(i).equals("+") ? left + right : left - right;
//
//                tokens.set(i - 1, String.valueOf(result));
//                tokens.remove(i); // הסרת האופרטור
//                tokens.remove(i); // הסרת המספר הימני
//                i--; // חזרה אחורה לבדיקה נוספת
//            }
//        }

//        // התוצאה הסופית היא האיבר היחיד שנותר ברשימה
//        return Double.parseDouble(tokens.get(0));
//    }
// פונקציה שמחשבת פורמולה
public static Double gg(String formula) {
    return calculateFromArray(formula.toCharArray());
}

    // פונקציה להכנת הפורמולה על ידי החלפת הפניות לתאים בערכים
    public static String prepareFormula(String formula, Spreadsheet spreadsheet) {
        List<String> visitedCells = new ArrayList<>(); // למניעת לולאות
        return resolveCellReferences(formula, spreadsheet, visitedCells);
    }

    // פונקציה פנימית שמטפלת בהחלפת הפניות לתאים בערכים שלהם
    private static String resolveCellReferences(String formula, Spreadsheet spreadsheet, List<String> visitedCells) {
        Pattern cellPattern = Pattern.compile("[A-Z]+\\d+"); // זיהוי תאים כמו "A1"
        Matcher matcher = cellPattern.matcher(formula);

        while (matcher.find()) {
            String cellRef = matcher.group(); // שם התא (למשל "A1")

            // מניעת לולאה
            if (visitedCells.contains(cellRef)) {
                throw new IllegalArgumentException("Cyclic reference detected: " + cellRef);
            }

            visitedCells.add(cellRef); // מעקב אחר תאים שבוצע בהם ביקור

            // קבלת הערך של התא מהגיליון
            String cellValue = spreadsheet.getValue(cellRef);

            // אם התא ריק, הצב 0
            if (cellValue == null) {
                cellValue = "0";
            } else if (isForm(cellValue)) {
                // אם התא מכיל נוסחה, בצע חישוב שלה
                cellValue = resolveCellReferences(cellValue.substring(1), spreadsheet, visitedCells);
            }

            // החלפת הפניה לערך המתאים
            formula = formula.replace(cellRef, cellValue);
        }

        return formula;
    }

    // פונקציה שמחשבת נוסחה מתמטית מתוך מערך תווים
    public static double calculateFromArray(char[] formulaArray) {
        List<String> tokens = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();

        for (char c : formulaArray) {
            if (Character.isDigit(c) || c == '.') { // מספרים ונקודות עשרוניות
                numberBuffer.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') { // אופרטורים מתמטיים
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }
                tokens.add(String.valueOf(c));
            }
        }

        if (numberBuffer.length() > 0) {
            tokens.add(numberBuffer.toString());
        }

        // שלב 1: חישוב כפל וחילוק
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
                double left = Double.parseDouble(tokens.get(i - 1));
                double right = Double.parseDouble(tokens.get(i + 1));
                double result = tokens.get(i).equals("*") ? left * right : left / right;

                tokens.set(i - 1, String.valueOf(result));
                tokens.remove(i);
                tokens.remove(i);
                i--;
            }
        }

        // שלב 2: חישוב חיבור וחיסור
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
                double left = Double.parseDouble(tokens.get(i - 1));
                double right = Double.parseDouble(tokens.get(i + 1));
                double result = tokens.get(i).equals("+") ? left + right : left - right;

                tokens.set(i - 1, String.valueOf(result));
                tokens.remove(i);
                tokens.remove(i);
                i--;
            }
        }

        // התוצאה הסופית
        return Double.parseDouble(tokens.get(0));
    }








    private static final String FORMULA_REGEX =
            "^=\\s*(\\((?:[A-Z]+\\d+|\\d+(\\.\\d+)?|\\((?:[A-Z]+\\d+|\\d+(\\.\\d+)?)(?:\\s*[-+*/]\\s*(?:[A-Z]+\\d+|\\d+(\\.\\d+)?))*\\))*\\)|[A-Z]+\\d+|\\d+(\\.\\d+)?)(?:\\s*[-+*/]\\s*(?:\\((?:[A-Z]+\\d+|\\d+(\\.\\d+)?)(?:\\s*[-+*/]\\s*(?:[A-Z]+\\d+|\\d+(\\.\\d+)?))*\\)|[A-Z]+\\d+|\\d+(\\.\\d+)?))*$";
}
