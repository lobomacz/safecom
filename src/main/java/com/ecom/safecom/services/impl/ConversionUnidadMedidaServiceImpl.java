package com.ecom.safecom.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecom.safecom.dao.ConversionUnidadMedidaDao;
import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;
import com.ecom.safecom.services.IConversionUnidadMedidaService;

@Service("conversionUnidadMedidaService")
@Scope(value="singleton",proxyMode=org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class ConversionUnidadMedidaServiceImpl implements IConversionUnidadMedidaService {

	@Autowired
	ConversionUnidadMedidaDao dao;
	
	public List<ConversionUnidadMedida> getListaConversiones() {
		return dao.getAll();
	}

	public ConversionUnidadMedida getConversion(ConversionUnidadPK _id) {
		return dao.get(_id);
	}

	public ConversionUnidadMedida reloadConversion(ConversionUnidadMedida conv) {
		return dao.reload(conv);
	}

	public ConversionUnidadMedida saveConversion(ConversionUnidadMedida conv) {
		return dao.save(conv);
	}

	public ConversionUnidadMedida updateConversion(ConversionUnidadMedida conv) {
		return dao.update(conv);
	}

	public void deleteConversion(ConversionUnidadMedida conv) {
		dao.delete(conv);
	}

}
