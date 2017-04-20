/**
 *
 */
package de.hybris.platform.customerticketingfacades.strategies;

import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerticketingfacades.data.TicketAssociatedData;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author i313024
 *
 */
public class TicketReturnRequestAssociationStrategy implements TicketAssociationStrategies
{

	private Converter<ReturnRequestModel, TicketAssociatedData> ticketAssociationCoverter;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.customerticketingfacades.strategies.TicketAssociationStrategies#getObjects(de.hybris.platform
	 * .core.model.user.UserModel)
	 */
	@Override
	public Map<String, List<TicketAssociatedData>> getObjects(final UserModel currentUser)
	{
		// YTODO Auto-generated method stub
		final Collection<OrderModel> orders = currentUser.getOrders();
		final List<TicketAssociatedData> associateList = new ArrayList<TicketAssociatedData>();
		for (final OrderModel order : orders)
		{
			final Collection<ReturnRequestModel> returns = order.getReturnRequests();
			if (returns != null && returns.size() > 0)
			{
				final List<TicketAssociatedData> requestList = new ArrayList<TicketAssociatedData>(Converters.convertAll(returns,
						getTicketAssociationCoverter()));
				associateList.addAll(requestList);
			}

		}
		final Map<String, List<TicketAssociatedData>> requests = new HashMap<String, List<TicketAssociatedData>>();
		requests.put("Claim", associateList);
		return requests;
	}

	/**
	 * @return the ticketAssociationCoverter
	 */
	public Converter<ReturnRequestModel, TicketAssociatedData> getTicketAssociationCoverter()
	{
		return ticketAssociationCoverter;
	}

	/**
	 * @param ticketAssociationCoverter
	 *           the ticketAssociationCoverter to set
	 */
	public void setTicketAssociationCoverter(final Converter<ReturnRequestModel, TicketAssociatedData> ticketAssociationCoverter)
	{
		this.ticketAssociationCoverter = ticketAssociationCoverter;
	}






}
