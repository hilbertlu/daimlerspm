<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="claim" tagdir="/WEB-INF/tags/addons/daimlerspmb2bacceleratoraddon/responsive/claim" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<div class="well well-tertiary well-lg">
    <ycommerce:testId code="return_overview_section">
        <claim:accountClaimOverview returnRequest="${returnRequestData}"/>
    </ycommerce:testId>
</div>
<br/>
<ycommerce:testId code="return_entries_section">
    <claim:accountClaimEntriesOverview returnRequest="${returnRequestData}"/>
</ycommerce:testId>
<br/>
<ycommerce:testId code="return_entries_section">
    <claim:accountClaimTotals returnRequest="${returnRequestData}"/>
</ycommerce:testId>
<br/>
<ycommerce:testId code="return_actions_section">
    <claim:accountClaimActions returnRequest="${returnRequestData}"/>
</ycommerce:testId>

