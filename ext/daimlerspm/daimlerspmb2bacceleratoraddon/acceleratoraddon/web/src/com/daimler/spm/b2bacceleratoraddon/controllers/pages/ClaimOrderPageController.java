/**
 * 
 */
package com.daimler.spm.b2bacceleratoraddon.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController.ShowMode;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.basecommerce.enums.RefundReason;
import de.hybris.platform.basecommerce.enums.ReturnAction;
import de.hybris.platform.basecommerce.enums.ReturnStatus;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordermanagementfacade.returns.OmsReturnFacade;
import de.hybris.platform.ordermanagementfacade.returns.data.ReturnEntryData;
import de.hybris.platform.ordermanagementfacade.returns.data.ReturnRequestData;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daimler.spm.b2bacceleratoraddon.forms.OrderEntryClaimForm;

/**
 * @author i313024
 *
 */

@Controller
@RequestMapping(value="/my-account/order")
public class ClaimOrderPageController extends AbstractSearchPageController
{

	public static final Logger LOG = Logger.getLogger(ClaimOrderPageController.class);
	
	public static final String BREADCRUMBS_ATTR = "breadcrumbs";
	public static final String MY_ACCOUNT_ORDERS = "/my-account/orders";
	public static final String REDIRECT_TO_ORDERS_HISTORY_PAGE = REDIRECT_PREFIX + MY_ACCOUNT_ORDERS;
	public static final String CLAIM_ORDER_CMS_PAGE = "claim-order";
	public static final String CLAIM_CONFIRM_ORDER_CMS_PAGE = "confirm-claim-order";
	public static final String MY_ACCOUNT_ORDER = "/my-account/order/";
	public static final String MY_ACCOUNT_CLAIMS = "/my-account/order/claims";
	public static final String MY_ACCOUNT_CLAIMS_PAGE = "claims";
	public static final String TEXT_ACCOUNT_CLAIMS_HISTORY = "text.account.claimsHistory";
	private static final String CLAIMS_REQUEST_DETAILS_CMS_PAGE = "claims-request-details";

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "omsReturnFacade")
	private OmsReturnFacade omsReturnFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "priceDataFactory")
	private PriceDataFactory priceDataFactory;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;
	
	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;

	@Resource(name = "productService")
	private ProductService productService;
	
	@RequestMapping(value="/claims",method = RequestMethod.GET)
	@RequireHardLogIn
	public String getMyReturnRequests(@RequestParam(value = "page", defaultValue = "0") final int pageNumber,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_ACCOUNT_CLAIMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_ACCOUNT_CLAIMS_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, accountBreadcrumbBuilder.getBreadcrumbs(TEXT_ACCOUNT_CLAIMS_HISTORY));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);

		final PageableData pageableData = createPageableData(pageNumber, 5, sortCode, showMode);
		final SearchPageData<ReturnRequestData> searchPageData = omsReturnFacade.getPagedReturnRequestsByCurrentUser(pageableData);

		populateModel(model, searchPageData, showMode);

		return getViewForPage(model);
	}
	
	@RequireHardLogIn
	@RequestMapping(value = "/claims/details/{returnRequestCode:.*}", method = { RequestMethod.POST, RequestMethod.GET })
	public String showReturnRequestDetailsPage(@PathVariable(value = "returnRequestCode") final String returnRequestCode, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException // NOSONAR
	{
		try
		{
			ReturnRequestData returnRequestDetails = omsReturnFacade.getReturnForReturnCode(returnRequestCode);

			for (ReturnEntryData returnEntryData : returnRequestDetails.getEntries())
			{
				ProductModel product = productService.getProductForCode(returnEntryData.getOrderEntry().getProduct().getCode());
				ProductData productData = productConverter.convert(product);
				returnEntryData.getOrderEntry().setProduct(productData);
			}

			model.addAttribute("returnRequestData", returnRequestDetails);

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb("/my-account/order/claims",
					getMessageSource().getMessage(TEXT_ACCOUNT_CLAIMS_HISTORY, null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage("text.account.claims.claimBreadcrumb", new Object[] { returnRequestDetails.getRma() }, "Claims Request {0}",
							getI18nService().getCurrentLocale()), null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		}
		catch (final UnknownIdentifierException e)
		{
			LOG.warn("Attempted to load a return request that does not exist or is not visible", e);
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page.not.found", null);
			return CLAIMS_REQUEST_DETAILS_CMS_PAGE;
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(CLAIMS_REQUEST_DETAILS_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CLAIMS_REQUEST_DETAILS_CMS_PAGE));
		return getViewForPage(model);
	}
	
	
	@RequireHardLogIn
	@RequestMapping(value = "/{orderCode:.*}/claims", method = { RequestMethod.POST, RequestMethod.GET })
	public String showClaimOrderPage(@PathVariable(value = "orderCode") final String orderCode, final Model model,
			final RedirectAttributes redirectModel)throws CMSItemNotFoundException // NOSONAR
	{
		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			model.addAttribute("orderData", orderDetails);
			model.addAttribute("orderEntryReturnForm", initializeForm(orderDetails));

			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_CLAIMS,
					getMessageSource().getMessage("text.account.orderHistory", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ORDER + orderCode, getMessageSource()
					.getMessage("text.account.order.orderBreadcrumb", new Object[] { orderDetails.getCode() }, "Order {0}",
							getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#",
					getMessageSource().getMessage("text.account.claimOrder", null, getI18nService().getCurrentLocale()), null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		}
		catch (final UnknownIdentifierException e)
		{
			return redirect(redirectModel, e);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(CLAIM_ORDER_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CLAIM_ORDER_CMS_PAGE));
		return getViewForPage(model);
	}



	/*
	 * Display the confirm return order page
	 */
	@RequireHardLogIn
	@RequestMapping(value = "/{orderCode:.*}/claims/confirm", method = RequestMethod.POST)
	public String confirmCancelOrderPage(@PathVariable("orderCode") final String orderCode,
			@ModelAttribute("orderEntryReturnForm") final OrderEntryClaimForm orderEntryReturnForm,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			orderEntryReturnForm.getReturnEntryQuantityMap().forEach((entryNumber, qty) ->
			{
				orderDetails.getEntries().forEach(orderEntryData ->
				{
					// Case of MultiD product
					if (isMultidimensionalEntry(orderEntryData))
					{
						orderEntryData.getEntries().stream()
								.filter(nestedOrderEntry -> nestedOrderEntry.getEntryNumber().equals(entryNumber))
								.forEach(nestedOrderEntryData -> setReturnedItemsPrice(qty, nestedOrderEntryData));
					}
					// Case of non MultiD product
					else
					{
						if (orderEntryData.getEntryNumber().equals(entryNumber))
						{
							setReturnedItemsPrice(qty, orderEntryData);
						}
					}
				});
			});
			model.addAttribute("orderData", orderDetails);
			model.addAttribute("orderEntryReturnForm", orderEntryReturnForm);
			final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ORDERS,
					getMessageSource().getMessage("text.account.orderHistory", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ORDER + orderCode, getMessageSource()
					.getMessage("text.account.order.orderBreadcrumb", new Object[] { orderDetails.getCode() }, "Order {0}",
							getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb(MY_ACCOUNT_ORDER + orderCode + "/claims",
					getMessageSource().getMessage("text.account.claimOrder", null, getI18nService().getCurrentLocale()), null));
			breadcrumbs.add(new Breadcrumb("#",
					getMessageSource().getMessage("text.account.confirm.claimOrder", null, getI18nService().getCurrentLocale()),
					null));
			model.addAttribute(BREADCRUMBS_ATTR, breadcrumbs);

		}
		catch (final UnknownIdentifierException e)
		{
			return redirect(redirectModel, e);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(CLAIM_CONFIRM_ORDER_CMS_PAGE));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CLAIM_CONFIRM_ORDER_CMS_PAGE));
		return getViewForPage(model);
	}

	protected String redirect(RedirectAttributes redirectModel, UnknownIdentifierException e)
	{
		LOG.warn("Attempted to load a order that does not exist or is not visible", e);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "system.error.page.not.found", null);
		return REDIRECT_TO_ORDERS_HISTORY_PAGE;
	}



	/*
	 * Submit the confirmed return items to be returned
	 */
	@RequireHardLogIn
	@RequestMapping(value = "/{orderCode:.*}/claims/submit", method = RequestMethod.POST)
	public String submitCancelOrderPage(@PathVariable("orderCode") final String orderCode,
			@ModelAttribute("orderEntryCancelForm") final OrderEntryClaimForm orderEntryReturnForm,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		try
		{
			final OrderData order = orderFacade.getOrderDetailsForCode(orderCode);
			omsReturnFacade.createReturnRequest(prepareReturnRequestData(order, orderEntryReturnForm));
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER, getMessageSource()
					.getMessage("text.account.claim.success", null, getI18nService().getCurrentLocale()), null);
			return REDIRECT_TO_ORDERS_HISTORY_PAGE;
		}
		catch (Exception exception)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "text.account.return.fail.generic");
			return REDIRECT_TO_ORDERS_HISTORY_PAGE;
		}
	}

	/**
	 * It prepares the {@link ReturnRequestData} object by taking the order and the map of orderentries {@link OrderEntryData} number and returned quantities
	 *
	 * @param order
	 * 		order {@link OrderData} which we want to return
	 * @param orderEntryReturnForm
	 * 		a {@link OrderEntryReturnForm} map of orderentries number and the returned quantities
	 * @return returnRequest {@link ReturnRequestData}
//	 */
	protected ReturnRequestData prepareReturnRequestData(final OrderData order, final OrderEntryClaimForm orderEntryReturnForm)
	{
		final ReturnRequestData returnRequest = new ReturnRequestData();
		returnRequest.setOrder(order);
		final List<ReturnEntryData> returnEntries = new ArrayList<>();
		orderEntryReturnForm.getReturnEntryQuantityMap().forEach((key, value) ->
		{

			final ReturnEntryData returnEntry = new ReturnEntryData();
			final OrderEntryData oed = new OrderEntryData();
			oed.setEntryNumber(key);
			returnEntry.setOrderEntry(oed);
			returnEntry.setExpectedQuantity(value);
			returnEntry.setRefundReason(RefundReason.GOODWILL);
			returnEntry.setAction(ReturnAction.HOLD);
			returnEntries.add(returnEntry);
		});
		returnRequest.setStatus(ReturnStatus.APPROVAL_PENDING);
		returnRequest.setEntries(returnEntries);
		returnRequest.setRefundDeliveryCost(false);
		return returnRequest;
	}



	/**
	 * Confirms if the given {@link OrderEntryData} is for multidimensional product
	 *
	 * @param orderEntry
	 * 		the given {@link OrderEntryData}
	 * @return true, if the given {@link OrderEntryData} is for multidimensional product
	 */
	protected boolean isMultidimensionalEntry(final OrderEntryData orderEntry)
	{
		return orderEntry.getProduct().getMultidimensional() != null && orderEntry.getProduct().getMultidimensional() && !orderEntry
				.getEntries().isEmpty();
	}

	/**
	 * Initialize the input form and takes care of the multiD case
	 *
	 * @param orderData
	 * 		The given order{@link OrderData} to be returned
	 * @return initialized form {@link OrderEntryReturnForm} with initial values of 0
	 */
	protected OrderEntryClaimForm initializeForm(final OrderData orderData)
	{
		final OrderEntryClaimForm orderEntryReturnForm = new OrderEntryClaimForm();
		final Map<Integer, Long> returnEntryQuantityMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(orderData.getEntries()))
		{
			orderData.getEntries().stream().filter(this::isMultiD).forEach(oed ->
					populateMapForNestedEntries(returnEntryQuantityMap, oed));
			orderData.getEntries().stream().filter(oed -> !isMultiD(oed)).forEach(oed ->
					returnEntryQuantityMap.put(oed.getEntryNumber(), 0L));
		}
		orderEntryReturnForm.setReturnEntryQuantityMap(returnEntryQuantityMap);
		return orderEntryReturnForm;
	}


	/**
	 * @param returnEntryQuantityMap
	 * 		The map to populate with entry number and quantity to return
	 * @param orderEntryData
	 * 		The order entry {@link OrderEntryData} that holds a MultiD product
	 */
	protected void populateMapForNestedEntries(final Map<Integer, Long> returnEntryQuantityMap,
			final OrderEntryData orderEntryData)
	{
		if (CollectionUtils.isNotEmpty(orderEntryData.getEntries()))
			orderEntryData.getEntries().forEach(nestedOed -> returnEntryQuantityMap.put(nestedOed.getEntryNumber(), 0L));
	}


	/**
	 * A method that checks if the product associated with this orderEntry is a multi dimensional product
	 *
	 * @param orderEntryData
	 * 		the order entry {@link OrderEntryData}
	 * @return true if the product in the orderEntryData is multiD and false if it is not set or it is not.
	 */
	protected boolean isMultiD(OrderEntryData orderEntryData)
	{
		return orderEntryData.getProduct().getMultidimensional() != null && orderEntryData.getProduct().getMultidimensional();
	}


	/**
	 * Updates the {@link OrderEntryData#returnedItemsPrice} for the given requested return quantity
	 *
	 * @param qty
	 * 		the quantity to be returned from the given {@link OrderEntryData}
	 * @param orderEntryData
	 * 		the {@link OrderEntryData}
	 */
	protected void setReturnedItemsPrice(final Long qty, final OrderEntryData orderEntryData)
	{
		final PriceData returnedItemsPriceData = priceDataFactory
				.create(PriceDataType.BUY, orderEntryData.getBasePrice().getValue().multiply(new BigDecimal(qty)),
						commonI18NService.getCurrentCurrency());
		orderEntryData.setReturnedItemsPrice(returnedItemsPriceData);
	}
	
}
