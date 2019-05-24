package com.suamSD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
					return "";
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
					return "";
				}
				
			} 
			while ( b );
			
		return estados;
		}
	
		// ENTRADA DA FUNÇÃO DE TRANSIÇAO
		public static String entraFuncaoTransicao( )
		{
			String delta;
			boolean b = false;
			
			do {
				delta = JOptionPane.showInputDialog( null,
						"\nEntre com as transiçãos de estado (δ: Q × Σ → Q):\n" + "\nPara ver o tutorial  novamente : 'i'"
								+ "\nPara sair : 's'\n" 
								//+ "\nVer entradas anteriores 'a'" + "\n "
								+ "Para checar entradas anteriores digite ?\n\n" );
				
				if ( delta == null)
				{
					b = true;
				}
			} 
			while ( b );
			
			return delta;
		}
		
		// ENTRADA DO ESTADO INICIAL
		public static String entraEstIN( ) throws RemoteException, NotBoundException
		{
			boolean validador = false;
			String  estadoInicial; 
			
			do 
			{
				estadoInicial = JOptionPane.showInputDialog( null, "ESTADO INICIAL:\nEntre com o estado inicial q0: " );


				if ( estadoInicial == null )
				{
					validador = true;
				}
				else
				{ 
					if ( estadoInicial.equalsIgnoreCase( "I" ) ) 
					{
						// Obtendo referência do serviço de registro
						Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

						// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
						AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
						
						Character k = 'k';
						
						stub.imprimirAutomatoCliente( k );
					}
				}
				
				
				
			} 
			while ( validador );

			return estadoInicial;
		}
		
		
		
		
		
	    
	    // TUTORIAL DE ENTRADA PARA FUNȿO DE TRANSIÇÃO
		public static void tutorialTransicao( ) 
		{
			JOptionPane.showMessageDialog( null,
					"       * Conjunto de regras de transição (Regra de Produção), funciona da seguinte forma: *\n"
							+ "         {* # ESTADO (LADO ESQUERDO), CONSOME (CENTRO); VAI PARA ESTADO (LADO DIREITO)# *}"
							+ "\n\nATENÇÃO AOS PASSOS PARA ENTRADA DA FUNÇÃO DE TRANSIÇÃO DE ESTADOS\n"
							+ "PASSO 1: Primeiro entre com o estado inicial - EX: q0\n"
							+ "PASSO 2: DIGITE UMA VIRGULA \",\"\n"
							+ "PASSO 3: Entre com o caracter a ser consumido pelo estado inicial - EX: a\n"
							+ "PASSO 4: DIGITE PONTO E VIRGULA. \";\"\n"
							+ "PASSO 5: Entre com o estado de destino - EX: q1\n" + "PASSO 6: APERTE ENTER\n"
							+ "A entrada pode ser verificada com a inserção da letra i + enter\n"
							+ "e a mesma deve estar da forma do exemplo abaixo:\n" + "EX: q0,a;q1",
					"WARNING", JOptionPane.WARNING_MESSAGE );
		}
		
		//Pegando valores atuais no servidor
	    public static boolean valoresAtuais( String info, Character user ) throws RemoteException, NotBoundException
		{
			if ( info.equalsIgnoreCase( "?" ) ) 
			{
				// Obtendo referência do serviço de registro
				Registry registro = LocateRegistry.getRegistry( ClienteAutomato.ipServer, Util.PORTA );

				// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
				AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
				
				JOptionPane.showInputDialog( null, stub.imprimirAutomatoCliente( user ) );
				
				return false;
			}	
		    return true;
		}
	    
	    //USUÁRIO VEZ
		static void aguardarVezOutroUsuarioCli1( )
		{
			JOptionPane.showMessageDialog( null, "Cliente A \nAguarde o outro usuário terminar!\nAguarde 30 segundos e tente novamente!" );
		}
		
	    //USUÁRIO VEZ
	    static void aguardarVezOutroUsuarioCli2( )
	    {
	    	JOptionPane.showMessageDialog( null, "Cliente B \nAguarde o outro usuário terminar!\nAguarde 30 segundos e tente novamente!" );
	    }
	    	
}
