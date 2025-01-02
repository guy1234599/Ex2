package Ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testIsNumber_ValidNumbers() {
        // בדיקת מספרים תקינים
        assertTrue(Cell.isNumber("123"));
        assertTrue(Cell.isNumber("-123"));
        assertTrue(Cell.isNumber("0"));
        assertTrue(Cell.isNumber("3.14159"));
        assertTrue(Cell.isNumber("-3.14159"));
        assertTrue(Cell.isNumber("1e10")); // פורמט מדעי
        assertTrue(Cell.isNumber("-1.5e-3")); // פורמט מדעי שלילי
    }

    @Test
    public void testIsNumber_InvalidNumbers() {
        // בדיקת קלטים שאינם מספרים
        assertFalse(Cell.isNumber("abc"));
        assertFalse(Cell.isNumber("123abc"));
        assertFalse(Cell.isNumber("3.14.15"));
        assertFalse(Cell.isNumber("")); // מחרוזת ריקה
        assertFalse(Cell.isNumber(null)); // מחרוזת null
        assertFalse(Cell.isNumber(" ")); // רווח בלבד
        assertFalse(Cell.isNumber("++")); // תווים לא חוקיים
    }

    @Test
    public void testIsNumber_BoundaryCases() {
        // בדיקת מקרים גבוליים
        assertTrue(Cell.isNumber("0")); // אפס
        assertTrue(Cell.isNumber("-0")); // אפס שלילי
        assertFalse(Cell.isNumber("+")); // סימן פלוס בלבד
        assertFalse(Cell.isNumber("-")); // סימן מינוס בלבד
    }

    @Test
    public void testIsText_ValidText() {
        // בדיקת טקסטים תקינים
        assertTrue(Cell.isText("hello"));
        assertTrue(Cell.isText("123abc"));
        assertTrue(Cell.isText("2a"));
        assertTrue(Cell.isText("{2}"));
        assertTrue(Cell.isText("text with spaces"));
    }

    @Test
    public void testIsText_InvalidText() {
        // בדיקת קלטים שאינם טקסטים
        assertFalse(Cell.isText("123")); // מספר בלבד
        assertFalse(Cell.isText("-123")); // מספר שלילי בלבד
        assertFalse(Cell.isText("=1+2")); // נוסחה
        assertFalse(Cell.isText(null)); // null
    }

    @Test
    public void testIsForm_ValidFormulas() {
        // בדיקת נוסחאות תקינות
        assertTrue(Cell.isForm("=1"));
        assertTrue(Cell.isForm("=1+2"));
        assertTrue(Cell.isForm("=(2*3)"));
        assertTrue(Cell.isForm("=(1+2)*3"));
        assertTrue(Cell.isForm("=A1+B2"));
        assertTrue(Cell.isForm("=((A1+2)*3)-4"));
    }

    @Test
    public void testIsForm_InvalidFormulas() {
        // בדיקת קלטים שאינם נוסחאות תקינות
        assertFalse(Cell.isForm("1+2")); // לא מתחיל ב-=
        assertFalse(Cell.isForm("=+")); // לא תקין
        assertFalse(Cell.isForm("=2+")); // לא תקין
        assertFalse(Cell.isForm("=(3+4")); // סוגריים לא סגורים
        assertFalse(Cell.isForm("3+4)")); // סוגריים מיותרים
        assertFalse(Cell.isForm("=")); // ריק אחרי =
        assertFalse(Cell.isForm(null)); // null
    }

//    @Test
//    public void testTranslateFormula() {
//        // בדיקת המרה של נוסחאות לערכים
//        Spreadsheet spreadsheet = new Spreadsheet(10, 10);
//        spreadsheet.set(0, 0, new Cell("5")); // תא A1
//        spreadsheet.set(0, 1, new Cell("10")); // תא B1
//
//        String translated = Cell.translateFormula("=A1+B1", spreadsheet);
//        assertEquals("5+10", translated);
//
//        translated = Cell.translateFormula("=A1*2", spreadsheet);
//        assertEquals("5*2", translated);
//    }

//    @Test
//    public void testComputeFormula() {
//        // בדיקת חישוב נוסחאות
//        Spreadsheet spreadsheet = new Spreadsheet(10, 10);
//        spreadsheet.set(0, 0, new Cell("5")); // תא A1
//        spreadsheet.set(0, 1, new Cell("10")); // תא B1
//
//        Double result = Cell.computeFormula("=A1+B1", spreadsheet);
//        assertEquals(15.0, result);
//
//        result = Cell.computeFormula("=A1*2", spreadsheet);
//        assertEquals(10.0, result);
//
//        result = Cell.computeFormula("=(A1+B1)/3", spreadsheet);
//        assertEquals(5.0, result);
//    }
}
