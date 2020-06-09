// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ClassCastException;
import java.util.ArrayList;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  ArrayList<String> employeeComment = new ArrayList<String>();

    /**
   * Get the number of comments to display from the user
   */
  private int getNumComments(HttpServletRequest request) {
    // Get the input from the form.
    String userNum = request.getParameter("number-comments");
    int requestedNum;
    //Convert input into an int
    try {
      requestedNum = Integer.parseInt(userNum);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to int: " + userNum);
      return -1;
    }

    // Check that the input is between 1 and the number of stored comments
    if (requestedNum < 1 || requestedNum > employeeComment.size()) {
      System.err.println("Player choice is out of range: " + requestedNum);
      return -1;
    }
    return requestedNum;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    Query query = new Query("Employee").addSort("Key", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity employee : results.asIterable()) {
        try {
            String comment = (String) employee.getProperty("comment");
            employeeComment.add(comment);
            System.out.println(comment);            
        } catch (ClassCastException e) {
            employeeComment.add("Invalid String");
        }
    }
    for(int i = 0; i < getNumComments(request); i++) {
        response.setContentType("text/html;");
        response.getWriter().println(employeeComment.get(i));
    }
    
    
    // String json = (employeeComment.size() - 1);
    // response.setContentType("text/html;");
    // response.getWriter().println(employeeComment.get);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name-box");
    String comment = request.getParameter("comment-box");
    long timestamp = System.currentTimeMillis();
    
    Entity employee = new Entity("Employee");
    employee.setProperty("timestamp", timestamp);
    employee.setProperty("name", name);
    employee.setProperty("comment", comment);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(employee);

    response.sendRedirect("/index.html");
  }

}
