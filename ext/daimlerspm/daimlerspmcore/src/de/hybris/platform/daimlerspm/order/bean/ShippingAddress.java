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
@XmlRootElement(name = "ShippingAddress")
public class ShippingAddress
{

	@XmlElement(required = true)
	protected String owner;

	@XmlElement(required = true)
	protected String streetname;

	@XmlElement(required = true)
	protected String streetnumber;

	@XmlElement(required = true)
	protected String countryCode;

	@XmlElement(required = true)
	protected String town;

	@XmlElement(required = true)
	protected String postalcode;

	@XmlElement(required = true)
	protected String phone;

	@XmlElement(required = true)
	protected String firstname;

	@XmlElement(required = true)
	protected String lastname;

	/**
	 * @return the owner
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * @param owner
	 *           the owner to set
	 */
	public void setOwner(final String owner)
	{
		this.owner = owner;
	}

	/**
	 * @return the streetname
	 */
	public String getStreetname()
	{
		return streetname;
	}

	/**
	 * @param streetname
	 *           the streetname to set
	 */
	public void setStreetname(final String streetname)
	{
		this.streetname = streetname;
	}

	/**
	 * @return the streetnumber
	 */
	public String getStreetnumber()
	{
		return streetnumber;
	}

	/**
	 * @param streetnumber
	 *           the streetnumber to set
	 */
	public void setStreetnumber(final String streetnumber)
	{
		this.streetnumber = streetnumber;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode()
	{
		return countryCode;
	}

	/**
	 * @param countryCode
	 *           the countryCode to set
	 */
	public void setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
	}

	/**
	 * @return the town
	 */
	public String getTown()
	{
		return town;
	}

	/**
	 * @param town
	 *           the town to set
	 */
	public void setTown(final String town)
	{
		this.town = town;
	}

	/**
	 * @return the postalcode
	 */
	public String getPostalcode()
	{
		return postalcode;
	}

	/**
	 * @param postalcode
	 *           the postalcode to set
	 */
	public void setPostalcode(final String postalcode)
	{
		this.postalcode = postalcode;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *           the phone to set
	 */
	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * @param firstname
	 *           the firstname to set
	 */
	public void setFirstname(final String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname()
	{
		return lastname;
	}

	/**
	 * @param lastname
	 *           the lastname to set
	 */
	public void setLastname(final String lastname)
	{
		this.lastname = lastname;
	}


}
