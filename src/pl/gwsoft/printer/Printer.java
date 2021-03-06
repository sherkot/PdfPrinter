package pl.gwsoft.printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class Printer {

	public static void main(String args[]) {
		FileInputStream psStream = null;
		try {
			String dir = System.getProperty("user.dir");
			File folder = new File(dir);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					if (listOfFiles[i].getName().endsWith(".pdf")) {
						System.out.println("Printing PDF File "
								+ listOfFiles[i].getName());
						psStream = new FileInputStream(listOfFiles[i].getName());
						printFile(psStream);
					}
				}
			}
		} catch (FileNotFoundException ffne) {
			ffne.printStackTrace();
		}
		if (psStream == null) {
			return;
		}

	}
	
	
	private void printFile2() {
		// TODO Auto-generated method stub

	}
	
	//PdfBox
//	private static void printFileWithPdfBox(FileInputStream psStream) {
//		PrinterJob job = PrinterJob.getPrinterJob();
//	    PageFormat pf = job.defaultPage();
//	    Paper paper = new Paper();
//	    paper.setSize(612.0, 832.0);
//	    double margin = 10;
//	    paper.setImageableArea(margin, margin, paper.getWidth() - margin, paper.getHeight() - margin);
//	    pf.setPaper(paper);
//	    pf.setOrientation(PageFormat.LANDSCAPE);
//
//	    // PDFBox
//	    PDDocument document = PDDocument.load("yourfile.pdf");
//	    job.setPageable(new PDPageable(document, job));
//
//	    job.setJobName("funciona?");
//	    try {
//	        job.print();
//	    } catch (PrinterException e) {
//	        System.out.println(e);
//	    }
//	}
	
	private static void printFile(FileInputStream psStream) {
		DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc myDoc = new SimpleDoc(psStream, psInFormat, null);
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		PrintService[] services = PrintServiceLookup.lookupPrintServices(
				psInFormat, aset);

		// this step is necessary because I have several printers configured
		PrintService myPrinter = null;
		for (int i = 0; i < services.length; i++) {
			String svcName = services[i].toString();
			if (svcName.contains("Epson")) {
				myPrinter = services[i];
				System.out.println("my printer found: " + svcName);
				break;
			}
		}

		if (myPrinter != null) {
			DocPrintJob job = myPrinter.createPrintJob();
			try {
				job.print(myDoc, aset);

			} catch (Exception pe) {
				pe.printStackTrace();
			}
		} else {
			System.out.println("no printer services found");
		}
	}
}
