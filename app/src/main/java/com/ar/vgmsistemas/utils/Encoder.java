package com.ar.vgmsistemas.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

	private static String convertToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (byte b : data) {
			int halfByte = (b >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				buf.append((0 <= halfByte) && (halfByte <= 9) ? (char) ('0' + halfByte) : (char) ('a' + (halfByte - 10)));
				halfByte = b & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		//byte[] textBytes = text.getBytes("iso-8859-1");
		byte[] textBytes = text.getBytes();
		md.update(textBytes, 0, textBytes.length);
		byte[] sha1hash = md.digest();
		return convertToHex(sha1hash);
	}
}