package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class DocController {

	DocGlobals model;
	DocFrame docFrame;
	DocPanel currentDoc;
	Timer autoSaveTimer;

	public DocController () {
		model = new DocGlobals ();
		DocGlobals.initFilePaths ();
		initComponents ();
	}

	public void initComponents () {
		Utils.copyResourcesToTemporalPath (this, DocGlobals.temporalPath);
		docFrame = new DocFrame ();
		docFrame.setSize (1100, 800);
		docFrame.setVisible (true);
		docFrame.setController (this);
	}

	DocPanel onNewDocument (String documentType, String newFilepath) {
		DocPanel newDocPanel = null;
		if ((documentType.equals ("cartaporte")))
			newDocPanel = new DocPanelCartaporte ();
		else if ((documentType.equals ("manifiesto")))
			newDocPanel = new DocPanelManifiesto ();

		newDocPanel.init (newFilepath);
		docFrame.addTab (newDocPanel.docName, newDocPanel);
		currentDoc = newDocPanel;
		return (newDocPanel);
	}

	public void onOpenDocument () {
		String existingFilepath = docFrame.selectFileFromFileChooser ();
		String documentType = this.getDocumentTypeFromFile (existingFilepath);
		currentDoc = onNewDocument (documentType, existingFilepath);
		this.loadJsonFileToDoc (currentDoc);
	}

	void onSaveCurrentToPDF () {
		System.out.println (">>> Guardando en formato PDF...");
		PdfPrinter pdfPrinter = new PdfPrinter (currentDoc);
		pdfPrinter.printDocument ();
	}

	public void onSaveCurrentToJson () {
		System.out.println ("Guardando en formato JSON...");
		this.writeToDocJsonFile (currentDoc);
	}

	void writeToDocJsonFile (DocPanel currentDoc) {
		System.out.println (">>> Guardando el archivo de datos en: " + currentDoc.jsonFilepath);
		String docType = currentDoc.docType;
		DocTextArea[] textAreas = currentDoc.textAreas;

		JsonObject field = new JsonObject ();
		field.addProperty ("doctype", currentDoc.docType);

		for (int i = 0; i < textAreas.length; i++) {
			DocTextArea textArea = textAreas[i];
			String text = textArea.getText ();
			String key = String.format ("txt%02d", i);
			field.addProperty (key, text);
		}
		// Create a Gson object with pretty printing
		Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();
		// Convert the JSON object to a JSON string
		String jsonString = gson.toJson (field);
		// Write the JSON string to a file
		try (FileWriter writer = new FileWriter (currentDoc.jsonFilepath)) {
			writer.write (jsonString);
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	void loadJsonFileToDoc (DocPanel existingDocPanel) {
		currentDoc = existingDocPanel;
		DocTextArea[] textAreas = null;
		String jsonFilepath = existingDocPanel.jsonFilepath;

		if (jsonFilepath != null)
			try {
			FileReader reader = new FileReader (jsonFilepath); 	// Create a FileReader to read the JSON file
			// Parse the JSON content using Gson's JsonParser
			JsonObject jsonObject = JsonParser.parseReader (reader).getAsJsonObject ();
			reader.close ();

			// Get DoctTextAreas
			textAreas = currentDoc.getDocTextAreas ();
			for (int i = 0; i < textAreas.length; i++) {
				String key = String.format ("txt%02d", i);
				String text = jsonObject.get (key).getAsString ();
				DocTextArea dta = textAreas[i];
				dta.loadText (text);
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger (DocController.class.getName ()).log (Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger (DocController.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	String getDocumentTypeFromFile (String jsonFilepath) {
		String docType = null;

		JsonObject jsonObject;
		// Parse the JSON content using Gson's JsonParser
		try (FileReader reader = new FileReader (jsonFilepath)) {
			// Parse the JSON content using Gson's JsonParser
			jsonObject = JsonParser.parseReader (reader).getAsJsonObject ();
			docType = jsonObject.get ("doctype").getAsString ();
		} catch (IOException ex) {
			Logger.getLogger (DocController.class.getName ()).log (Level.SEVERE, null, ex);
		}

		return docType;
	}

	void startSavingTimer () {
		// 60000 milliseconds (1 minute)
		if (autoSaveTimer != null)
			autoSaveTimer.stop ();

		autoSaveTimer = new Timer (10000, e -> onSaveCurrentToJson ());
		autoSaveTimer.start ();
	}

	void onExit () {
		System.exit (0);
	}

	public static void main (String args[]) {
		// Set the Nimbus look and feel
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels ()) {
				if ("Nimbus".equals (info.getName ())) {
					javax.swing.UIManager.setLookAndFeel (info.getClassName ());
					break;

				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger (DocFrame.class
				.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater (new Runnable () {
			public void run () {
				new DocController ();
			}
		});
	}

}
