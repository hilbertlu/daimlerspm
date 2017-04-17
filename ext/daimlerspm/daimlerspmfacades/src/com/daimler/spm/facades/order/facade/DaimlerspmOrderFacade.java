/**
 * 
 */
package com.daimler.spm.facades.order.facade;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmOrder;

/**
 * @author i313024
 *
 */
public interface DaimlerspmOrderFacade
{

	void saveOrder(DaimlerspmOrder orderData);
	
	OrderData findOrderByCode(String code);
	
	void saveConsignmentDocument(String order, String code, String documentnumber, boolean invoice, boolean deliveryNote);
}
