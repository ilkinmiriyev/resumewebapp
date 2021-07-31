<%--
    Document   : user
    Created on : May 14, 2021, 7:58:47 PM
    Author     : Admin
--%>

<%@page import="com.mycompany.entity.Country" %>
<%@page import="com.mycompany.entity.Skill" %>
<%@page import="com.mycompany.entity.User" %>
<%@ page import="com.mycompany.entity.UserSkill" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User JSP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link href="assets/css/users.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/065a3a8248.js" crossorigin="anonymous"></script>
</head>
<body>

<%

    User u = (User) request.getAttribute("user");
    List<UserSkill> skills = (List<UserSkill>) request.getAttribute("skills");
    List<Country> countries = (List<Country>) request.getAttribute("country");
    List<Skill> allSkill = (List<Skill>) request.getAttribute("allSkill");

%>
<div class="container">

    <form action="userdetail" id="details" method="POST">
        <input type="hidden" name="id" value=<%=u.getId()%>>
        <input type="hidden" name="action" value="update">
        <div class="row">

            <div class="col-md-4 col-sm-6">
                <label>Name </label> <input class="form-control mycolor" type="text" name="name"
                                            value="<%=u.getName()%>">
            </div>

            <div class="col-md-4 col-sm-6">
                <label>Surname </label> <input class="form-control mycolor" type="text" name="surname"
                                               value="<%=u.getSurname()%>">
            </div>

            <div class="col-md-4 col-sm-6">
                <label>Address </label> <input class="form-control mycolor" type="text" name="address"
                                               value="<%=u.getAddress()%>">
            </div>

            <div class="col-md-4 col-sm-6">
                <label>Email </label> <input class="form-control mycolor" type="email" name="email"
                                             value="<%=u.getEmail()%>">
            </div>

            <div class="col-md-4 col-sm-6">
                <label>Phone </label> <input class="form-control mycolor" type="tel" name="phone"
                                             value="<%=u.getPhone()%>">
            </div>

            <div class=" col-md-4 col-sm-6">
                <label>Birtday </label> <input class="form-control mycolor" type="date" name="birthdate"
                                               value="<%=u.getBirthdate()%>">
            </div>

            <div class="col-md-4 col-sm-6">
                <label>Birthplace </label>
                <select class="form-select mycolor" form="details" name="birthplace">
                    <option hidden selected value="<%=u.getBirthPlace().getId()%>"><%=u.getBirthPlace().getName()%>
                    </option>
                    <%
                        for (Country country : countries) {
                    %>
                    <option value="<%=country.getId()%>"><%=country.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="col-md-4 col-sm-6">
                <label>Nationality </label>
                <select class="form-select mycolor" form="details" name="nationality">
                    <option hidden selected
                            value="<%=u.getNationality().getId()%>"><%=u.getNationality().getNationality()%>
                    </option>
                    <%
                        for (Country country : countries) {
                    %>
                    <option value="<%=country.getId()%>"><%=country.getNationality()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="col-md-12">
                <label>Profile: </label> <textarea class="mycolor form-control" name="profile"
                                                   form="details"><%=u.getProfileDesc()%></textarea>
            </div>


            <%--skill--%>
            <div class="col-md-12">
                <table class="table">
                    <thead>
                    <th>Skill</th>
                    <th>Power</th>
                    </thead>

                    <tbody>
                    <%
                        for (UserSkill skill : skills) {
                            Skill s = skill.getSkill();
                    %>
                    <tr>
                        <td>
                            <input hidden form="details" name="userSkillId" value="<%=skill.getId()%>">
                            <input type="text" name="userSkillName" value="<%=s.getName()%>">
                        </td>
                        <td>
                            <input type="range" max="10" name="userSkillPower" min="1" value="<%=skill.getPower()%>">
                        </td>
                        <td>

                            <button class="btn" type="submit" form="skillDel" name="action" value="skillDelete">
                                <i class="btnUserSkill fas fa-minus-circle"></i>
                            </button>
                            <input type="hidden" form="skillDel" name="skillId" value="<%=skill.getId()%>">
                        </td>
                    </tr>

                    <%
                        }
                    %>
                    </tbody>

                </table>
            </div>
            <div class="col-md-3">
                <input class="form-control mycolor" type="text" form="addSkill" name="newSkillName" value=""
                       placeholder="Enter new Skill name">
            </div>

            <div class="col-md-3">

                <select class="form-select  mycolor" form="addSkill" name="skillList">
                    <%
                        for (Skill skill : allSkill) {
                    %>
                    <option value="<%=skill.getId()%>"><%=skill.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>


            <div class="col-md-3">
                <input type="range" form="addSkill" min="1" max="10" name="newSkillPower" value="">
            </div>

            <div class="col-md-3">
                <button class="btn" type="submit" name="action" form="addSkill" value="Add">
                    <i class="btnUserSkill fas fa-plus"></i>
                </button>
            </div>


            <input class="btn btn-primary" type="submit" value="Save">
        </div>
    </form>

    <form method="POST" action="userdetail" id="skillDel">
        <input type="hidden" name="id" value="<%=u.getId()%>">
    </form>

    <form action="userdetail" method="POST" id="addSkill">
        <input type="hidden" name="id" value=<%=u.getId()%>>
    </form>
</div>
</body>
</html>
