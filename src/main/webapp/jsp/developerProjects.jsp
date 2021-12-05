<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Developer projects page</title>
    <%@ include file="headers.jsp" %>

</head>
<body>

<%@ include file="navigation.jsp" %>


    <div class="container">
        <div class="row">
            <h2>Developer projects page</h2>
        </div>

        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/developers" type="button" class="btn btn-success">Back to developers</a>
                </div>
            </div>

            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/developers/projects/add?developerId=${developerId}" type="button" class="btn btn-primary">Add</a>
                </div>
            </div>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Project ID</th>
                    <th scope="col">Company ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Cost</th>
                    <th scope="col">Creation Date</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="project" items="${projects}">
                        <tr>
                            <td><c:out value = "${project.id}"/></td>
                            <td><c:out value = "${project.companyId}"/></td>
                            <td><c:out value = "${project.getName()}"/></td>
                            <td><c:out value = "${project.description}"/></td>
                            <td><c:out value = "${project.cost}"/></td>
                            <td><c:out value = "${project.creationDate}"/></td>
                            <td>
                                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                    <div class="btn-group me-2" role="group" aria-label="Second group">
                                        <a href="/developers/projects/${developerId}?deleteProjectId=<c:out value = '${project.id}'/>" type="button" class="btn btn-danger">Remove</a>
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