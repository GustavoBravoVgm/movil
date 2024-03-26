package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class ComparatorDateTime {

	private final static String FORMAT_HOUR_MINUTES_SECONDS = "HH:mm:ss";
	private final static String FORMAT_HOUR_MINUTES = "HH:mm";	
	public final static int FECHA_ENVIO_MENOR = 1;
	public final static int FECHA_ENVIO_MAYOR = -1;
	public final static int FECHA_ENVIO_IGUAL = 0;
	
	/**
	 * 
	 * @param horarioInicio
	 * @param horarioFin
	 * @param horaAComparar
	 * @return Devuelve true si horaAComparar esta entre horarioInicio y horarioFin - considera segundos tambien
	 */
	public static boolean compareHourMinutesSeconds(String horarioInicio, String horarioFin, String horaAComparar){
		return compareTime(horarioInicio, horarioFin, horaAComparar, FORMAT_HOUR_MINUTES_SECONDS);
	}
	
	/**
	 * 
	 * @param horarioInicio
	 * @param horarioFin
	 * @param horaAComparar
	 * @return Devuelve true si horaAComparar esta entre horarioInicio y horarioFin
	 */
	public static boolean compareHourMinutes(String horarioInicio, String horarioFin, String horaAComparar){
		return compareTime(horarioInicio, horarioFin, horaAComparar, FORMAT_HOUR_MINUTES);
	}
	
	private static boolean compareTime(String horarioInicioStg, String horarioFinStg, String horaAcompararStg, String formato){
		boolean dentroDelRango = false;
		try {
			DateFormat dateFormat = new SimpleDateFormat (formato);
			Date horarioInicioDate, horarioFinDate, horaComprarDate;
			horarioInicioDate = dateFormat.parse(horarioInicioStg);
			horarioFinDate = dateFormat.parse(horarioFinStg);
			horaComprarDate = dateFormat.parse(horaAcompararStg);
			if ((horarioInicioDate.compareTo(horaComprarDate) <= 0) && (horarioFinDate.compareTo(horaComprarDate) >= 0)){
				dentroDelRango = true;
				//El horario a comparar esta dentro del rango horarioInicio-horarioFin
			} else {
				// El horario a comparar esta fuera del rango horarioInicio-horarioFin
			}
			
		} catch (ParseException parseException){
			parseException.printStackTrace();
		}
		return dentroDelRango;
	}
	
	/**
	 * Devuelve true si el horarioA es menor que el horarioB
	 * @param horarioAstg
	 * @param horarioBstg
	 * @return
	 */
	public static boolean compareTimeRange(String horarioAstg, String horarioBstg){
		boolean isMenor = false;
		try {
			DateFormat dateFormat = new SimpleDateFormat ("HH:mm");

			Date horarioAdate, horarioBdate;
			horarioAdate = dateFormat.parse(horarioAstg);
			horarioBdate = dateFormat.parse(horarioBstg);

			if (horarioAdate.before(horarioBdate)){
				isMenor = true;
			} else {
			}
		} catch (ParseException parseException){
			parseException.printStackTrace();
		}
		return isMenor;
	}
	
	/**
	 * Valida que la hora / minutos actuales este dentro de lo configurado para enviar pedidos
	 * @return
	 */
	public static boolean validarRangoHorarioEnvioPedidos(){
		boolean isValido = false;
		//Verifico que este habilitado el envío de pedidos dentro de franja horaria
		if(PreferenciaBo.getInstance().getPreferencia().getIsEnvioPedidosPorFranja()){
			//En caso afirmativo, controlo que la hora / minutos actual este dentro del rango de preferencias	

			//Obtengo las horas y minutos de Preferencias
			int horaInicioEnvioPreferencias = PreferenciaBo.getInstance().getPreferencia().getHoraInicioEnvio();
			int minutosInicioEnvioPreferencias = PreferenciaBo.getInstance().getPreferencia().getMinutosInicioEnvio();
			int horaFinEnvioPreferencias = PreferenciaBo.getInstance().getPreferencia().getHoraFinEnvio();
			int minutosFinEnvioPreferencias = PreferenciaBo.getInstance().getPreferencia().getMinutosFinEnvio();

			//Obtengo la hora y minutos actuales
			int horaActual = Calendar.getInstance().getTime().getHours();
			int minutosActual = Calendar.getInstance().getTime().getMinutes();

			/**/
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String horaMinutos = sdf.format(Calendar.getInstance());


			//Armo los Strings de comparación
			String horaMinutosInicio = String.valueOf(horaInicioEnvioPreferencias)+ ":"+
			String.valueOf(minutosInicioEnvioPreferencias);

			String horaMinutosFin = String.valueOf(horaFinEnvioPreferencias)+ ":" +
			String.valueOf(minutosFinEnvioPreferencias);

			String horaMinutosActual = String.valueOf(horaActual) + ":" +
			String.valueOf(minutosActual);

			//Llamo al método de Formatter para comparar las fechas
			isValido = compareHourMinutes(horaMinutosInicio, horaMinutosFin, horaMinutosActual);

		}else{
			//Caso cuando no esta habilitado el envío por franja, dejo enviar todos los PD
			isValido = true;
		}

		return isValido;
	}
	
	/**
	 * Valida que la hora y minutos actuales este dentro del horario de trabajo del vendedor para enviar ubicaciones
	 * @return true si puede enviar ubicaciones geográficas, false en caso contrario
	 */
	public static boolean validarRangoHorarioEnvioLocalizacion(){
			boolean isValido = false;
			//Obtengo las horas y minutos de Preferencias		
			Date horaEntradaManana = PreferenciaBo.getInstance().getPreferencia().getHoraEntradaManana();
			Date horaSalidaManana = PreferenciaBo.getInstance().getPreferencia().getHoraSalidaManana();
			Date horaEntradaTarde = PreferenciaBo.getInstance().getPreferencia().getHoraEntradaTarde();
			Date horaSalidaTarde = PreferenciaBo.getInstance().getPreferencia().getHoraSalidaTarde();
		
			//Obtengo la hora y minutos actuales
			int horaActual = Calendar.getInstance().getTime().getHours();
			int minutosActual = Calendar.getInstance().getTime().getMinutes();

			//Armo los strings de comparación						
			String horaMinutosEntradaManiana = String.valueOf(horaEntradaManana.getHours()) + ":" + String.valueOf(horaEntradaManana.getMinutes());
			String horaMinutosSalidaManiana = String.valueOf(horaSalidaManana.getHours()) + ":" + String.valueOf(horaSalidaManana.getMinutes());
			String horaMinutosEntradaTarde = String.valueOf(horaEntradaTarde.getHours()) + ":" + String.valueOf(horaEntradaTarde.getMinutes());
			String horaMinutosSalidaTarde = String.valueOf(horaSalidaTarde.getHours()) + ":" + String.valueOf(horaSalidaTarde.getMinutes());

			String horaMinutosActual = String.valueOf(horaActual) + ":" + String.valueOf(minutosActual);

			//Llamo al método de Formatter para comparar las fechas en ambos rangos
			boolean isValidoManiana = compareHourMinutes(horaMinutosEntradaManiana, horaMinutosSalidaManiana, horaMinutosActual);
			boolean isValidoTarde = compareHourMinutes(horaMinutosEntradaTarde, horaMinutosSalidaTarde, horaMinutosActual);
			
			if(isValidoManiana || isValidoTarde){
				isValido = true;
			}
			
		return isValido;
	}
	
	/**
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return FECHA_ENVIO_MAYOR si fecha1 > fecha2, FECHA_ENVIO_MENOR si fecha1 < fecha2, FECHA_ENVIO_IGUAL si son iguales 
	 */
	public static int compareDates(Date fecha1, Date fecha2){
		
		int resultadoComparacion = FECHA_ENVIO_MAYOR;
		
		if(fecha1.compareTo(fecha2)>0){
    		resultadoComparacion = FECHA_ENVIO_MAYOR;
    	}else if(fecha1.compareTo(fecha2)<0){
    		resultadoComparacion = FECHA_ENVIO_MENOR;
    	}else if(fecha1.compareTo(fecha2)==0){
    		resultadoComparacion = FECHA_ENVIO_IGUAL;
    	}
		
		return resultadoComparacion;		
	}
	
	public static boolean isWithinRange(Date dateToCompare, Date dateStart, Date dateEnd ) {
	    return dateToCompare.getTime() >= dateStart.getTime() &&
	    		dateToCompare.getTime() <= dateEnd.getTime();
	}

}
