package com.ar.vgmsistemas.repository;

import android.content.Context;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.exclusionmutua.Monitor;
import com.ar.vgmsistemas.repository.implementation.LineaIntegradoMercaderiaRepositoryImpl;
import com.ar.vgmsistemas.utils.SdSpaceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

/***
 * Factories para generar repositories
 */
public abstract class RepositoryFactory {

    public static final int SQLITE = 1;
    public static final int HIBERNATE = 2;
    public static final int TOPLINK = 3;
    public static final int ROOM = 4;

    public Context _context;

    public static RepositoryFactory getRepositoryFactory(Context context, int whichFactory) {
        switch (whichFactory) {
            case ROOM:
                return new RepositoryImpl(context);
            default:
                return null;
        }
    }

    public abstract IAccionesComDetalleRepository getAccionesComDetalleRepository();

    public abstract IAccionesComRepository getAccionesComRepository();

    public abstract IArticuloRepository getArticuloRepository();

    public abstract IAuditoriaGpsRepository getAuditoriaGpsRepository();

    public abstract IBancoRepository getBancoRepository();

    public abstract ICantidadesMovimientoRepository getCantidadesMovimientoRepository();

    public abstract ICategoriaFiscalRepository getCategoriaFiscalRepository();

    public abstract IChequeRepository getChequeRepository();

    public abstract IClienteRepository getClienteRepository();

    public abstract IClienteVendedorRepository getClienteVendedorRepository();

    public abstract ICodigoAutorizacionCobranzaRepository getCodigoAutorizacionCobranzaRepository();

    public abstract IComercioLoginRepository getComercioLoginRepository();

    public abstract ICompraRepository getCompraRepository();

    public abstract IComprasImpuestosRepository getComprasImpuestosRepository();

    public abstract ICondicionDirscRepository getCondicionDirscRepository();

    public abstract ICondicionRentaRepository getCondicionRentaRepository();

    public abstract ICondicionVentaRepository getCondicionVentaRepository();

    /*public abstract IControlesMovilRepository getControlesMovilRepository();*/

    public abstract ICuentaCorrienteRepository getCuentaCorrienteRepository();

    public abstract IDepositoRepository getDepositoRepository();

    public abstract IDescuentoProveedorRepository getDescuentoProveedorRepository();

    public abstract IDescuentoProveedorGeneralRepository getDescuentoProveedorGeneralRepository();

    public abstract IDocumentoRepository getDocumentoRepository();

    public abstract IDocumentosListaRepository getDocumentosListaRepository();

    public abstract IEmpresaRepository getEmpresaRepository();

    public abstract IEntregaRendicionRepository getEntregaRendicionRepository();

    public abstract IEntregaRepository getEntregaRepository();

    public abstract IErrorRepository getErrorRepository();

    public abstract IHojaDetalleRepository getHojaDetalleRepository();

    public abstract IHojaRepository getHojaRepository();

    public abstract IImpresoraRepository getImpresoraRepository();

    public abstract IImpuestoRepository getImpuestoRepository();

    public abstract LineaIntegradoMercaderiaRepositoryImpl getLineaIntegradoMercaderiaRepository();

    public abstract IListaPrecioDetalleRepository getListaPrecioDetalleRepository();

    public abstract IListaPrecioRepository getListaPrecioRepository();

    public abstract ILocalidadRepository getLocalidadRepository();

    public abstract IMarcaRepository getMarcaRepository();

    /*MenuProfileRepository*/
    public abstract IMotivosAutorizacionRepository getMotivoAutorizacionRepository();

    public abstract IMotivoCreditoRepository getMotivoCreditoRepository();

    public abstract IMotivoNoPedidoRepository getMotivoNoPedidoRepository();

    public abstract IMovimientoRepository getMovimientoRepository();

    public abstract INegocioRepository getNegocioRepository();

    public abstract INoPedidoRepository getNoPedidoRepository();

    public abstract IObjetivoVentaRepository getObjetivoVentaRepository();

    public abstract IPagoEfectivoRepository getPagoEfectivoRepository();

    public abstract IPlanCuentaRepository getPlanCuentaRepository();

    public abstract IPromocionRepository getPromocionRepository();

    public abstract IPromocionRequisitoRepository getPromocionRequisitoRepository();

    public abstract IProveedorRepository getProveedorRepository();

    public abstract IProveedorVendComercioRepository getProveedorVendComercioRepository();

    public abstract IProvinciaRepository getProvinciaRepository();

    public abstract IRangoRentabilidadRepository getRangoRentabilidadRepository();

    public abstract IReciboDetalleRepository getReciboDetalleRepository();

    public abstract IReciboRepository getReciboRepository();

    public abstract IRecursoHumanoRepository getRecursoHumanoRepository();

    public abstract IRepartidorRepository getRepartidorRepository();

    public abstract IRetencionRepository getRetencionRepository();

    public abstract IRubroRepository getRubroRepository();

    public abstract ISecuenciaRuteoRepository getSecuenciaRuteoRepository();

    public abstract ISubrubroRepository getSubrubroRepository();

    public abstract ISucursalRepository getSucursalRepository();

    public abstract ITipoMonedaRepository getTipoMonedaRepository();

    public abstract IUbicacionGeograficaRepository getUbicacionGeograficaRepository();

    public abstract IVendedorObjetivoDetalleRepository getVendedorObjetivoDetalleRepository();

    public abstract IVendedorObjetivoRepository getVendedorObjetivoRepository();

    public abstract IVendedorRepository getVendedorRepository();

    public abstract IVentaDetalleRepository getVentaDetalleRepository();

    public abstract IVentaRepository getVentaRepository();


    /*base de datos*/
    /*public abstract void beginTransaction() throws Exception;
    public abstract void rollback();
    public abstract void commit() throws Exception;*/
    public abstract void closeConnection() throws Exception;
    public abstract boolean backupDb() throws Exception;
    public abstract boolean validateEspacioSDSincronizacion() throws Exception;
    public abstract void restoreDb() throws Exception;
    public abstract void resetDb();
    public abstract void reOpenConnection() throws Exception;
    public abstract void open() throws Exception;
    public abstract void cleanBackups();
    public abstract boolean dataBaseExists();
    public abstract boolean deleteZip();



}