/**
 *
 */
package de.hybris.platform.daimlerspm.order.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author i313024
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "daimlerspmOrder")
@XmlType(name = "RequestMessage")
public class DaimlerspmOrder
{

	@XmlElement(required = true)
	protected String code;

	@XmlElement(required = true)
	protected String status;

	@XmlElement(required = true, name = "consignments", type = DaimlerspmConsignment.class)
	protected List<DaimlerspmConsignment> consignments;

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
	 * @return the consignments
	 */
	public List<DaimlerspmConsignment> getConsignments()
	{
		return consignments;
	}

	/**
	 * @param consignments
	 *           the consignments to set
	 */
	public void setConsignments(final List<DaimlerspmConsignment> consignments)
	{
		this.consignments = consignments;
	}



}
