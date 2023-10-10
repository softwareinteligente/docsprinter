package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Utils {

	public static Color HIGH_GREEN = new Color (240, 255, 240);
	public static Color MID_YELLOW = new Color (255, 255, 192);
	public static Color LOW_RED = new Color (255, 229, 229);

	public static String getTemporalPath (String pathName) {
		String osTempDir = Utils.convertToOSPath (System.getProperty ("java.io.tmpdir"));
		//String pathName = Utils.convertToOSPath (Paths.get (workingDir).getFileName ().toString ());
		String path = Paths.get (osTempDir, pathName).toString ();
		return Utils.convertToOSPath (path);
	}

	// Convert first page PDF file to image and write to tmpDir
	public static File ConvertPDFToImage (File pdfFilepath) {
		File tmpDir = new File (System.getProperty ("java.io.tmpdir"));
		File outImgFilepath = new File (tmpDir, pdfFilepath.getName ().replace (".pdf", ".jpg"));
		if (outImgFilepath.exists ())
			return (outImgFilepath);
		//else:
		try {
			PDDocument document = PDDocument.load (pdfFilepath);
			PDFRenderer pdfRenderer = new PDFRenderer (document);

			int numberOfPages = document.getNumberOfPages ();
			System.out.println ("Total PDF pages  to be converting -> " + numberOfPages);

			int dpi = 200;// use less dpi for to save more space in harddisk. For professional usage you can use more than 300dpi 
			BufferedImage bImage = pdfRenderer.renderImageWithDPI (0, dpi, ImageType.RGB);
			ImageIO.write (bImage, "jpg", outImgFilepath);
			document.close ();
		} catch (IOException ex) {
			Logger.getLogger (Utils.class.getName ()).log (Level.SEVERE, null, ex);
		}
		return (outImgFilepath);
	}

	// Return if a file is a pdf or a image or none
	public static String getFileContentType (File file) {
		String mimeType = URLConnection.guessContentTypeFromName (file.getName ());

		if (mimeType.contains ("pdf"))
			return ("pdf");
		else if (mimeType.contains ("image"))
			return ("image");
		else
			return ("");
	}

	// Get OS name
	public static String getOSName () {
		String OSType = System.getProperty ("os.name").toLowerCase ();
		if (OSType.contains ("windows"))
			return ("windows");
		else
			return ("linux");
	}

	// Return OS tmp dir
	public static File getOSTmpDir () {
		File tmpDir = new File (System.getProperty ("java.io.tmpdir"));
		return (tmpDir);
	}

	// Return save/open projects directory 
	public static String convertToOSPath (String path) {
		if (getOSName ().equals ("windows"))
			path = path.replace ("\\", "\\\\");
		return (path);
	}

	public static String getResultsFile (String docFilepath) {
		String fileName = new File (docFilepath).getPath ();
		String docFilename = fileName.substring (0, fileName.lastIndexOf ('.'));
		String resultsFilename = String.format ("%s-RESULTS.json", docFilename);
		File resultsFilepath = new File (resultsFilename);
		return (resultsFilepath.toString ());
	}

	public static String getCacheFile (String docFilepath) {
		String fileName = new File (docFilepath).getPath ();
		String docFilename = fileName.substring (0, fileName.lastIndexOf ('.'));
		String resultsFilename = String.format ("%s-azure-CACHE.pkl", docFilename);
		File resultsFilepath = new File (resultsFilename);
		return (resultsFilepath.toString ());
	}

	public static String getResourcePath (Object obj, String resourceName) {
		URL resourceURL = null;
		resourceURL = obj.getClass ().getClassLoader ().getResource ("resources/" + resourceName);
		return (resourceURL.getPath ());
	}

	public static String getResourcePath (String runningPath, String resourceName) {
		Path resourcePath = Paths.get (runningPath, resourceName);
		return (resourcePath.toString ());
	}

	public static String createTempCompressedFileFromText (String text) {
		File tempFile = null;
		try {
			// Create a temporary file to hold the compressed data
			tempFile = File.createTempFile ("compressed", ".zip");

			// Create a ZipOutputStream to write to the temporary file
			try (FileOutputStream fos = new FileOutputStream (tempFile); ZipOutputStream zipOut = new ZipOutputStream (fos)) {
				// Add a new ZIP entry
				zipOut.putNextEntry (new ZipEntry ("text.txt"));

				// Write the text content to the ZIP entry
				zipOut.write (text.getBytes ());

				// Close the ZIP entry
				zipOut.closeEntry ();
			}
		} catch (IOException ex) {
			Logger.getLogger (Utils.class.getName ()).log (Level.SEVERE, null, ex);
		}
		return tempFile.toString ();
	}

	// Used to read to fill Ecuapass comboBoxes (e.g paises, ciudades, etc.)
	public static String[] readDataFromFile (String filename) {
		List<String> data = new ArrayList<> ();
		String[] arrData = null;
		try (BufferedReader reader = new BufferedReader (new FileReader (filename))) {
			String line;
			while ((line = (String) reader.readLine ()) != null) {
				data.add (new String (line.getBytes (), "UTF-8"));
			}
			arrData = data.toArray (new String[0]);
		} catch (IOException e) {
			e.printStackTrace ();
		}
		return arrData;
	}

	// Create folder given the absolute path
	public static void createFolder (String folderPath) {
		File folder = new File (folderPath);
		if (folder.exists () == false)
			if (folder.mkdirs () == false)
				System.out.println (">>> Error al crear la carpeta: " + folder.toString ());
	}

	// Copy emmbeded resources  (programs and images) to temporal dir
	public static boolean copyResourcesToTemporalPath (Object CLASS, String resourcesPath) {
		Utils.createFolder (DocGlobals.resourcesPath);
		if (Utils.copyResourcesToTemporalPathFromJar (CLASS, resourcesPath) == false)
			if (Utils.copyResourcesToTemporalPathFromPath (CLASS, resourcesPath) == false){
				JOptionPane.showMessageDialog (null, "No se pudieron copiar los recursos necesarios para la ejecución!");
				return false;
			}
		return true;
	}

	public static boolean copyResourcesToTemporalPathFromJar (Object CLASS, String temporalPath) {
		System.out.println ("CLIENTE: Copiando recursos desde un JAR...");
		try {
			String resourceDirPath = "/resources"; // Specify the resource directory path within the JAR
			// Get the JAR file containing the resources
			File jarFile = new File (CLASS.getClass ().getProtectionDomain ().getCodeSource ().getLocation ().toURI ());
			JarFile jar = new JarFile (jarFile);

			Enumeration<JarEntry> entries = jar.entries (); // Get a list of resource file and directory names
			while (entries.hasMoreElements ()) {
				JarEntry entry = entries.nextElement ();
				String entryName = entry.getName ();

				if (entryName.startsWith ("resources")) {  // Check if the entry is in the specified resource directory
					Path destinationPath = Paths.get (temporalPath, entryName.substring (resourceDirPath.length ()));
					if (entry.isDirectory ())
						Files.createDirectories (destinationPath); // This is a directory, so create it in the destination
					else // This is a file, so copy it to the destination
						try (InputStream inputStream = jar.getInputStream (entry)) {
						Files.copy (inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
					}
				}
			}
			jar.close ();
		} catch (IOException | URISyntaxException ex) {
			//ex.printStackTrace ();
			return false;
		}
		return true;
	}

	public static boolean copyResourcesToTemporalPathFromPath (Object CLASS, String temporalPath) {
		System.out.println ("CLIENTE: Copiando recursos desde un PATH.");
		try {
			String resourceDir = "resources/";// Specify the resource directory path within the JAR			
			ClassLoader classLoader = CLASS.getClass ().getClassLoader ();// Get a reference to the current class loader			
			URL resourceUrl = classLoader.getResource (resourceDir);// Get the URL of the resource directory
			if (resourceUrl == null)
				System.out.println ("SERVER: Carpeta de recursos no encontrada: " + resourceDir);
			else {
				URI uri = resourceUrl.toURI ();
				Path resourcePath = Paths.get (uri);// Convert the URL to a Path		
				Files.walk (resourcePath)// Walk the directory and collect all resource names
					.forEach (filePath -> {
						if (filePath.equals (resourcePath) == false) {
							Path relativePath = resourcePath.relativize (filePath);
							Path destinationPath = Paths.get (temporalPath, relativePath.toString ());
							if (Files.isDirectory (destinationPath))
								destinationPath.toFile ().mkdir ();
							else
							try {
								Files.copy (filePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
							} catch (IOException ex) {
								Logger.getLogger (CLASS.getClass ().getName ()).log (Level.SEVERE, null, ex);
							}
						}
					}
					);
			}
		} catch (URISyntaxException | IOException ex) {
			ex.printStackTrace ();
			return false;
		}
		return true;
	}

	public static void main (String[] args) {
		Utils.ConvertPDFToImage (new File ("/home/lg/AAA/factura-oxxo2.pdf"));
	}
}
