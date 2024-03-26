package com.ar.vgmsistemas.printer;

import com.ar.vgmsistemas.printer.util.UtilPrinter;

import java.io.OutputStream;

public class PrintStreamFactory {
	 public static final int NORMAL_PRINT_STREAM = 0;
     public static final int ZEBRA_PRINT_STREAM = 1;

     public static BasePrintStream getPrintStream(String printer, OutputStream outputStream, int widthPrinter) {
	 
    	switch (UtilPrinter.getTypeStream(printer)) {
		case ZEBRA_PRINT_STREAM:
			return new CustomPrintStream(outputStream, widthPrinter);

		default:
			return new BasePrintStream(outputStream, widthPrinter);
		}
	         }
}
