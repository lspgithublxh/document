/*
 * Classe Base apra todas as Actions. 
 * 
 * Possue sistema de persistencia para as actions compartilharem objetos entre sis
 * e manter persistencia de informacoes sem ser no banco de dados.
 * 
*/
package com.lisi.addr.controller;

import java.util.Map;

import javax.inject.Inject;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.lisi.addr.business.LoginService;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware{

	// I put here my services bean classes and inject with CDI 
	@Inject
	private LoginService loginService;
	
	/**
	 *  Serializada
	 */
	private static final long serialVersionUID = 1L;

	static { // LOAD BLOCK
	} // LOAD BLOCK 

	private SessionMap<String,Object> sessionMap;
	
	
	/* method to get sessions
	*/
	@Override
	public void setSession(Map<String, Object> session) {
	    sessionMap = (SessionMap) session; 
	}
	
	public SessionMap getSessionMap(){
		return this.sessionMap;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	
	
	
}
