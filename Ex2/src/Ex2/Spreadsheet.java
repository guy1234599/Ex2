package Ex2;


public class Spreadsheet {
    private Cell[][] cells;

    public Spreadsheet(int rows, int cols) {
        cells = new Cell[rows][cols];
    }

    public void set(int row, int col, Cell cell) {
        cells[row][col] = cell;
    }

    public String getValue(String cellRef) {
        int row = getRow(cellRef);
        int col = getCol(cellRef);

        if (row >= 0 && col >= 0 && row < cells.length && col < cells[0].length) {
            Cell cell = cells[row][col];
            return cell != null ? cell.getText() : "0"; // ברירת מחדל אם התא ריק
        }
        throw new IllegalArgumentException("Invalid cell reference: " + cellRef);
    }

    private int getRow(String cellRef) {
        String rowPart = cellRef.replaceAll("[A-Z]+", "");
        return Integer.parseInt(rowPart) - 1; // שורות מתחילות מ-0
    }

    private int getCol(String cellRef) {
        String colPart = cellRef.replaceAll("\\d+", "");
        int col = 0;
        for (char c : colPart.toCharArray()) {
            col = col * 26 + (c - 'A' + 1);
        }
        return col - 1; // עמודות מתחילות מ-0
    }
}
