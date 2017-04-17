/**
 * 
 */
package com.daimler.spm.core.order.dao.impl;

import de.hybris.platform.accountsummaryaddon.model.B2BDocumentModel;
import de.hybris.platform.accountsummaryaddon.model.B2BDocumentTypeModel;
import de.hybris.platform.accountsummaryaddon.model.DocumentMediaModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.daimler.spm.core.order.dao.DaimlerspmOrderDao;

/**
 * @author i313024
 *
 */
public class DaimlerspmOrderDaoImpl extends AbstractItemDao  implements DaimlerspmOrderDao
{

	
	private static final String CONSIGNMENT_CODE = "consignmentCode";

	private static final String ORDER_CODE = "orderCode";

	private static final String FQL = "select {" + ConsignmentModel.PK + "} from {" + ConsignmentModel._TYPECODE + "} where {"
			+ ConsignmentModel.CODE + "} = ?consignmentCode and {" + ConsignmentModel.ORDER + "} in ({{ select {" + OrderModel.PK
			+ "} from {" + OrderModel._TYPECODE + "} where {" + OrderModel.CODE + "} = ?orderCode }})";
	
//	private static final String FIND_DOCUMENT_IGNORE_UNIT = "SELECT {" + B2BDocumentModel._TYPECODE + ":pk}  " + "FROM { "
//			+ B2BDocumentModel._TYPECODE + " as " + B2BDocumentModel._TYPECODE + " join " + B2BDocumentTypeModel._TYPECODE + " as "
//			+ B2BDocumentTypeModel._TYPECODE + " on {" + B2BDocumentModel._TYPECODE + ":documentType} = {"
//			+ B2BDocumentTypeModel._TYPECODE + ":pk} " + "join " + DocumentMediaModel._TYPECODE + " as "
//			+ DocumentMediaModel._TYPECODE + " on {" + B2BDocumentModel._TYPECODE + ":documentMedia} = {"
//			+ DocumentMediaModel._TYPECODE + ":pk} " + "} ";
	

	private static final String FIND_DOCUMENT_IGNORE_UNIT ="SELECT {" + B2BDocumentModel._TYPECODE+ ":pk}  " + "FROM { "+ B2BDocumentModel._TYPECODE+ " as " + B2BDocumentModel._TYPECODE +"} ";
	
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

	/* (non-Javadoc)
	 * @see com.daimler.spm.core.products.dao.DaimlerspmWarehouseDao#findDocumentsByConsignmentCode(java.lang.String)
	 */
	@Override
	public List<B2BDocumentModel> findDocumentsByConsignmentCode(String code)
	{
		final StringBuffer whereStatement = new StringBuffer();
		whereStatement.append(" where ");

		final Map<String, Object> queryParams = new HashMap<String, Object>();

		//add criteria for consignment Code
		if (StringUtils.isNotEmpty(code))
		{
			whereStatement.append(" {" + B2BDocumentModel._TYPECODE + ":" + B2BDocumentModel.CONSIGNMENT+ "} = ?consignment");
			queryParams.put("consignment", code);
		}else{
			return null;
		}

		//search
		FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_DOCUMENT_IGNORE_UNIT + whereStatement.toString());
		query.getQueryParameters().putAll(queryParams);
		//final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_DOCUMENT_IGNORE_UNIT + whereStatement.toString());
		query.addQueryParameters(queryParams);
		
		SearchResult result = this.getFlexibleSearchService().search(query);
		return result.getResult();
	}

	@Override
	public List<B2BDocumentModel> findDocumentsByDocumentNumber(String number){
		final StringBuffer whereStatement = new StringBuffer();
		whereStatement.append(" where ");

		final Map<String, Object> queryParams = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(number)){
			whereStatement.append(" {" + B2BDocumentModel._TYPECODE + ":" + B2BDocumentModel.DOCUMENTNUMBER+ "} = ?number");
			queryParams.put("number", number);
		}else{
			return null;
		}
		
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_DOCUMENT_IGNORE_UNIT + whereStatement.toString());
		query.addQueryParameters(queryParams);
		SearchResult result = this.getFlexibleSearchService().search(query);
		return result.getResult();
	}

	/* (non-Javadoc)
	 * @see com.daimler.spm.core.order.dao.DaimlerspmOrderDao#findConsignmentByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public ConsignmentModel findConsignmentByCode(String orderCode, String consignmentCode)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FQL);
		query.addQueryParameter(CONSIGNMENT_CODE, consignmentCode);
		query.addQueryParameter(ORDER_CODE, orderCode);		
		//return getFlexibleSearchService().searchUnique(query);
		
		SearchResult result = this.getFlexibleSearchService().search(query);
		List consignments = result.getResult();
		return consignments.isEmpty() ? null : (ConsignmentModel) consignments.get(0);
		
	}
}
