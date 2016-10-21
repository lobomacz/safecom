package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.ConversionUnidadMedida;
import com.ecom.safecom.entity.ConversionUnidadPK;

public interface IConversionUnidadMedidaService {
	
	List<ConversionUnidadMedida> getListaConversiones();
	
	ConversionUnidadMedida getConversion(ConversionUnidadPK _id);
	
	ConversionUnidadMedida reloadConversion(ConversionUnidadMedida conv);
	
	ConversionUnidadMedida saveConversion(ConversionUnidadMedida conv);
	
	ConversionUnidadMedida updateConversion(ConversionUnidadMedida conv);
	
	void deleteConversion(ConversionUnidadMedida conv);

}
