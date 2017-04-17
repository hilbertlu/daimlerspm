/**
 * 
 */
package com.daimler.spm.b2bacceleratoraddon.populators;

import de.hybris.platform.accountsummaryaddon.document.data.B2BDocumentData;
import de.hybris.platform.accountsummaryaddon.document.populators.B2BDocumentPopulator;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import javax.annotation.Resource;

import org.springframework.util.Assert;

/**
 * @author i313024
 *
 */
public class DaimlerspmConsignmentPopulator implements Populator<ConsignmentModel, ConsignmentData>{

	private Converter<ConsignmentEntryModel, ConsignmentEntryData> consignmentEntryConverter;
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter;
	private Converter<AddressModel, AddressData> addressConverter;
	
	@Resource(name="b2bDocumentPopulator")
	private B2BDocumentPopulator b2bDocumentPopulator;
	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(ConsignmentModel source, ConsignmentData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		target.setCode(source.getCode());
		target.setTrackingID(source.getTrackingID());
		target.setStatus(source.getStatus());
		target.setEntries(Converters.convertAll(source.getConsignmentEntries(), getConsignmentEntryConverter()));
		
		if(source.getInvoice()!=null){
			B2BDocumentData invoice=new B2BDocumentData();
			b2bDocumentPopulator.populate(source.getInvoice(), invoice);
			target.setInvoiceData(invoice);
		}
		if(source.getShippingNotes()!=null){
			B2BDocumentData shippingNotes=new B2BDocumentData();
			b2bDocumentPopulator.populate(source.getShippingNotes(), shippingNotes);
			target.setDeliveryNote(shippingNotes);
		}
		
		if (ConsignmentStatus.SHIPPED.equals(source.getStatus()) || ConsignmentStatus.READY_FOR_PICKUP.equals(source.getStatus()))
		{
			target.setStatusDate(source.getShippingDate());
		}
		if (source.getDeliveryPointOfService() != null)
		{
			target.setDeliveryPointOfService(getPointOfServiceConverter().convert(source.getDeliveryPointOfService()));
		}
		if (source.getShippingAddress() != null)
		{
			target.setShippingAddress(getAddressConverter().convert(source.getShippingAddress()));
		}
		
	}
	/**
	 * @return the consignmentEntryConverter
	 */
	public Converter<ConsignmentEntryModel, ConsignmentEntryData> getConsignmentEntryConverter()
	{
		return consignmentEntryConverter;
	}
	/**
	 * @param consignmentEntryConverter the consignmentEntryConverter to set
	 */
	public void setConsignmentEntryConverter(Converter<ConsignmentEntryModel, ConsignmentEntryData> consignmentEntryConverter)
	{
		this.consignmentEntryConverter = consignmentEntryConverter;
	}
	/**
	 * @return the pointOfServiceConverter
	 */
	public Converter<PointOfServiceModel, PointOfServiceData> getPointOfServiceConverter()
	{
		return pointOfServiceConverter;
	}
	/**
	 * @param pointOfServiceConverter the pointOfServiceConverter to set
	 */
	public void setPointOfServiceConverter(Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceConverter)
	{
		this.pointOfServiceConverter = pointOfServiceConverter;
	}
	/**
	 * @return the addressConverter
	 */
	public Converter<AddressModel, AddressData> getAddressConverter()
	{
		return addressConverter;
	}
	/**
	 * @param addressConverter the addressConverter to set
	 */
	public void setAddressConverter(Converter<AddressModel, AddressData> addressConverter)
	{
		this.addressConverter = addressConverter;
	}
	/**
	 * @return the b2bDocumentPopulator
	 */
	public B2BDocumentPopulator getB2bDocumentPopulator()
	{
		return b2bDocumentPopulator;
	}
	/**
	 * @param b2bDocumentPopulator the b2bDocumentPopulator to set
	 */
	public void setB2bDocumentPopulator(B2BDocumentPopulator b2bDocumentPopulator)
	{
		this.b2bDocumentPopulator = b2bDocumentPopulator;
	}
	
	

}
