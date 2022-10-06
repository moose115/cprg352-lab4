<%-- 
    Document   : editnote
    Created on : 2022-09-29, 13:21:27
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
        <h2>Edit note</h2>
        <form action="note" method="POST">
            <input type="text" name="_action" value="edit" style="display: none;" />
            <input type="text" name="fileName" value="${note.fileName}" style="display: none;" />
            <div>
                <label>
                    Title<br/>
                    <input type="text" name="title" value="${note.title}" />
                </label>
            </div>
            <div>
                <label>
                    Contents<br/>
                    <textarea name="contents">${note.contents}</textarea>
                </label>
            </div>
            <div><input type="submit" value="Edit"/></div>
        </form>
        <div>
            <a href="/Lab4_SimpleNoteKeeper/">Go back</a>
        </div>
    </body>
</html>
