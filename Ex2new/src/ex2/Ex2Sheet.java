package ex2;

import ex2.Cell;
import ex2.SCell;
import ex2.Sheet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ex2Sheet implements Sheet {
    public static Cell[][] table;

    public Ex2Sheet(int rows, int cols) {
        table = new SCell[rows][cols];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                table[i][j] = new SCell();
            }
        }
    }

    public Ex2Sheet() {
        this(9, 17);
    }

    public String value(int row, int col) {
        String result = "";
        Cell cell = this.getCell(row, col);
        if (cell != null) {
            if (cell.getType() == 3) {
                try {
                    double computedValue = SCell.computeForms(cell.getData());
                    result = String.valueOf(computedValue);
                } catch (Exception e) {
                    result = "ERR_FORM!";
                }
            } else {
                result = cell.toString();
            }
        }

        return result;
    }

    public Cell get(int row, int col) {
        return this.isIn(row, col) ? table[row][col] : null;
    }

    public Cell get(String cords) {
        Cell ans = null;
        return (Cell)ans;
    }

    public int width() {
        return table.length;
    }

    public int height() {
        return table[0].length;
    }

    public void set(int row, int col, String value) {
        if (this.isIn(row, col)) {
            Cell cell = this.get(row, col);
            if (cell == null) {
                cell = new SCell();
                table[row][col] = cell;
            }

            cell.setData(value);
            if (value != null && !value.trim().isEmpty()) {
                if (value.startsWith("=")) {
                    cell.setType(3);
                } else if (SCell.isNumber(value)) {
                    cell.setType(2);
                } else {
                    cell.setType(1);
                }
            } else {
                cell.setType(1);
            }
        }
    }

    public void eval() {
        int[][] cellDepths = this.depth();

        for (int row = 0; row < this.width(); ++row) {
            for (int col = 0; col < this.height(); ++col) {
                Cell cell = this.get(row, col);
                String data = cell.getData();
                if (data != null && !data.isEmpty()) {
                    if (cellDepths[row][col] == -1) {
                        cell.setData("ERR_CYCLE!");
                        cell.setType(-1);
                        cell.setComputedValue("ERR_CYCLE!");
                    } else if (data.startsWith("=")) {
                        if (!SCell.isForm(data)) {
                            cell.setData("ERR_FORM!");
                            cell.setType(-2);
                            cell.setComputedValue("ERR_FORM!");
                        } else {
                            try {
                                double result = SCell.computeForms(data);
                                if (result == -1.0) {
                                    cell.setData("ERR_CYCLE!");
                                    cell.setType(-1);
                                    cell.setComputedValue("ERR_CYCLE!");
                                } else if (result == -2.0) {
                                    cell.setData("ERR_FORM!");
                                    cell.setType(-2);
                                    cell.setComputedValue("ERR_FORM!");
                                } else {
                                    cell.setComputedValue(String.valueOf(result));
                                }
                            } catch (Exception e) {
                                cell.setData("ERR_FORM!");
                                cell.setType(-2);
                                cell.setComputedValue("ERR_FORM!");
                            }
                        }
                    } else {
                        cell.setComputedValue(cell.getData());
                    }
                } else {
                    cell.setComputedValue("");
                }
            }
        }
    }

    public boolean isIn(int row, int col) {
        return row >= 0 && row < this.width() && col >= 0 && col < this.height();
    }

    public int[][] depth() {
        int[][] depths = new int[this.width()][this.height()];

        for (int row = 0; row < this.width(); ++row) {
            for (int col = 0; col < this.height(); ++col) {
                depths[row][col] = -2;
            }
        }

        for (int row = 0; row < this.width(); ++row) {
            for (int col = 0; col < this.height(); ++col) {
                if (depths[row][col] == -2) {
                    this.calculateDepth(row, col, depths, new HashSet<>());
                }
            }
        }

        return depths;
    }

    private int calculateDepth(int row, int col, int[][] depths, Set<String> visited) {
        if (depths[row][col] != -2) {
            return depths[row][col];
        } else {
            Cell cell = this.get(row, col);
            String data = cell.getData();
            if (cell != null && data != null && !data.isEmpty()) {
                if (!data.startsWith("=")) {
                    depths[row][col] = 0;
                    return 0;
                } else {
                    String currentCell = row + "," + col;
                    if (visited.contains(currentCell)) {
                        depths[row][col] = -1;
                        return -1;
                    } else {
                        visited.add(currentCell);
                        String formula = data.substring(1).trim();
                        Pattern cellPattern = Pattern.compile("[A-Z][0-9]+");
                        Matcher matcher = cellPattern.matcher(formula);
                        if (!matcher.find()) {
                            depths[row][col] = 0;
                            visited.remove(currentCell);
                            return 0;
                        } else {
                            matcher.reset();
                            int maxDepth = -1;

                            while (matcher.find()) {
                                String cellRef = matcher.group();
                                int refRow = cellRef.charAt(0) - 65;
                                int refCol = Integer.parseInt(cellRef.substring(1));
                                if (this.isIn(refRow, refCol)) {
                                    int dependencyDepth = this.calculateDepth(refRow, refCol, depths, visited);
                                    if (dependencyDepth == -1) {
                                        depths[row][col] = -1;
                                        visited.remove(currentCell);
                                        return -1;
                                    }

                                    maxDepth = Math.max(maxDepth, dependencyDepth);
                                }
                            }

                            visited.remove(currentCell);
                            depths[row][col] = maxDepth + 1;
                            return depths[row][col];
                        }
                    }
                }
            } else {
                depths[row][col] = 0;
                return 0;
            }
        }
    }

    public void load(String fileName) throws IOException {
        for (int row = 0; row < this.width(); ++row) {
            for (int col = 0; col < this.height(); ++col) {
                this.set(row, col, "");
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        try {
            boolean isFirstLine = true;

            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        try {
                            int row = Integer.parseInt(parts[0]);
                            int col = Integer.parseInt(parts[1]);
                            String data = parts[2];
                            if (this.isIn(row, col)) {
                                this.set(row, col, data);
                            }
                        } catch (NumberFormatException e) {
                            // Ignore invalid data
                        }
                    }
                }
            }
        } finally {
            reader.close();
        }

        this.eval();
    }

    public void save(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        try {
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment");
            writer.newLine();

            for (int row = 0; row < this.width(); ++row) {
                for (int col = 0; col < this.height(); ++col) {
                    Cell cell = this.get(row, col);
                    String data = cell.getData();
                    if (data != null && !data.isEmpty()) {
                        writer.write(row + "," + col + "," + data);
                        writer.newLine();
                    }
                }
            }
        } finally {
            writer.close();
        }
    }

    public String eval(int row, int col) {
        Cell cell = this.get(row, col);
        if (cell == null) {
            return "";
        } else {
            String data = cell.getData();
            if (data != null && !data.isEmpty()) {
                if (data.startsWith("=")) {
                    if (!SCell.isForm(data)) {
                        cell.setType(-2);
                        return "ERR_FORM!";
                    } else {
                        try {
                            int[][] depths = this.depth();
                            if (depths[row][col] == -1) {
                                cell.setType(-1);
                                return "ERR_CYCLE!";
                            } else {
                                Double result = SCell.computeForms(data);
                                if (result == -2.0) {
                                    cell.setType(-2);
                                    return "ERR_FORM!";
                                } else {
                                    return String.valueOf(result);
                                }
                            }
                        } catch (Exception e) {
                            cell.setType(-2);
                            return "ERR_FORM!";
                        }
                    }
                } else {
                    return data;
                }
            } else {
                return "";
            }
        }
    }

    public static Cell getCell(int row, int col) {
        return table[row][col];
    }
}
