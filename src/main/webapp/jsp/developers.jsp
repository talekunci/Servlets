<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Developers page</title>
        <%@ include file="headers.jsp" %>


    </head>
<body>

<%@ include file="navigation.jsp" %>


    <div class="container">
        <div class="row">
            <h2>Developers page</h2>
        </div>

        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/developers/new" type="button" class="btn btn-primary">New</a>
                </div>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Age</th>
                    <th scope="col">Gender</th>
                    <th scope="col">Description</th>
                    <th scope="col">Salary</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="developer" items="${developers}">
                        <tr>
                            <td><c:out value = "${developer.id}"/></td>
                            <td><c:out value = "${developer.name}"/></td>
                            <td><c:out value = "${developer.age}"/></td>
                            <td><c:out value = "${developer.gender}"/></td>
                            <td><c:out value = "${developer.description}"/></td>
                            <td><c:out value = "${developer.salary}"/></td>
                            <td>
                                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                    <div class="btn-group me-2" role="group" aria-label="Second group">
                                        <a href="/developers/<c:out value = '${developer.id}'/>" type="button" class="btn btn-warning">Edit</a>
                                        <a href="/developers?deleteId=<c:out value = '${developer.id}'/>" type="button" class="btn btn-danger">Remove</a>
                                        <a href="/developers/skills/<c:out value = '${developer.id}'/>" type="button" class="btn btn-info">Skills</a>
                                        <a href="/developers/projects/<c:out value = '${developer.id}'/>" type="button" class="btn btn-info">Projects</a>
                                        <a href="/developers/companies/<c:out value = '${developer.id}'/>" type="button" class="btn btn-info">Companies</a>
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