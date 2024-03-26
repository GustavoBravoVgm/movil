package com.ar.vgmsistemas.printer;

import com.ar.vgmsistemas.entity.Recibo;
import com.bixolon.android.library.BxlService;

public class PrinterSpp {
	
	private BxlService bxlService;
	
	public PrinterSpp() {
		bxlService = new BxlService();
	}
	
	public void printRecibo(Recibo recibo)throws Exception{
		try{
			int status = bxlService.Connect();
			if(status == BxlService.BXL_SUCCESS){
				bxlService.PrintImage("/sdcard/Jotabe3.jpg", BxlService.BXL_WIDTH_NONE, BxlService.BXL_ALIGNMENT_CENTER ,50);
			}
		}catch (Exception e) {
			throw e;
		}finally{
			bxlService.Disconnect();
		}
	}
	
	
}
