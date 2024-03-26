package com.ar.vgmsistemas.printer;

import java.io.OutputStream;
import java.io.PrintStream;

public class BasePrintStream extends PrintStream {
	private int mWidth;

	public BasePrintStream(OutputStream out, int width) {
		super(out);
		mWidth = width;
	}

	public void printLine() {
		String lines = new String(new char[mWidth]).replace('\0', '_');
		println(lines);
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int mWidth) {
		this.mWidth = mWidth;
	}
}
