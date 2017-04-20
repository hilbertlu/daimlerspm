<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<spring:url value="/my-account/order/${orderData.code}/claims" var="orderClaimUrl" htmlEscape="false"/>
<common:headline url="${orderClaimUrl}" labelKey="text.account.claim.confirm.order.title" labelArguments="${orderData.code}" />
