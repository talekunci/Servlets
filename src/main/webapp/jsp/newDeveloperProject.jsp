<!DOCTYPE html>
<html>
<head>
    <title>New developer project</title>
    <meta charset="UTF-8">
    <%@ include file="headers.jsp" %>

</head>
    <body>

    <%@ include file="navigation.jsp" %>


    <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/developers/projects/${developerId}" type="button" class="btn btn-success">Back to developer projects</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="mb-3">
                    <label for="id" class="form-label">Developer ID</label>
                    <input type="text" disabled class="form-control"
                           value="${developerId}"
                           id="id" placeholder="Developer Id">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Project ID</label>
                    <input type="text" class="form-control"
                           value=""
                           id="projectId" placeholder="Project ID">
                </div>
            </div>


        <div class="row">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group me-2" role="group" aria-label="Second group">
                    <button onclick="add()" type="button" class="btn btn-primary">Add</button>
                </div>
            </div>
        </div>

        <script>
            let devId = document.getElementById('id');
            let projectId = document.getElementById('projectId');

            function add() {
                 let body = {
                     devId: devId.value,
                     projectId: projectId.value,
                 }

                let url = '/developers/projects/add';
                let method = 'PUT';

                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/developers/projects/${developerId}';
                }
                );
            }
        </script>
    </body>
</html>