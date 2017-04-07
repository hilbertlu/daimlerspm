/**
 *
 */
package com.daimler.spm.core.products.dao.impl;

import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import org.apache.log4j.Logger;

import com.daimler.spm.core.products.dao.DaimlerspmWarehouseDao;


/**
 * @author i313024
 *
 */
public class DaimlerspmWarehouseDaoImpl extends AbstractItemDao implements DaimlerspmWarehouseDao
{

	private static final Logger LOG = Logger.getLogger(DaimlerspmWarehouseDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.daimler.spm.core.products.dao.DaimlerspmWarehouseDao#findWarehouseByProductCode(java.lang.String)
	 */
	@Override
	public List<WarehouseModel> findWarehouseByProductCode(final String code)
	{
		// YTODO Auto-generated method stub
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(
				" select {w.pk} from {Warehouse as w JOIN StockLevel as s on {s.warehouse}={w.pk} JOIN {Product as p} on {p.code}={s.productcode}} where {p.code}=?productCode");
		fQuery.addQueryParameter("productCode", code);
		final SearchResult result = this.getFlexibleSearchService().search(fQuery);
		final List warehouses = result.getResult();
		if (warehouses.isEmpty())
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("no warehouse found for product [" + code + "]  found.");
			}

			return null;
		}
		else
		{
			return warehouses;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.daimler.spm.core.products.dao.DaimlerspmWarehouseDao#findStockLevelsByProductCode(java.lang.String)
	 */
	@Override
	public List<StockLevelModel> findStockLevelsByProductCode(final String productCode)
	{
		// YTODO Auto-generated method stub
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(
				"select {s.pk} from {StockLevel as s JOIN Warehouse as w on {s.Warehouse}={w.pk}} where {s.productCode}=?productCode");
		fQuery.addQueryParameter("productCode", productCode);
		final SearchResult result = this.getFlexibleSearchService().search(fQuery);
		final List<StockLevelModel> stockLevels = result.getResult();
		if (stockLevels.isEmpty())
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("no stocklevel found for product [" + productCode + "]  found.");
			}

			return null;
		}
		else
		{
			return stockLevels;
		}
	}

}
