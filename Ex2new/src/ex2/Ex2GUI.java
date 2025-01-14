package ex2;

import java.awt.Color;
import java.io.IOException;

public class Ex2GUI {
	private static ex2.Sheet table;
	private static ex2.Index2D cord = null;

	public Ex2GUI() {
	}

	public static void main(String[] a) {
		table = new ex2.Ex2Sheet(9, 17);
		testSimpleGUI(table);
	}

	public static void testSimpleGUI(ex2.Sheet table) {
		ex2.StdDrawEx2.setCanvasSize(1200, 600);
		ex2.StdDrawEx2.setScale(0.0, 20.0);
		ex2.StdDrawEx2.setPenRadius(0.001);
		ex2.StdDrawEx2.enableDoubleBuffering();
		table.eval();

		while(true) {
			ex2.StdDrawEx2.clear();
			drawFrame();
			drawCells();
			ex2.StdDrawEx2.show();
			int xx = ex2.StdDrawEx2.getXX();
			int yy = ex2.StdDrawEx2.getYY();
			inputCell(xx, yy);
			StdDrawEx2.pause(10);
		}
	}

	public static void save(String fileName) {
		try {
			table.save(fileName);
		} catch (IOException var2) {
			IOException e = var2;
			e.printStackTrace();
		}

	}

	public static void load(String fileName) {
		try {
			table.load(fileName);
		} catch (IOException var2) {
			IOException e = var2;
			e.printStackTrace();
		}

	}

	private static Color getColorFromType(int t) {
		Color ans = Color.GRAY;
		if (t == -1) {
			ans = StdDrawEx2.BOOK_RED;
		}

		if (t == 2) {
			ans = Color.BLACK;
		}

		if (t == 3) {
			ans = Color.BLUE;
		}

		if (t == -2) {
			ans = Color.RED;
		}

		return ans;
	}

	private static void drawFrame() {
		ex2.StdDrawEx2.setPenColor(ex2.StdDrawEx2.BLACK);
		int max_y = table.height();
		double x_space = 2.0;
		double x_start = 3.0;
		double y_height = 0.4;

		for(int y = 0; y < max_y; ++y) {
			double xs = (double)y * x_space;
			double xc = x_start + (double)y * x_space;
			ex2.StdDrawEx2.line(0.0, (double)(y + 1), 20.0, (double)(y + 1));
			ex2.StdDrawEx2.line(xs, 0.0, xs, (double)max_y);
			int yy = max_y - (y + 1);
			ex2.StdDrawEx2.text(1.0, (double)y + y_height, "" + yy);
			double var10001 = (double)max_y + y_height;
			String var10002 = ex2.Ex2Utils.ABC[y];
			ex2.StdDrawEx2.text(xc, var10001, "" + var10002);
		}

	}

	private static void drawCells() {
		StdDrawEx2.setPenColor(StdDrawEx2.BLACK);
		int max_y = table.height();
		int maxx = table.width();
		double x_space = 2.0;
		double x_start = 3.0;
		double y_height = 0.4;

		for(int x = 0; x < maxx; ++x) {
			double xc = x_start + (double)x * x_space;

			for(int y = 0; y < max_y; ++y) {
				String w = table.value(x, y);
				Cell cc = table.get(x, y);
				int t = cc.getType();
				StdDrawEx2.setPenColor(getColorFromType(t));
				int max = Math.min(8, w.length());
				w = w.substring(0, max);
				double yc = (double)max_y - ((double)(y + 1) - y_height);
				StdDrawEx2.text(xc, yc, w);
			}
		}

	}

	private static void inputCell(int xx, int yy) {
		if (xx != -1 && yy != -1 && table.isIn(xx, yy)) {
			if (table.isIn(xx, yy)) {
				Cell cc = table.get(xx, yy);
				cord = new CellEntry(xx, yy);
				String var10000 = String.valueOf(cord);
				String ww = var10000 + ": " + cc.getData();
				ex2.StdDrawEx2.text(3.0, 19.0, ww);
				StdDrawEx2.show();
				String c = StdDrawEx2.getCell(cord, cc.getData());
				String s1 = table.get(xx, yy).getData();
				if (c != null) {
					table.set(xx, yy, c);
					int[][] calc_d = table.depth();
					if (calc_d[xx][yy] == -1) {
						table.get(xx, yy).setType(-1);
					}
				}

				table.eval();
				StdDrawEx2.resetXY();
			}

		}
	}
}
