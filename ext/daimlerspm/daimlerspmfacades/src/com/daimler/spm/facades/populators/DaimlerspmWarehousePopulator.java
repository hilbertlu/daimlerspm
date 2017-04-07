/**
 *
 */
package com.daimler.spm.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.apache.commons.lang.StringUtils;

import com.daimler.spm.facades.product.data.WarehouseData;


/**
 * @author i313024
 *
 */
public class DaimlerspmWarehousePopulator<SOURCE extends WarehouseModel, TARGET extends WarehouseData> implements
		Populator<SOURCE, TARGET>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final SOURCE source, final TARGET target) throws ConversionException
	{
		// YTODO Auto-generated method stub

		target.setCode(source.getCode());
		target.setName(StringUtils.isNotEmpty(source.getName()) ? source.getName() : source.getCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */

}
