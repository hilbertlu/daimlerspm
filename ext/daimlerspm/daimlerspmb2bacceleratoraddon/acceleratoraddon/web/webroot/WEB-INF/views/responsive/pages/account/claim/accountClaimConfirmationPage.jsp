<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/addons/daimlerspmb2bacceleratoraddon/responsive/claim" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>



<ycommerce:testId code="returnOrder_section">
    <order:accountConfirmClaimOrder order="${orderData}"/>
</ycommerce:testId>
