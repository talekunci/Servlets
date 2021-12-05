<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customers page</title>
    <%@ include file="headers.jsp" %>

</head>
<body>

<%@ include file="navigation.jsp" %>


    <div class="container">
        <div class="row">
            <h2>Customers page</h2>
        </div>

        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/customers/new" type="button" class="btn btn-primary">New</a>
                </div>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="customer" items="${customers}">
                        <tr>
                            <td><c:out value = "${customer.id}"/></td>
                            <td><c:out value = "${customer.name}"/></td>
                            <td><c:out value = "${customer.description}"/></td>
                            <td>
                                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                    <div class="btn-group me-2" role="group" aria-label="Second group">
                                        <a href="/customers/<c:out value = '${customer.id}'/>" type="button" class="btn btn-warning">Edit</a>
                                        <a href="/customers?deleteId=<c:out value = '${customer.id}'/>" type="button" class="btn btn-danger">Remove</a>
                                        <a href="/customers/projects/<c:out value = '${customer.id}'/>" type="button" class="btn btn-info">Projects</a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>