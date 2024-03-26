package com.ar.vgmsistemas.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Formatter {

    public static String FORMAT_MONEY = "#,##0.00";
    public static String FORMAT_MONEY_C_PESOS = "$ #,##0.00";
    public static String FORMAT_MONEY_C_PESOS_S_ESPACIO = "$#,##0.00";

    public static String formatMoney(double value) {
        DecimalFormat formater = new DecimalFormat(FORMAT_MONEY_C_PESOS);
        return formater.format(value);
    }

    public static String formatMoneyTwo(double value) {
        DecimalFormat formater = new DecimalFormat(FORMAT_MONEY_C_PESOS_S_ESPACIO);
        return formater.format(value);
    }

    public static String formatNumber(Number value, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * @param date
     * @return string of date in format dd/mm/aaaa
     */
    public static String formatDate(Date date) {
        String sDate = "";
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            sDate = simpleDateFormat.format(date);
        }
        return sDate;
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (date == null) date = Calendar.getInstance().getTime();
        return simpleDateFormat.format(date);
    }

    public static String formatDateTimeToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String formatDateTimeMinutes(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String formatDateWs(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(date);
    }

    public static String formatJulianDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date convertToDateTime(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.parse(date);
    }

    public static Date convertToDateTimeTwo(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(date);
    }

    public static Date convertToDateTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

    public static Date convertToDate2(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.parse(date);
    }

    public static Date convertToDateTimeWs(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.parse(date);
    }

    public static Date convertToDateWs(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static Date convertToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static String formatPercent(double value) {
        DecimalFormat formater = new DecimalFormat("##0.00%");
        return formater.format(value);
    }

    public static boolean isValidCuit(String cuit) {
        if (cuit.length() < 11 && cuit.length() != 0) {
            return false;
        }
        if (cuit.length() == 0) {
            return true;
        }
        int factor = 5;
        int[] c = new int[11];
        int resultado = 0;

        for (int i = 0; i < 10; i++) {
            c[i] = Integer.valueOf(Character.toString(cuit.charAt(i))).intValue();
            resultado = resultado + c[i] * factor;
            factor = (factor == 2) ? 7 : factor - 1;
        }
        int modOnce = resultado % 11;
        int control = 11 - modOnce;
        if (control == 11) {
            control = 0;
        } else if (control == 10) {
            control = 9;
        }
        int posOnce = Integer.valueOf(Character.toString(cuit.charAt(10)));
        if (control == posOnce) {
            return true;
        } else {
            return false;
        }

    }

    public static double parseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException exception) {
            return 0d;
        }
    }

    public static int parseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    public static boolean parseBooleanString(String value) {
        if (value != null) {
            if (value.equals("S")) return true;

        }
        return false;

    }

    public static double redondearDouble(double prARedondear, int caDecimal) {
        double prResultado;
        switch (caDecimal) {
            case 1:
                prResultado = (Math.round(prARedondear * 10) / 10.0d);
                break;
            case 2:
                prResultado = (Math.round(prARedondear * 100) / 100.0d);
                break;
            case 3:
                prResultado = (Math.round(prARedondear * 1000) / 1000.0d);
                break;
            default:
                prResultado = prARedondear;
        }
        return prResultado;
    }
}
