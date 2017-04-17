/**
 * 
 */
package com.daimler.spm.core.order.dao;

import de.hybris.platform.accountsummaryaddon.model.B2BDocumentModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;

import java.util.List;

/**
 * @author i313024
 *
 */
public interface DaimlerspmOrderDao
{

	OrderModel getOrderModelByCode(String code);
	
	List<B2BDocumentModel> findDocumentsByConsignmentCode(String code);
	
	List<B2BDocumentModel> findDocumentsByDocumentNumber(String number);
	
	ConsignmentModel findConsignmentByCode(final String orderCode, final String consignmentCode);
	
}
