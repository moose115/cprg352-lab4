<%-- 
    Document   : viewnotes
    Created on : 2022-09-29, 13:37:33
    Author     : musta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simple Note Keeper</title>
    </head>
    <body>
        <h1>Simple Note Keeper</h1>
        <h2>Choose note</h2>
        <c:forEach items="${noteNames}" var="noteName">
            <div>
                <a href="/Lab4_SimpleNoteKeeper/?action=view&name=${noteName}">${noteName}</a>
            </div>
        </c:forEach>
        <div>
            <a href="/Lab4_SimpleNoteKeeper/?action=create&name=notnull">Create a note</a>
        </div>
    </body>
</html>
