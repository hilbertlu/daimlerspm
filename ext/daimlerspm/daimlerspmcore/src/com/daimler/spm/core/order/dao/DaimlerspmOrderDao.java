/**
 * 
 */
package com.daimler.spm.core.order.dao;

import de.hybris.platform.core.model.order.OrderModel;

/**
 * @author i313024
 *
 */
public interface DaimlerspmOrderDao
{

	OrderModel getOrderModelByCode(String code);
}
