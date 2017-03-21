package com.lisi.addr.controller;

import com.opensymphony.xwork2.Action;

import com.lisi.addr.model.Usuario;

public class LoginAction extends BaseAction{

	private String login;

	private String senha;


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String login(){
		Usuario usuario = getLoginService().doLogin(getLogin(), getSenha());

		if (usuario != null) {
			// create session
			getSessionMap().put("Usuario", usuario);

			return "valid";
		}
		else {
			addActionError("INVALID LOGIN / PASSWORD");
			return "invalid";
		}

	} // end login method

	// method para testar session apenas
	public String sessionTest(){
		
		// deletar print em producao
		System.out.println("LoginAction : sessionTest: \nIniciando test de session/Interceptor que foi criada antes pelo login");
		
		Usuario usuarioLogado = (Usuario) this.getSessionMap().get("Usuario");
		System.out.println("Usuario logado: "+usuarioLogado);

		return "success";
	}


	// method para efetuar logout
	public String logout(){
		Usuario usuarioLogado = (Usuario) this.getSessionMap().get("Usuario");
		
		// deletar print em producao
		System.out.println("LoginAction : logout: \nEfetuando LOGOUT Usuario : "+usuarioLogado);
		
		this.getSessionMap().remove("Usuario");
		return Action.SUCCESS;

	}


}
