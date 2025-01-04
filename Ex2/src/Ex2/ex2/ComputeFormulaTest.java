package Ex2.ex2;
public class ComputeFormulaTest {
    public static void main(String[] args) {
        // טסטים בסיסיים
        assert computeFormula("2+3") == 5.0 : "Test 1 failed";
        assert computeFormula("4*5") == 20.0 : "Test 2 failed";
        assert computeFormula("10-7") == 3.0 : "Test 3 failed";
        assert computeFormula("8/2") == 4.0 : "Test 4 failed";

        // טסטים מורכבים (סדר פעולות)
        assert computeFormula("2+3*4") == 14.0 : "Test 5 failed";
        assert computeFormula("10/2+3") == 8.0 : "Test 6 failed";
        assert computeFormula("10-2*3") == 4.0 : "Test 7 failed";

        // טסטים עם סוגריים
        assert computeFormula("(2+3)*4") == 20.0 : "Test 8 failed";
        assert computeFormula("2*(3+4)") == 14.0 : "Test 9 failed";
        assert computeFormula("(10-2)/(4-2)") == 4.0 : "Test 10 failed";

        // טסטים עם מספרים עשרוניים
        assert computeFormula("2.5*4") == 10.0 : "Test 11 failed";
        assert computeFormula("5.5+4.5") == 10.0 : "Test 12 failed";

        // טסטים מורכבים במיוחד
        assert computeFormula("2+3*(4-1)") == 11.0 : "Test 13 failed";
        assert computeFormula("(2+3)*(4-1)") == 15.0 : "Test 14 failed";
        assert computeFormula("10/(2+3)") == 2.0 : "Test 15 failed";

                // טסט 1: נוסחה בסיסית
                assert computeFormula("=(2+(5*(5-1))*2+(3*(1/2))*12)") == 60.0 : "Test 1 failed";

                // טסט 2: בדיקה של החלק הראשון בלבד (2 + (5 * (5 - 1)))
                assert computeFormula("=(2+(5*(5-1)))") == 22.0 : "Test 2 failed";

                // טסט 3: בדיקה של חלק עם סוגריים פנימיים בלבד ((5 - 1) * 5)
                assert computeFormula("=((5-1)*5)") == 20.0 : "Test 3 failed";

                // טסט 4: חלק מהנוסחה (חיבור כפל וחילוק יחד)
                assert computeFormula("=(3*(1/2)*12)") == 18.0 : "Test 4 failed";

                // טסט 5: נוסחה פשוטה יותר
                assert computeFormula("=(2+40+18)") == 60.0 : "Test 5 failed";

                // טסט 6: רק כפל וסוגריים
                assert computeFormula("=(5*(5-1)*2)") == 40.0 : "Test 6 failed";

                // טסט 7: חילוק פשוט עם עשרוני
                assert computeFormula("=(3*(1/2))") == 1.5 : "Test 7 failed";

                System.out.println("All tests passed successfully!");


        CellEntry cell1 = new CellEntry("A1");
        assert cell1.isValid() : "A1 should be valid";
        assert cell1.getX() == 1 : "X value for A1 should be 1";
        assert cell1.getY() == 1 : "Y value for A1 should be 1";

        CellEntry cell2 = new CellEntry("B12");
        assert cell2.isValid() : "B12 should be valid";
        assert cell2.getX() == 2 : "X value for B12 should be 2";
        assert cell2.getY() == 12 : "Y value for B12 should be 12";

        CellEntry cell3 = new CellEntry("Z99");
        assert cell3.isValid() : "Z99 should be valid";
        assert cell3.getX() == 26 : "X value for Z99 should be 26";
        assert cell3.getY() == 99 : "Y value for Z99 should be 99";

        // Invalid inputs
        CellEntry cell4 = new CellEntry("1A");
        assert !cell4.isValid() : "1A should not be valid";

        CellEntry cell5 = new CellEntry("AA12");
        assert !cell5.isValid() : "AA12 should not be valid";

        CellEntry cell6 = new CellEntry("A-1");
        assert !cell6.isValid() : "A-1 should not be valid";

        CellEntry cell7 = new CellEntry("A100");
        assert !cell7.isValid() : "A100 should not be valid";

        CellEntry cell8 = new CellEntry("");
        assert !cell8.isValid() : "Empty string should not be valid";

        // Case sensitivity
        CellEntry cell9 = new CellEntry("a3");
        assert cell9.isValid() : "a3 should be valid";
        assert cell9.getX() == 1 : "X value for a3 should be 1";
        assert cell9.getY() == 3 : "Y value for a3 should be 3";

        // Boundary cases
        CellEntry cell10 = new CellEntry("A0");
        assert cell10.isValid() : "A0 should be valid";
        assert cell10.getX() == 1 : "X value for A0 should be 1";
        assert cell10.getY() == 0 : "Y value for A0 should be 0";

        CellEntry cell11 = new CellEntry("Z99");
        assert cell11.isValid() : "Z99 should be valid";
        assert cell11.getX() == 26 : "X value for Z99 should be 26";
        assert cell11.getY() == 99 : "Y value for Z99 should be 99";

        // Print confirmation if all assertions pass
        System.out.println("All assertions passed!");



        System.out.println("All tests passed successfully!");
    }

    public static Double computeFormula(String formula) {
        // פונקציית ה-computeFormula שלך נכנסת כאן
        return 0.0; // החלף במימוש שלך
    }
}
