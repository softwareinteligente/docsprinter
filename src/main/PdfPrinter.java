package main;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfPrinter {

	String imgFilepath;
	String pdfFilepath;

	DocPanel currentDoc;
	DocText[] textAreas;
	int imgWidth, imgHeight, pdfWidth, pdfHeight;

	public PdfPrinter (DocPanel currentDoc) {
		this.currentDoc = currentDoc;
		this.pdfFilepath = currentDoc.pdfTemplateFilepath;
		this.imgFilepath = currentDoc.imgTemplateFilepath;
		textAreas = currentDoc.getDocTextAreas ();
		getDocSizes ();
	}

	public void printDocument () {
		try {
			// Create a new PDF mainDocument from existing PDF
			PDDocument mainDocument = new PDDocument ();
			PDDocument document = PDDocument.load (new File (pdfFilepath));
			PDPage page = document.getPage (0); // Assuming you want to add text to the first page

			//PDDocument document = new PDDocument ();
			//PDPage page = new PDPage ();
			//document.addPage (page);
			for (DocText textArea : textAreas) {
				String text = textArea.getText ();
				String fontSizeName = textArea.fontSizeName;
				Rectangle imgBox = textArea.getBounds ();
				Rectangle pdfBox = this.getPdfBox (imgBox);
				this.printBox (document, page, text, fontSizeName, pdfBox);
			}

			document.save (currentDoc.pdfFilepath);
			document.close ();
		} catch (IOException ex) {
			Logger.getLogger (PdfPrinter.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	// Print text within a box region
	// Replaces system new line with "\n" to check as a single char
	// Then checks for large string to word wrapping with the last space 
	public void printBox (PDDocument document, PDPage page, String text, String fontSizeName, Rectangle pdfBox) {
		int margin = 0;
		AppendMode APPENDMODE = PDPageContentStream.AppendMode.APPEND;
		try (PDPageContentStream contentStream = new PDPageContentStream (document, page, APPENDMODE, true)) {
			PDType1Font font = PDType1Font.HELVETICA;
			int fontSize = DocGlobals.fontSizeNormal;
			switch (fontSizeName) {
				case "large":
					fontSize = DocGlobals.fontSizeLarge;
					margin = -8;
					break;
				case "small":
					fontSize = DocGlobals.fontSizeSmall;
					break;
			}
			float width = pdfBox.width;
			float textWidth = 0;
			float x = pdfBox.x;
			float y = pdfBox.y + margin;

			int lastSpaceIndex = -1;
			int startLineIndex = 0;

			text = text.replace (System.lineSeparator (), "\n");
			char[] charArray = text.toCharArray ();
			StringBuilder line = new StringBuilder ();
			int i = 0;
			while (i < text.length ()) {
				char c = charArray[i];
				String charString = "" + c;

				if (charString.equals ("\n")) {
					showLine (line, contentStream, font, fontSize, x, y, false);
					startLineIndex = ++i;
					y -= fontSize;
				} else {
					if (charString.equals (" "))
						lastSpaceIndex = i;
					textWidth = font.getStringWidth (line.toString ()) / 1000 * fontSize;
					if (textWidth < width) {
						line.append (c);
						i++;
					} else if (charString.equals (" ")) {
						showLine (line, contentStream, font, fontSize, x, y, false);
						startLineIndex = ++i;
						y -= fontSize;
					} else if (lastSpaceIndex > 0) {
						line = new StringBuilder (text.substring (startLineIndex, lastSpaceIndex));
						showLine (line, contentStream, font, fontSize, x, y, false);
						i = lastSpaceIndex + 1;
						lastSpaceIndex = -1;
						startLineIndex = i;
						y -= fontSize;
					} else {
						line = new StringBuilder (text.substring (startLineIndex, i));
						showLine (line, contentStream, font, fontSize, x, y, false);
						startLineIndex = ++i;
						y -= fontSize;
					}
				}
			}
			showLine (line, contentStream, font, fontSize, x, y, true);
		} catch (IOException ex) {
			System.out.println ("EXCEPCION: En printBox. Text: " + text + " Line:");
			Logger.getLogger (PdfPrinter.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	void showLine (StringBuilder line, PDPageContentStream contentStream, PDType1Font font, int fontSize, float x, float y, boolean ENDLINE) {
		try {
			contentStream.beginText ();
			contentStream.setFont (font, fontSize);
			this.setTextTransformation (contentStream);
			contentStream.newLineAtOffset (x, y);
			contentStream.showText (line.toString ());

			if (ENDLINE == false) {
				contentStream.newLine ();
				line.setLength (0);
			}
			contentStream.endText ();
		} catch (IOException ex) {
			System.out.println ("EXCEPCION: En showLine. Line:" + line);
			Logger.getLogger (PdfPrinter.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	void setTextTransformation (PDPageContentStream contentStream) {
		try {
			// Set transformation as printed string are upside-down
			AffineTransform affineTransform = new AffineTransform ();
			affineTransform.concatenate (AffineTransform.getRotateInstance (Math.PI, 0, 390)); // No translation
			affineTransform.concatenate (AffineTransform.getScaleInstance (-1, 1)); // No translation
			contentStream.setTextMatrix (affineTransform);
		} catch (IOException ex) {
			Logger.getLogger (PdfPrinter.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	public Rectangle getPdfBox (Rectangle imgBounds) {
		double conversionFactor = (float) pdfWidth / imgWidth;
		int xPdf = (int) (imgBounds.x * conversionFactor);
		int yPdf = -20 + pdfHeight - (int) (imgBounds.y * conversionFactor);
		int widthPdf = (int) (imgBounds.width * conversionFactor);
		int heightPdf = (int) (imgBounds.height * conversionFactor);

		return (new Rectangle (xPdf, yPdf, widthPdf, heightPdf));
	}

	public void getDocSizes () {
		try {
			// First: PDF page
			PDDocument mainDocument = new PDDocument ();
			PDDocument existingDocument = PDDocument.load (new File (pdfFilepath));
			PDPage existingPage = existingDocument.getPage (0); // Assuming you want to add text to the first page
			PDRectangle mediaBox = existingPage.getMediaBox ();
			pdfWidth = (int) mediaBox.getWidth ();
			pdfHeight = (int) mediaBox.getHeight ();
			existingDocument.close ();

			// Second: IMG page
			File imageFile = new File (imgFilepath);
			BufferedImage bufferedImage = ImageIO.read (imageFile);
			imgWidth = bufferedImage.getWidth ();
			imgHeight = bufferedImage.getHeight ();

			// Output the page dimensions
			System.out.println ("PDF Page Width: " + pdfWidth + " points");
			System.out.println ("PDF Page Height: " + pdfHeight + " points");
			System.out.println ("IMG Page Width: " + imgWidth + " points");
			System.out.println ("IMG Page Height: " + imgHeight + " points");
		} catch (IOException ex) {
			Logger.getLogger (DocController.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	public static void openPDF (String pdfFilepath) {
		try {
			// Specify the path to the PDF file you want to open
			File pdfFile = new File (pdfFilepath);

			if (Desktop.isDesktopSupported ()) {
				Desktop desktop = Desktop.getDesktop ();

				if (pdfFile.exists () && pdfFile.getName ().toLowerCase ().endsWith (".pdf"))
					desktop.open (pdfFile);
				else
					System.out.println (">>> The specified file is not a valid PDF.");
			} else
				System.out.println (">>> Desktop API is not supported on this platform.");
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}
}
