/**
 *
 */
package de.hybris.platform.daimlerspm.order.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author i313024
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DaimlerspmConsignment")
public class DaimlerspmConsignment
{

	@XmlElement(required = true)
	protected String code;

	@XmlElement(required = true)
	protected String order;

	@XmlElement(required = true)
	protected String status;

	@XmlElement(required = true)
	protected String warehouse;

	@XmlElement(required = true)
	protected String shippingDate;

	@XmlElement(required = true, name = "shippingAddress", type = ShippingAddress.class)
	protected ShippingAddress shippingAddress;

	@XmlElement(required = true, name = "daimlerspmConsignmentEntry", type = DaimlerspmConsignmentEntry.class)
	protected List<DaimlerspmConsignmentEntry> daimlerspmConsignmentEntry;

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the order
	 */
	public String getOrder()
	{
		return order;
	}

	/**
	 * @param order
	 *           the order to set
	 */
	public void setOrder(final String order)
	{
		this.order = order;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}



	/**
	 * @return the warehouse
	 */
	public String getWarehouse()
	{
		return warehouse;
	}

	/**
	 * @param warehouse
	 *           the warehouse to set
	 */
	public void setWarehouse(final String warehouse)
	{
		this.warehouse = warehouse;
	}

	/**
	 * @return the shippingDate
	 */
	public String getShippingDate()
	{
		return shippingDate;
	}

	/**
	 * @param shippingDate
	 *           the shippingDate to set
	 */
	public void setShippingDate(final String shippingDate)
	{
		this.shippingDate = shippingDate;
	}

	/**
	 * @return the shippingAddress
	 */
	public ShippingAddress getShippingAddress()
	{
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *           the shippingAddress to set
	 */
	public void setShippingAddress(final ShippingAddress shippingAddress)
	{
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the daimlerspmConsignmentEntry
	 */
	public List<DaimlerspmConsignmentEntry> getDaimlerspmConsignmentEntry()
	{
		return daimlerspmConsignmentEntry;
	}

	/**
	 * @param daimlerspmConsignmentEntry
	 *           the daimlerspmConsignmentEntry to set
	 */
	public void setDaimlerspmConsignmentEntry(final List<DaimlerspmConsignmentEntry> daimlerspmConsignmentEntry)
	{
		this.daimlerspmConsignmentEntry = daimlerspmConsignmentEntry;
	}



}
