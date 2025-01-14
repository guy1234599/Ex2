package ex2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SCell implements Cell {
    private String originalData;
    private String computedValue;
    private int cellType;

    public SCell() {
        originalData = Ex2Utils.EMPTY_CELL;
        computedValue = Ex2Utils.EMPTY_CELL;
        cellType = Ex2Utils.TEXT;
    }

    public SCell(Cell sourceCell) {
        this.originalData = sourceCell.getData();
        this.computedValue = sourceCell.getComputedValue();
        this.cellType = sourceCell.getType();
    }

    public SCell(String data) {
        this();
        this.setData(data);
    }

    public String getComputedValue() {
        if (this.cellType == 3) {
            try {
                String formula = this.originalData;
                if (formula.startsWith("=")) {
                    formula = formula.substring(1);
                }

                Double result = evaluateFormula(formula, 0, formula.length() - 1);
                return result.toString();
            } catch (Exception var3) {
                return "ERR_FORM!";
            }
        } else {
            return this.originalData;
        }
    }

    public void setComputedValue(String value) {
        this.computedValue = value;
    }

    public int updatetype() {
        if (originalData == null || originalData.trim().isEmpty()) {
            this.cellType = Ex2Utils.TEXT;
        } else if (originalData.startsWith("=")) {
            this.cellType = Ex2Utils.FORM;
        } else if (isNumber(originalData)) {
            this.cellType = Ex2Utils.NUMBER;
        } else {
            this.cellType = Ex2Utils.TEXT;
        }
        return this.cellType;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String toString() {
        return getData();
    }

    @Override
    public void setData(String data) {
        originalData = data;
        updatetype();
    }

    @Override
    public String getData() {
        return originalData;
    }

    @Override
    public int getType() {
        return cellType;
    }

    @Override
    public void setType(int type) {
        cellType = type;
    }

    @Override
    public void setOrder(int order) {
    }

    public static boolean isForm(String formula) {
        if (formula == null || formula.trim().isEmpty()) {
            return false;
        }

        formula = formula.trim();

        if (formula.charAt(0) != '=') {
            return false;
        }

        formula = formula.substring(1).replaceAll("\\s+", "");

        int balance = 0;
        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
        }
        if (balance != 0) return false;

        String numberPattern = "-?\\d+(\\.\\d+)?";
        String cellReferencePattern = "[A-Z]+[0-9]+";
        String validTerm = "(" + numberPattern + "|" + cellReferencePattern + ")";
        String operatorPattern = "[+\\-*/]";

        String formulaPattern = "\\(*" + validTerm + "\\)*(" + operatorPattern + "\\(*" + validTerm + "\\)*)*";

        return formula.matches(formulaPattern);
    }

    public static boolean isNumber(String str) {
        if (str.contains("+")) return false;
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isText(String text) {
        if (text.equals(Ex2Utils.ERR_CYCLE) || text.equals(Ex2Utils.ERR_FORM) || text.equals(Ex2Utils.ERR_FORM_FORMAT)) {
            return false;
        }
        return !isNumber(text) && !isForm(text);
    }

    public static Double computeForms(String formula) {
        if (formula == null || formula.isEmpty()) {
            return (double) Ex2Utils.ERR_FORM_FORMAT;
        }

        try {
            String processedFormula = formula.startsWith("=") ? formula.substring(1).trim() : formula.trim();

            Pattern cellPattern = Pattern.compile("[A-Z][0-9]+");
            Matcher matcher = cellPattern.matcher(processedFormula);
            StringBuffer processedString = new StringBuffer();

            while (matcher.find()) {
                String cellRef = matcher.group();
                int col = cellRef.charAt(0) - 'A';
                int row = Integer.parseInt(cellRef.substring(1));

                Cell referencedCell = Ex2Sheet.getCell(col, row);
                if (referencedCell == null) {
                    matcher.appendReplacement(processedString, "0");
                    continue;
                }

                String cellValue = referencedCell.getData();
                if (cellValue == null || cellValue.isEmpty()) {
                    matcher.appendReplacement(processedString, "0");
                } else if (cellValue.startsWith("=")) {
                    Double refValue = computeForms(cellValue);
                    matcher.appendReplacement(processedString, String.valueOf(refValue));
                } else if (isNumber(cellValue)) {
                    matcher.appendReplacement(processedString, cellValue);
                } else {
                    matcher.appendReplacement(processedString, "0");
                }
            }
            matcher.appendTail(processedString);

            return evaluateFormula(processedString.toString(), 0, processedString.length() - 1);
        } catch (Exception e) {
            return (double) Ex2Utils.ERR_FORM_FORMAT;
        }
    }

    public static double evaluateFormula(String text, int start, int end) {
        String expr = text.substring(start, end + 1).trim();

        if (expr.isEmpty()) {
            return 0;
        }

        if (isNumber(expr)) {
            return Double.parseDouble(expr);
        }

        while (expr.startsWith("(") && expr.endsWith(")")) {
            String inner = expr.substring(1, expr.length() - 1).trim();
            if (!inner.isEmpty()) {
                expr = inner;
            } else {
                break;
            }
        }

        if (expr.startsWith("-")) {
            String rest = expr.substring(1).trim();
            if (isNumber(rest)) {
                return Double.parseDouble(expr);
            }
            return -evaluateFormula(rest, 0, rest.length() - 1);
        }

        int mainOpIndex = -1;
        int parenCount = 0;

        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == ')') parenCount++;
            else if (c == '(') parenCount--;
            else if (parenCount == 0 && (c == '+' || c == '-')) {
                mainOpIndex = i;
                break;
            }
        }

        if (mainOpIndex == -1) {
            for (int i = expr.length() - 1; i >= 0; i--) {
                char c = expr.charAt(i);
                if (c == ')') parenCount++;
                else if (c == '(') parenCount--;
                else if (parenCount == 0 && (c == '*' || c == '/')) {
                    mainOpIndex = i;
                    break;
                }
            }
        }

        if (mainOpIndex == -1) {
            return Double.parseDouble(expr);
        }

        String leftExpr = expr.substring(0, mainOpIndex).trim();
        String rightExpr = expr.substring(mainOpIndex + 1).trim();

        double leftVal = leftExpr.isEmpty() ? 0 : evaluateFormula(leftExpr, 0, leftExpr.length() - 1);
        double rightVal = evaluateFormula(rightExpr, 0, rightExpr.length() - 1);

        char operator = expr.charAt(mainOpIndex);
        switch (operator) {
            case '+': return leftVal + rightVal;
            case '-': return leftVal - rightVal;
            case '*': return leftVal * rightVal;
            case '/':
                if (rightVal == 0) throw new ArithmeticException("Division by zero");
                return leftVal / rightVal;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
@Override
    public ArrayList<String> getDependencies(String formula) {
        ArrayList<String> dependencies = new ArrayList<>();
        Pattern cellPattern = Pattern.compile("[A-Za-z]+[0-9]+");
        Matcher matcher = cellPattern.matcher(formula);
        while (matcher.find()) {
            dependencies.add(matcher.group());
        }
        return dependencies;
    }
}
