<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="b2b-product" tagdir="/WEB-INF/tags/addons/daimlerspmb2bacceleratoraddon/responsive/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<style>
<!--

-->
.warehouse_table{
margin-top: 20px;
 position:relative;
}
.warehouse_table_header{
border:1px solid black;
background-color: #142939;
color: #7f919e;
text-transform: uppercase;
text-align:left;
vertical-align:center;
padding: 8px 10px;
}
.warehouse_table_data{
text-align:left
padding: 8px 10px;
}
</style>
<c:choose> 
  <c:when test="${product.stock.stockLevelStatus.code eq 'inStock' and empty product.stock.stockLevel}">
    <c:set var="maxQty" value="FORCE_IN_STOCK"/>
  </c:when>
  <c:otherwise>
    <c:set var="maxQty" value="${product.stock.stockLevel}"/>
  </c:otherwise>
</c:choose>

<c:set var="qtyMinus" value="1" />
		
<div class="addtocart-component">
    <c:if test="${empty showAddToCart ? true : showAddToCart}">
        <div class="qty-selector input-group js-qty-selector">
			<span class="input-group-btn">
				<button class="btn btn-default js-qty-selector-minus" type="button" <c:if test="${qtyMinus <= 1}"><c:out value="disabled='disabled'"/></c:if>><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button>
			</span>
            <input type="text" maxlength="3" class="form-control js-qty-selector-input" size="1" value="${qtyMinus}" data-max="${maxQty}" data-min="1" name="pdpAddtoCartInput"  id="pdpAddtoCartInput" />
			<span class="input-group-btn">
				<button class="btn btn-default js-qty-selector-plus" type="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
			</span>
        </div>
    </c:if>
	<div>
		<table class="warehouse_table">
			<c:if test="${ stockDatas ne null}">
    		<tr>
			<th class="warehouse_table_header" style="text-align: left"><spring:theme code="pdp.product.stockinfo.warehouse"/></th>
			<th class="warehouse_table_header" style="text-align: left"><spring:theme code="pdp.product.stockinfo.stocklevel"/></th>
			</tr>
			<tbody>
			<c:forEach var="stock" items="${stockDatas}">
				<tr>
					<td class="warehouse_table_data">
						${stock.warehouse.name }
					</td>
					<td class="warehouse_table_data">${stock.available}</td>
				</tr>
			</c:forEach>
			</tbody>
			</c:if>
		</table>
	</div>
    <div class="stock-wrapper clearfix">
    	
        <ycommerce:testId code="productDetails_productInStock_label">
            <b2b-product:productStockThreshold product="${product}"/>
        </ycommerce:testId>
        <product:productFutureAvailability product="${product}" futureStockEnabled="${futureStockEnabled}" />
    </div>

    <div class="actions">
        <c:if test="${multiDimensionalProduct}" >
                <c:url value="${product.url}/orderForm" var="productOrderFormUrl"/>
                <a href="${productOrderFormUrl}" class="btn btn-default btn-block btn-icon js-add-to-cart glyphicon-list-alt">
                    <spring:theme code="order.form" />
                </a>
        </c:if>
        <action:actions element="div"  parentComponent="${component}"/>
    </div>
</div>