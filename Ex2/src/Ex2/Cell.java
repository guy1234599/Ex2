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
