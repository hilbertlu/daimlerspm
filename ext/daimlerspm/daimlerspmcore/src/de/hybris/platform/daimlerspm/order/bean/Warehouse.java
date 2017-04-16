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
@XmlRootElement(name = "Warehouse")
public class Warehouse
{

	@XmlElement(required = true)
	protected String code;

	@XmlElement(required = true)
	protected String name;

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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *           the name to set
	 */
	public void setName(final String name)
	{
		this.name = name;
	}



}
