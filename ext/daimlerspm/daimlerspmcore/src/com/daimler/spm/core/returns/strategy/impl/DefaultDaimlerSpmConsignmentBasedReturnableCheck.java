/**
 * 
 */
package com.daimler.spm.core.returns.strategy.impl;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.basecommerce.jalo.BasecommerceManager;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.ordersplitting.jalo.Consignment;
import de.hybris.platform.ordersplitting.jalo.ConsignmentEntry;
import de.hybris.platform.returns.strategy.impl.DefaultConsignmentBasedReturnableCheck;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

/**
 * @author i059045
 * To fix the return request quantity issue for consignment split case
 */
public class DefaultDaimlerSpmConsignmentBasedReturnableCheck extends DefaultConsignmentBasedReturnableCheck
{
	@Resource
	private ModelService modelService;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(DefaultDaimlerSpmConsignmentBasedReturnableCheck.class.getName());
	
	@Override
	public boolean perform(final OrderModel order, final AbstractOrderEntryModel orderentry, final long returnQuantity)
	{
		// FALSE, in case of invalid quantity
		if (returnQuantity < 1 || orderentry.getQuantity().longValue() < returnQuantity)
		{
			return false;
		}

		// any existing consignments out there?
		final Set<Consignment> consignments = BasecommerceManager.getInstance()
				.getConsignments((Order) getModelService().toPersistenceLayer(order)); // TODO: Avoid accessing the JALO layer

		// initial isReturnable STATE
		boolean isReturnable = false;
		
		long returnableQuantity = 0;

		if (!consignments.isEmpty())
		{
			// Now, isReturnable state will only be true, if the status is SHIPPED and the return quantity is lower or equal than the shipped quantity
			for (final Consignment consignment : consignments)
			{
				// only SHIPPED consignments will be examined?
				if (consignment.getStatus().getCode().equals(ConsignmentStatus.SHIPPED.getCode()))
				{
					final Set<ConsignmentEntry> entries = consignment.getConsignmentEntries();
					for (final ConsignmentEntry entry : entries)
					{
						if (modelService.toModelLayer(entry.getOrderEntry()).equals(orderentry))
						{
							// isReturnable -> TRUE, in case of 'return quantity' is lower or equal than the 'shipped quantity'
//							isReturnable = (entry.getShippedQuantityAsPrimitive() >= returnQuantity);
							returnableQuantity += entry.getShippedQuantityAsPrimitive();
						}
					}
				}
			}
			
			// To fix the return request quantity issue for consignment split case:
			//if the quantity from a specific orderentry is requested for return, all consignmententries of SHIPPED 
			// consignments related to this orderentry should be counted together rather than only a single consignmententry, 
			// since the quantity from one orderentry can be splitted to different consignmententries which even can belong to different consignment
			isReturnable =  (returnableQuantity >= returnQuantity);
		}
		else
		{
			// ... let'S FAIL if there were no consignments!
			return false;
		}
		return isReturnable;
	}
}
