/**
 *
 */
package de.hybris.platform.ycommercewebservices.v2.controller;

import de.hybris.platform.daimlerspm.order.bean.DaimlerspmOrder;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
	public void getOrderConfirmation(@RequestBody final DaimlerspmOrder orderDTO, final HttpServletResponse response)
	{
		try
		{
			final StringBuilder msg = new StringBuilder();
			response.setHeader("content-type", "text/html;charset=UTF-8");
			if (orderDTO != null)
			{

				daimlerspmOrderFacdes.saveOrder(orderDTO);
				msg.append("Update Order Confirmation Success !");
				response.setStatus(response.SC_OK);
				response.getWriter().write(msg.toString());
				response.getWriter().flush();
				response.flushBuffer();
			}
			else
			{
				throw new Exception("Update Order Confirmation Failed, Please check your xml file !");
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
			try
			{
				response.sendError(response.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			catch (final IOException e1)
			{
				// YTODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Secured(
	{ "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@RequestMapping(value = "/document", method = RequestMethod.POST)
	public void updateConsignmentDocuments(final HttpServletRequest request, final HttpServletResponse response)
	{

		final String order = StringUtils.isNotEmpty(request.getParameter("order")) ? request.getParameter("order") : "00004007";
		final String code = StringUtils.isNotEmpty(request.getParameter("consignment")) ? request.getParameter("consignment")
				: "a00004007";
		final String document = StringUtils.isNotEmpty(request.getParameter("doucment")) ? request.getParameter("doucment")
				: "INPG-00100001";
		final String invoice = StringUtils.isNotEmpty(request.getParameter("invoice")) ? request.getParameter("invoice") : "true";
		final String deliveryNote = StringUtils.isNotEmpty(request.getParameter("deliverynote")) ? request
				.getParameter("deliverynote") : "false";
		try
		{
			response.setHeader("content-type", "text/html;charset=UTF-8");
			final String msg = "Update Consignment Document Success !";
			daimlerspmOrderFacdes.saveConsignmentDocument(order, code, document, StringUtils.isNotEmpty(invoice) ? true : false,
					StringUtils.isNotEmpty(deliveryNote) ? true : false);
			response.setStatus(response.SC_OK);
			response.getWriter().write(msg);
			response.getWriter().flush();
			response.flushBuffer();
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
			try
			{
				response.sendError(response.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			catch (final IOException e1)
			{
				// YTODO Auto-generated catch block
				e1.printStackTrace();
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
