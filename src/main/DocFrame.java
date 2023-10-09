package main;

import java.io.File;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DocFrame extends javax.swing.JFrame {

	DocController controller;

	public DocFrame () {
		super ("IADOCS: Creación de Carta de Portes");
		initComponents ();
		// Set the unit increment (small scroll step)
		JScrollBar verticalScrollBar = scrollPanel.getVerticalScrollBar ();
		verticalScrollBar.setUnitIncrement (20); // Adjust this value as needed

		setSpanishLabels ();
	}

	public void setController (DocController controller) {
		this.controller = controller;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    scrollPanel = new javax.swing.JScrollPane();
    docsTabs = new javax.swing.JTabbedPane();
    menuBar = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    newCartaporte = new javax.swing.JMenuItem();
    newManifiesto = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JPopupMenu.Separator();
    openFile = new javax.swing.JCheckBoxMenuItem();
    saveFile = new javax.swing.JCheckBoxMenuItem();
    saveAsPdf = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JPopupMenu.Separator();
    closeFle = new javax.swing.JMenuItem();
    exitFile = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    scrollPanel.setMaximumSize(new java.awt.Dimension(1002, 1296));
    scrollPanel.setMinimumSize(new java.awt.Dimension(1002, 1296));
    scrollPanel.setViewportView(docsTabs);

    getContentPane().add(scrollPanel, java.awt.BorderLayout.CENTER);

    fileMenu.setText("Archivo");

    newCartaporte.setText("Nueva Cartaporte");
    newCartaporte.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newCartaporteActionPerformed(evt);
      }
    });
    fileMenu.add(newCartaporte);

    newManifiesto.setText("Nuevo Manifiesto");
    newManifiesto.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newManifiestoActionPerformed(evt);
      }
    });
    fileMenu.add(newManifiesto);
    fileMenu.add(jSeparator1);

    openFile.setText("Abrir");
    openFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        openFileActionPerformed(evt);
      }
    });
    fileMenu.add(openFile);

    saveFile.setText("Guardar");
    saveFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveFileActionPerformed(evt);
      }
    });
    fileMenu.add(saveFile);

    saveAsPdf.setText("Exportar a PDF");
    saveAsPdf.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveAsPdfActionPerformed(evt);
      }
    });
    fileMenu.add(saveAsPdf);
    fileMenu.add(jSeparator2);

    closeFle.setText("Cerrar actual");
    fileMenu.add(closeFle);

    exitFile.setText("Salir");
    exitFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitFileActionPerformed(evt);
      }
    });
    fileMenu.add(exitFile);

    menuBar.add(fileMenu);

    jMenu2.setText("Edit");
    menuBar.add(jMenu2);

    setJMenuBar(menuBar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void saveAsPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsPdfActionPerformed
		controller.onSaveCurrentToPDF ();
  }//GEN-LAST:event_saveAsPdfActionPerformed

// Save as JSON file
  private void saveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileActionPerformed
		controller.onSaveCurrentToJson ();
  }//GEN-LAST:event_saveFileActionPerformed

	// Shows file dialog for selecting files
	public String selectFileFromFileChooser () {
		String lastUsedDir = this.getLastUsedDir ();

		FileNameExtensionFilter filter = new FileNameExtensionFilter ("Documentos importación", "json");

		JFileChooser fileChooser = new JFileChooser ();
		fileChooser.setFileFilter (filter);

		fileChooser.setCurrentDirectory (new File (lastUsedDir));

		int result = fileChooser.showOpenDialog (this);
		if (result == JFileChooser.OPEN_DIALOG) {
			File selectedFile = fileChooser.getSelectedFile ();
			String outputPath = selectedFile.getAbsolutePath ();
			String selectedDir = selectedFile.getParent ();
			this.setLastUsedDir (selectedDir);
			return (outputPath);
		}
		return null;
	}
	
  private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
		// TODO add your handling code here:
		controller.onOpenDocument ();
  }//GEN-LAST:event_openFileActionPerformed

  private void exitFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitFileActionPerformed
		// TODO add your handling code here:
		controller.onExit ();
  }//GEN-LAST:event_exitFileActionPerformed

  private void newCartaporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCartaporteActionPerformed
		// Create a file filter for a specific extension (e.g., ".txt")

		String newFilepath = this.selectFileFromFileChooser ();
		if (newFilepath != null)
			controller.onNewDocument ("cartaporte", newFilepath);
  }//GEN-LAST:event_newCartaporteActionPerformed

	void addTab (String tabName, DocPanel docPanel) {
		docsTabs.add (tabName, docPanel);
	}
  private void newManifiestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newManifiestoActionPerformed
		String newFilepath = this.selectFileFromFileChooser ();
		if (newFilepath != null)
			controller.onNewDocument ("manifiesto", newFilepath);
  }//GEN-LAST:event_newManifiestoActionPerformed

	public void setSpanishLabels () {
		// Set the Spanish labels and messages for the file chooser
		UIManager.put ("FileChooser.lookInLabelText", "Buscar en:");
		UIManager.put ("FileChooser.saveInLabelText", "Guardar en:");
		UIManager.put ("FileChooser.openDialogTitleText", "Abrir");
		UIManager.put ("FileChooser.saveDialogTitleText", "Guardar");
		UIManager.put ("FileChooser.openButtonText", "Abrir");
		UIManager.put ("FileChooser.saveButtonText", "Guardar");
		UIManager.put ("FileChooser.cancelButtonText", "Cancelar");
		UIManager.put ("FileChooser.fileNameLabelText", "Nombre de archivo:");
		UIManager.put ("FileChooser.filesOfTypeLabelText", "Archivos de tipo:");
		UIManager.put ("FileChooser.upFolderToolTipText", "Subir un nivel");
		UIManager.put ("FileChooser.homeFolderToolTipText", "Inicio");
		UIManager.put ("FileChooser.newFolderToolTipText", "Crear carpeta");
		UIManager.put ("FileChooser.listViewButtonToolTipText", "Vista de lista");
		UIManager.put ("FileChooser.detailsViewButtonToolTipText", "Vista de detalles");
	}

// Get the last used directory from preferences
	String getLastUsedDir () {
		String LAST_USED_DIRECTORY_KEY = "last_used_directory";
		Preferences prefs = Preferences.userNodeForPackage (this.getClass ());
		String lastUsedDirectory = prefs.get (LAST_USED_DIRECTORY_KEY, null);
		if (lastUsedDirectory == null)
			lastUsedDirectory = System.getProperty ("user.home");
		return (lastUsedDirectory);
	}

	void setLastUsedDir (String lastUsedDir) {
		String LAST_USED_DIRECTORY_KEY = "last_used_directory";

		Preferences prefs = Preferences.userNodeForPackage (this.getClass ());
		prefs.put (LAST_USED_DIRECTORY_KEY, lastUsedDir);

	}

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem closeFle;
  private javax.swing.JTabbedPane docsTabs;
  private javax.swing.JMenuItem exitFile;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JPopupMenu.Separator jSeparator1;
  private javax.swing.JPopupMenu.Separator jSeparator2;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem newCartaporte;
  private javax.swing.JMenuItem newManifiesto;
  private javax.swing.JCheckBoxMenuItem openFile;
  private javax.swing.JMenuItem saveAsPdf;
  private javax.swing.JCheckBoxMenuItem saveFile;
  private javax.swing.JScrollPane scrollPanel;
  // End of variables declaration//GEN-END:variables

}