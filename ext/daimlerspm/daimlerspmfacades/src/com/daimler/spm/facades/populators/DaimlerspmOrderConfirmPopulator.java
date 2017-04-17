/**
 * 
 */
package com.daimler.spm.facades.populators;

import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.converters.populator.ConsignmentPopulator;
import de.hybris.platform.commercefacades.order.data.ConsignmentData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmConsignment;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmOrder;
import de.hybris.platform.order.OrderService;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.daimler.spm.core.order.service.DaimlerspmOrderService;
import com.daimler.spm.core.order.service.impl.DaimlerspmOrderServiceImpl;

/**
 * @author i313024
 *
 */
public class DaimlerspmOrderConfirmPopulator<SOURCE extends DaimlerspmOrder, TARGET extends OrderModel> implements
Populator<SOURCE, TARGET>
{
	
	
	@Resource(name="daimlerspmOrderService")
	private DaimlerspmOrderService daimlerspmOrderService;
	
	/* (non-Javadoc)
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(SOURCE source, TARGET target) 
	{
		// YTODO Auto-generated method stub
			if(source!=null && target!=null){
				target.setStatus(OrderStatus.valueOf(source.getStatus()));
				Set<ConsignmentModel> consignments=target.getConsignments();
				if(consignments==null || consignments.size()<=0 || !consignments.iterator().hasNext()){
					consignments=new HashSet<ConsignmentModel>();
					List<DaimlerspmConsignment> conDatas= source.getConsignments();
					if(conDatas!=null && conDatas.size()>0){
						for(DaimlerspmConsignment con: conDatas){
							ConsignmentModel consignment=daimlerspmOrderService.createConsignmentmodel(target, con.getCode(), con.getShippingDate(), con.getWarehouse()	, con.getStatus(), con.getDaimlerspmConsignmentEntry());
							
							if(consignment!=null){
								consignment.setShippingAddress(target.getDeliveryAddress());
								setWarehouseOfConsignment(con.getWarehouse(), target, consignment);
								consignments.add(consignment);
							}
								
						}
					}
					target.setConsignments(consignments);
				}
			}	
		
		
	}
	
	protected void setWarehouseOfConsignment(final String warehouseId, final AbstractOrderModel order,
			final ConsignmentModel consignment)
	{
		final String erpDeliverPlant = warehouseId;
		for (final WarehouseModel warehouse : order.getStore().getWarehouses())
		{
			if (warehouse.getCode().equals(erpDeliverPlant))
			{
				consignment.setWarehouse(warehouse);
				return;
			}
		}
	}
	

	/**
	 * @return the daimlerspmOrderService
	 */
	public DaimlerspmOrderService getDaimlerspmOrderService()
	{
		return daimlerspmOrderService;
	}

	/**
	 * @param daimlerspmOrderService the daimlerspmOrderService to set
	 */
	public void setDaimlerspmOrderService(DaimlerspmOrderService daimlerspmOrderService)
	{
		this.daimlerspmOrderService = daimlerspmOrderService;
	}

	

}
