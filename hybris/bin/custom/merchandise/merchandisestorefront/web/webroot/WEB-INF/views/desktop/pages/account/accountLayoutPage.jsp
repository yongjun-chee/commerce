
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>

<template:page pageTitle="${pageTitle}">
	<div id="globalMessages">
		<common:globalMessages/>

		<c:if test="${not empty orderCancelResult}">
            <c:choose>
                <c:when test="${orderCancelResult.success}">
                    <div class="span-24">
                        <div class="information_message positive">
                            <span class="single"></span>
                            <p><spring:theme code="order.cancel.success" arguments="${orderCancelResult.orderId}"/></p>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="span-24">
                        <div class="information_message negative">
                        <p><spring:theme code="order.cancel.failed" arguments="${orderCancelResult.orderId},${orderCancelResult.failReason}"/></p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
	</div>

	<div class="span-24">
		<div class="span-4 accountLeftNavigation">
			<cms:pageSlot position="SideContent" var="feature">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
		</div>
		<div class="span-20 last accountContentPane">
			<cms:pageSlot position="TopContent" var="feature" element="div" class="accountTopContentSlot">
				<cms:component component="${feature}" element="div" class="clearfix" />
			</cms:pageSlot>
	
			<cms:pageSlot position="BodyContent" var="feature" element="div" class="accountBodyContentSlot">
				<cms:component component="${feature}" element="div" class="clearfix" />
			</cms:pageSlot>

			<cms:pageSlot position="BottomContent" var="feature" element="div" class="accountBottomContentSlot">
				<cms:component component="${feature}" element="div" class="clearfix" />
			</cms:pageSlot>
		</div>
	</div>

</template:page>
