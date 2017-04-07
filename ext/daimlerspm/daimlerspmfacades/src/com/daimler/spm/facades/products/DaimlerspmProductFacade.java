/**
 *
 */
package com.daimler.spm.facades.products;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

import java.util.List;

import com.daimler.spm.facades.product.data.StockLevelData;


/**
 * @author i313024
 *
 */
public interface DaimlerspmProductFacade
{

	public List<WarehouseModel> findAllWarehouseForProduct(ProductModel product);

	public List<StockLevelData> findStockLevelsForProduct(ProductModel product);

}
