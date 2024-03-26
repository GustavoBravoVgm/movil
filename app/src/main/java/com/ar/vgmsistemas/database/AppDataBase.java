package com.ar.vgmsistemas.database;

import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Transaction;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ObjetivoVentaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.database.dao.IAccionesComDao;
import com.ar.vgmsistemas.database.dao.IAccionesComDetalleDao;
import com.ar.vgmsistemas.database.dao.IAccionesGruposCodigosDao;
import com.ar.vgmsistemas.database.dao.IAccionesGruposDao;
import com.ar.vgmsistemas.database.dao.IArticuloDao;
import com.ar.vgmsistemas.database.dao.IAuditoriaGpsDao;
import com.ar.vgmsistemas.database.dao.IBancoDao;
import com.ar.vgmsistemas.database.dao.ICantidadesMovimientosDao;
import com.ar.vgmsistemas.database.dao.ICategoriaFiscalDao;
import com.ar.vgmsistemas.database.dao.IChequeDao;
import com.ar.vgmsistemas.database.dao.IClienteDao;
import com.ar.vgmsistemas.database.dao.IClienteVendedorDao;
import com.ar.vgmsistemas.database.dao.ICodigoAutorizacionCobranzaDao;
import com.ar.vgmsistemas.database.dao.IComercioLoginDao;
import com.ar.vgmsistemas.database.dao.ICompraDao;
import com.ar.vgmsistemas.database.dao.IComprasImpuestosDao;
import com.ar.vgmsistemas.database.dao.ICondicionDirscDao;
import com.ar.vgmsistemas.database.dao.ICondicionRentaDao;
import com.ar.vgmsistemas.database.dao.ICondicionVentaDao;
import com.ar.vgmsistemas.database.dao.ICuentaCorrienteDao;
import com.ar.vgmsistemas.database.dao.IDepositoDao;
import com.ar.vgmsistemas.database.dao.IDescuentoProveedorDao;
import com.ar.vgmsistemas.database.dao.IDescuentoProveedorGeneralDao;
import com.ar.vgmsistemas.database.dao.IDocumentoDao;
import com.ar.vgmsistemas.database.dao.IDocumentosListaDao;
import com.ar.vgmsistemas.database.dao.IEmpresaDao;
import com.ar.vgmsistemas.database.dao.IEntregaDao;
import com.ar.vgmsistemas.database.dao.IEntregaRendicionDao;
import com.ar.vgmsistemas.database.dao.IErrorMovilDao;
import com.ar.vgmsistemas.database.dao.IGrupoClientesDetalleDao;
import com.ar.vgmsistemas.database.dao.IHojaDao;
import com.ar.vgmsistemas.database.dao.IHojaDetalleDao;
import com.ar.vgmsistemas.database.dao.IImpresoraDao;
import com.ar.vgmsistemas.database.dao.IImpuestoDao;
import com.ar.vgmsistemas.database.dao.IInfoDao;
import com.ar.vgmsistemas.database.dao.IListaPrecioDao;
import com.ar.vgmsistemas.database.dao.IListaPrecioDetalleDao;
import com.ar.vgmsistemas.database.dao.ILocalidadDao;
import com.ar.vgmsistemas.database.dao.IMarcaDao;
import com.ar.vgmsistemas.database.dao.IMotivoAnulacionVentaDao;
import com.ar.vgmsistemas.database.dao.IMotivoAutorizacionDao;
import com.ar.vgmsistemas.database.dao.IMotivoCreditoDao;
import com.ar.vgmsistemas.database.dao.IMotivoNoPedidoDao;
import com.ar.vgmsistemas.database.dao.IMovimientoDao;
import com.ar.vgmsistemas.database.dao.INegocioDao;
import com.ar.vgmsistemas.database.dao.INoPedidoDao;
import com.ar.vgmsistemas.database.dao.IObjetivoVentaDao;
import com.ar.vgmsistemas.database.dao.IPagoEfectivoDao;
import com.ar.vgmsistemas.database.dao.IPlanCuentaDao;
import com.ar.vgmsistemas.database.dao.IPromocionDetalleDao;
import com.ar.vgmsistemas.database.dao.IPromocionRequisitoDao;
import com.ar.vgmsistemas.database.dao.IProveedorDao;
import com.ar.vgmsistemas.database.dao.IProveedorVendComercioDao;
import com.ar.vgmsistemas.database.dao.IProvinciaDao;
import com.ar.vgmsistemas.database.dao.IRangoRentabilidadDao;
import com.ar.vgmsistemas.database.dao.IReciboDao;
import com.ar.vgmsistemas.database.dao.IReciboDetalleDao;
import com.ar.vgmsistemas.database.dao.IRecursoHumanoDao;
import com.ar.vgmsistemas.database.dao.IRepartidorDao;
import com.ar.vgmsistemas.database.dao.IRetencionDao;
import com.ar.vgmsistemas.database.dao.IRubroDao;
import com.ar.vgmsistemas.database.dao.ISecuenciaRuteoDao;
import com.ar.vgmsistemas.database.dao.IStockDao;
import com.ar.vgmsistemas.database.dao.ISubrubroDao;
import com.ar.vgmsistemas.database.dao.ISucursalDao;
import com.ar.vgmsistemas.database.dao.ITipoMonedaDao;
import com.ar.vgmsistemas.database.dao.IUbicacionGeograficaDao;
import com.ar.vgmsistemas.database.dao.IVendedorDao;
import com.ar.vgmsistemas.database.dao.IVendedorObjetivoDao;
import com.ar.vgmsistemas.database.dao.IVendedorObjetivoDetalleDao;
import com.ar.vgmsistemas.database.dao.IVentaDao;
import com.ar.vgmsistemas.database.dao.IVentaDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.AccionesComBd;
import com.ar.vgmsistemas.database.dao.entity.AccionesComDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.AccionesGruposBd;
import com.ar.vgmsistemas.database.dao.entity.AccionesGruposCodigosBd;
import com.ar.vgmsistemas.database.dao.entity.ArticuloBd;
import com.ar.vgmsistemas.database.dao.entity.AuditoriaGpsBd;
import com.ar.vgmsistemas.database.dao.entity.BancoBd;
import com.ar.vgmsistemas.database.dao.entity.CategoriaFiscalBd;
import com.ar.vgmsistemas.database.dao.entity.ChequeBd;
import com.ar.vgmsistemas.database.dao.entity.ClienteBd;
import com.ar.vgmsistemas.database.dao.entity.ClienteVendedorBd;
import com.ar.vgmsistemas.database.dao.entity.CodigoAutorizacionCobranzaBd;
import com.ar.vgmsistemas.database.dao.entity.ComercioLoginBd;
import com.ar.vgmsistemas.database.dao.entity.CompraBd;
import com.ar.vgmsistemas.database.dao.entity.ComprasImpuestosBd;
import com.ar.vgmsistemas.database.dao.entity.CondicionDirscBd;
import com.ar.vgmsistemas.database.dao.entity.CondicionRentaBd;
import com.ar.vgmsistemas.database.dao.entity.CondicionVentaBd;
import com.ar.vgmsistemas.database.dao.entity.CuentaCorrienteBd;
import com.ar.vgmsistemas.database.dao.entity.DepositoBd;
import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorBd;
import com.ar.vgmsistemas.database.dao.entity.DescuentoProveedorGeneralBd;
import com.ar.vgmsistemas.database.dao.entity.DocumentoBd;
import com.ar.vgmsistemas.database.dao.entity.DocumentosListaBd;
import com.ar.vgmsistemas.database.dao.entity.EmpresaBd;
import com.ar.vgmsistemas.database.dao.entity.EntregaBd;
import com.ar.vgmsistemas.database.dao.entity.EntregaRendicionBd;
import com.ar.vgmsistemas.database.dao.entity.ErrorMovilBd;
import com.ar.vgmsistemas.database.dao.entity.GrupoClientesDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.HojaBd;
import com.ar.vgmsistemas.database.dao.entity.HojaDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.ImpresoraBd;
import com.ar.vgmsistemas.database.dao.entity.ImpuestoBd;
import com.ar.vgmsistemas.database.dao.entity.InfoBd;
import com.ar.vgmsistemas.database.dao.entity.ListaPrecioBd;
import com.ar.vgmsistemas.database.dao.entity.ListaPrecioDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.LocalidadBd;
import com.ar.vgmsistemas.database.dao.entity.MarcaBd;
import com.ar.vgmsistemas.database.dao.entity.MotivoAnulacionVentaBd;
import com.ar.vgmsistemas.database.dao.entity.MotivoAutorizacionBd;
import com.ar.vgmsistemas.database.dao.entity.MotivoCreditoBd;
import com.ar.vgmsistemas.database.dao.entity.MotivoNoPedidoBd;
import com.ar.vgmsistemas.database.dao.entity.MovimientoBd;
import com.ar.vgmsistemas.database.dao.entity.NegocioBd;
import com.ar.vgmsistemas.database.dao.entity.NoPedidoBd;
import com.ar.vgmsistemas.database.dao.entity.ObjetivoVentaBd;
import com.ar.vgmsistemas.database.dao.entity.PagoEfectivoBd;
import com.ar.vgmsistemas.database.dao.entity.PlanCuentaBd;
import com.ar.vgmsistemas.database.dao.entity.PromocionDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.PromocionRequisitoBd;
import com.ar.vgmsistemas.database.dao.entity.ProveedorBd;
import com.ar.vgmsistemas.database.dao.entity.ProveedorVendComercioBd;
import com.ar.vgmsistemas.database.dao.entity.ProvinciaBd;
import com.ar.vgmsistemas.database.dao.entity.RangoRentabilidadBd;
import com.ar.vgmsistemas.database.dao.entity.ReciboBd;
import com.ar.vgmsistemas.database.dao.entity.ReciboDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.RecursoHumanoBd;
import com.ar.vgmsistemas.database.dao.entity.RepartidorBd;
import com.ar.vgmsistemas.database.dao.entity.RetencionBd;
import com.ar.vgmsistemas.database.dao.entity.RubroBd;
import com.ar.vgmsistemas.database.dao.entity.SecuenciaRuteoBd;
import com.ar.vgmsistemas.database.dao.entity.StockBd;
import com.ar.vgmsistemas.database.dao.entity.SubrubroBd;
import com.ar.vgmsistemas.database.dao.entity.SucursalBd;
import com.ar.vgmsistemas.database.dao.entity.TipoMonedaBd;
import com.ar.vgmsistemas.database.dao.entity.UbicacionGeograficaBd;
import com.ar.vgmsistemas.database.dao.entity.VendedorBd;
import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoBd;
import com.ar.vgmsistemas.database.dao.entity.VendedorObjetivoDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.VentaBd;
import com.ar.vgmsistemas.database.dao.entity.VentaDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.view.AccionesComMaxEmpViewBd;
import com.ar.vgmsistemas.database.dao.entity.view.AccionesComMaxProvViewBd;
import com.ar.vgmsistemas.database.dao.entity.view.CantidadMovimientosViewBd;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.exclusionmutua.Monitor;
import com.ar.vgmsistemas.helper.TipoOperacion;
import com.ar.vgmsistemas.utils.FileManager;
import com.ar.vgmsistemas.utils.SdSpaceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

/**
 * Singleton para acceder a la base de datos
 * Se debe anotar con @Database con los parametros entities: que es un arreglo de todas
 * nuestras clases anotadas con @Entity las cuales representan las tablas de nuesta db,
 * version es la versión actual de nuestra db
 */
@Database(entities = {
        AccionesComBd.class, AccionesComDetalleBd.class, AccionesGruposBd.class, AccionesGruposCodigosBd.class,
        ArticuloBd.class, AuditoriaGpsBd.class, BancoBd.class,
        ChequeBd.class, CategoriaFiscalBd.class, ClienteBd.class, ClienteVendedorBd.class, CodigoAutorizacionCobranzaBd.class,
        ComercioLoginBd.class, CompraBd.class, ComprasImpuestosBd.class, CondicionRentaBd.class,
        CondicionDirscBd.class, CondicionVentaBd.class, CuentaCorrienteBd.class,
        DepositoBd.class, DescuentoProveedorBd.class, DescuentoProveedorGeneralBd.class, DocumentoBd.class,
        DocumentosListaBd.class, EmpresaBd.class, EntregaBd.class, EntregaRendicionBd.class, ErrorMovilBd.class,
        GrupoClientesDetalleBd.class, HojaBd.class, HojaDetalleBd.class, ImpresoraBd.class, ImpuestoBd.class,
        InfoBd.class, ListaPrecioBd.class, ListaPrecioDetalleBd.class, LocalidadBd.class,
        MarcaBd.class, MotivoAnulacionVentaBd.class, MotivoAutorizacionBd.class, MotivoCreditoBd.class, MotivoNoPedidoBd.class, MovimientoBd.class,
        NegocioBd.class, NoPedidoBd.class, ObjetivoVentaBd.class, PagoEfectivoBd.class, PlanCuentaBd.class,
        PromocionRequisitoBd.class, PromocionDetalleBd.class, ProveedorBd.class, ProveedorVendComercioBd.class,
        ProvinciaBd.class, RangoRentabilidadBd.class, ReciboBd.class, ReciboDetalleBd.class, RecursoHumanoBd.class,
        RepartidorBd.class, RetencionBd.class, RubroBd.class,
        SecuenciaRuteoBd.class, SubrubroBd.class, StockBd.class, SucursalBd.class, TipoMonedaBd.class,
        UbicacionGeograficaBd.class, VendedorBd.class, VendedorObjetivoBd.class, VendedorObjetivoDetalleBd.class,
        VentaBd.class, VentaDetalleBd.class},/*tablas de la bd.- No se mapean las siguientes tablas: listasPromocionales,mtvanulacionvta,sqlite_sequence, vend_comercio */ /*33*/
        views = {AccionesComMaxEmpViewBd.class, AccionesComMaxProvViewBd.class, CantidadMovimientosViewBd.class},/*vistas de la bd*/
        version = 2 /*version de la bd*/,
        exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase ROOM_INSTANCE;

    /*Singleton para la instancia de BD*/
    public static AppDataBase getInstance(final Context context) {
        if (FileManager.isSdPresent()) { // chequeo que la sdcard este montada
            if (Monitor.isRelease()) { // chequeo que no este bloqueada la BD
                String dbPathDB;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dbPathDB = context.getExternalFilesDir(null) + "" + Preferencia.getFolderVgmgemaAndPathDB();
                } else {
                    dbPathDB = Preferencia.getPathDB();
                }
                /*si la instancia esta nula, creamos una nueva instancia*/
                if (ROOM_INSTANCE == null || !ROOM_INSTANCE.isOpen()) {
                    /* para crear la instancia de Room necesitamos el context de la aplicacion,
                     * la clase que tiene la logica para crear las tablas (@Entity) y las funciones
                     * para acceder a los DAO y por último el nombre con el cual se creará el archivo
                     * físico de nuesta db*/
                    ROOM_INSTANCE = Room.databaseBuilder(context, AppDataBase.class, dbPathDB)
                            /*para este ejemplo ejecutaremos nuestras consultas en el MainThread
                             * esta opcion no se debe utilizar en producción.*/
                            .allowMainThreadQueries()
                            /* en este ejemplo no utilizaremos Migrations
                             * con este metodo la base de datos se recreara cada vez que actualicemos
                             * nuesta aplicación*/
                            .fallbackToDestructiveMigration()
                            /*JournalMode truncate: hace que se trabaje con un solo archivo de bd sin los archivo
                             * shm y wall*/
                            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                            .build();
                }
            } else {
                throw new RuntimeException("Error al acceder a la base de datos, se esta realizando una copia de seguridad");
            }
        } else {
            throw new RuntimeException("Error al acceder a la tarjeta de memoria.");
        }
        return ROOM_INSTANCE;
    }

    /**
     * Operaciones con la base de datos
     */

    public static void cleanBackups() {
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

    public static void cleanBackupsXIntervaloTiempo() {
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
                    long fechaModificacion2 = 0;
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

    public static void cleanJournals() {//deja los dos ultimos nada mas
        File directorio;
        directorio = new File(Preferencia.getPathSistema());
        // Crea el directorio
        if (directorio.mkdirs()) {
            File[] archivos = directorio.listFiles();
            String comienzoNombre = "preventa.sqlite-journal.";

            if (archivos != null) {
                //ordeno por fecha para eliminar los mas viejos
                Arrays.sort(archivos, (f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));
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
                            if (archivos[i].delete()) cantidadBorrados++;
                        }
                    }
                }
            }
        }
    }

    public static void cleanBackupsPorCantidad() {
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

    public static boolean backupDb(AppDataBase _sqLiteDatabase) throws Exception {
        boolean backUpOk = false;
        boolean backUpJournal = false;
        // Verifico que no haya transacciones activas
        try {
            if (_sqLiteDatabase == null || !_sqLiteDatabase.inTransaction()) {
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

    public static boolean getJournal(String timeStamp) throws Exception {
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

    private static boolean validateEspacioSDBackup() throws Exception {
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

    public static boolean deleteZip() {
        String pathDbZip = Preferencia.getPathDBZip();
        File file = new File(pathDbZip);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    public void closeConnection(AppDataBase _sqLiteDatabase) throws SQLException {
        _sqLiteDatabase.close();
    }

    public RoomDatabase open(AppDataBase _sqLiteDatabase) throws SQLException {
        return _sqLiteDatabase;
    }

    public boolean dataBaseExists() {
        String pathDb = Preferencia.getPathDB();
        boolean existe = false;
        File file = new File(pathDb);
        existe = file.exists();
        return existe;
    }

    public void reOpenConnection(AppDataBase _sqLiteDatabase) throws Exception {
        if (dataBaseExists() && _sqLiteDatabase != null) {
            _sqLiteDatabase.close();
            open(_sqLiteDatabase);
        }

    }

    public void restoreDb(AppDataBase _sqLiteDatabase) throws Exception {
        // Verifico que no haya transacciones activas
        if (_sqLiteDatabase == null || !dataBaseExists() || !_sqLiteDatabase.inTransaction()) {
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
        } else {

        }
    }

    /**
     * declaramos funciones abstract de nuestros daos para ejecutar las operaciones
     * en la db
     *
     * @return DAO
     */
    public abstract IAccionesComDao accionesComDao();

    public abstract IAccionesComDetalleDao accionesComDetalleDao();

    public abstract IAccionesGruposCodigosDao accionesGruposCodigosDao();

    public abstract IAccionesGruposDao accionesGruposDao();

    public abstract IArticuloDao articuloDao();

    public abstract IAuditoriaGpsDao auditoriaGpsDao();

    public abstract IBancoDao bancoDao();

    public abstract ICantidadesMovimientosDao cantidadesMovimientosDao();

    public abstract ICategoriaFiscalDao categoriaFiscalDao();

    public abstract IChequeDao chequeDao();

    public abstract IClienteDao clienteDao();

    public abstract IClienteVendedorDao clienteVendedorDao();

    public abstract ICodigoAutorizacionCobranzaDao codigoAutorizacionCobranzaDao();

    public abstract IComercioLoginDao comercioLoginDao();

    public abstract ICompraDao compraDao();

    public abstract IComprasImpuestosDao comprasImpuestosDao();

    public abstract ICondicionDirscDao condicionDirscDao();

    public abstract ICondicionRentaDao condicionRentaDao();

    public abstract ICondicionVentaDao condicionVentaDao();

    public abstract ICuentaCorrienteDao cuentaCorrienteDao();

    public abstract IDepositoDao depositoDao();

    public abstract IDescuentoProveedorDao descuentoProveedorDao();

    public abstract IDescuentoProveedorGeneralDao descuentoProveedorGeneralDao();

    public abstract IDocumentoDao documentoDao();

    public abstract IDocumentosListaDao documentosListaDao();

    public abstract IEmpresaDao empresaDao();

    public abstract IEntregaDao entregaDao();

    public abstract IEntregaRendicionDao entregaRendicionDao();

    public abstract IErrorMovilDao errorMovilDao();

    public abstract IGrupoClientesDetalleDao grupoClientesDetalleDao();

    public abstract IHojaDao hojaDao();

    public abstract IHojaDetalleDao hojaDetalleDao();

    public abstract IImpresoraDao impresoraDao();

    public abstract IImpuestoDao impuestoDao();

    public abstract IInfoDao infoDao();

    public abstract IListaPrecioDao listaPrecioDao();

    public abstract IListaPrecioDetalleDao listaPrecioDetalleDao();

    public abstract ILocalidadDao localidadDao();

    public abstract IMarcaDao marcaDao();

    public abstract IMotivoAnulacionVentaDao motivoAnulacionVenta();

    public abstract IMotivoAutorizacionDao motivoAutorizacionDao();

    public abstract IMotivoCreditoDao motivoCreditoDao();

    public abstract IMotivoNoPedidoDao motivoNoPedidoDao();

    public abstract IMovimientoDao movimientoDao();

    public abstract INegocioDao negocioDao();

    public abstract INoPedidoDao noPedidoDao();

    public abstract IObjetivoVentaDao objetivoVentaDao();

    public abstract IPagoEfectivoDao pagoEfectivoDao();

    public abstract IPlanCuentaDao planCuentaDao();

    public abstract IPromocionDetalleDao promocionDetalleDao();

    public abstract IPromocionRequisitoDao promocionRequisitoDao();

    public abstract IProveedorDao proveedorDao();

    public abstract IProveedorVendComercioDao proveedorVendComercioDao();

    public abstract IProvinciaDao provinciaDao();

    public abstract IRangoRentabilidadDao rangoRentabilidadDao();

    public abstract IReciboDao reciboDao();

    public abstract IReciboDetalleDao reciboDetalleDao();

    public abstract IRecursoHumanoDao recursoHumanoDao();

    public abstract IRepartidorDao repartidorDao();

    public abstract IRetencionDao retencionDao();

    public abstract IRubroDao rubroDao();

    public abstract ISecuenciaRuteoDao secuenciaRuteoDao();

    public abstract IStockDao stockDao();

    public abstract ISubrubroDao subrubroDao();

    public abstract ISucursalDao sucursalDao();

    public abstract ITipoMonedaDao tipoMonedaDao();

    public abstract IUbicacionGeograficaDao ubicacionGeograficaDao();

    public abstract IVendedorDao vendedorDao();

    public abstract IVendedorObjetivoDao vendedorObjetivoDao();

    public abstract IVendedorObjetivoDetalleDao vendedorObjetivoDetalleDao();

    public abstract IVentaDao ventaDao();

    public abstract IVentaDetalleDao ventaDetalleDao();

    /***
     * Operaciones realizadas en una misma transacción
     * */

    @Transaction
    public boolean createVentaTransaction(VentaBd ventaBd, List<VentaDetalleBd> ventaDetalleBds,
                                          MovimientoBd movimientoBd, CodigoAutorizacionCobranzaBd codigoAutorizacionCobranzaBd) {
        try {
            if (ROOM_INSTANCE != null) {
                ////actualizo venta
                ROOM_INSTANCE.ventaDao().create(ventaBd);
                ////actualizo el nro de documento
                ROOM_INSTANCE.documentoDao().updateNumeroDocumento(ventaBd.getId().getIdDocumento(), ventaBd.getId().getIdLetra(),
                        ventaBd.getId().getPuntoVenta(), ventaBd.getId().getIdNumeroDocumento());
                ////actualizo venta_detalle
                int tipoOperacion = -1;
                for (VentaDetalleBd item : ventaDetalleBds) {
                    ROOM_INSTANCE.ventaDetalleDao().create(item);
                    tipoOperacion = -1;
                    if (item.getTipoOperacion() == TipoOperacion.delete) {
                        tipoOperacion = 1;
                    }
                    ////actualizo stock
                    ROOM_INSTANCE.stockDao().updateStock(item.getIdArticulo(), item.getCantidad(), tipoOperacion);

                    ////actualizo acciones
                    if (item.getIdAccionComDetalleEmp() != 0 || item.getIdAccionComDetalleProveedor() != 0) {
                        AccionesComDetalleBd accionesComDetalleBd;

                        //Accion CONJUNTA
                        if (item.getIdAccionComDetalleEmp() != 0 && item.getIdAccionComDetalleProveedor() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionEmp(), item.getIdAccionComDetalleEmp());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }

                        //Accion de la EMPRESA
                        if (item.getIdAccionComDetalleEmp() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionEmp(), item.getIdAccionComDetalleEmp());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }

                        //Accion del proveedor
                        if (item.getIdAccionComDetalleProveedor() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionesCom(), item.getIdAccionComDetalleProveedor());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }
                    }

                    //Actualizo cantidad vendida de articulos en objetivos de venta
                    ObjetivoVentaBd mObjVta = ROOM_INSTANCE.objetivoVentaDao().recoveryByID(item.getIdArticulo(), ventaBd.getIdSucursal(),
                            ventaBd.getIdCliente(), ventaBd.getIdComercio(), 0);
                    if (mObjVta != null) {
                        double cantidadVendida = mObjVta.getCantidadVendida() + (item.getCantidad() * ObjetivoVentaBo.SIGNO_SUMA);
                        mObjVta.setCantidadVendida(cantidadVendida);
                        ROOM_INSTANCE.objetivoVentaDao().update(mObjVta);
                    }
                }
                ////actualizo codigo de autorizacion cobranza
                if (codigoAutorizacionCobranzaBd != null) {
                    ROOM_INSTANCE.codigoAutorizacionCobranzaDao().create(codigoAutorizacionCobranzaBd);
                }
                ////actualizo movimiento
                if (movimientoBd.getId() == 0) {
                    movimientoBd.setId(ROOM_INSTANCE.movimientoDao().maxIdMovimiento() + 1);
                }
                ROOM_INSTANCE.movimientoDao().create(movimientoBd);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //realizo rollback
            throw new RuntimeException(e);
        }
        return false;
    }

    @Transaction
    public void createNoPedidoTransaction(NoPedidoBd noPedidoBd, MovimientoBd movimientoBd) {
        try {
            if (ROOM_INSTANCE != null) {
                ////actualizo no pedido
                ROOM_INSTANCE.noPedidoDao().create(noPedidoBd);

                ////actualizo movimiento
                if (movimientoBd.getId() == 0) {
                    movimientoBd.setId(ROOM_INSTANCE.movimientoDao().maxIdMovimiento() + 1);
                }
                ROOM_INSTANCE.movimientoDao().create(movimientoBd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //realizo rollback
            throw new RuntimeException(e);
        }
    }

    @Transaction
    public void createReciboTransaction(ReciboBd reciboBd, Boolean isReciboProvisorio, EntregaBd entregaBd,
                                        ClienteBd clienteBd, List<CuentaCorrienteBd> comprobantesGeneradosBdList,
                                        List<ReciboDetalleBd> reciboDetalleBdList,
                                        List<CuentaCorrienteBd> cuentaCorrienteBdList, List<ChequeBd> chequeBdList,
                                        List<PagoEfectivoBd> pagoEfectivoBdList, List<RetencionBd> retencionBdList,
                                        List<DepositoBd> depositoBdList, MovimientoBd movimientoBd) {
        try {
            if (ROOM_INSTANCE != null) {
                ////creo entrega
                int idEntregaNuevo = ROOM_INSTANCE.entregaDao().maxIdEntrega() + 1;
                entregaBd.setId(idEntregaNuevo);
                ROOM_INSTANCE.entregaDao().create(entregaBd);

                ////Creo o actualizo el recibo
                if (isReciboProvisorio) {
                    ROOM_INSTANCE.reciboDao().create(reciboBd);
                } else {
                    ROOM_INSTANCE.reciboDao().update(reciboBd);
                }

                ////Genero los comprobantes anticipos
                if (comprobantesGeneradosBdList.size() > 0) {
                    for (CuentaCorrienteBd item : comprobantesGeneradosBdList) {
                        ROOM_INSTANCE.cuentaCorrienteDao().create(item);
                    }
                }

                ////Actualizo el cliente con limite de disponibilidad nuevo
                ROOM_INSTANCE.clienteDao().update(clienteBd);

                ////Creo pagos
                if (reciboDetalleBdList.size() > 0) {
                    for (ReciboDetalleBd item : reciboDetalleBdList) {
                        ROOM_INSTANCE.reciboDetalleDao().create(item);
                    }
                }

                ////Actualizo cuenta corriente
                if (cuentaCorrienteBdList.size() > 0) {
                    for (CuentaCorrienteBd item : cuentaCorrienteBdList) {
                        ROOM_INSTANCE.cuentaCorrienteDao().update(item);
                    }
                }

                ////creo cheques
                if (chequeBdList.size() > 0) {
                    for (ChequeBd item : chequeBdList) {
                        item.setIdEntrega(idEntregaNuevo);
                        ROOM_INSTANCE.chequeDao().create(item);
                    }
                }

                ////creo Pagos Efectivo
                if (pagoEfectivoBdList.size() > 0) {
                    for (PagoEfectivoBd item : pagoEfectivoBdList) {
                        item.setIdEntrega(idEntregaNuevo);
                        ROOM_INSTANCE.pagoEfectivoDao().create(item);
                    }
                }

                ////creo Retenciones
                if (retencionBdList.size() > 0) {
                    for (RetencionBd item : retencionBdList) {
                        item.setIdEntrega(idEntregaNuevo);
                        ROOM_INSTANCE.retencionDao().create(item);
                    }
                }

                ////creo Depositos
                if (depositoBdList.size() > 0) {
                    for (DepositoBd item : depositoBdList) {
                        item.setIdEntrega(idEntregaNuevo);
                        ROOM_INSTANCE.depositoDao().create(item);
                    }
                }

                ////actualizo movimiento
                if (movimientoBd.getId() == 0) {
                    movimientoBd.setId(ROOM_INSTANCE.movimientoDao().maxIdMovimiento() + 1);
                }
                ROOM_INSTANCE.movimientoDao().create(movimientoBd);

            }
        } catch (Exception e) {
            e.printStackTrace();
            //realizo rollback
            throw new RuntimeException(e);
        }
    }

    @Transaction
    public boolean updateVentaTransaction(VentaBd ventaBd, List<VentaDetalleBd> ventaDetalleBdsActualizar,
                                          List<VentaDetalleBd> ventaDetalleBdsNuevo,
                                          MovimientoBd movimientoBdOriginal, MovimientoBd movimientoBdNuevo) {
        try {
            if (ROOM_INSTANCE != null) {
                ////actualizo movimientos
                ROOM_INSTANCE.movimientoDao().update(movimientoBdOriginal);

                ////actualizo venta
                ROOM_INSTANCE.ventaDao().update(ventaBd);

                /*tratamiento del detalle modificado*/
                ////actualizo movimientos stock y objetivos de venta de detalle venta original.
                int tipoOperacion;
                for (VentaDetalleBd item : ventaDetalleBdsActualizar) {
                    tipoOperacion = -1;
                    if (item.getTipoOperacion() == TipoOperacion.delete) {
                        tipoOperacion = 1;
                    }
                    ////actualizo stock
                    ROOM_INSTANCE.stockDao().updateStock(item.getIdArticulo(), item.getCantidad(), tipoOperacion);

                    ////actualizo acciones
                    if (item.getIdAccionComDetalleEmp() != 0 || item.getIdAccionComDetalleProveedor() != 0) {
                        AccionesComDetalleBd accionesComDetalleBd;

                        //Accion CONJUNTA
                        if (item.getIdAccionComDetalleEmp() != 0 && item.getIdAccionComDetalleProveedor() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionEmp(), item.getIdAccionComDetalleEmp());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }

                        //Accion de la EMPRESA
                        if (item.getIdAccionComDetalleEmp() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionEmp(), item.getIdAccionComDetalleEmp());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }

                        //Accion del PROVEEDOR
                        if (item.getIdAccionComDetalleProveedor() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionesCom(), item.getIdAccionComDetalleProveedor());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }
                    }

                    ////Actualizo cantidad vendida de articulos en objetivos de venta
                    ObjetivoVentaBd mObjVta = ROOM_INSTANCE.objetivoVentaDao().recoveryByID(item.getIdArticulo(), ventaBd.getIdSucursal(),
                            ventaBd.getIdCliente(), ventaBd.getIdComercio(), 0);
                    if (mObjVta != null) {
                        double cantidadVendida = mObjVta.getCantidadVendida() + (item.getCantidad() * ObjetivoVentaBo.SIGNO_RESTA);
                        mObjVta.setCantidadVendida(cantidadVendida);
                        ROOM_INSTANCE.objetivoVentaDao().update(mObjVta);
                    }
                }

                //// Elimino el detalle de venta de bd para agregar luego lo nuevo.
                ROOM_INSTANCE.ventaDetalleDao().deleteVentasDetalleByVenta(ventaBd.getId().getIdDocumento(),
                        ventaBd.getId().getIdLetra(), ventaBd.getId().getPuntoVenta(), ventaBd.getId().getIdNumeroDocumento());

                //// Luego de eliminar de la bd, registro nuevamente los detalles en bd con los nuevos datos
                for (VentaDetalleBd item : ventaDetalleBdsNuevo) {
                    ////agrego el detalle a la bd
                    ROOM_INSTANCE.ventaDetalleDao().create(item);
                    tipoOperacion = -1;
                    if (item.getTipoOperacion() == TipoOperacion.delete) {
                        tipoOperacion = 1;
                    }
                    ////actualizo stock
                    ROOM_INSTANCE.stockDao().updateStock(item.getIdArticulo(), item.getCantidad(), tipoOperacion);

                    ////actualizo acciones
                    if (item.getIdAccionComDetalleEmp() != 0 || item.getIdAccionComDetalleProveedor() != 0) {
                        AccionesComDetalleBd accionesComDetalleBd;

                        //Accion CONJUNTA
                        if (item.getIdAccionComDetalleEmp() != 0 && item.getIdAccionComDetalleProveedor() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionEmp(), item.getIdAccionComDetalleEmp());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }

                        //Accion de la EMPRESA
                        if (item.getIdAccionComDetalleEmp() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionEmp(), item.getIdAccionComDetalleEmp());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }

                        //Accion del proveedor
                        if (item.getIdAccionComDetalleProveedor() != 0) {
                            accionesComDetalleBd = ROOM_INSTANCE.accionesComDetalleDao().recoveryByID(item.getIdAccionesCom(), item.getIdAccionComDetalleProveedor());
                            if (accionesComDetalleBd != null) {
                                if (accionesComDetalleBd.getCaVendida() == null || accionesComDetalleBd.getCaVendida() < 0D) {
                                    accionesComDetalleBd.setCaVendida(0D);
                                }
                                /*actualizo con cantidades*/
                                accionesComDetalleBd.setCaVendida(accionesComDetalleBd.getCaVendida() + tipoOperacion * item.getCantidad());

                                ROOM_INSTANCE.accionesComDetalleDao().updateAccionesComDetalle(accionesComDetalleBd);
                            }
                        }
                    }

                    ////Actualizo cantidad vendida de articulos en objetivos de venta
                    ObjetivoVentaBd mObjVta = ROOM_INSTANCE.objetivoVentaDao().recoveryByID(item.getIdArticulo(), ventaBd.getIdSucursal(),
                            ventaBd.getIdCliente(), ventaBd.getIdComercio(), 0);
                    if (mObjVta != null) {
                        double cantidadVendida = mObjVta.getCantidadVendida() + (item.getCantidad() * ObjetivoVentaBo.SIGNO_SUMA);
                        mObjVta.setCantidadVendida(cantidadVendida);
                        ROOM_INSTANCE.objetivoVentaDao().update(mObjVta);
                    }
                }

                ////creo el nuevo movimiento
                if (movimientoBdNuevo.getId() == 0) {
                    movimientoBdNuevo.setId(ROOM_INSTANCE.movimientoDao().maxIdMovimiento() + 1);
                }
                ROOM_INSTANCE.movimientoDao().create(movimientoBdNuevo);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //realizo rollback
            throw new RuntimeException(e);
        }
        return false;
    }

    @Transaction
    public boolean anularVentaTransaction(VentaBd ventaBd, MovimientoBd movimientoBd,
                                          List<VentaDetalleBd> ventaDetalleBds, String fechaAnulacion) {
        try {
            if (ROOM_INSTANCE != null) {
                ////anulo la venta
                ROOM_INSTANCE.ventaDao().delete(ventaBd.getId().getIdDocumento(), ventaBd.getId().getIdLetra(),
                        ventaBd.getId().getPuntoVenta(), ventaBd.getId().getIdNumeroDocumento());

                ////anulo la venta detalle
                ROOM_INSTANCE.ventaDetalleDao().anularVentasDetalleByVenta(ventaBd.getId().getIdDocumento(),
                        ventaBd.getId().getIdLetra(), ventaBd.getId().getPuntoVenta(),
                        ventaBd.getId().getIdNumeroDocumento());

                ////si hay movimiento agrego
                if (movimientoBd != null) {
                    ////creo el nuevo movimiento
                    if (movimientoBd.getId() == 0) {
                        movimientoBd.setId(ROOM_INSTANCE.movimientoDao().maxIdMovimiento() + 1);
                    }
                    ROOM_INSTANCE.movimientoDao().create(movimientoBd);
                }

                ////actualizo el limite de disponibilidad del cliente
                ROOM_INSTANCE.clienteDao().updateLimiteDisponibilidad(ventaBd.getIdSucursal(), ventaBd.getIdCliente(),
                        ventaBd.getIdComercio(), (ventaBd.getTotal() * -1));

                ////anulo el movimiento
                ROOM_INSTANCE.movimientoDao().updateFechaAnulacion(ventaBd.getIdMovil(), fechaAnulacion);

                if (ventaDetalleBds != null && ventaDetalleBds.size() > 0) {
                    for (VentaDetalleBd item : ventaDetalleBds) {
                        ////Actualizo cantidad vendida de articulos en objetivos de venta
                        ObjetivoVentaBd mObjVta = ROOM_INSTANCE.objetivoVentaDao().recoveryByID(item.getIdArticulo(), ventaBd.getIdSucursal(),
                                ventaBd.getIdCliente(), ventaBd.getIdComercio(), 0);
                        if (mObjVta != null) {
                            double cantidadVendida = mObjVta.getCantidadVendida() + (item.getCantidad() * ObjetivoVentaBo.SIGNO_RESTA);
                            mObjVta.setCantidadVendida(cantidadVendida);
                            ROOM_INSTANCE.objetivoVentaDao().update(mObjVta);
                        }
                    }
                }

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //realizo rollback
            throw new RuntimeException(e);
        }
        return false;
    }
}
