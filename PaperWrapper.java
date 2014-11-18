package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PaperWrapper {
	static final Integer firstLevel = 0;

	public PaperWrapper(int rowCount, int columnCount) throws Exception {
		if (rowCount % 2 != 0 || columnCount % 2 != 0)
			throw new Exception("Invalid configuration");

		init(rowCount, columnCount);
	}

	public void foldFromLeft() {
		TreeMap<Integer, Map<Integer, List<Integer>>> mp = new TreeMap<Integer, Map<Integer, List<Integer>>>();
		int layerCount = getLayerCount();
		int rowCount = getRowCount();
		int columnCount = getColumnCount();

		for (int iLevel = 0; iLevel < layerCount; iLevel++) {
			for (int i = 0; i < rowCount; i++) {
				List<Integer> srcRow = getList(_dataMap, iLevel, i);
				List<Integer> tgtRow = getList(mp, iLevel, i);
				List<Integer> tgtRowFolded = getList(mp, getFoldedLevel(iLevel, layerCount), i);
				for (int j = columnCount / 2; j < columnCount; j++)
					tgtRow.add(srcRow.get(j)); // Keep right side
				for (int j = columnCount / 2 - 1; j >= 0; j--) {
					tgtRowFolded.add(srcRow.get(j));
				}
			}
		}

		_dataMap = mp;
	}

	public void foldFromTop() {
		TreeMap<Integer, Map<Integer, List<Integer>>> mp = new TreeMap<Integer, Map<Integer, List<Integer>>>();
		int layerCount = getLayerCount();
		int rowCount = getRowCount();

		for (int iLevel = 0; iLevel < layerCount; iLevel++) {
			for (int i = 0; i < rowCount; i++) {
				List<Integer> srcRow = getList(_dataMap, iLevel, i);
				List<Integer> tgtRow = getList(mp, iLevel, i % 2);
				List<Integer> tgtRowFolded = getList(mp, getFoldedLevel(iLevel, layerCount), getFoldedRow(i, rowCount));

				if (i >= rowCount / 2)
					tgtRow.addAll(srcRow);
				else
					tgtRowFolded.addAll(srcRow);
			}
		}

		_dataMap = mp;
	}

	// Top row to bottom row
	public List<Integer> showSequence() {
		List<Integer> dt = new ArrayList<Integer>();

		int layerCount = getLayerCount();
		int rowCount = getRowCount();
		List<Integer> rowData = null;
		for (int i = 0; i < rowCount; i++) {
			for (int iLevel = layerCount - 1; iLevel >= 0; iLevel--) {
				rowData = getList(_dataMap, iLevel, i);
				dt.addAll(rowData);
			}
		}

		return dt;
	}

	private void init(int rowCount, int columnCount) {
		_dataMap = new TreeMap<Integer, Map<Integer, List<Integer>>>();

		int cellValue = 1;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				getList(_dataMap, firstLevel, i).add(cellValue);
				cellValue++;
			}
		}
	}

	private List<Integer> getList(Map<Integer, Map<Integer, List<Integer>>> mp, Integer iLevel, Integer iRow) {
		Map<Integer, List<Integer>> rowMap = mp.get(iLevel);
		if (rowMap == null) {
			rowMap = new TreeMap<Integer, List<Integer>>();
			mp.put(iLevel, rowMap);
		}

		List<Integer> dt = rowMap.get(iRow);
		if (dt == null) {
			dt = new ArrayList<Integer>();
			rowMap.put(iRow, dt);
		}

		return dt;
	}

	private int getLayerCount() {
		return _dataMap.keySet().size();
	}

	private int getRowCount() {
		return _dataMap.get(firstLevel).keySet().size();
	}

	private int getColumnCount() {
		return getList(_dataMap, firstLevel, 0).size();
	}

	private int getFoldedLevel(int srcLevel, int srcLevelCount) {
		// 0 -- 0 + (4-0) * 2 - 1 = 4 * 2 - 0 - 1 = 7 - 0
		// 1 -- 7 - 1 = 6
		// 2 -- 7 - 2 = 5
		// 3 -- 7 - 3 = 4
		return srcLevelCount * 2 - srcLevel - 1;
	}

	private int getFoldedRow(int srcRow, int rowCount) {
		// 0 -- 3
		// 1 -- 2
		// 2 -- 1
		// 3 -- 0
		return (rowCount - srcRow - 1) % 2;
	}

	public static void main(String[] args) {
		try {
			PaperWrapper pw = new PaperWrapper(4, 4);
			System.out.println(pw.showSequence());

			pw.foldFromTop();
			System.out.println(pw.showSequence());

			pw.foldFromTop();
			System.out.println(pw.showSequence());

			pw.foldFromLeft();
			System.out.println(pw.showSequence());

			pw.foldFromLeft();
			System.out.println(pw.showSequence());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// iLevel (starting at bottom 1) --> row --> row values
	private Map<Integer, Map<Integer, List<Integer>>> _dataMap;
}
