<!DOCTYPE html>
<html>
<head>
    <title>View developer</title>
    <meta charset="UTF-8">
    <%@ include file="headers.jsp" %>

</head>
    <body>

    <%@ include file="navigation.jsp" %>
    <% ua.goit.model.Developer developer = (ua.goit.model.Developer) request.getAttribute("developer"); %>

    <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/developers" type="button" class="btn btn-success">Back to developers</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="mb-3">
                    <label for="id" class="form-label">ID</label>
                    <input type="text" disabled class="form-control"
                           value="${developer.getId()}"
                           id="id" placeholder="Id">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control"
                           value="${developer.getName()}"
                           id="name" placeholder="Developer name">
                </div>
                <div class="mb-3">
                    <label for="age" class="form-label">Age</label>
                    <input type="text" class="form-control"
                           value="${developer.getAge()}"
                           id="age" placeholder="Developer age">
                </div>
                <div class="mb-3">
                    <label for="gender" class="form-label">Gender</label>
                    <input type="text" class="form-control"
                           value="${developer.getGender()}"
                           id="gender" placeholder="Developer gender">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <input type="text" class="form-control"
                           value="${developer.getDescription()}"
                           id="description" placeholder="Developer description">
                </div>
                <div class="mb-3">
                    <label for="salary" class="form-label">Salary</label>
                    <input type="text" class="form-control"
                           value="${developer.getSalary()}"
                           id="salary" placeholder="Developer salary">
                </div>
            </div>

            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <button onclick="save()" type="button" class="btn btn-primary">Save</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            let id = document.getElementById('id');
            let name = document.getElementById('name');
            let age = document.getElementById('age');
            let gender = document.getElementById('gender');
            let description = document.getElementById('description');
            let salary = document.getElementById('salary');

            function save() {
             let body = {
             <% if(developer.getId() != null) {%>
                 id: id.value,
              <% } %>
                name: name.value,
                age: age.value,
                gender: gender.value,
                description: description.value,
                salary: salary.value,
              }
              <% if(developer.getId() == null) {%>
                 let url = '/developers';
                 let method = 'POST';
              <% } else { %>
                 let url = '/developers/<%= developer.getId() %>';
                 let method = 'PUT';
              <% } %>
                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/developers';
                }
                );
            }
        </script>
    </body>
</html>