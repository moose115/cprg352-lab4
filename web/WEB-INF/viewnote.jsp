<%-- 
    Document   : viewnote
    Created on : 2022-09-29, 13:21:08
    Author     : musta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simple Note Keeper</title>
    </head>
    <body>
        <h1>Simple Note Keeper</h1>
        <h2>View note</h2>
        <p><strong>Title: </strong>${note.title}</p>
        <p><strong>Contents:</strong></br>${note.contents}</p>
        <div>
            <a href="/Lab4_SimpleNoteKeeper/note?action=edit&name=${note.fileName}">Edit</a>
        </div>
        <form action="note" method="POST">
            <input type="text" name="_action" value="delete" style="display: none;" />
            <input type="text" name="fileName" value="${note.fileName}" style="display: none;" />
            <input type="submit" value="Delete" />
        </form>
        <div>
            <a href="/Lab4_SimpleNoteKeeper/">Go back</a>
        </div>
    </body>
</html>
