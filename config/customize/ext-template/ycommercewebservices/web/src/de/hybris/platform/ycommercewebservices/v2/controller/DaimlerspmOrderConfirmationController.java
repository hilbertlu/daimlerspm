/**
 *
 */
package de.hybris.platform.ycommercewebservices.v2.controller;

import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmConsignment;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmConsignmentEntry;
import de.hybris.platform.daimlerspm.order.bean.DaimlerspmOrder;
import de.hybris.platform.daimlerspm.order.bean.ShippingAddress;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ws.client.core.WebServiceTemplate;

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

	@Resource(name = "b2bOrderFacade")
	private OrderFacade b2bOrderFacade;

	@Resource(name = "daimlerspmWSTemplate")
	private WebServiceTemplate daimlerspmWSTemplate;


	@Resource(name = "daimlerspmOrderFacdes")
	private DaimlerspmOrderFacade daimlerspmOrderFacdes;

	@Secured(
	{ "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@RequestMapping(value = "/xml", method = RequestMethod.POST, headers = "Accept=application/xml")
	public List<OrderModel> getOrderConfirmationWithStringXML(final HttpServletRequest request)
	{

		System.out.println("request==============" + request);

		try
		{
			//			if (daimlerspmWSTemplate == null)
			//			{
			//				daimlerspmWSTemplate = new WebServiceTemplate();
			//				final Jaxb2Marshaller marshall = new Jaxb2Marshaller();
			//				marshall.setContextPath("de.hybris.platform.daimlerspm.order.bean");
			//				marshall.setCheckForXmlRootElement(false);
			//				daimlerspmWSTemplate.setMarshaller(marshall);
			//				daimlerspmWSTemplate.setUnmarshaller(marshall);
			//			}
			//			final Jaxb2Marshaller marshall = (Jaxb2Marshaller) daimlerspmWSTemplate.getMarshaller();


			final DaimlerspmOrder order = new DaimlerspmOrder();
			order.setCode("00003001");
			order.setStatus("Completed");
			final List<DaimlerspmConsignment> consigments = new ArrayList<DaimlerspmConsignment>();


			final DaimlerspmConsignment con = new DaimlerspmConsignment();
			con.setCode("a00003001");
			con.setOrder("00003001");
			con.setStatus("Shipped");

			final List<DaimlerspmConsignmentEntry> entries = new ArrayList<DaimlerspmConsignmentEntry>();

			final DaimlerspmConsignmentEntry entry = new DaimlerspmConsignmentEntry();
			entry.setConsignment("a00003001");
			entry.setEntrynumber("0");
			entry.setQuantity(1);
			entry.setShippedQuantity(1);
			entries.add(entry);
			final DaimlerspmConsignmentEntry entry1 = new DaimlerspmConsignmentEntry();
			entry1.setConsignment("a00003001");
			entry1.setEntrynumber("1");
			entry1.setQuantity(16);
			entry1.setShippedQuantity(16);
			entries.add(entry1);
			final ShippingAddress address = new ShippingAddress();
			address.setCountryCode("US");
			address.setFirstname("Klaus");
			address.setLastname("Demokunde");
			address.setPhone("101-1938398");
			address.setPostalcode("102930");
			address.setOwner("william.hunter@hybris.com");
			address.setStreetname("Bagby Street");
			address.setStreetnumber("1000");
			con.setWarehouse("pw_warehouse_w");
			con.setShippingAddress(address);
			con.setDaimlerspmConsignmentEntry(entries);
			con.setShippingDate("10.04.2017 18:06");
			consigments.add(con);


			final DaimlerspmConsignment con1 = new DaimlerspmConsignment();
			con1.setCode("b00003001");
			con1.setOrder("00003001");
			con1.setStatus("Shipped");
			final List<DaimlerspmConsignmentEntry> entries1 = new ArrayList<DaimlerspmConsignmentEntry>();
			final DaimlerspmConsignmentEntry entry2 = new DaimlerspmConsignmentEntry();
			entry2.setConsignment("b00003001");
			entry2.setEntrynumber("1");
			entry2.setQuantity(1);
			entry2.setShippedQuantity(1);
			entries1.add(entry2);
			final ShippingAddress address1 = new ShippingAddress();
			address1.setCountryCode("CN");
			address1.setFirstname("Klaus");
			address1.setLastname("Demokunde");
			address1.setPhone("101-1938398");
			address1.setPostalcode("102930");
			address.setOwner("william.hunter@hybris.com");
			address1.setTown("California");
			address1.setStreetname("testStrasse");
			address.setStreetnumber("1001");
			con1.setWarehouse("pw_warehouse_s");
			con1.setShippingAddress(address1);
			con1.setDaimlerspmConsignmentEntry(entries1);
			con1.setShippingDate("12.04.2017 10:23");
			consigments.add(con1);

			order.setConsignments(consigments);

			final ClassLoader classLoader = DaimlerspmOrder.class.getClassLoader();

			final JAXBContext jc = JAXBContext.newInstance("de.hybris.platform.daimlerspm.order.bean", classLoader);
			final JAXBElement<DaimlerspmOrder> jaxbElement = new JAXBElement<DaimlerspmOrder>(new QName("daimlerspmOrder"),
					DaimlerspmOrder.class, order);
			final Marshaller marshall = jc.createMarshaller();
			//final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.setProperty("jaxb.encoding", "UTF-8");
			marshall.marshal(jaxbElement, System.out);

			marshall.marshal(jaxbElement, new StreamResult(new FileOutputStream("daimlerspmOrder.xml")));


		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



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
	 * @return the b2bOrderFacade
	 */
	public OrderFacade getB2bOrderFacade()
	{
		return b2bOrderFacade;
	}

	/**
	 * @param b2bOrderFacade
	 *           the b2bOrderFacade to set
	 */
	public void setB2bOrderFacade(final OrderFacade b2bOrderFacade)
	{
		this.b2bOrderFacade = b2bOrderFacade;
	}


	/**
	 * @return the daimlerspmWSTemplate
	 */
	public WebServiceTemplate getDaimlerspmWSTemplate()
	{
		return daimlerspmWSTemplate;
	}

	/**
	 * @param daimlerspmWSTemplate
	 *           the daimlerspmWSTemplate to set
	 */
	public void setDaimlerspmWSTemplate(final WebServiceTemplate daimlerspmWSTemplate)
	{
		this.daimlerspmWSTemplate = daimlerspmWSTemplate;
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
