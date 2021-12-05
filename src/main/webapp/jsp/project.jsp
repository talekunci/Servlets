<!DOCTYPE html>
<html>
<head>
    <title>View project</title>
    <meta charset="UTF-8">
    <%@ include file="headers.jsp" %>

</head>
    <body>

    <%@ include file="navigation.jsp" %>
    <% ua.goit.model.Project project = (ua.goit.model.Project) request.getAttribute("project"); %>

    <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/projects" type="button" class="btn btn-success">Back to projects</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="mb-3">
                    <label for="id" class="form-label">ID</label>
                    <input type="text" disabled class="form-control"
                           value="${project.getId()}"
                           id="id" placeholder="Id">
                </div>
                <div class="mb-3">
                    <label for="companyId" class="form-label">Company ID</label>
                    <input type="text" class="form-control"
                           value="${project.getCompanyId()}"
                           id="companyId" placeholder="Project company Id">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control"
                           value="${project.getName()}"
                           id="name" placeholder="Project name">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <input type="text" class="form-control"
                           value="${project.getDescription()}"
                           id="description" placeholder="Project description">
                </div>
                <div class="mb-3">
                    <label for="cost" class="form-label">Cost</label>
                    <input type="text" class="form-control"
                           value="${project.getCost()}"
                           id="cost" placeholder="Project cost">
                </div>
                <div class="mb-3">
                    <label for="creationDate" class="form-label">Creation Date</label>
                    <input type="text" class="form-control"
                           value="${project.getCreationDate()}"
                           id="creationDate" placeholder="Project creation date (yyyy-MM-dd)">
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
            let companyId = document.getElementById('companyId');
            let name = document.getElementById('name');
            let description = document.getElementById('description');
            let cost = document.getElementById('cost');
            let creationDate = document.getElementById('creationDate');

            function save() {
             let body = {
             <% if(project.getId() != null) {%>
                 id: id.value,
              <% } %>
                companyId: companyId.value,
                name: name.value,
                description: description.value,
                cost: cost.value,
                creationDate: creationDate.value,
              }
              <% if(project.getId() == null) {%>
                 let url = '/projects';
                 let method = 'POST';
              <% } else { %>
                 let url = '/projects/<%= project.getId() %>';
                 let method = 'PUT';
              <% } %>
                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/projects';
                }
                );
            }
        </script>
    </body>
</html>