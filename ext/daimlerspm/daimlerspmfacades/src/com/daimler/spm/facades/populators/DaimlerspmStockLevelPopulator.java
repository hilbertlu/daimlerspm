/**
 *
 */
package com.daimler.spm.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.daimler.spm.core.products.service.DaimlerspmWarehouseService;
import com.daimler.spm.facades.product.data.StockLevelData;
import com.daimler.spm.facades.product.data.WarehouseData;


/**
 * @author i313024
 *
 */
//public class DaimlerspmStockLevelPopulator<SOURCE extends StockLevelModel, TARGET extends StockLevelData> implements Populator<SOURCE, TARGET>
public class DaimlerspmStockLevelPopulator<SOURCE extends StockLevelModel, TARGET extends StockLevelData> implements
		Populator<SOURCE, TARGET>
{

	@Autowired
	private DaimlerspmWarehousePopulator daimlerspmWarehousePopulator;

	@Autowired
	private DaimlerspmWarehouseService warehouseService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final SOURCE source, final TARGET target) throws ConversionException
	{
		if (source != null && target != null)
		{
			target.setInStockStatus(source.getInStockStatus());
			target.setReleaseDate(source.getReleaseDate());
			target.setProductCode(source.getProductCode());
			target.setAvailable(source.getAvailable());
			final WarehouseData warehouse = new WarehouseData();
			daimlerspmWarehousePopulator.populate(source.getWarehouse(), warehouse);
			target.setWarehouse(warehouse);
			target.setInitialQuantityOnHand(Integer.valueOf(source.getAvailable()));
		}
		//WarehouseModel warehouse=t

	}


}
