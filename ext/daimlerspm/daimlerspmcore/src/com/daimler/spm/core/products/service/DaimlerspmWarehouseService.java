/**
 *
 */
package com.daimler.spm.core.products.service;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;


/**
 * @author i313024
 *
 */
public interface DaimlerspmWarehouseService
{

	public List<WarehouseModel> getWarehouseForProductsAndBaseStore(ProductModel product, BaseStoreModel store);

	public List<WarehouseModel> findWarehouseForProduct(String productCode);

	public List<StockLevelModel> findStockLevelsForProduct(String productCode);
}
