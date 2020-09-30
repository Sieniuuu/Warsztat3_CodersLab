<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<div class="container-fluid">

<div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="text-left h3 mb-0 text-gray-800">UsersCRUD</h1>
            <a href="<c:url value="/user/add"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
                <i class="text-right fas fa-download fa-sm text-white-50"></i> Dodaj użytkownika</a>
        </div>
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Szczegóły użytkownika
                </h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table">
                        <tbody>
                        <tr>
                            <th scope="id">ID:</th>
                            <td>${user.id}</td>
                        </tr>
                        <tr>
                            <th scope="username">Nazwa użytkownika:</th>
                            <td>${user.userName}</td>
                        </tr>
                        <tr>
                            <th scope="email">Email:</th>
                            <td>${user.email}</td>
                        </tr>
                        </tbody>
                    </table>

                    <form method="post">
                        <button type="submit" name="back" class="btn btn-primary shadow-sm" value="true">Wróć do listy użytkowników</button></form>
                </div>
            </div>
        </div>
</div>
<%@ include file="footer.jsp" %>
