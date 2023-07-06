package etu1932.framework.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.text.AttributedCharacterIterator.Attribute;

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
                Parameter[] parameters = method.getParameters();
                List<String> allparametre = Collections.list(req.getParameterNames());
                Field[] allFields = myClass.getDeclaredFields();
                for(Field f : allFields){
                    for(String parameter : allparametre){
                        if(f.getName().equals(parameter)){
                            String attributeName = parameter.substring(0, 1).toUpperCase() + parameter.substring(1, parameter.length());
                            Method m = myClass.getDeclaredMethod("set"+attributeName, f.getType());
                            String value = req.getParameter(parameter);
                            Object valTemp = value;
                            if(f.getType() == Integer.class){
                                valTemp = Integer.parseInt(String.valueOf(value));
                            }else if(f.getType() == String.class){
                                valTemp = value;
                            }else if(f.getType() == Double.class){
                                valTemp = Double.parseDouble(value);
                            }else if(f.getType() == Boolean.class){
                                valTemp = Boolean.parseBoolean(value);
                            }else if(f.getType() == Date.class){
                               valTemp = java.sql.Date.valueOf(value);
                            }else{
                                valTemp = f.getType().getConstructor(String.class).newInstance(value);
                            }
                            m.invoke(ob, valTemp);
                            break;
                        }
                    }
                }
                Parameter[] allParam = m.getParameters();
                Object[] obj =new Object[allParam.length];
                for (int i = 0; i < allParam.length; i++) {
                    Parameter p = allParam[i];
                    if(p.isAnnotationPresent(Param.class)){
                        Param param = p.getAnnotation(Param.class);
                        for (String inparam : allparametre) {
                            if(param.key().equals(inparam)){
                                String value = request.getParameter(inparam);
                                Object valTemp = value;
                                if(p.getType() == Integer.class){
                                    valTemp = Integer.parseInt(String.valueOf(value));
                                }else if(p.getType() == String.class){
                                    valTemp = value;
                                }else if(p.getType() == Double.class){
                                    valTemp = Double.parseDouble(value);
                                }else if(p.getType() == Boolean.class){
                                    valTemp = Boolean.parseBoolean(value);
                                }else if(p.getType() == Date.class){//sql.date
                                   valTemp = java.sql.Date.valueOf(value);
                                }
                                obj[i]= valTemp;
                                break;
                            }
                        }
                    }
                }
                ModelView mv = (ModelView) method.invoke(ob);
                for(Map.Entry<String, Object> entry : mv.getData().entrySet()){
                    req.setAttribute(entry.getKey(), entry.getValue());
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
                RequestDispatcher rd = req.getRequestDispatcher("./WEB-INF/jsp/"+mv.getUrl());
                rd.forward(req, res);
            }catch(Exception e){
                e.printStackTrace(out);
            }
        }
    }
}