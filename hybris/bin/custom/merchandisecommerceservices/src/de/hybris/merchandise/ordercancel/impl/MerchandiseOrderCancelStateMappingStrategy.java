package de.hybris.merchandise.ordercancel.impl;

import de.hybris.platform.basecommerce.enums.OrderCancelState;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordercancel.OrderCancelService;
import de.hybris.platform.ordercancel.OrderCancelStateMappingStrategy;
import de.hybris.platform.servicelayer.util.ServicesUtil;


/**
 * This strategy maps orders state in the merchandise shop to the proper {@link OrderCancelState} that are
 * understandable by {@link OrderCancelService}.
 * 
 */
public class MerchandiseOrderCancelStateMappingStrategy implements OrderCancelStateMappingStrategy
{

	@Override
	public OrderCancelState getOrderCancelState(final OrderModel order)
	{
		ServicesUtil.validateParameterNotNull(order, "Order must not be null");
		if (OrderStatus.WAITING_FOR_IMMEDIATE_CANCEL.equals(order.getStatus()))
		{
			//this state means that order is eligible for immediate cancel
			return OrderCancelState.PENDINGORHOLDINGAREA;
		}
		return OrderCancelState.CANCELIMPOSSIBLE;
	}

}
