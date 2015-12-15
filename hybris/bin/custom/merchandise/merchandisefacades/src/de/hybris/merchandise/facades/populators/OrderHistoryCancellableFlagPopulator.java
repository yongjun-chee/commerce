package de.hybris.merchandise.facades.populators;

import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commerceservices.converter.Populator;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.ordercancel.CancelDecision;
import de.hybris.platform.ordercancel.OrderCancelService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import org.springframework.beans.factory.annotation.Required;


public class OrderHistoryCancellableFlagPopulator implements Populator<OrderModel, OrderHistoryData>
{
	private OrderCancelService orderCancelService;
	private UserService userService;

	@Override
	public void populate(final OrderModel source, final OrderHistoryData target) throws ConversionException
	{
		target.setCancellable(isOrderCancellable(source));
	}

	private boolean isOrderCancellable(final OrderModel source)
	{
		final PrincipalModel user = userService.getCurrentUser();
		final CancelDecision cancelDecision = orderCancelService.isCancelPossible(source, user, false, false);
		return cancelDecision.isAllowed();
	}

	@Required
	public void setOrderCancelService(final OrderCancelService orderCancelService)
	{
		this.orderCancelService = orderCancelService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}
}
