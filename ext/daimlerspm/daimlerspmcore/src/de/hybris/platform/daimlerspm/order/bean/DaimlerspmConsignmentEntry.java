/**
 *
 */
package de.hybris.platform.daimlerspm.order.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author i313024
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DaimlerspmConsignmentEntry")
public class DaimlerspmConsignmentEntry
{

	@XmlElement(required = true)
	protected String entrynumber;

	@XmlElement(required = true)
	protected String consignment;

	@XmlElement(required = true)
	protected int quantity;

	@XmlElement(required = true)
	protected int shippedQuantity;

	/**
	 * @return the entrynumber
	 */
	public String getEntrynumber()
	{
		return entrynumber;
	}

	/**
	 * @param entrynumber
	 *           the entrynumber to set
	 */
	public void setEntrynumber(final String entrynumber)
	{
		this.entrynumber = entrynumber;
	}

	/**
	 * @return the consignment
	 */
	public String getConsignment()
	{
		return consignment;
	}

	/**
	 * @param consignment
	 *           the consignment to set
	 */
	public void setConsignment(final String consignment)
	{
		this.consignment = consignment;
	}



	/**
	 * @return the quantity
	 */
	public long getQuantity()
	{
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * @return the shippedQuantity
	 */
	public long getShippedQuantity()
	{
		return shippedQuantity;
	}

	/**
	 * @param shippedQuantity the shippedQuantity to set
	 */
	public void setShippedQuantity(int shippedQuantity)
	{
		this.shippedQuantity = shippedQuantity;
	}


}
