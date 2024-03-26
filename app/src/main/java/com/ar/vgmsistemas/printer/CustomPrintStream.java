package com.ar.vgmsistemas.printer;

import java.io.OutputStream;

public class CustomPrintStream extends BasePrintStream {


	public CustomPrintStream(OutputStream out, int width) {
		super(out,width);
	}

	@Override
	public synchronized void println(String str) {
		int padding = getWidth()-str.length();
		if (padding>0){
			for (int i=0;i<padding;i++){
				str = str + " ";
			}
			print(str);
		} else {
			print(str);
		}
		
		
	}
	@Override
	public void println() {
			String lines = new String(new char[getWidth()]).replace('\0', ' ');
			print(lines);
	
	}
	
	
	
}
