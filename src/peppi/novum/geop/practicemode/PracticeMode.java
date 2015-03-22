package peppi.novum.geop.practicemode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import peppi.novum.geop.Main;
import peppi.novum.geop.gui.practicemode.FinishWindow;
import peppi.novum.geop.gui.practicemode.PracticePane;
import peppi.novum.geop.gui.practicemode.SettingsPane;
import peppi.novum.geop.util.GeopWord;
import peppi.novum.geop.util.WordList;

public class PracticeMode {

	public static boolean ACTIVE = false;
	public static SettingsPane SETTINGS_PANE;
	public static PracticePane PANE;
	public static WordList WORD_LIST;

	public static int MODE = 0;
	public static int ORDER = 0;
	public static boolean CASE_SENSITIVE = true;

	private static List<PracticeWord> WORDS;
	public static HashMap<Integer, List<PracticeWord>> WRONG;
	public static HashMap<Integer, List<PracticeWord>> RIGHT;
	private static List<PracticeWord> FIRST_WRONG;
	private static boolean CLICKED = false;
	private static int TOTAL = 0;

	public static void load(final WordList wordList, final int mode, final int order, final boolean caseSensitive) {
		if (wordList == null) {
			return;
		}

		WORDS = new ArrayList<PracticeWord>();
		WRONG = new HashMap<Integer, List<PracticeWord>>();
		RIGHT = new HashMap<Integer, List<PracticeWord>>();
		FIRST_WRONG = new ArrayList<PracticeWord>();

		WORD_LIST = wordList;

		List<GeopWord> list = WORD_LIST.getWords();

		MODE = mode;
		ORDER = order;
		CASE_SENSITIVE = caseSensitive;

		for (GeopWord gw : list) {
			PracticeWord pw = new PracticeWord(gw.getPracticeWord(), gw.getDefinition());
			WORDS.add(pw);
		}

		Collections.shuffle(WORDS);

		PANE = new PracticePane();
		Main.MAIN_WINDOW.loadArea(PANE, PANE);

		TOTAL = WORDS.size();

		ACTIVE = true;

		next(false);
	}

	public static void reset() {
		PANE = null;
		MODE = 0;
		ORDER = 0;
		CASE_SENSITIVE = true;
		WORDS = null;
		WRONG = null;
		FIRST_WRONG = null;
		CLICKED = false;
		TOTAL = 0;

		ACTIVE = false;

		Main.MAIN_WINDOW.clearArea();
	}

	public static void click(String answer) {
		if (!CLICKED) {
			if (WORDS.size() > 0) {
				check(answer);
			}
		} else {
			CLICKED = false;
			next(true);
		}
	}

	public static void click() {
		if (!CLICKED) {
			if (WORDS.size() > 0) {
				showAnswer();
			}
		} else {
			CLICKED = false;
			next(true);
		}
	}

	public static void check(String answer) {
		PracticeWord word = WORDS.get(0);
		boolean wrong = false;

		if (ORDER == 0) {
			if (!CASE_SENSITIVE) {
				if (!answer.equalsIgnoreCase(word.getDefinition())) {
					wrong = true;
				}
			} else {
				if (!answer.equals(word.getDefinition())) {
					wrong = true;
				}
			}
		} else {
			if (!CASE_SENSITIVE) {
				if (!answer.equalsIgnoreCase(word.getWord())) {
					wrong = true;
				}
			} else {
				if (!answer.equals(word.getWord())) {
					wrong = true;
				}
			}
		}

		if (wrong) {
			if (!FIRST_WRONG.contains(word)) {
				FIRST_WRONG.add(word);
				WORDS.add(word);
			}

			boolean placed = false;

			for (Integer i : WRONG.keySet()) {
				if (!WRONG.get(i).contains(word) && (RIGHT.get(i) == null || (RIGHT.get(i) != null && !RIGHT.get(i).contains(word)))) {
					WRONG.get(i).add(word);
					placed = true;
					break;
				}
			}

			if (!placed) {
				int index = WRONG.size();
				WRONG.put(index, new ArrayList<PracticeWord>());
				WRONG.get(index).add(word);
			}

			if (WORDS.size() > 3) {
				WORDS.add(3, word);
			} else {
				WORDS.add(WORDS.size(), word);
			}

			CLICKED = true;

			PANE.setInputForeground(Color.RED);
			PANE.setInputEditable(false);

			if (ORDER == 0) {
				PANE.setAnswerText(WORDS.get(0).getDefinition());
			} else {
				PANE.setAnswerText(WORDS.get(0).getWord());
			}
		} else {
			boolean placed = false;

			for (Integer i : RIGHT.keySet()) {
				if (!RIGHT.get(i).contains(word) && (WRONG.get(i) == null || (WRONG.get(i) != null && !WRONG.get(i).contains(word)))) {
					RIGHT.get(i).add(word);
					placed = true;
					break;
				}
			}

			if (!placed) {
				int index = RIGHT.size();
				RIGHT.put(index, new ArrayList<PracticeWord>());
				RIGHT.get(index).add(word);
			}

			next(true);
		}
	}

	public static void showAnswer() {
		PANE.showAnswer(true);
		if (ORDER == 0) {
			PANE.setAnswer(WORDS.get(0).getDefinition());
		} else {
			PANE.setAnswer(WORDS.get(0).getWord());
		}
		PANE.showWrongRight(true);

		PANE.setCalculating(false);
	}

	public static void wrong() {
		PracticeWord word = WORDS.get(0);

		if (!FIRST_WRONG.contains(word)) {
			FIRST_WRONG.add(word);
			WORDS.add(word);
		}

		boolean placed = false;

		for (Integer i : WRONG.keySet()) {
			if (!WRONG.get(i).contains(word) && (RIGHT.get(i) == null || (RIGHT.get(i) != null && !RIGHT.get(i).contains(word)))) {
				WRONG.get(i).add(word);
				placed = true;
				break;
			}
		}

		if (!placed) {
			int index = WRONG.size();
			WRONG.put(index, new ArrayList<PracticeWord>());
			WRONG.get(index).add(word);
		}

		if (WORDS.size() > 3) {
			WORDS.add(3, word);
		} else {
			WORDS.add(WORDS.size(), word);
		}

		next(true);
	}

	public static void right() {
		PracticeWord word = WORDS.get(0);
		boolean placed = false;

		for (Integer i : RIGHT.keySet()) {
			if (!RIGHT.get(i).contains(word) && (WRONG.get(i) == null || (WRONG.get(i) != null && !WRONG.get(i).contains(word)))) {
				RIGHT.get(i).add(word);
				placed = true;
				break;
			}
		}

		if (!placed) {
			int index = RIGHT.size();
			RIGHT.put(index, new ArrayList<PracticeWord>());
			RIGHT.get(index).add(word);
		}

		next(true);
	}

	public static void next(boolean remove) {
		if (remove) {
			WORDS.remove(0);
		}

		if (WORDS.size() > 0) {
			if (ORDER == 0) {
				PANE.setText(WORDS.get(0).getWord());
			} else {
				PANE.setText(WORDS.get(0).getDefinition());
			}

			if (MODE == 0) {
				PANE.setInputForeground(Color.BLACK);
				PANE.setInputText("");
				PANE.setInputEditable(true);
			} else if (MODE == 1) {
				PANE.showAnswer(false);
				PANE.showWrongRight(false);
				PANE.setAnswer("");
			}

			PANE.setAnswerText("");
			PANE.setWordsLeft(WORDS.size());

			PANE.updateStats();
			PANE.setCalculating(false);
		} else {
			PANE.setInputForeground(Color.BLACK);
			PANE.setText("Finished");
			PANE.setInputText("");
			PANE.setInputEditable(true);
			PANE.setAnswerText("");
			PANE.showAnswer(false);
			PANE.showWrongRight(false);
			PANE.setAnswer("");

			Main.MAIN_WINDOW.clearArea();

			new FinishWindow(TOTAL, WRONG);
		}
	}

}
