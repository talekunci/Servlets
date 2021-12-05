<!DOCTYPE html>
<html>
<head>
    <title>New project developer</title>
    <meta charset="UTF-8">
    <%@ include file="headers.jsp" %>

</head>
    <body>

    <%@ include file="navigation.jsp" %>


    <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/projects/developers/${projectId}" type="button" class="btn btn-success">Back to project developers</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="mb-3">
                    <label for="id" class="form-label">Project ID</label>
                    <input type="text" disabled class="form-control"
                           value="${projectId}"
                           id="id" placeholder="Developer Id">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Developer ID</label>
                    <input type="text" class="form-control"
                           value=""
                           id="developerId" placeholder="Developer ID">
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
            let projectId = document.getElementById('id');
            let devId = document.getElementById('developerId');

            function add() {
                 let body = {
                     projectId: projectId.value,
                     devId: devId.value,
                 }

                let url = '/projects/developers/add';
                let method = 'PUT';

                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/projects/developers/${projectId}';
                }
                );
            }
        </script>
    </body>
</html>