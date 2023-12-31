package main;

import com.google.gson.JsonObject;
import java.nio.file.Paths;

public class DocGlobals {
	static public String imgCartaporteFilename = "image-cartaporte-vacia-SILOG.png";
	static public String pdfCartaporteFilename = "image-cartaporte-vacia-SILOG.pdf";
	static public String imgCartaporteFilepath;
	static public String pdfCartaporteFilepath;

	static public String imgManifiestoFilename = "image-manifiesto-vacio-NTA.png";
	static public String pdfManifiestoFilename = "image-manifiesto-vacio-NTA.pdf";
	static public String imgManifiestoFilepath;
	static public String pdfManifiestoFilepath;

	static public String imgDeclaracionFilename = "image-declaracion-vacia-NTA.png";
	static public String pdfDeclaracionFilename = "image-declaracion-vacia-NTA.pdf";
	static public String imgDeclaracionFilepath;
	static public String pdfDeclaracionFilepath;
	
	static public String temporalPath;
	static public String resourcesPath;
	static public String runningPath;
	static public String databasePath;
	
	static public String docFilename;
	static public String docPdfFilename;
	static public String docJsonFilename;
	
	static public int fontSizeNormal = 6;
	static public int fontSizeLarge = 12;
	static int fontSizeSmall = 4;
	
	static public int fontLineSpacing;
	static JsonObject database;
	
	
	public static void initFilepaths () {
		temporalPath = Utils.getTemporalPath ("tmp-docsprinter");
		runningPath = Utils.convertToOSPath (System.getProperty ("user.dir"));
		databasePath = Paths.get (runningPath, "importexport-suite-database.json").toString ();
		
		Utils.createFolder (temporalPath);
		imgCartaporteFilepath = Paths.get (temporalPath, "resources",  imgCartaporteFilename).toString ();
		pdfCartaporteFilepath = Paths.get (temporalPath, "resources", pdfCartaporteFilename).toString ();
		imgManifiestoFilepath = Paths.get (temporalPath, "resources",  imgManifiestoFilename).toString ();
		pdfManifiestoFilepath = Paths.get (temporalPath, "resources", pdfManifiestoFilename).toString ();
		imgDeclaracionFilepath = Paths.get (temporalPath, "resources",  imgDeclaracionFilename).toString ();
		pdfDeclaracionFilepath = Paths.get (temporalPath, "resources", pdfDeclaracionFilename).toString ();
		resourcesPath = Paths.get (temporalPath, "resources").toString ();
	}	
	
	public static void printFilepaths () {
		System.out.println (">>> Filepaths: <<<");
		System.out.println (">>> temporalPath:" + temporalPath);
		System.out.println (">>> resourcesPath:" + resourcesPath);
		System.out.println (">>> runningPath:" + runningPath);
		System.out.println (">>> databasePath:" + databasePath);
	}
}
