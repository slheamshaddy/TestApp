/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static controller.notification.jsonResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.myDB;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author shadrach.udoka
 */
public class reversal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
      String terminalID =  request.getHeader("TerminalID");
      String authorise = request.getHeader("Authorization");
      String authenticate = request.getAuthType();
      boolean isTrue = false;
       StringBuffer jb = new StringBuffer();
        System.out.println("Authorization :......"+authenticate + " " + authorise);  
  String line = null;
  try {
    BufferedReader reader = request.getReader();
    while ((line = reader.readLine()) != null)
      jb.append(line);
      System.out.println("Request is..."+jb.toString());
 
    JSONObject jsonObject = new JSONObject(jb.toString());
           // HTTP.toJSONObject(jb.toString());
  
  // Work with the data using methods like...
  // int someInt = jsonObject.getInt("intParamName");
  // String someString = jsonObject.getString("stringParamName");
  // JSONObject nestedObj = jsonObject.getJSONObject("nestedObjName");
  // JSONArray arr = jsonObject.getJSONArray("arrayParamName");
  // etc...
  
//  { "status": true, 
//  "code": "SUCCESS", 
//  "message": "Transaction logged successfully" }
  
      System.out.println("HERE");
  String refId = jsonObject.getString("RefferenceId");
  
      if(myDB.verifyTerminalId(terminalID)){
          if(myDB.verifyRefId(refId)){
              isTrue = true;
              out.println(jsonResponseReversal(00,"Successful",isTrue));
          }else{
              out.println(jsonResponse(404,"Transaction Not Found"));
          }
          
      }else{
          out.println(jsonResponse(403,"UnAuthorized, Invalid TerminalID"));
      }
      }catch(Exception e){
      out.println(jsonResponse(403,"UnAuthorized"));
      } 
//  catch (JSONException e) {
////    e.printStackTrace();
//   out.println(jsonResponse(400,"Bad Request"));
////    throw new IOException("Error parsing JSON request string");
//  }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static String jsonResponse(int responsecode, String mesg) {
        HashMap responseMap = new HashMap();
        responseMap.put("ResponseCode", responsecode);
        responseMap.put("ResponseDescription", mesg);
        Gson gson = new GsonBuilder().create();
        String jsonResp = gson.toJson(responseMap);
        return jsonResp;
    }
     
          public static String jsonResponseReversal(int responsecode, String mesg,boolean istrue) {
        HashMap responseMap = new HashMap();
        responseMap.put("ResponseCode", responsecode);
        responseMap.put("ResponseDescription", mesg);
        responseMap.put("Status", istrue);
        Gson gson = new GsonBuilder().create();
        String jsonResp = gson.toJson(responseMap);
        return jsonResp;
    }
    
}
