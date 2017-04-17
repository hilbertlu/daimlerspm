/**
 * 
 */
package com.daimler.spm.core.order.service;

import de.hybris.platform.accountsummaryaddon.model.B2BDocumentModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmConsignmentEntry;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;

import java.util.List;

/**
 * @author i313024
 *
 */
public interface DaimlerspmOrderService
{

	OrderModel getOrderByCode(String code);
	
	ConsignmentModel createConsignmentmodel(OrderModel order, String code,String shippingDate, String warehouseCode,String status, List<DaimlerspmConsignmentEntry> entries);
	
	void saveConsignmentDocument(String order, String code,String number, boolean invoice, boolean deliveryNote);
}
