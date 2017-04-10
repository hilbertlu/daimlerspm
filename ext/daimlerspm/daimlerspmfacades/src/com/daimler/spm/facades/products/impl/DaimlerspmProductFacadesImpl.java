/**
 *
 */
package com.daimler.spm.facades.products.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.daimler.spm.core.products.service.DaimlerspmWarehouseService;
import com.daimler.spm.facades.populators.DaimlerspmStockLevelPopulator;
import com.daimler.spm.facades.product.data.StockLevelData;
import com.daimler.spm.facades.products.DaimlerspmProductFacade;





/**
 * @author i313024
 *
 */
public class DaimlerspmProductFacadesImpl implements DaimlerspmProductFacade
{

	@Autowired
	private DaimlerspmWarehouseService warehouseService;

	@Autowired
	private DaimlerspmStockLevelPopulator daimlerspmWarehousePopulator;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.daimler.spm.facades.products.DaimlerspmProductFacade#findAllWarehouseForProduct(de.hybris.platform.core.model
	 * .product.ProductModel)
	 */
	@Override
	public List<WarehouseModel> findAllWarehouseForProduct(final ProductModel product)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daimler.spm.facades.products.DaimlerspmProductFacade#findStockLevelsForProduct(de.hybris.platform.core.model
	 * .product.ProductModel)
	 */
	@Override
	public List<StockLevelData> findStockLevelsForProduct(final ProductModel product)
	{
		// YTODO Auto-generated method stub
		if (product == null)
		{
			return null;
		}
		final String productCode = product.getCode();
		final List<StockLevelModel> stocklevels = warehouseService.findStockLevelsForProduct(productCode);
		if (stocklevels != null && stocklevels.size() > 0)
		{
			final List<StockLevelData> stockDatas = new ArrayList<StockLevelData>();
			for (final StockLevelModel stock : stocklevels)
			{
				final StockLevelData stockData = new StockLevelData();
				daimlerspmWarehousePopulator.populate(stock, stockData);
				stockDatas.add(stockData);
			}
			return stockDatas;
		}

		return null;
	}



}
