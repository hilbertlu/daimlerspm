<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="claim" tagdir="/WEB-INF/tags/addons/daimlerspmb2bacceleratoraddon/responsive/claim" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<ycommerce:testId code="returnOrder_section">
    <claim:accountClaimOrder order="${orderData}"/>
</ycommerce:testId>


