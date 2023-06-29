package etu1932.framework.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.Map;

import etu1932.framework.*;
import etu1932.annotation.*;


public class FrontServlet extends HttpServlet{
    HashMap<String, Mapping> MappingUrls = new HashMap<>();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURL());
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(req.getRequestURL());
        processRequest(req, res);
    }

    public void init() throws ServletException{
        try{
            getAllFile();            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAllFile() throws Exception{
        String packageName = "etu1932.model";
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replaceAll("[.]", "\\\\"));
        File packDir = new File(root.toURI());
        System.out.println(root.toURI());
        File[] inside = packDir.listFiles(file->file.getName().endsWith(".class"));
        List<Class> lists = new ArrayList<>();
        for (File f : inside) {
            String c = packageName+"."+f.getName().substring(0, f.getName().lastIndexOf("."));
            lists.add(Class.forName(c));
        }
        for ( Class c : lists) {
            Method[] methods = c.getDeclaredMethods();
            for(Method m : methods){
                if(m.isAnnotationPresent(etu1932.annotation.Url.class)){
                    Url url= m.getAnnotation(etu1932.annotation.Url.class);
                    if(! url.key().isEmpty() && url.key() != null){
                        Mapping map = new Mapping(c.getName() , m.getName());
                        this.MappingUrls.put(url.key(),map);
                    }
                }
            }
        }
    }


    public void processRequest(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        String base_url = req.getRequestURI();
        base_url=base_url.substring(req.getContextPath().length()+1);
        if(MappingUrls.containsKey(base_url)){
            Mapping map = MappingUrls.get(base_url);
            String methodName = map.getMethod();
            try{
                Class<?> myClass = Class.forName(map.getClassName());
                Object ob = myClass.getConstructor().newInstance();
                Method method = null;
                Method[] methods = myClass.getDeclaredMethods();
                for (Method m : methods) {
                    if(m.getName().equals(methodName) && m.isAnnotationPresent(Url.class)){
                        method = m;
                        break;
                    }
                }
                ModelView mv = (ModelView) method.invoke(ob);
                for(Map.Entry<String, Object> entry : mv.getData().entrySet()){
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                RequestDispatcher rd = req.getRequestDispatcher(mv.getUrl());
                rd.forward(req, res);
            }catch(Exception e){
                e.printStackTrace(out);
            }
        }
    }
}