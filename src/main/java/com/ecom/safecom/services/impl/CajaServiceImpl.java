package com.ecom.safecom.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.ArqueoCajaDao;
import com.ecom.safecom.dao.CajaDao;
import com.ecom.safecom.dao.MovimientoCajaDao;
import com.ecom.safecom.entity.ArqueoCaja;
import com.ecom.safecom.entity.Caja;
import com.ecom.safecom.entity.MovimientoCaja;
import com.ecom.safecom.services.ICajaService;

@Service("cajaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CajaServiceImpl implements ICajaService {
	
	@Autowired
	private CajaDao dao;
	@Autowired
	private MovimientoCajaDao mdao;
	@Autowired
	private ArqueoCajaDao acdao;

	public List<Caja> ListaCajas() {
		return dao.listaTodo();
	}

	public Caja BuscarCaja(Integer id_caja) {
		return dao.get(id_caja);
	}

	public Caja RecargarCaja(Caja caja) {
		return dao.reload(caja);
	}

	public Caja ActualizarCaja(Caja caja) {
		return dao.update(caja);
	}

	public Caja GuardarCaja(Caja caja) {
		return dao.save(caja);
	}

	public void BorrarCaja(Caja caja) {
		dao.delete(caja);

	}

	public List<MovimientoCaja> ListaMovimientos(Integer idcaja) {
		return mdao.listaTodo(idcaja);
	}

	public List<MovimientoCaja> ListaMovimientosPorFecha(Integer idcaja,
			Date finicio, Date ffinal) {
		return mdao.listaPorFecha(idcaja, finicio, ffinal);
	}

	public MovimientoCaja BuscarMovimientoCaja(Integer id_mov) {
		return mdao.get(id_mov);
	}

	public MovimientoCaja RecargarMovimientoCaja(MovimientoCaja mov) {
		return mdao.reload(mov);
	}

	public MovimientoCaja GuardarMovimientoCaja(MovimientoCaja mov) {
		return mdao.save(mov);
	}

	public MovimientoCaja ActualizarMovimientoCaja(MovimientoCaja mov) {
		return mdao.update(mov);
	}

	public void BorrarMovimientoCaja(MovimientoCaja mov) {
		mdao.delete(mov);
	}

	public List<ArqueoCaja> ListaArqueos(Integer idcaja) {
		return acdao.listaTodo(idcaja);
	}

	public List<ArqueoCaja> ListaArqueosPorFecha(Integer idcaja, Date finicio,
			Date ffinal) {
		return acdao.listaPorFecha(idcaja, finicio, ffinal);
	}

	public ArqueoCaja BuscarArqueoCaja(Integer idarq) {
		return acdao.get(idarq);
	}

	public ArqueoCaja RecargarArqueoCaja(ArqueoCaja arq) {
		return acdao.reload(arq);
	}

	public ArqueoCaja GuardarArqueoCaja(ArqueoCaja arq) {
		return acdao.save(arq);
	}

	public ArqueoCaja ActualizarArqueoCaja(ArqueoCaja arq) {
		return acdao.update(arq);
	}

	public void BorrarArqueoCaja(ArqueoCaja arq) {
		acdao.delete(arq);
	}

}
