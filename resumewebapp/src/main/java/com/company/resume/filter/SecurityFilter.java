//package com.company.resume.filter;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebFilter(filterName = "SecurityFilter", urlPatterns = {"*"})
//    public class SecurityFilter implements Filter {
//
//        public void  doFilter(ServletRequest request, ServletResponse response,
//                              FilterChain chain) {
//
//            HttpServletResponse res= (HttpServletResponse) response;
//            HttpServletRequest req= (HttpServletRequest) request;
//
//            try {
//                boolean contains = req.getRequestURI().contains("/login");
//
//                if (!req.getRequestURI().contains("/login") && req.getSession().getAttribute("loggedInUser")==null){
//                    response.getWriter().println(req.getRequestURI()+"if");
//                    res.sendRedirect("login");
//                }else{
//
//                    chain.doFilter(request, response);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
