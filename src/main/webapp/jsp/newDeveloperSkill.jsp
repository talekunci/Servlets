<!DOCTYPE html>
<html>
<head>
    <title>New developer skill</title>
    <meta charset="UTF-8">
    <%@ include file="headers.jsp" %>

</head>
    <body>

    <%@ include file="navigation.jsp" %>


    <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/developers/skills/${developerId}" type="button" class="btn btn-success">Back to developer skills</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="mb-3">
                    <label for="id" class="form-label">developer ID</label>
                    <input type="text" disabled class="form-control"
                           value="${developerId}"
                           id="id" placeholder="Developer Id">
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Branch(Language)</label>
                    <input type="text" class="form-control"
                           value=""
                           id="branch" placeholder="Branch(Language)">
                </div>
                <div class="mb-3">
                    <label for="age" class="form-label">Skill Level</label>
                    <input type="text" class="form-control"
                           value=""
                           id="level" placeholder="Skill Level">
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
            let branch = document.getElementById('branch');
            let level = document.getElementById('level');

            function add() {
                 let body = {
                     devId: devId.value,
                     branch: branch.value,
                     level: level.value,
                 }

                let url = '/developers/skills/add';
                let method = 'PUT';

                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/developers/skills/${developerId}';
                }
                );
            }
        </script>
    </body>
</html>