/**
 * 
 */
package com.daimler.spm.core.order.service.impl;

import de.hybris.platform.accountsummaryaddon.model.B2BDocumentModel;
import de.hybris.platform.accountsummaryaddon.model.B2BDocumentTypeModel;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.commercefacades.order.data.ConsignmentEntryData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmConsignmentEntry;
import de.hybris.platform.ordersplitting.ConsignmentCreationException;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.security.SecureRandom;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import com.daimler.spm.core.order.dao.DaimlerspmOrderDao;
import com.daimler.spm.core.order.service.DaimlerspmOrderService;

/**
 * @author i313024
 *
 */
public class DaimlerspmOrderServiceImpl implements DaimlerspmOrderService
{

	/* (non-Javadoc)
	 * @see com.daimler.spm.core.order.service.DaimlerspmOrderService#getOrderByCode(java.lang.String)
	 */
	@Resource(name="daimlerspmOrderDao")
	private DaimlerspmOrderDao daimlerspmOrderDao;
	
	@Resource(name="modelService")
	private ModelService modelService;
	
	@Resource(name="warehouseService")
	private WarehouseService warehouseService;
	
	@Override
	public OrderModel getOrderByCode(String code)
	{
		// YTODO Auto-generated method stub
		return daimlerspmOrderDao.getOrderModelByCode(code);
	}
	
	
	public ConsignmentModel createConsignmentmodel(OrderModel order, String code,String shippingDate, String warehouseCode,String status, List<DaimlerspmConsignmentEntry> entries){
			
		try
		{
			ConsignmentModel cons = (ConsignmentModel) this.modelService
					.create(ConsignmentModel.class);

			cons.setStatus(ConsignmentStatus.valueOf(status));
			cons.setConsignmentEntries(new HashSet());
			cons.setCode(code);
			final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			cons.setShippingDate(formatter.parse(shippingDate));
			cons.setShippingAddress(order.getDeliveryAddress());
			
			for(DaimlerspmConsignmentEntry entryData: entries){
				
				ConsignmentEntryModel entry = (ConsignmentEntryModel) this.modelService
					.create(ConsignmentEntryModel.class);
				entry.setQuantity(Long.valueOf(entryData.getQuantity()));
				entry.setShippedQuantity(Long.valueOf(entryData.getShippedQuantity()));
				String entryNumber=entryData.getEntrynumber();
				Iterator orderEntries = order.getEntries().iterator();
				while(orderEntries.hasNext()){
					AbstractOrderEntryModel orderEntry=(AbstractOrderEntryModel)orderEntries.next();
					
					if(entryNumber.equals(orderEntry.getEntryNumber().toString())){
						entry.setOrderEntry(orderEntry);
						cons.setDeliveryMode(orderEntry.getDeliveryMode());
					}
					
				}
				entry.setConsignment(cons);
				cons.getConsignmentEntries().add(entry);
					
			}
			WarehouseModel warehouse=warehouseService.getWarehouseForCode(warehouseCode);
			cons.setWarehouse(warehouse);
			cons.setOrder(order);
			modelService.save(cons);
			return cons;
		}
		catch (ParseException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.daimler.spm.core.order.service.DaimlerspmOrderService#saveConsignmentDocument()
	 */
	@Override
	public void saveConsignmentDocument(String order, String code,String documentnumber,boolean invoice, boolean deliveryNote)
	{
		// YTODO Auto-generated method stub
		List<B2BDocumentModel> documents=daimlerspmOrderDao.findDocumentsByConsignmentCode(code);
		ConsignmentModel consignment=daimlerspmOrderDao.findConsignmentByCode(order, code);
		if(documents==null || documents.size()<=0){
			documents=daimlerspmOrderDao.findDocumentsByDocumentNumber(documentnumber);
		}
		for(B2BDocumentModel doc:documents){
			if(doc.getDocumentType()!=null ){
				
				if(invoice){
					consignment.setInvoice(doc);
					doc.setConsignment(consignment);
					
				}
				if(deliveryNote){
					consignment.setShippingNotes(doc);
					doc.setConsignment(consignment);
				}
				
				this.modelService.save(consignment);
				this.modelService.save(doc);
				
			}else{
//				B2BDocumentTypeModel documentType=this.modelService.create(B2BDocumentTypeModel.class);
//				if(invoice)
//					documentType.setCode(type);
//				documentType.setCreationtime(new Date(System.currentTimeMillis()));
//				documentType.setDisplayInAllList(Boolean.TRUE);
//				documentType.setDocument(documents);
//				documentType.setName(type);
//				documentType.setName(type, Locale.ENGLISH);
//				doc.setDocumentType(documentType);
				continue;
			}			
		}
	}

	

	/**
	 * @return the daimlerspmOrderDao
	 */
	public DaimlerspmOrderDao getDaimlerspmOrderDao()
	{
		return daimlerspmOrderDao;
	}

	/**
	 * @param daimlerspmOrderDao the daimlerspmOrderDao to set
	 */
	public void setDaimlerspmOrderDao(DaimlerspmOrderDao daimlerspmOrderDao)
	{
		this.daimlerspmOrderDao = daimlerspmOrderDao;
	}


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}


	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService)
	{
		this.modelService = modelService;
	}


	/**
	 * @return the warehouseService
	 */
	public WarehouseService getWarehouseService()
	{
		return warehouseService;
	}


	/**
	 * @param warehouseService the warehouseService to set
	 */
	public void setWarehouseService(WarehouseService warehouseService)
	{
		this.warehouseService = warehouseService;
	}



	
	
}
