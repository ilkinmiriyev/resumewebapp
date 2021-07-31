<%-- 
    Document   : user
    Created on : May 14, 2021, 7:58:47 PM
    Author     : Admin
--%>

<%@page import="com.mycompany.entity.User" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://kit.fontawesome.com/065a3a8248.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link href="assets/css/users.css" rel="stylesheet">
    <script src="assets/js/users.js"></script>
    <title>Users JSP</title>
</head>
<body>


<%
    List<User> list = (List<User>) request.getAttribute("users");
%>

<div class="container">
    <div class="row">
        <div class="col-4">
            <form action="users" method="GET">
                <div class="form-group">
                    <label>Firstname</label>
                    <input type="text" class="form-control mycolor" placeholder="Enter name" name="name" value=""> <br>
                </div>

                <div class="form-group">
                    <label>Lastname</label>
                    <input type="text" class="form-control mycolor" placeholder="Enter surname" name="surname" value="">
                    <br>
                </div>


                <input class="btn btn-primary mycolor" id="search" type="submit" name="search" value="Search">
            </form>
        </div>

    </div>

    <hr>
    <div>
        <table class="table">
            <thead>
            <th>Name</th>
            <th>Surname</th>
            <th>Nationality</th>
            <th></th>

            </thead>
            <tbody>
            <%
                for (User u : list) {

            %>
            <tr>
                <td><%=u.getName()%>
                </td>
                <td><%=u.getSurname()%>
                </td>
                <td>
                    <%=u.getNationality().getNationality() == null ? "N/A" : u.getNationality().getNationality()%>
                </td>
                <td>

                    <button class="btn" type="button"  data-toggle="modal" data-target="#exampleModal"
                            onclick="setIdForDelete(<%=u.getId()%>)">
                        <i id="delete" class="far fa-trash-alt"></i>
                    </button>

                    <form action="userdetail" method="GET" class="form-check-inline">
                        <input type="hidden" name="id" value="<%=u.getId()%>">
                                                <input type="hidden" name="action" value="update">

                        <button class="btn " type="submit" value="uptdate">
                            <i class="fas fa-user-edit"></i>
                        </button>
                    </form>
                </td>
            </tr>

            <%}%>
            </tbody>
        </table>

    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure?
            </div>
            <div class="modal-footer">
                <form action="userdetail" method="POST">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" id="idForDelete" name="id" value="">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <input type="submit" class="btn btn-danger btn-primary" value="Delete">
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
