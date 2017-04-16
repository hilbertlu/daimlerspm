/**
 * 
 */
package com.daimler.spm.core.order.dao.impl;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.HashMap;
import java.util.List;

import com.daimler.spm.core.order.dao.DaimlerspmOrderDao;

/**
 * @author i313024
 *
 */
public class DaimlerspmOrderDaoImpl extends AbstractItemDao  implements DaimlerspmOrderDao
{

	/* (non-Javadoc)
	 * @see com.daimler.spm.core.order.dao.DaimlerspmOrderDao#getOrderModelByCode(java.lang.String)
	 */
	@Override
	public OrderModel getOrderModelByCode(String code)
	{
		HashMap attr = new HashMap(1);
		attr.put("code", code);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT {o:pk} from { ").append("Order")
				.append(" as o} WHERE {o:code} = ?code ")
				.append("AND {o:versionID} IS NULL");
		FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.getQueryParameters().putAll(attr);
		SearchResult result = this.getFlexibleSearchService().search(query);
		List orders = result.getResult();
		return orders.isEmpty() ? null : (OrderModel) orders.get(0);
	}

}
