<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<c:url var="actionUrl" value="${fn:replace(url, '{orderCode}', orderCode)}" scope="page"/>
<c:if test="${cancellable}">
     <form:form action="${actionUrl}" method="post">
         <p><button class="form"><spring:theme code="text.cancel" text="Cancel"/></button></p>
     </form:form>
</c:if>
