/**
 *
 */
package com.daimler.spm.core.products.dao;

import de.hybris.platform.accountsummaryaddon.model.B2BDocumentModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

import java.util.List;


/**
 * @author i313024
 *
 */
public interface DaimlerspmWarehouseDao
{

	public List<WarehouseModel> findWarehouseByProductCode(String code);

	public List<StockLevelModel> findStockLevelsByProductCode(String productCode);
}
