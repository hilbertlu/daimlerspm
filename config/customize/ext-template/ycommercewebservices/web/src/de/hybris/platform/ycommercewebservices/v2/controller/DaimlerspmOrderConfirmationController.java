/**
 *
 */
package de.hybris.platform.ycommercewebservices.v2.controller;

import de.hybris.platform.daimlerspm.order.bean.DaimlerspmOrder;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daimler.spm.facades.order.facade.DaimlerspmOrderFacade;



/**
 * @author i313024
 *
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/daimlerspmOrderConfirm")
public class DaimlerspmOrderConfirmationController extends BaseCommerceController
{

	private static final Logger LOG = Logger.getLogger(DaimlerspmOrderConfirmationController.class);



	@Resource(name = "daimlerspmOrderFacdes")
	private DaimlerspmOrderFacade daimlerspmOrderFacdes;





	@Secured(
	{ "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@RequestMapping(method = RequestMethod.POST)
	//@RequestMapping(method = RequestMethod.POST, consumes ={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void getOrderConfirmation(@RequestBody final DaimlerspmOrder orderDTO)
	{

		if (orderDTO != null)
		{
			try
			{
				daimlerspmOrderFacdes.saveOrder(orderDTO);
			}
			catch (final Exception e)
			{
				LOG.error(e.getMessage());
				e.printStackTrace();
			}

		}
	}


	/**
	 * @return the daimlerspmOrderFacdes
	 */
	public DaimlerspmOrderFacade getDaimlerspmOrderFacdes()
	{
		return daimlerspmOrderFacdes;
	}


	/**
	 * @param daimlerspmOrderFacdes
	 *           the daimlerspmOrderFacdes to set
	 */
	public void setDaimlerspmOrderFacdes(final DaimlerspmOrderFacade daimlerspmOrderFacdes)
	{
		this.daimlerspmOrderFacdes = daimlerspmOrderFacdes;
	}



}
