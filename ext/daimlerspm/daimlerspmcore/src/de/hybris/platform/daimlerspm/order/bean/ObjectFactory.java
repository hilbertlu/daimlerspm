/**
 *
 */
package de.hybris.platform.daimlerspm.order.bean;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * @author i313024
 *
 */
@XmlRegistry
public class ObjectFactory
{

	public ObjectFactory()
	{ // empty constructor // empty constructor
	}

	public DaimlerspmConsignment createDaimlerspmConsignment()
	{
		return new DaimlerspmConsignment();
	}

	public ShippingAddress createShippingAddress()
	{
		return new ShippingAddress();
	}

	public DaimlerspmConsignmentEntry createDaimlerspmConsignmentEntry()
	{
		return new DaimlerspmConsignmentEntry();
	}

	//	public ShippingAddressWsDTO createShippingAddressWsDTO()
	//	{
	//		return new ShippingAddressWsDTO();
	//	}
	//
	//	public DaimlerspmConsignmentEntryWsDTO createDaimlerspmConsignmentEntryWsDTO()
	//	{
	//		return new DaimlerspmConsignmentEntryWsDTO();
	//	}
	//
	//	public DaimlerspmConsignmentWsDTO createDaimlerspmConsignmentWsDTO()
	//	{
	//		return new DaimlerspmConsignmentWsDTO();
	//	}

	public DaimlerspmOrder createDaimlerspmOrder()
	{
		return new DaimlerspmOrder();
	}
}
