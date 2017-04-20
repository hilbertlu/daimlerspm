/**
 *
 */
package de.hybris.platform.customerticketingfacades.converters.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.customerticketingfacades.data.TicketAssociatedData;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author i313024
 *
 */
public class DefaultTicketReturnRequestPopulator<SOURCE extends ReturnRequestModel, TARGET extends TicketAssociatedData>
		implements Populator<SOURCE, TARGET>
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
		target.setModifiedtime(source.getModifiedtime());
		target.setType(source.getItemtype());
		target.setSiteUid(source.getOrder().getSite().getUid());

	}

}
