package com.ar.vgmsistemas.printer.util;

import com.ar.vgmsistemas.printer.PrintStreamFactory;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UtilPrinter {

    public static final int CABECERA_NUMERO_FECHA_IMPORTE = 1;
    public static final int CABECERA_TIPO_FECHA_IMPORTE = 2;
    public static final int CABECERA_TIPO_COT_IMPORTE_IMPORTE = 3;
    public static final int CABECERA_BANCO_FECHA_IMPORTE = 4;
    public static final int CABECERA_SALDO_DTO_PAGO = 5;
    public static final int CABECERA_DESCRIPCION = 6;
    public static final int CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL = 7;
    public static final int CABECERA_TIPO_IMPORTE = 8;
    public static final int CABECERA_RECIBO_IMPORTE = 9;


    public static final int TOTAL_CARACTERES = 32;
    public static final String SPP_R200 = "SPP-R200";
    public static final String SPP_R200II = "SPP-R200II";
    public static final String SPP_R300 = "SPP-R300";
    public static final String IMZ_220 = "XXXXJ";
    public static final String POS_5802DD = "POS_5802DD";
    public static final String SOL_58 = "SOL58";
    public static final String QSPRINTER = "Qsprinter";
    public static final String IMPRESORA_GENERICA = "Impresora";
    public static final String emulador = "BLU VIVO AIR LTE";
    public static final String BlueTooth_Printer = "BlueTooth Printer";
    /**
     * Mapea el tamaño de la linea en caracteres de acuerdo al nombre de la impresora.
     * SPP-R200 = 32, SPP-R300 = 48, IMZ-220= 32.
     */
    public static final Map<String, Integer> printersSizeMap;

    static {
        Map<String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put(SPP_R200, 32);
        aMap.put(SPP_R300, 48);
        aMap.put(SPP_R200II, 32);
        aMap.put(IMZ_220, 32);
        aMap.put(emulador, 32);
        aMap.put(QSPRINTER, 32);
        aMap.put(IMPRESORA_GENERICA, 32);
        aMap.put(BlueTooth_Printer, 32);
        aMap.put(POS_5802DD, 32);
        aMap.put(SOL_58, 32);
        printersSizeMap = Collections.unmodifiableMap(aMap);
    }

    public static int getTypeStream(String printer) {

        if (printer.equals(IMZ_220) || printer.equals(emulador)) {
            return PrintStreamFactory.ZEBRA_PRINT_STREAM;
        }
        return PrintStreamFactory.NORMAL_PRINT_STREAM;
    }

    public static String center(String text, int width) {
        int size = text.length();
        int disponible = (width - size) / 2;
        for (int i = 0; i < disponible; i++) {
            text = " " + text;
        }
        return text;
    }

    public static String alignTextRight(String textAlignment, String line, int totalCaracteres) {
        int disponible = totalCaracteres - line.length();
        int espaciosCompletar = disponible - textAlignment.length();
        for (int i = 0; i < espaciosCompletar; i++) {
            line = " " + line;
        }
        line = textAlignment + line;
        return line;
    }

    public static String alignTextRight(String text, int totalCaracteres) {
        int disponible = totalCaracteres - text.length();
        for (int i = 0; i < disponible; i++) {
            text = " " + text;
        }
        return text;
    }

    public static String armarLinea(String text, String line, int begin) {
        int sizeLine = text.length();
        if (sizeLine > begin) {
            text = text.substring(0, begin - 1);
            text = text.concat(" ");
        } else {
            for (int i = sizeLine; i < begin; i++) {
                text = text.concat(" ");
            }
        }

        text = text.concat(line);
        return text;
    }

    public static String formatDate(Date date) {
        String sDate = "";
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            sDate = simpleDateFormat.format(date);
        }
        return sDate;
    }

    public static String getCabecera(int type, int width, boolean snMovilReciboDto) {
        switch (type) {
            case CABECERA_BANCO_FECHA_IMPORTE:
                if (width == 32) {
                    return "BANCO        FECHA    IMPORTE($)";
                } else {
                    return "BANCO                FECHA            IMPORTE($)";
                }
            case CABECERA_NUMERO_FECHA_IMPORTE:
                String importe = "IMPORTE($)";
                if (snMovilReciboDto) {
                    //si es con descuento se saca importe porque va el pago abajo
                    importe = "";
                }
                if (width == 32) {
                    return "NUMERO       FECHA    " + importe;
                } else {
                    return "NUMERO               FECHA            " + importe;
                }
            case CABECERA_TIPO_COT_IMPORTE_IMPORTE:
                if (width == 32) {
                    return "TIPO    COT  IMPORTE  IMPORTE($)";
                } else {
                    return "TIPO         COT       IMPORTE        IMPORTE($)";
                }
            case CABECERA_TIPO_FECHA_IMPORTE:
                if (width == 32) {
                    return "TIPO         FECHA    IMPORTE($)";
                } else {
                    return "TIPO                 FECHA            IMPORTE($)";
                }
            case CABECERA_SALDO_DTO_PAGO:
                if (width == 32) {
                    return "SALDO        DTO         PAGO($)";
                } else {
                    return "SALDO                DTO                 PAGO($)";
                }
            case CABECERA_DESCRIPCION:
                if (width == 32) {
                    return "DESCRIPCION                     ";
                } else {
                    return "DESCRIPCION                                     ";
                }
            case CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL:
                if (width == 32) {
                    return "U.     PREC.UNIT($)      SUBT($)";
                } else {
                    return "U.         PREC.UNIT($)                  SUBT($)";
                }
            case CABECERA_TIPO_IMPORTE:
                if (width == 32) {
                    return "TIPO                  IMPORTE($)";
                } else {
                    return "TIPO                                  IMPORTE($)";
                }
            case CABECERA_RECIBO_IMPORTE:
                if (width == 32) {
                    return "RECIBO                  TOTAL($)";
                } else {
                    return "RECIBO                              TOTAL($)";
                }
        }
        return "";

    }

    public static int getSeparationCabecera(int type, int width) {
        switch (type) {
            case CABECERA_BANCO_FECHA_IMPORTE:
                if (width == 32) {
                    return 13;
                } else {
                    return 21;
                }
            case CABECERA_NUMERO_FECHA_IMPORTE:
                if (width == 32) {
                    return 13;
                } else {
                    return 21;
                }
            case CABECERA_TIPO_COT_IMPORTE_IMPORTE:
                if (width == 32) {
                    return 8;
                } else {
                    return 13;
                }
            case CABECERA_TIPO_FECHA_IMPORTE:
                if (width == 32) {
                    return 13;
                } else {
                    return 21;
                }
            case CABECERA_DESCRIPCION:
                if (width == 32) {
                    return 13;
                } else {
                    return 21;
                }
            case CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL:
                if (width == 32) {
                    return 7;
                } else {
                    return 11;
                }
            case CABECERA_TIPO_IMPORTE:
                if (width == 32) {
                    return 8;
                } else {
                    return 13;
                }
        }
        return 0;
    }

    public static int getSecondSeparationTipoCot(int width) {
        if (width == 32) {
            return 20;
        } else {
            return 30;
        }
    }
}
