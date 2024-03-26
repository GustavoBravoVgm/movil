package com.ar.vgmsistemas.printer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.ImpresoraBo;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Impresora;
import com.ar.vgmsistemas.printer.util.PrintersEnum;
import com.ar.vgmsistemas.printer.util.UtilPrinter;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SuperPrinter extends Printer{
    protected Empresa empresaDto;
    protected String empresa;
    protected String printerName;
    protected int widthPrinter;
    protected boolean snReciboMovilDto;
    protected BluetoothDevice printer = null;
    protected BluetoothSocket socket = null;
    protected BasePrintStream ps;
    protected OutputStream os;
    //cambio por lectura de la impresora guardadas en bd
    protected boolean impresoraEnBaseDatos;
    protected List<Impresora> listImpresoras;

    public Context _context;
    RepositoryFactory _repoFactory;

    @Override
    public void print(Context context) throws Exception {
        String UUID_BPP = "00001122-0000-1000-8000-00805F9B34FB";
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        EmpresaBo empresaBo = new EmpresaBo(repoFactory);
        empresaDto = empresaBo.recoveryEmpresa();
        snReciboMovilDto = empresaDto.getSnMovilReciboDto().equals("S");
        empresa = empresaDto.getNombreEmpresa().trim();

        ImpresoraBo impresoraBo = new ImpresoraBo(repoFactory);
        listImpresoras = impresoraBo.recoveryAll();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {

            throw new Exception("El telefono no soporta bluetooth");
        }

        if (!bluetoothAdapter.isEnabled()) {
            throw new Exception("El bluetooth se encuentra desactivado, por favor activelo");
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        // Loop through paired devices
        impresoraEnBaseDatos = false;
        for (BluetoothDevice device : pairedDevices) {
            printerName = device.getName();

            //busco la impresora en lo que esta guardado en la bd
            if (!listImpresoras.isEmpty() && listImpresoras != null && listImpresoras.size() > 0 ){
                for (Impresora impresora : listImpresoras){
                    if (impresora.getDescripcion().toUpperCase().equals(printerName.toUpperCase())){
                        widthPrinter = impresora.getTamanioHoja();
                        printer = device;
                        impresoraEnBaseDatos = true;
                        break;
                    }
                }
                if (impresoraEnBaseDatos) break;
            }
            // #15194
            if (PrintersEnum.existsPrinter(printerName) && !impresoraEnBaseDatos) {
                widthPrinter = UtilPrinter.printersSizeMap.get(printerName);
                printer = device;
                break;
            }else if (PrintersEnum.existsPrinter(printerName.substring(0,5)) && !impresoraEnBaseDatos) {
                printerName = printerName.substring(0,5);
                widthPrinter = UtilPrinter.printersSizeMap.get(printerName);
                printer = device;
                break;
            }
        }
        if (printer == null) throw new Exception("Las impresoras configuradas en el sistema no coincide con la emparejada en su dispositivo");
        Method m = printer.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
        socket = (BluetoothSocket) m.invoke(printer, 1);

        try {
            socket.connect();
        } catch (Exception e) {
            throw new Exception("La impresora se encuentra apagada");
        }

        os = socket.getOutputStream();
        ps = PrintStreamFactory.getPrintStream(printerName, os, widthPrinter);
        if(printer == null)
            throw new Exception("No se encuentra vinculado la impresora con el dispositivo movil");
    }

    protected List<String> dividirString(String cadenaADividir){
        List<String> nombreEmpresaLargo = new ArrayList<String>();
        if(cadenaADividir.length() > widthPrinter){
            String[] empresaName = cadenaADividir.split("");
            for(int i = (int)cadenaADividir.length()/2;  i < cadenaADividir.length(); i++){
                if(empresaName[i].equals(" ")){
                    nombreEmpresaLargo.add(UtilPrinter.center(cadenaADividir.substring(0, i).trim(), widthPrinter));
                    nombreEmpresaLargo.add(UtilPrinter.center(cadenaADividir.substring(i).trim(), widthPrinter));
                    break;
                }
            }
        }else
            nombreEmpresaLargo.add(empresa);
        return nombreEmpresaLargo;
    }
}
