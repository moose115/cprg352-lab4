/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Note;

/**
 *
 * @author musta
 */
public class NoteServlet extends HttpServlet {    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher index = getServletContext().getRequestDispatcher("/WEB-INF/viewnotes.jsp");
        RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp");
        RequestDispatcher create = getServletContext().getRequestDispatcher("/WEB-INF/createnote.jsp");
        RequestDispatcher edit = getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp");
        
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        ArrayList<String> noteNames = getNoteNames();
        
        if (action == null || action.equals("") || name == null || name.equals("")) {
            req.setAttribute("noteNames", noteNames);
            index.forward(req, resp);
            return;
        }
        
        if(action.equals("view")) {
            Note note = getNoteFromFile(name);
            note.setContents(note.getContents().replaceAll("\r\n|\r|\n", "</br>"));
            req.setAttribute("note", note);
            if(note == null) {
                resp.sendError(404);
                return;
            }
            view.forward(req, resp);
            return;
        }
        if (action.equals("create")) {
            Note note = getNoteFromFile("../defaultnote");
            req.setAttribute("note", note);
            create.forward(req, resp);
            return;
        }
        if (action.equals("edit")) {
            Note note = getNoteFromFile(name);
            req.setAttribute("note", note);
            if(note == null) {
                resp.sendError(404);
                return;
            }
            edit.forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        
        String action = req.getParameter("_action");
        String fileName = req.getParameter("fileName");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        
        if (action == null || action.equals("")) {
            resp.sendError(400);
            return;
        }
        
        
        // HTML forms support only GET and POST
        if(action.equals("create")) {
            // create
            if (
                fileName == null || fileName.equals("") ||
                title == null || title.equals("") ||
                contents == null || contents.equals("")
                ) {
                resp.sendError(400);
                return;
            }
            Note note = new Note(fileName, title, contents);
            saveNoteToFile(note);
            resp.sendRedirect("/Lab4_SimpleNoteKeeper/note?action=view&name=" + note.getFileName());
            return;
        }
        if(action != null && action.equals("edit")) {
            doPut(req, resp);
            return;
        }
        if(action.equals("delete")) {
            doDelete(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        
        if (
            fileName == null || fileName.equals("") ||
            title == null || title.equals("") ||
            contents == null || contents.equals("")
            ) {
            resp.sendError(400);
            return;
        }
        
        Note note = new Note(fileName, title, contents);
        
        saveNoteToFile(note);
        resp.sendRedirect("/Lab4_SimpleNoteKeeper/note?action=view&name=" + note.getFileName());
        return;
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        
        String path = getServletContext().getRealPath("/WEB-INF/notes/" + fileName + ".txt");
        File file = new File(path);
        
        file.delete();
        
        resp.sendRedirect("/Lab4_SimpleNoteKeeper");
        return;
    }
    
    
    
    private Note getNoteFromFile(String name) {
        String path = getServletContext().getRealPath("/WEB-INF/notes/" + name + ".txt");
        
        try {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            sc.useDelimiter(String.format("%n"));
            
            String title = sc.nextLine();
            String contents = "";
            
            while(sc.hasNext()) {
                contents += sc.nextLine();
                if (sc.hasNext()) contents += sc.delimiter();
            }
            
            System.out.println(contents);
            
            sc.close();
            return new Note(name, title, contents);
        } catch(FileNotFoundException e) {
            return null;
        } catch(IOException e) {
            return null;
        }
    }
    
    private ArrayList<String> getNoteNames() {
        File folder = new File(getServletContext().getRealPath("/WEB-INF/notes/"));
        File[] listOfFiles = folder.listFiles();
        ArrayList fileNames = new ArrayList<String>();
        
        for(int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                fileNames.add(fileName.substring(0, fileName.length() - 4));
            }
        }
        
        return fileNames;
    }
    
    private void saveNoteToFile(Note note) throws IOException {
        FileWriter fw = new FileWriter(
            getServletContext().getRealPath("/WEB-INF/notes/" + 
            note.getFileName() +
            ".txt")
        );
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw, false);
                
        pw.write(note.toString());
        pw.close();
    }
}
