package main;

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
	
	static public String temporalPath;
	static public String resourcesPath;
	static public String docFilename;
	static public String docPdfFilename;
	static public String docJsonFilename;
	
	static public int fontSizeNormal = 6;
	static public int fontSizeLarge = 12;
	static int fontSizeSmall = 4;
	
	static public int fontLineSpacing;
	
	
	public static void initFilePaths () {
		temporalPath = Utils.getTemporalPath ("tmp-docsprinter");
		Utils.createFolder (temporalPath);
		imgCartaporteFilepath = Paths.get (temporalPath, "resources",  imgCartaporteFilename).toString ();
		pdfCartaporteFilepath = Paths.get (temporalPath, "resources", pdfCartaporteFilename).toString ();
		imgManifiestoFilepath = Paths.get (temporalPath, "resources",  imgManifiestoFilename).toString ();
		pdfManifiestoFilepath = Paths.get (temporalPath, "resources", pdfManifiestoFilename).toString ();
		resourcesPath = Paths.get (temporalPath, "resources").toString ();
	}	
}
