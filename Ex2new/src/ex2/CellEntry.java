package ex2;


import ex2.Index2D;

public class CellEntry implements Index2D {
    private String data;
    private int x;
    private int y;

    public CellEntry(int x, int y, String data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public CellEntry(int x, int y) {
        this(x, y, (String)null);
    }

    public boolean isValid() {
        if (this.data == null) {
            return false;
        } else if (this.data.length() >= 2 && this.data.length() <= 3) {
            if (!Character.isUpperCase(this.data.charAt(0))) {
                return false;
            } else {
                for(int i = 1; i < this.data.length(); ++i) {
                    if (!Character.isDigit(this.data.charAt(i))) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public int getX() {
        return this.isValid() ? this.data.charAt(0) - 65 : -1;
    }

    public int getY() {
        return this.isValid() ? Integer.parseInt(this.data.substring(1)) : -1;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String convertX(int x) {
        return x >= 0 && x < 26 ? String.valueOf((char)(65 + x)) : null;
    }

    public static String convertY(int y) {
        return y >= 0 ? String.valueOf(y) : null;
    }

    public String getCellReference() {
        String var10000 = convertX(this.x);
        return var10000 + convertY(this.y);
    }

    public String getData() {
        return this.data;
    }

    public String toString() {
        String var10000 = this.getCellReference();
        return var10000 + (this.data != null ? this.data : "");
    }
}
