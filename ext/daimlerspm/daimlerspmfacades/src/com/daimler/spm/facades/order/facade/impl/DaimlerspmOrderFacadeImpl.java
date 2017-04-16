/**
 * 
 */
package com.daimler.spm.facades.order.facade.impl;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmOrder;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import com.daimler.spm.core.order.service.DaimlerspmOrderService;
import com.daimler.spm.facades.order.facade.DaimlerspmOrderFacade;
import com.daimler.spm.facades.populators.DaimlerspmOrderConfirmPopulator;

/**
 * @author i313024
 *
 */
public class DaimlerspmOrderFacadeImpl implements DaimlerspmOrderFacade
{

	/* (non-Javadoc)
	 * @see com.daimler.spm.facades.order.facade.DaimlerspmOrderFacade#saveOrder(de.hybris.platform.commercefacades.order.data.OrderData)
	 */
	
	@Resource(name="daimlerspmOrderService")
	private DaimlerspmOrderService daimlerspmOrderService;
	
	
	@Resource(name="modelService")
	private ModelService modelService;
	
	@Resource(name="daimlerOrderConfirmPopulator")
	private DaimlerspmOrderConfirmPopulator daimlerOrderConfirmPopulator;
	
	@Override
	public void saveOrder(DaimlerspmOrder orderData)
	{
		// YTODO Auto-generated method stub
		if(orderData!=null){
			OrderModel order=daimlerspmOrderService.getOrderByCode(orderData.getCode());
			daimlerOrderConfirmPopulator.populate(orderData, order);
			modelService.save(order);
		}
		
		
		
	}
	

	/* (non-Javadoc)
	 * @see com.daimler.spm.facades.order.facade.DaimlerspmOrderFacade#findOrderByCode(java.lang.String)
	 */
	@Override
	public OrderData findOrderByCode(String code)
	{
		// YTODO Auto-generated method stub
		return null;
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
	 * @return the daimlerOrderConfirmPopulator
	 */
	public DaimlerspmOrderConfirmPopulator getDaimlerOrderConfirmPopulator()
	{
		return daimlerOrderConfirmPopulator;
	}

	/**
	 * @param daimlerOrderConfirmPopulator the daimlerOrderConfirmPopulator to set
	 */
	public void setDaimlerOrderConfirmPopulator(DaimlerspmOrderConfirmPopulator daimlerOrderConfirmPopulator)
	{
		this.daimlerOrderConfirmPopulator = daimlerOrderConfirmPopulator;
	}



	
	
}
