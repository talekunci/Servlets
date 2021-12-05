<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Developer companies page</title>
    <%@ include file="headers.jsp" %>

</head>
<body>

<%@ include file="navigation.jsp" %>


    <div class="container">
        <div class="row">
            <h2>Developer companies page</h2>
        </div>

        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/developers" type="button" class="btn btn-success">Back to developers</a>
                </div>
            </div>

            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/developers/companies/add?developerId=${developerId}" type="button" class="btn btn-primary">Add</a>
                </div>
            </div>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Company Name</th>
                    <th scope="col">Company Description</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="company" items="${companies}">
                        <tr>
                            <td><c:out value = "${company.getName()}"/></td>
                            <td><c:out value = "${company.getDescription()}"/></td>
                            <td>
                                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                    <div class="btn-group me-2" role="group" aria-label="Second group">
                                        <a href="/developers/companies/${developerId}?deleteCompanyId=<c:out value = '${company.id}'/>" type="button" class="btn btn-danger">Remove</a>
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