<!DOCTYPE html>
<html>
<head>
    <title>New company developer</title>
    <meta charset="UTF-8">
    <%@ include file="headers.jsp" %>

</head>
    <body>

    <%@ include file="navigation.jsp" %>


    <div class="container">
            <div class="row">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group me-2" role="group" aria-label="Second group">
                        <a href="/companies/developers/${companyId}" type="button" class="btn btn-success">Back to company developers</a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="mb-3">
                    <label for="id" class="form-label">Company ID</label>
                    <input type="text" disabled class="form-control"
                           value="${companyId}"
                           id="id" placeholder="Company ID">
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
            let companyId = document.getElementById('id');
            let devId = document.getElementById('developerId');

            function add() {
                 let body = {
                     devId: devId.value,
                     companyId: companyId.value,
                 }

                let url = '/companies/developers/add';
                let method = 'PUT';

                fetch(url, {
                    method: method,
                    body: JSON.stringify(body)
                })
                .then( _ => {
                    window.location.href = '/companies/developers/${companyId}';
                }
                );
            }
        </script>
    </body>
</html>