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
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

public class DocController {
	DocGlobals model;
	DocFrame docFrame;
	DocBar docBar;
	JTabbedPane docsTabs;
	DocPanel currentDoc;
	Timer autoSaveTimer;

	public DocController () {
		model = new DocGlobals ();
		DocGlobals.initFilepaths ();
		DocGlobals.printFilepaths ();
		initComponents ();
		DocGlobals.database = DocDB.initializeDatabase (DocGlobals.databasePath);
	}

	public void initComponents () {
		Utils.copyResourcesToTemporalPath (this, DocGlobals.resourcesPath);

		docFrame = new DocFrame ();
		docFrame.setSize (1250, 800);
		docFrame.setVisible (true);
		docFrame.setController (this);

		docBar = docFrame.getDocBar ();
		docsTabs = docFrame.getDocTabs ();
		docBar.setController (this);

	}

	DocPanel createDocument (String documentType, String docFilepath) {
		DocPanel newDocPanel = null;
		switch (documentType) {
			case "cartaporte":
				System.out.println (">>> Creando cartaporte...");
				newDocPanel = new DocPanelCartaporte ();
				newDocPanel.docNumber = String.format ("CTP-%06d", DocDB.getNextNroDocumento (documentType));
				break;
			case "manifiesto":
				newDocPanel = new DocPanelManifiesto ();
				newDocPanel.docNumber = String.format ("MNF-%06d", DocDB.getNextNroDocumento (documentType));
				break;
			case "declaracion":
				newDocPanel = new DocPanelDeclaracion ();	
				newDocPanel.docNumber = String.format ("DCL-%06d", DocDB.getNextNroDocumento (documentType));
				break;
		}
		if (docFilepath != null) {
			newDocPanel.init (docFilepath);
			docsTabs.addTab (newDocPanel.docName, newDocPanel);
			int lastIndex = docsTabs.getTabCount () - 1;
			docsTabs.setSelectedIndex (lastIndex);
			currentDoc = newDocPanel;
		}
		return (newDocPanel);
	}

	DocPanel onNewDocument (String documentType) {
		DocPanel newDocPanel;
		String newFilepath = docFrame.selectFileFromFileChooser ();
		newDocPanel = createDocument (documentType, newFilepath);
		return (newDocPanel);
	}

	public void onOpenDocument () {
		String existingFilepath = docFrame.selectFileFromFileChooser ();
		String documentType = this.getDocumentTypeFromFile (existingFilepath);
		currentDoc = this.createDocument (documentType, existingFilepath);
		this.loadJsonFileToDoc (currentDoc);
	}

	public void onSaveCurrentToJson () {
		System.out.println ("Guardando en formato JSON...");
		if (this.checkActiveDocument ())
			this.writeToDocJsonFile (currentDoc);
	}

	void onSaveCurrentToPDF () {	
		System.out.println (">>> Guardando en formato PDF...");
		if (this.checkActiveDocument ()) {			
			PdfPrinter pdfPrinter = new PdfPrinter (currentDoc);
			pdfPrinter.printDocument ();
		}
	}

	void onCloseCurrent () {
		if (this.checkActiveDocument ()) {
			this.onSaveCurrentToJson ();
			docsTabs.remove (currentDoc);
			if (docsTabs.getTabCount () > 0) {
				currentDoc = (DocPanel) docsTabs.getTabComponentAt (0);
				docsTabs.setSelectedIndex (0);
			} else
				currentDoc = null;
		}
	}

	boolean checkActiveDocument () {
		if (currentDoc == null) {
			JOptionPane.showMessageDialog (null, "No hay documentos activos.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else
			return true;
	}

	void writeToDocJsonFile (DocPanel currentDoc) {
		System.out.println (">>> Guardando el archivo de datos en: " + currentDoc.jsonFilepath);
		String docType = currentDoc.docType;
		DocText[] textAreas = currentDoc.textAreas;

		JsonObject field = new JsonObject ();
		field.addProperty ("doctype", currentDoc.docType);

		for (int i = 0; i < textAreas.length; i++) {
			DocText textArea = textAreas[i];
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
		DocText[] textAreas = null;
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
				DocText dta = textAreas[i];
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

	void viewPrintPdf () {
		String pdfFilepath = currentDoc.pdfFilepath;
		PdfPrinter.openPDF (pdfFilepath);
	}

}
