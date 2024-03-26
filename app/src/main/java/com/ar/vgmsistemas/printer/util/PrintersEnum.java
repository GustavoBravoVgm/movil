package com.ar.vgmsistemas.printer.util;

public enum PrintersEnum {
	// NOMBRE VARIABLE ("NOMBRE REAL DE IMPRESORA")
	
	SPP_R200(UtilPrinter.SPP_R200),
	SPP_R200II(UtilPrinter.SPP_R200II),
	SPP_R300(UtilPrinter.SPP_R300),
	IMZ_220(UtilPrinter.IMZ_220),
	QSPRINTER(UtilPrinter.QSPRINTER),
    IMPRESORA_GENERICA(UtilPrinter.IMPRESORA_GENERICA),
	BlueTooth_Printer(UtilPrinter.BlueTooth_Printer),
	SOL_58(UtilPrinter.SOL_58),
	emulador(UtilPrinter.emulador);
	
	private final String text;
	
	private PrintersEnum(final String text){
		this.text = text;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}
	public static boolean existsPrinter(String printer){
		for (PrintersEnum printerEnum: PrintersEnum.values()){
			if (printerEnum.toString().equals(printer)){
				return true;
			}
		}
		return false;
	}
	
}
