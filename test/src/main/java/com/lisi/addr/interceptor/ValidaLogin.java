/*
 * interceptor class to verify sessions before
*/
package com.lisi.addr.interceptor;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


public class ValidaLogin implements Interceptor{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpSession session = ServletActionContext.getRequest().getSession(false);  
        if(session==null || session.getAttribute("Usuario")==null){  
        	
        	// deletar print em producao
        	System.out.println("Interceptor : ValidaLogin: \nIdentificado Session expired ou tentativa de visualizar sem Login primeiro");
        	
            return "expired";   // if no session or expired; send to it
        }  
        else{
        	
        	// deletar print em producao
        	System.out.println("Interceptor : ValidaLogin: \nIdentificado Session ja existente e liberando invocacao normal da Action");
        	
            return invocation.invoke(); // or ; just continue the stack
            
        }  
	}

}


