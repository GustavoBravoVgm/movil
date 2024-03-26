package com.ar.vgmsistemas.repository;

import android.content.Context;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.exclusionmutua.Monitor;
import com.ar.vgmsistemas.repository.implementation.AccionesComDetalleRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.AccionesComRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ArticuloRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.AuditoriaGpsRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.BancoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CantidadesMovimientoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CategoriaFiscalRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ChequeRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ClienteRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ClienteVendedorRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CodigoAutorizacionCobranzaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ComercioLoginRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CompraRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ComprasImpuestosRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CondicionDirscRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CondicionRentaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CondicionVentaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.CuentaCorrienteRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.DepositoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.DescuentoProveedorGeneralRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.DescuentoProveedorRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.DocumentoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.DocumentosListaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.EmpresaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.EntregaRendicionRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.EntregaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ErrorMovilRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.HojaDetalleRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.HojaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ImpresoraRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ImpuestoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.LineaIntegradoMercaderiaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ListaPrecioDetalleRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ListaPrecioRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.LocalidadRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.MarcaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.MotivoAutorizacionRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.MotivoCreditoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.MotivoNoPedidoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.MovimientoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.NegocioRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.NoPedidoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ObjetivoVentaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.PagoEfectivoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.PlanCuentaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.PromocionRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.PromocionRequisitoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ProveedorRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ProveedorVendComercioRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ProvinciaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.RangoRentabilidadRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ReciboDetalleRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.ReciboRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.RecursoHumanoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.RepartidorRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.RetencionRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.RubroRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.SecuenciaRuteoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.SubrubroRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.SucursalRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.TipoMonedaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.UbicacionGeograficaRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.VendedorObjetivoDetalleRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.VendedorObjetivoRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.VendedorRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.VentaDetalleRepositoryImpl;
import com.ar.vgmsistemas.repository.implementation.VentaRepositoryImpl;
import com.ar.vgmsistemas.utils.SdSpaceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

public class RepositoryImpl extends RepositoryFactory {
    //Creo instancia de base de datos con Room
    private static AppDataBase _db;

    public RepositoryImpl(Context context) {
        this._context = context;
        _db = AppDataBase.getInstance(_context);
    }

    /***repositories implementations***/
    @Override
    public IAccionesComRepository getAccionesComRepository() {
        return new AccionesComRepositoryImpl(_db);
    }

    @Override
    public IAccionesComDetalleRepository getAccionesComDetalleRepository() {
        return new AccionesComDetalleRepositoryImpl(_db);
    }

    @Override
    public IArticuloRepository getArticuloRepository() {
        return new ArticuloRepositoryImpl(_db);
    }

    @Override
    public IAuditoriaGpsRepository getAuditoriaGpsRepository() {
        return new AuditoriaGpsRepositoryImpl(_db);
    }

    @Override
    public IBancoRepository getBancoRepository() {
        return new BancoRepositoryImpl(_db);
    }

    @Override
    public ICantidadesMovimientoRepository getCantidadesMovimientoRepository() {
        return new CantidadesMovimientoRepositoryImpl(_db);
    }

    @Override
    public ICategoriaFiscalRepository getCategoriaFiscalRepository() {
        return new CategoriaFiscalRepositoryImpl(_db);
    }

    @Override
    public IChequeRepository getChequeRepository() {
        return new ChequeRepositoryImpl(_db);
    }

    @Override
    public IClienteRepository getClienteRepository() {
        return new ClienteRepositoryImpl(_db);
    }

    @Override
    public IClienteVendedorRepository getClienteVendedorRepository() {
        return new ClienteVendedorRepositoryImpl(_db);
    }

    @Override
    public ICodigoAutorizacionCobranzaRepository getCodigoAutorizacionCobranzaRepository() {
        return new CodigoAutorizacionCobranzaRepositoryImpl(_db);
    }

    @Override
    public IComercioLoginRepository getComercioLoginRepository() {
        return new ComercioLoginRepositoryImpl(_db);
    }

    @Override
    public ICompraRepository getCompraRepository() {
        return new CompraRepositoryImpl(_db);
    }

    @Override
    public IComprasImpuestosRepository getComprasImpuestosRepository() {
        return new ComprasImpuestosRepositoryImpl(_db);
    }

    @Override
    public ICondicionDirscRepository getCondicionDirscRepository() {
        return new CondicionDirscRepositoryImpl(_db);
    }

    @Override
    public ICondicionRentaRepository getCondicionRentaRepository() {
        return new CondicionRentaRepositoryImpl(_db);
    }

    @Override
    public ICondicionVentaRepository getCondicionVentaRepository() {
        return new CondicionVentaRepositoryImpl(_db);
    }

    /*@Override
    public IControlesMovilRepository getControlesMovilRepository() {
        return new ControlesMovilRepositoryImpl(_db);
    }*/

    @Override
    public ICuentaCorrienteRepository getCuentaCorrienteRepository() {
        return new CuentaCorrienteRepositoryImpl(_db);
    }

    @Override
    public IDepositoRepository getDepositoRepository() {
        return new DepositoRepositoryImpl(_db);
    }

    @Override
    public IDescuentoProveedorRepository getDescuentoProveedorRepository() {
        return new DescuentoProveedorRepositoryImpl(_db);
    }

    @Override
    public IDescuentoProveedorGeneralRepository getDescuentoProveedorGeneralRepository() {
        return new DescuentoProveedorGeneralRepositoryImpl(_db);
    }

    @Override
    public IDocumentoRepository getDocumentoRepository() {
        return new DocumentoRepositoryImpl(_db);
    }

    @Override
    public IDocumentosListaRepository getDocumentosListaRepository() {
        return new DocumentosListaRepositoryImpl(_db);
    }

    @Override
    public IEmpresaRepository getEmpresaRepository() {
        return new EmpresaRepositoryImpl(_db);
    }

    @Override
    public IEntregaRendicionRepository getEntregaRendicionRepository() {
        return new EntregaRendicionRepositoryImpl(_db);
    }

    @Override
    public IEntregaRepository getEntregaRepository() {
        return new EntregaRepositoryImpl(_db);
    }

    @Override
    public IErrorRepository getErrorRepository() {
        return new ErrorMovilRepositoryImpl(_db);
    }

    @Override
    public IHojaRepository getHojaRepository() {
        return new HojaRepositoryImpl(_db);
    }

    @Override
    public IHojaDetalleRepository getHojaDetalleRepository() {
        return new HojaDetalleRepositoryImpl(_db);
    }

    @Override
    public IImpresoraRepository getImpresoraRepository() {
        return new ImpresoraRepositoryImpl(_db);
    }

    @Override
    public IImpuestoRepository getImpuestoRepository() {
        return new ImpuestoRepositoryImpl(_db);
    }

    @Override
    public LineaIntegradoMercaderiaRepositoryImpl getLineaIntegradoMercaderiaRepository() {
        return new LineaIntegradoMercaderiaRepositoryImpl(_db);
    }


    @Override
    public IListaPrecioRepository getListaPrecioRepository() {
        return new ListaPrecioRepositoryImpl(_db);
    }

    @Override
    public IListaPrecioDetalleRepository getListaPrecioDetalleRepository() {
        return new ListaPrecioDetalleRepositoryImpl(_db);
    }

    @Override
    public ILocalidadRepository getLocalidadRepository() {
        return new LocalidadRepositoryImpl(_db);
    }

    @Override
    public IMarcaRepository getMarcaRepository() {
        return new MarcaRepositoryImpl(_db);
    }

    @Override
    public IMotivosAutorizacionRepository getMotivoAutorizacionRepository() {
        return new MotivoAutorizacionRepositoryImpl(_db);
    }

    @Override
    public IMotivoCreditoRepository getMotivoCreditoRepository() {
        return new MotivoCreditoRepositoryImpl(_db);
    }

    @Override
    public IMotivoNoPedidoRepository getMotivoNoPedidoRepository() {
        return new MotivoNoPedidoRepositoryImpl(_db);
    }

    @Override
    public IMovimientoRepository getMovimientoRepository() {
        return new MovimientoRepositoryImpl(_db);
    }

    @Override
    public INegocioRepository getNegocioRepository() {
        return new NegocioRepositoryImpl(_db);
    }

    @Override
    public INoPedidoRepository getNoPedidoRepository() {
        return new NoPedidoRepositoryImpl(_db);
    }

    @Override
    public IObjetivoVentaRepository getObjetivoVentaRepository() {
        return new ObjetivoVentaRepositoryImpl(_db);
    }

    @Override
    public IPagoEfectivoRepository getPagoEfectivoRepository() {
        return new PagoEfectivoRepositoryImpl(_db);
    }

    @Override
    public IPlanCuentaRepository getPlanCuentaRepository() {
        return new PlanCuentaRepositoryImpl(_db);
    }

    @Override
    public IPromocionRepository getPromocionRepository() {
        return new PromocionRepositoryImpl(_db);
    }

    @Override
    public IPromocionRequisitoRepository getPromocionRequisitoRepository() {
        return new PromocionRequisitoRepositoryImpl(_db);
    }

    @Override
    public IProveedorRepository getProveedorRepository() {
        return new ProveedorRepositoryImpl(_db);
    }

    @Override
    public IProveedorVendComercioRepository getProveedorVendComercioRepository() {
        return new ProveedorVendComercioRepositoryImpl(_db);
    }

    @Override
    public IProvinciaRepository getProvinciaRepository() {
        return new ProvinciaRepositoryImpl(_db);
    }

    @Override
    public IRangoRentabilidadRepository getRangoRentabilidadRepository() {
        return new RangoRentabilidadRepositoryImpl(_db);
    }

    public IReciboDetalleRepository getReciboDetalleRepository() {
        return new ReciboDetalleRepositoryImpl(_db);
    }

    @Override
    public IReciboRepository getReciboRepository() {
        return new ReciboRepositoryImpl(_db);
    }

    @Override
    public IRecursoHumanoRepository getRecursoHumanoRepository() {
        return new RecursoHumanoRepositoryImpl(_db);
    }

    @Override
    public IRepartidorRepository getRepartidorRepository() {
        return new RepartidorRepositoryImpl(_db);
    }

    @Override
    public IRetencionRepository getRetencionRepository() {
        return new RetencionRepositoryImpl(_db);
    }

    @Override
    public IRubroRepository getRubroRepository() {
        return new RubroRepositoryImpl(_db);
    }

    @Override
    public ISecuenciaRuteoRepository getSecuenciaRuteoRepository() {
        return new SecuenciaRuteoRepositoryImpl(_db);
    }

    @Override
    public ISubrubroRepository getSubrubroRepository() {
        return new SubrubroRepositoryImpl(_db);
    }

    @Override
    public ISucursalRepository getSucursalRepository() {
        return new SucursalRepositoryImpl(_db);
    }

    @Override
    public ITipoMonedaRepository getTipoMonedaRepository() {
        return new TipoMonedaRepositoryImpl(_db);
    }

    @Override
    public IUbicacionGeograficaRepository getUbicacionGeograficaRepository() {
        return new UbicacionGeograficaRepositoryImpl(_db);
    }

    @Override
    public IVendedorObjetivoDetalleRepository getVendedorObjetivoDetalleRepository() {
        return new VendedorObjetivoDetalleRepositoryImpl(_db);
    }

    @Override
    public IVendedorObjetivoRepository getVendedorObjetivoRepository() {
        return new VendedorObjetivoRepositoryImpl(_db);
    }

    @Override
    public IVendedorRepository getVendedorRepository() {
        return new VendedorRepositoryImpl(_db);
    }

    @Override
    public IVentaDetalleRepository getVentaDetalleRepository() {
        return new VentaDetalleRepositoryImpl(_db);
    }

    @Override
    public IVentaRepository getVentaRepository() {
        return new VentaRepositoryImpl(_db);
    }

    /*base de datos*/
    @Override
    public boolean backupDb() throws Exception {
        boolean backUpOk = false;
        boolean backUpJournal = false;
        // Verifico que no haya transacciones activas
        try {
            if (_db == null || !_db.inTransaction()) {
                //Valido que haya espacio suficiente para crear el archivo de backup
                boolean isSpaceFree = validateEspacioSDBackup();
                if (isSpaceFree) {
                    //Hay espacio en la tarjeta SD suficiente para hacer el backup lo realizo
                    //Bloqueo la base de datos
                    Monitor.lock();
                    String pathDb = Preferencia.getPathDB();
                    File file = new File(pathDb);
                    if (file.exists()) {
                        String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
                        String pathBackupDb = Preferencia.getPathDB() + "." + timeStamp;
                        // Verifico si existen archivos *-journal
                        backUpJournal = getJournal(timeStamp);
                        FileChannel backupDbChannel = new FileOutputStream(pathBackupDb).getChannel();
                        FileChannel dbChannel = new FileInputStream(pathDb).getChannel();
                        dbChannel.transferTo(0, dbChannel.size(), backupDbChannel);
                        backupDbChannel.close();
                        dbChannel.close();
                        PreferenciaBo.getInstance().getPreferencia().setPathDbBackup(pathBackupDb);
                        backUpOk = true;
                    } else {
                        // No existe el archivo preventa.sqlite
                        backUpOk = true;
                        backUpJournal = true;
                    }
                    //Al finalizar desbloqueo la base de datos
                    Monitor.unlock();
                }
            }
        } catch (IllegalStateException ise) {
            //cuando se elimina la bd, en algunos dispositvos, el _dafactory.inTransaction() revienta por eso se agrego el try
            backUpOk = false;
            backUpJournal = false;
            Monitor.unlock();
        }
        return backUpOk && backUpJournal;
    }

    @Override
    public boolean validateEspacioSDSincronizacion() {
        boolean isSpaceFree = false;
        String pathDb = Preferencia.getPathDB();
        File file = new File(pathDb);
        //Requiero espacio mayor o igual al doble para que sincronice por el zip
        long spaceReq = file.length() * 2;
        //Obtengo el espacio libre de la tarjeta SD
        long spaceFree = SdSpaceManager.getExternalAvailableSpaceInBytes();
        //Evalúo la disponibilidad de espacio en tarjeta SD
        if (spaceFree > spaceReq) {
            isSpaceFree = true;
        }
        return isSpaceFree;
    }

    @Override
    public void restoreDb() throws Exception {
        // Verifico que no haya transacciones activas
        if (_db == null || !dataBaseExists() || !_db.inTransaction()) {
            // Verificar si existe un journal backUp
            boolean backupDb = false;
            if (dataBaseExists()) {
                File file = new File(Preferencia.getPathDB());
                String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
                String pathBackupDb = Preferencia.getPathDB() + "." + timeStamp;
                File fileBackUp = new File(pathBackupDb);
                backupDb = file.renameTo(fileBackUp);
            }
            if (backupDb || !dataBaseExists()) {
                String pathDb = Preferencia.getPathDB();
                String pathBackupDb = PreferenciaBo.getInstance().getPreferencia().getPathDbBackup();

                FileChannel dbChannel = new FileOutputStream(pathDb).getChannel();
                FileChannel backupDbChannel = new FileInputStream(pathBackupDb).getChannel();

                //restauro el backup
                backupDbChannel.transferTo(0, backupDbChannel.size(), dbChannel);
                backupDbChannel.close();
                dbChannel.close();

                //this.resetDb();
                Monitor.unlock();
            }
        }
    }

    @Override
    public void resetDb() {
        _db = null;
    }

    @Override
    public void reOpenConnection() {
        if (dataBaseExists() && _db != null) {
            _db.close();
            this.open();
        }
    }

    @Override
    public void cleanBackups() {
        switch (Preferencia.getBorradoBckupPreferido()) {
            case R.id.rdbCantidad:
                cleanBackupsPorCantidad();
                break;
            case R.id.rdbIntervalo:

            default:
                cleanBackupsXIntervaloTiempo();
                break;
        }

    }

    public void cleanBackupsXIntervaloTiempo() {
        // instanciamos un objeto File para crear un nuevo directorio
        File directorio;
        directorio = new File(Preferencia.getPathSistema());
        // Crea el directorio
        directorio.mkdirs();
        File[] archivos = directorio.listFiles();
        String comienzoNombre = "preventa.sqlite.";

        long fechaActual = Calendar.getInstance().getTimeInMillis();
        long diasLimpieza = Preferencia.getIntervaloBorradoBck();
        long diasEnMilis = 1000 * 60 * 60 * 24 * diasLimpieza;
        long fechaBorrado = fechaActual - diasEnMilis;

        if (archivos != null) { //En caso de que se hayan eliminado manualmente los datos de la aplicación no hacer nada
            for (int i = 0; i < archivos.length; i++) {
                long fechaModificacion = archivos[i].lastModified();
                if (archivos[i].getName().startsWith(comienzoNombre)) {
                    //Setea la fecha que esta en el nombre del archivo "preventa.sqlite." despues del punto y
                    //toma en cuenta eso tambien para hacer el borrado
                    long fechaModificacion2;
                    try {
                        fechaModificacion2 = Long.parseLong(archivos[i].getName().substring(16));
                    } catch (Exception e) {
                        continue;
                    }

                    if (fechaModificacion < fechaBorrado || fechaModificacion2 < fechaBorrado) {
                        archivos[i].delete();
                    }
                }
            }
            cleanJournals();
        }
    }

    public void cleanJournals() {//deja los dos ultimos nada mas
        File directorio;
        directorio = new File(Preferencia.getPathSistema());
        // Crea el directorio
        directorio.mkdirs();
        File[] archivos = directorio.listFiles();
        String comienzoNombre = "preventa.sqlite-journal.";

        //ordeno por fecha para eliminar los mas viejos
        Arrays.sort(archivos, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });
        int cantidadBackup = 0;
        for (int i = 0; i <= archivos.length - 1; i++) {
            if (archivos[i].getName().startsWith(comienzoNombre)) {
                cantidadBackup++;
            }
        }
        int cantBackupsABorrar = cantidadBackup - 2;
        if (cantBackupsABorrar < archivos.length) {
            int cantidadBorrados = 0;
            for (int i = 0; i <= archivos.length - 1; i++) {
                if (cantidadBorrados >= cantBackupsABorrar) {
                    break;
                }
                if (archivos[i].getName().startsWith(comienzoNombre)) {
                    archivos[i].delete();
                    cantidadBorrados++;
                }
            }
        }
    }

    public void cleanBackupsPorCantidad() {
        File directorio;
        directorio = new File(Preferencia.getPathSistema());
        // Crea el directorio
        directorio.mkdirs();
        File[] archivos = directorio.listFiles();
        String comienzoNombre = "preventa.sqlite.";

        //ordeno por fecha para eliminar los mas viejos
        Arrays.sort(archivos, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });
        int cantidadMaximaDeBackups = Preferencia.getBorradoBckXCantidad();//enganchar con la preferencia de cantidad maxima de backups
        int cantidadBackup = 0;
        for (int i = 0; i <= archivos.length - 1; i++) {
            if (archivos[i].getName().startsWith(comienzoNombre)) {
                cantidadBackup++;
            }
        }
        int cantBackupsABorrar = cantidadBackup - cantidadMaximaDeBackups;
        if (cantBackupsABorrar < archivos.length) {
            int cantidadBorrados = 0;
            for (int i = 0; i <= archivos.length - 1; i++) {
                if (cantidadBorrados >= cantBackupsABorrar) {
                    break;
                }
                if (archivos[i].getName().startsWith(comienzoNombre)) {
                    archivos[i].delete();
                    cantidadBorrados++;
                }
            }
        }
        cleanJournals();
    }


    @Override
    public boolean dataBaseExists() {
        String pathDb = Preferencia.getPathDB();
        boolean existe;
        File file = new File(pathDb);
        existe = file.exists();
        return existe;
    }

    public boolean getJournal(String timeStamp) throws Exception {
        String pathJournal = Preferencia.getPathDB() + "-journal";
        File archivoJournal = new File(pathJournal);

        if (archivoJournal.exists()) {
            String pathBackupJournal = pathJournal + "." + timeStamp;

            FileChannel journalDbBackUpChannel = new FileOutputStream(pathBackupJournal).getChannel();
            FileChannel dbJournalChannel = new FileInputStream(pathJournal).getChannel();

            dbJournalChannel.transferTo(0, dbJournalChannel.size(), journalDbBackUpChannel);
            journalDbBackUpChannel.close();
            dbJournalChannel.close();
        }
        return true;
    }


    private boolean validateEspacioSDBackup() {
        boolean isSpaceFree = false;
        String pathDb = Preferencia.getPathDB();
        File file = new File(pathDb);
        //Obtengo su tamaño en bytes
        long spaceReq = file.length();
        //Obtengo el espacio libre de la tarjeta SD
        long spaceFree = SdSpaceManager.getExternalAvailableSpaceInBytes();
        //Evalúo la disponibilidad de espacio en tarjeta SD
        if (spaceFree > spaceReq) {
            isSpaceFree = true;
        }
        return isSpaceFree;
    }

    @Override
    public boolean deleteZip() {
        String pathDbZip = Preferencia.getPathDBZip();
        File file = new File(pathDbZip);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }


    @Override
    public void closeConnection() {
        _db.close();
    }

    @Override
    public void open() {
        _db = AppDataBase.getInstance(_context);
    }
}
