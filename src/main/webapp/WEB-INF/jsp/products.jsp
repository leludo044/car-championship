<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<body>
<h1>Products</h1>
<table>
    <tbody>
    <c:forEach items="${products}" var="product">
        <tr>
            <td>${product.name}</td>
            <td><img src="${product.imageUrl}"/></td>
            <td><a href="${product.imageCreditsUrl}" target="_blank">credits</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>