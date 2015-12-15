package de.hybris.merchandise.facades.order.impl;

import de.hybris.merchandise.facades.order.OrderCancelFacade;
import de.hybris.merchandise.facades.order.data.OrderCancelResultData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordercancel.CancelDecision;
import de.hybris.platform.ordercancel.DefaultOrderCancelDenialReason;
import de.hybris.platform.ordercancel.OrderCancelDenialReason;
import de.hybris.platform.ordercancel.OrderCancelException;
import de.hybris.platform.ordercancel.OrderCancelRequest;
import de.hybris.platform.ordercancel.OrderCancelService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.services.BaseStoreService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * Implementation of {@link OrderCancelFacade} for the merchandise shop.
 * 
 */
public class MerchandiseOrderCancelFacade implements OrderCancelFacade
{

	private static final Logger LOG = Logger.getLogger(MerchandiseOrderCancelFacade.class);

	private OrderCancelService orderCancelService;
	private CustomerAccountService customerAccountService;
	private UserService userService;
	private BaseStoreService baseStoreService;
	private ModelService modelService;

	@Override
	public OrderCancelResultData cancelOrder(final OrderData order)
	{
		ServicesUtil.validateParameterNotNull(order, "Order must not be null");
		final CustomerModel currentUser = (CustomerModel) getUserService().getCurrentUser();
		final OrderModel orderModel = getCustomerAccountService().getOrderForCode(currentUser, order.getCode(),
				getBaseStoreService().getCurrentBaseStore());
		if (orderModel == null)
		{
			throw new UnknownIdentifierException("Order with code " + order.getCode()
					+ " not found for current user in current BaseStore");
		}

		final CancelDecision cancelDecision = orderCancelService.isCancelPossible(orderModel, currentUser, false, false);

		final OrderCancelResultData result = new OrderCancelResultData();
		result.setOrderId(order.getCode());

		if (cancelDecision.isAllowed())
		{
			try
			{
				final OrderCancelRequest orderCancelRequest = new OrderCancelRequest(orderModel);
				orderCancelService.requestOrderCancel(orderCancelRequest, currentUser);
				modelService.save(orderModel);
				result.setSuccess(true);
			}
			catch (final OrderCancelException e)
			{
				LOG.error("Cancel failed for order [" + orderModel + "]", e);
				result.setSuccess(false);
				result.setFailReason(e.getMessage());
			}
		}
		else
		{
			result.setSuccess(false);
			final StringBuilder denialReasonDescriptionBuilder = new StringBuilder();
			for (final OrderCancelDenialReason reason : cancelDecision.getDenialReasons())
			{
				if (reason instanceof DefaultOrderCancelDenialReason)
				{
					final DefaultOrderCancelDenialReason denialReason = (DefaultOrderCancelDenialReason) reason;
					denialReasonDescriptionBuilder.append(denialReason.getDescription()).append("</br>");
				}
			}
			result.setFailReason(denialReasonDescriptionBuilder.toString());
		}

		return result;
	}

	private BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	protected UserService getUserService()
	{
		return userService;
	}


	protected CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	@Required
	public void setOrderCancelService(final OrderCancelService orderCancelService)
	{
		this.orderCancelService = orderCancelService;
	}


	@Required
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}


	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Required
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}
}
