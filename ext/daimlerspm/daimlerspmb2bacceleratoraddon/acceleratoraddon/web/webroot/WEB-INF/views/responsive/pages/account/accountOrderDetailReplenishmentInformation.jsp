<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/addons/daimlerspmb2bacceleratoraddon/responsive/order" %>

<c:if test="${not empty orderData.triggerData}">
	<order:replenishmentScheduleInformation order="${orderData}"/>
</c:if>
