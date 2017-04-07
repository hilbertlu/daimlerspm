/**
 *
 */
package com.daimler.spm.core.products.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;

import com.daimler.spm.core.products.dao.DaimlerspmWarehouseDao;
import com.daimler.spm.core.products.service.DaimlerspmWarehouseService;


/**
 * @author i313024
 *
 */
public class DaimlerspmWarehouseServiceImpl implements DaimlerspmWarehouseService
{
	private DaimlerspmWarehouseDao warehoueDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.daimler.spm.core.products.service.DaimlerspmProductService#getWarehouseForProductsAndBaseStore(de.hybris.platform
	 * .core.model.product.ProductModel, de.hybris.platform.store.BaseStoreModel)
	 */
	@Override
	public List<WarehouseModel> getWarehouseForProductsAndBaseStore(final ProductModel product, final BaseStoreModel store)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.daimler.spm.core.products.service.DaimlerspmWarehouseService#findWarehouseForProduct(java.lang.String)
	 */
	@Override
	public List<WarehouseModel> findWarehouseForProduct(final String productCode)
	{
		// YTODO Auto-generated method stub
		return warehoueDao.findWarehouseByProductCode(productCode);
	}

	/**
	 * @return the warehoueDao
	 */
	public DaimlerspmWarehouseDao getWarehoueDao()
	{
		return warehoueDao;
	}

	/**
	 * @param warehoueDao
	 *           the warehoueDao to set
	 */
	public void setWarehoueDao(final DaimlerspmWarehouseDao warehoueDao)
	{
		this.warehoueDao = warehoueDao;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.daimler.spm.core.products.service.DaimlerspmWarehouseService#findStockLevelsForProduct(java.lang.String)
	 */
	@Override
	public List<StockLevelModel> findStockLevelsForProduct(final String productCode)
	{
		// YTODO Auto-generated method stub
		return warehoueDao.findStockLevelsByProductCode(productCode);
	}



}
