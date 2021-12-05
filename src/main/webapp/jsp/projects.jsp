<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Projects page</title>
    <%@ include file="headers.jsp" %>

</head>
<body>

<%@ include file="navigation.jsp" %>


    <div class="container">
        <div class="row">
            <h2>Projects page</h2>
        </div>

        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/projects/new" type="button" class="btn btn-primary">New</a>
                </div>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Company Id</th>
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
                            <td><c:out value = "${project.name}"/></td>
                            <td><c:out value = "${project.description}"/></td>
                            <td><c:out value = "${project.cost}"/></td>
                            <td><c:out value = "${project.creationDate}"/></td>
                            <td>
                                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                    <div class="btn-group me-2" role="group" aria-label="Second group">
                                        <a href="/projects/${project.id}" type="button" class="btn btn-warning">Edit</a>
                                        <a href="/projects?deleteId=${project.id}" type="button" class="btn btn-danger">Remove</a>
                                        <a href="/projects/developers/${project.id}" type="button" class="btn btn-info">Developers</a>
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