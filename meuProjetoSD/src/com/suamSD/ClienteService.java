package com.suamSD;

import javax.swing.JOptionPane;

public class ClienteService
{

	/**
	 * 
	 * @return
	 */
	 public static String entrarConjuntoCaracteres_Alfabeto( ) 
		{		
			String alfabeto;
			boolean b = false;
			do 
			{
				alfabeto = JOptionPane.showInputDialog( null,
						"Entre com o alfabeto Σ:\nCada caracter deve ser separado por virgula, "
								+ "sem espaço.\nEX: a,b,c,d,e ...\n"
								+ "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
								+ "\nTamanho permitido:\n" 
								+ "       Mínimo = 1(um)   elemento. \n"
								+ "       Máximo = 3(três) elementos.\n" );
				
				if ( alfabeto == null)
				{
					b = true;
				}
			 
			} 
			while ( b );
			
			return alfabeto;
		}
	 
	 
	    public static String entraConjuntoEstado( ) 
		{
			String  estados;
			boolean b = false;
			
			do {
				estados  = JOptionPane.showInputDialog( null,
						"ATENÇÃO AO MODELO DE INSERÇÃO NO CONJUNTO DE ESTADOS\nCada estado deve ser "
								+ "separado por virgula, sem espaço.\n" 
								+ "EX: A,B,C ... e1,e2,e3...\n"
								+ "Lembre-se, tratando de conjunto não são permitidos elementos duplicados."
								+ "\nTamanho permitido:\n"
								+ "       Mínimo = 1(um)   elemento. \n"
								+ "       Máximo = 3(três) elementos.\n"
								+ "Para checar entradas anteriores digite '?'.\n\n" );
				if ( estados == null)
				{
					b = true;
				}
				
			} 
			while ( b );
			
		return estados;
		}
	 
	 
	 
	    
	
	
}
