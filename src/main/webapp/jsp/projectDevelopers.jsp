<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Project developers page</title>
    <%@ include file="headers.jsp" %>

</head>
<body>

<%@ include file="navigation.jsp" %>


    <div class="container">
        <div class="row">
            <h2>Project developers page</h2>
        </div>

        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/projects" type="button" class="btn btn-success">Back to projects</a>
                </div>
            </div>

            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <a href="/projects/developers/add?projectId=${projectId}" type="button" class="btn btn-primary">Add</a>
                </div>
            </div>
        </div>

        <div class="row">
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
                                        <a href="/projects/developers/${projectId}?deleteDeveloperId=<c:out value = '${developer.id}'/>" type="button" class="btn btn-danger">Remove</a>
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