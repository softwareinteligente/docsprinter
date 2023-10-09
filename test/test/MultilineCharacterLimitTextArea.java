package test;


import javax.swing.*;
import javax.swing.text.*;

public class MultilineCharacterLimitTextArea {

	public static void main (String[] args) {
		System.out.println (">>> Main");
		SwingUtilities.invokeLater (() -> {
			JFrame frame = new JFrame ("Multiline Character Limit TextArea");
			frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

			int maxLines = 4;
			int maxChars = 5; // Specify the maximum characters per line

			JTextArea textArea = new JTextArea ();
			textArea.setLineWrap (true); // Enable word wrapping
			textArea.setWrapStyleWord (true); // Wrap at word boundaries

			// Add a document filter to enforce character limit per line
			CharacterLimitDocumentFilter chaLim = new CharacterLimitDocumentFilter (maxLines, maxChars);

			((AbstractDocument) textArea.getDocument ()).setDocumentFilter (new CharacterLimitDocumentFilter (maxLines, maxChars));

			frame.getContentPane ().add (new JScrollPane (textArea));
			frame.setSize (400, 300);
			frame.setVisible (true);
		});
	}
}

// Control that writen text remains within TextArea limits
class CharacterLimitDocumentFilter extends DocumentFilter {

	private int maxChars;
	private int maxLines;

	public CharacterLimitDocumentFilter (int maxLines, int maxChars) {
		System.out.println (">>> constructor");
		this.maxChars = maxChars;
		this.maxLines = maxLines;
	}

	@Override
	public void insertString (FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
		System.out.println (">>> insert");
		if (text == null)
			return;
		Document doc = fb.getDocument ();
		String currentText = doc.getText (0, doc.getLength ());

		// Check if adding the text would exceed the character limit per line
		if (countCharactersInLine (currentText, offset) + text.length () <= maxChars)
			super.insertString (fb, offset, text, attr);
	}

	@Override
	public void replace (FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		System.out.println (">>> replace");
		if (text == null)
			return;
		Document doc = fb.getDocument ();
		String currentText = doc.getText (0, doc.getLength ());

		// Check if replacing the text would exceed the character limit per line
		String[] lines = currentText.split ("\n");
		int nLines = lines.length;

		if (text.equals ("\n") && nLines < maxLines) {
			super.replace (fb, offset, length, "\n", attrs);
			return;
		}
		System.out.println (">>> After");

		int nChars = countCharactersInLine (currentText, offset) + text.length ();
		if (nChars <= maxChars)
			if (text.equals ("\n") == false || nLines <= maxLines) {
				super.replace (fb, offset, length, text, attrs);
				currentText = doc.getText (0, doc.getLength ());
				for (String l : currentText.split ("\n")) {
					if (l.length () > maxChars) {
						super.remove (fb, offset, 1);
						break;
					}
				}
			}
	}

	private int countCharactersInLine (String text, int offset) {
		int lineStart = text.lastIndexOf ('\n', offset) + 1;
		int lineEnd = text.indexOf ('\n', offset);
		if (lineEnd == -1)
			lineEnd = text.length ();
		return lineEnd - lineStart;
	}
}
