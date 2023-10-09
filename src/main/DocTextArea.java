package main;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class DocTextArea extends JTextPane {

	public String fontSizeName = "normal";

	public DocTextArea () {
		super ();
		addListeners ();
		setFocusNavigation ();

		setBorder (BorderFactory.createLineBorder (new java.awt.Color (204, 204, 204)));
		this.setAutoscrolls (false);

		this.setFontSize ("normal");
		this.setAlignment ("alignLeft");
		this.setLineSpacing ();
	}

	// Attach an upper case document filter to the text pane's document
	void setUpperCaseFilter () {
		AbstractDocument doc = (AbstractDocument) getDocument ();
		doc.setDocumentFilter (new UpperCaseDocument ());
	}

	public void setFontSize (String fontSizeName) {
		this.fontSizeName = fontSizeName;
		int fontSize;
		if (fontSizeName.equals ("large"))
			fontSize = 14;
		else if (fontSizeName.equals ("small"))
			fontSize = 8;
		else
			fontSize = 10;

		// Create a SimpleAttributeSet with the desired font
		SimpleAttributeSet attributeSet = new SimpleAttributeSet ();
		//StyleConstants.setFontFamily (attributeSet, "Arial"); // Set the font family
		//"Open Sans Condensed", 0, 14
		StyleConstants.setFontSize (attributeSet, fontSize); // Set the font size
		StyleConstants.setBold (attributeSet, true); // Set bold style
		//StyleConstants.setItalic (attributeSet, true); // Set italic style

		// Apply the font attribute to the text
		setCharacterAttributes (attributeSet, false);
	}

	public void setAlignment (String alignmentName) {
		// Create a SimpleAttributeSet to set text alignment to left
		StyledDocument doc = this.getStyledDocument ();
		SimpleAttributeSet leftAlignment = new SimpleAttributeSet ();
		if (alignmentName.equals ("alignLeft"))
			StyleConstants.setAlignment (leftAlignment, StyleConstants.ALIGN_LEFT);
		else if (alignmentName.equals ("alignRight"))
			StyleConstants.setAlignment (leftAlignment, StyleConstants.ALIGN_RIGHT);
		else if (alignmentName.equals ("alignCenter"))
			StyleConstants.setAlignment (leftAlignment, StyleConstants.ALIGN_CENTER);
		else
			StyleConstants.setAlignment (leftAlignment, StyleConstants.ALIGN_JUSTIFIED);

		// Apply the left alignment attribute to the entire document
		doc.setParagraphAttributes (0, doc.getLength (), leftAlignment, false);
	}

	public void setLineSpacing () {
		// Adjust JTextPane line spacing by creating a custom document with adjusted line spacing
		StyledDocument document = new DefaultStyledDocument ();
		SimpleAttributeSet lineSpacing = new SimpleAttributeSet ();
		StyleConstants.setLineSpacing (lineSpacing, 0.00001f); // Set line spacing factor (e.g., 1.5 for 1.5x spacing)
		document.setParagraphAttributes (0, document.getLength (), lineSpacing, true);
		setStyledDocument (document);
	}

	// Add a document filter to enforce character limit per line
	public void setParameters (int maxLines, int maxChars, String fontSizeName) {
		this.fontSizeName = fontSizeName;
		//((AbstractDocument) getDocument ()).setDocumentFilter (new CharacterLimitDocumentFilter (maxLines, maxChars));

		// Attach an upper case document filter to the text pane's document
		AbstractDocument doc = (AbstractDocument) getDocument ();
		doc.setDocumentFilter (new UpperCaseDocument ());

		setFontSize (fontSizeName);
	}

	public void setParameters (int maxLines, int maxChars, String fontSizeName, String alignName) {
		setParameters (maxLines, maxChars, fontSizeName);
		setAlignment (alignName);
	}

	public void addListeners () {
		// Add a custom KeyListener to the JTextPane
		this.addKeyListener (new KeyAdapter () {
			@Override
			public void keyPressed (KeyEvent e) {
				if (e.getKeyCode () == KeyEvent.VK_TAB) {
					// Consume the TAB key event to prevent inserting a tab character
					e.consume ();
					// Move the focus to the next component
					KeyboardFocusManager.getCurrentKeyboardFocusManager ().focusNextComponent ();
				}
			}
		});
	}

	public void setFocusNavigation () {
		setFocusTraversalKeys (KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
	}

	public void loadText (String text) {
		DocumentFilter docFilter = ((AbstractDocument) getDocument ()).getDocumentFilter ();
		((AbstractDocument) getDocument ()).setDocumentFilter (null);
		super.setText (text);
		((AbstractDocument) getDocument ()).setDocumentFilter (docFilter);
	}
}

//--------------------------------------------------------------
// Document filter to upper case typing
//--------------------------------------------------------------
class UpperCaseDocument extends DocumentFilter {

	@Override
	public void insertString (DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
		throws BadLocationException {
		// Convert the text to uppercase and insert it
		super.insertString (fb, offset, text.toUpperCase (), attr);
	}

	@Override
	public void replace (DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
		throws BadLocationException {
		// Convert the text to uppercase and replace it
		super.replace (fb, offset, length, text.toUpperCase (), attrs);
	}
}

//--------------------------------------------------------------
// Control for  writen text to remain within maxLines and maxChars
//--------------------------------------------------------------
class CharacterLimitDocumentFilter extends DocumentFilter {

	private int maxChars;
	private int maxLines;

	public CharacterLimitDocumentFilter (int maxLines, int maxChars) {
		this.maxChars = maxChars;
		this.maxLines = maxLines;
	}

	@Override
	public void insertString (DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
		//System.out.println (">>> Inserting text: " + text);
		if (text == null)
			return;
		text = text.toUpperCase ();
		Document doc = fb.getDocument ();
		String currentText = doc.getText (0, doc.getLength ());

		// Check if adding the text would exceed the character limit per line
		if (countCharactersInLine (currentText, offset) + text.length () <= maxChars)
			super.insertString (fb, offset, text, attr);
	}

	@Override
	public void replace (DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		//System.out.println (">>> Replacing text: " + text + ", Offset:" + offset);
		if (text == null)
			return;
		text = text.toUpperCase ();

		Document doc = fb.getDocument ();
		String currentText = doc.getText (0, doc.getLength ());

		// Check if replacing the text would exceed the character limit per line
		String[] lines = currentText.split (System.lineSeparator ());
		int nLines = lines.length;

		if (text.equals (System.lineSeparator ()) && nLines < maxLines) {
			super.replace (fb, offset, length, System.lineSeparator (), attrs);
			return;
		}

		int nChars = countCharactersInLine (currentText, offset) + text.length ();
		if (nChars <= maxChars)
			if (text.equals (System.lineSeparator ()) == false || nLines < maxLines) {
				super.replace (fb, offset, length, text, attrs);
				currentText = doc.getText (0, doc.getLength ());
				for (String l : currentText.split (System.lineSeparator ())) {
					if (l.length () > maxChars) {
						super.remove (fb, offset, System.lineSeparator ().length ());
						break;
					}
				}
			}
	}

	private int countCharactersInLine (String text, int offset) {
		int lineStart = text.lastIndexOf ("\n", offset) + 1;
		int lineEnd = text.indexOf (System.lineSeparator (), offset);
		//printr ("LineStart: " + lineStart + " LineEnd: " + lineEnd + " Text: " + text);
		if (lineEnd == -1)
			lineEnd = text.length ();
		return lineEnd - lineStart;
	}

	public void printr (String stringWithEscapeSequences) {
		// Print the string without interpreting escape sequences
		for (int i = 0; i < stringWithEscapeSequences.length (); i++) {
			char c = stringWithEscapeSequences.charAt (i);
			System.out.print (c);
		}
	}
}
