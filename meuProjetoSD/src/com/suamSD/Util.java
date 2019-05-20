package com.suamSD;

import javax.swing.JOptionPane;

public class Util {

	/*
	 * Constantes que indicam onde est� sendo executado o servi�o de registro, qual
	 * porta e qual o nome do objeto distribu�do
	 */
	public static final String LOCALHOST   = "localhost";
	public static final String IPSERVIDOR  = "127.0.0.1";
	public static final int    PORTA       =  1099;
	public static final String NOMEOBJDIST = "MeuAutomato";
	
	private static String defineIPservidor( ) 
	{		
		String ip = JOptionPane.showInputDialog(null,
					"Entre com o IP do servidor:\n ");
			
		return ip;
	}
	
}
