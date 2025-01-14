# Spreadsheet Management System

This project implements a spreadsheet management system with custom functionality for handling cells, formulas, and graphical representation. Below is an overview of the key components:

---

## **Classes Overview**

### **1. SCell**
Represents a single cell in the spreadsheet.
- **Attributes:**
  - `originalData`: Stores the raw data of the cell.
  - `computedValue`: Stores the evaluated value of the cell.
  - `cellType`: Specifies the type of cell (text, number, formula).
- **Methods:**
  - `getComputedValue()`: Evaluates and returns the computed value.
  - `setData(String data)`: Updates the cell data and type.
  - `isNumber(String str)`: Checks if a string is numeric.
  - `isForm(String formula)`: Validates a formula.
  - `computeForms(String formula)`: Computes the result of a formula.
  - `evaluateFormula(String text, int start, int end)`: Recursive formula evaluator.

---

### **2. Ex2Sheet**
Represents the spreadsheet itself.
- **Attributes:**
  - `table`: A 2D array of `Cell` objects.
- **Methods:**
  - `value(int row, int col)`: Gets the computed value of a cell.
  - `set(int row, int col, String value)`: Updates a cell's value.
  - `eval()`: Evaluates all cells in the sheet.
  - `depth()`: Calculates dependency depths for cells.
  - `save(String fileName)`: Saves the spreadsheet to a file.
  - `load(String fileName)`: Loads a spreadsheet from a file.

---

### **3. Ex2GUI**
Provides a graphical interface for interacting with the spreadsheet.
- **Key Features:**
  - Draws the spreadsheet grid.
  - Allows users to interact with cells.
  - Supports loading and saving files.
  - **Example Output:**

![image](https://github.com/user-attachments/assets/c2b49ab3-908d-463a-8430-9c27e330a0e3)

---

### **4. CellEntry**
Handles the representation of a cell's coordinates and associated data.
- **Attributes:**
  - `x` and `y`: Coordinates of the cell.
  - `data`: The data stored in the cell.
- **Methods:**
  - `getCellReference()`: Converts coordinates to cell references (e.g., A1).
  - `isValid()`: Validates cell references.

---

## **Usage Instructions**

### **1. Run the Application**
Execute the `main` method in `Ex2GUI` to start the graphical interface:
```java
public static void main(String[] args) {
    Ex2GUI.main(args);
}
```

### **2. Loading and Saving Spreadsheets**
- **Load:**
  ```java
  Ex2GUI.load("fileName.txt");
  ```
- **Save:**
  ```java
  Ex2GUI.save("fileName.txt");
  ```

### **3. Inputting Data**
Click on cells in the graphical interface and input text, numbers, or formulas (e.g., `=A1+B2`).

---

## **Error Handling**
- **Cycle Errors:** Displays `ERR_CYCLE!` if circular dependencies are detected.
- **Formula Errors:** Displays `ERR_FORM!` for invalid formulas.
- **Empty Cells:** Treated as empty text by default.

---

## **File Format**
Saved spreadsheets are stored as CSV-like text files with the following structure:
```
I2CS ArielU: SpreadSheet (Ex2) assignment
row,col,data
...
```

---

## **Dependencies**
- Java Standard Libraries (e.g., `java.util`, `java.io`, `java.util.regex`).
- Custom Utilities (`Ex2Utils`, `StdDrawEx2`).

---

## **Future Improvements**
- Enhance the GUI for better user interaction.
- Support advanced formulas and functions.
- Implement undo/redo functionality.

---

## **License**
This project is for educational purposes only.
