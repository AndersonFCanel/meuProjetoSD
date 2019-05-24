package com.suamSD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Cliente de uma aplicação Java RMI
 *
 */
public class ClienteAutomato extends Thread 
{
	static String  ipServer;
	
	public static void main ( String[ ] args ) 
	{
         ipServer = Util.defineIPservidor( );
		
		if ( ipServer == null )
		{
			System.out.println( "IP inválido" );
			return;
		}
		
		try 
		{
			// Obtendo referência do serviço de registro
			Registry registro = LocateRegistry.getRegistry( ipServer, Util.PORTA );

			// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
			AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
			
			
				System.out.println("Iniciando cliente ...");
				
			    switch ( stub.getIdentificaUsuario( ) ) 
				{
				case 'A':
					new Thread(t1).start( );
					break;

				case 'B':
					new Thread(t2).start();
					break;

				default:
					
					break;
				}
			
			
		    
			
		}
		catch ( RemoteException e ) 
		{
			e.printStackTrace();
		} 
		catch ( NotBoundException e )
		{
			e.printStackTrace( );
		}

		
	}

	
	private static Thread t1 = new Thread( new Thread( ) ) 
	{
        public void run( )
        {
        	Thread.currentThread( ).setName( "CLIENTE_1" );       	
            try
            {
            	// Obtendo referência do serviço de registro
    			Registry registro = LocateRegistry.getRegistry( ipServer, Util.PORTA );

    			// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
    			AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
    			
    			System.out.println("\n***********************************************************************");
    			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getIdentificaUsuario( ) +"\n\n");    			
    			System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
				System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
				System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
				System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
				System.out.println("***********************************************************************\n");

        		try 
				{
        			    //Controle de execução //Próximo usuário será o B
        			    stub.setIdentificaUsuario( 'B' );
        			   
        			    //Execução do primeiro método
        			    String isValid;
        			    do
        			    {
        			    	isValid = stub.setAlfabeto( ClienteService.entrarConjuntoCaracteres_Alfabeto( ) );
        			    
        			    	if( !"OK".equals( isValid ) )
        			    		JOptionPane.showMessageDialog( null, isValid );
        			    	else 
        			    	{
        			    		JOptionPane.showMessageDialog( null, "Entrada Verificada" );
        			    		stub.incrementaContaPasso( );
        			    	}
        			    
        			    }
        			    while( !"OK".equals( isValid ) );
        			    				    
					    //Execução de um cliente apenas
					    /*if( stub.getContaUsuario( ) == 2 )
					    {
					         stub.setEstados ( );
					         stub.incrementaContaPasso( );
					    }
					    stub.incrementaContaPasso( );
					    
					    if( stub.getContaUsuario( ) != 2 )
					    { }*/
        			    
					    
        			    //Execução do terceiro método
        			    do 
           		        {
           		     	    aguardarVezOutroUsuarioCli1( );
					    }      
           		        while ( stub.getContaPasso() != 3);
        			    
        			    ClienteService.tutorialTransicao( );
        			    do
        			    {
        			    	isValid = stub.setRegra( ClienteService.entraFuncaoTransicao( ) );
        			     
        			    	if( !"OK".equals( isValid ) )
        			    		JOptionPane.showMessageDialog( null, isValid );
        			    	else 
        			    	{
        			    		JOptionPane.showMessageDialog( null, "Entrada Verificada" );
        			    		stub.incrementaContaPasso( );
        			    	}
        			    	
        			    	if ("I".equalsIgnoreCase( isValid ) )  
        					{
        			    		ClienteService.tutorialTransicao( );
        					}
        			    
        			    }
        			    while( !"OK".equals( isValid ) );

        			    
        			    //Execução do quinto método
        			    do 
        		        {
        		        	aguardarVezOutroUsuarioCli1( );
					    } 
        		        while ( stub.getContaPasso() != 5);
				    
        			    
        			    
        			    //pronto até aqui
					    
					    
					    	//stub.setEstInicial ( );
						    //stub.setConjuntoEstadosFinais( );
					        //stub.incrementaContaPasso( );
					    
					    
					   
					    stub.checaPalavra( );
        			//}
				}
        		catch ( RemoteException e ) 
				{
        			stub.setIdentificaUsuario( 'A' );
					e.printStackTrace( );
				}
                
        		
        		System.out.println( "Fim da execução do cliente_1!" );
            } 
            catch (RemoteException | NotBoundException ex) 
    		{
    			Logger.getLogger( ClienteAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
    		}
 
        }
    };
 
    
    private static Thread t2 = new Thread( new Thread( ) ) 
    {
        public void run( ) 
        {
        	Thread.currentThread( ).setName( "CLIENTE_2" );
            try
            {
            	// Obtendo referência do serviço de registro
    			Registry registro = LocateRegistry.getRegistry( ipServer, Util.PORTA );

    			// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
    			AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );

    			System.out.println("\n***********************************************************************");
    			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getIdentificaUsuario( ) +"\n\n");    			
    			System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
				System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
				System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
				System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
				System.out.println("***********************************************************************\n");
    					
            	try 
				{
            		    //Controle de execução //Próximo usuário será o C
            		     stub.setIdentificaUsuario( 'C' );
            		     
					     // stub.setAlfabeto ( );
            		   
            		    //Executando segundo método 
            		    do 
            		    {
            		    	aguardarVezOutroUsuarioCli2( );
						} 
            		    while ( stub.getContaPasso() != 2);
                    
            		    String isValid;
            		    String entrada;
            		    boolean checkEntradasAnteriores;
            		    
         			    do
         			    {
         			    	entrada = ClienteService.entraConjuntoEstado( );
         			    	isValid = stub.setEstados( entrada );
         			    	checkEntradasAnteriores = valoresAtuais( entrada );
         			    	
         			    	if( !"OK".equals( isValid ) && checkEntradasAnteriores  )
         			    		JOptionPane.showMessageDialog( null, isValid );
         			    	else 
         			    	{
         			    		JOptionPane.showMessageDialog( null, "Entrada Verificada" );
         			    		stub.incrementaContaPasso( );
         			    	}
         			    
         			    }
         			    while( !"OK".equals( isValid )  && checkEntradasAnteriores );
         			    
            		    // stub.setRegra ( );
            		    
         			    do 
            		    {
            		    	aguardarVezOutroUsuarioCli2( );
						} 
            		    while ( stub.getContaPasso() != 4);
            		    
         			    //Pronto ate aqui
         			    
         			    
         			    
					    stub.setEstInicial( );
					    stub.setConjuntoEstadosFinais( );
					    stub.incrementaContaPasso( );
					     
					     
					     //stub.setConjuntoEstadosFinais( );
					    do 
            		    {
            		    	aguardarVezOutroUsuarioCli2( );
						} 
            		    while ( stub.getContaPasso() != 6);
					     stub.checaPalavra ( );
					     stub.incrementaContaPasso( );
            		//}
				} 
				catch ( RemoteException e ) 
				{
					stub.setIdentificaUsuario( 'B' );
					e.printStackTrace( );
				} 
				
            	 System.out.println( "Fim da execução do cliente_2!" );
            } 
            catch (RemoteException | NotBoundException ex) 
    		{
    			Logger.getLogger( ClienteAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
    		}
            
           
       }
    };
    
    
    public static boolean valoresAtuais( String info ) throws RemoteException, NotBoundException
	{
		if ( info.equalsIgnoreCase( "?" ) ) 
		{
			// Obtendo referência do serviço de registro
			Registry registro = LocateRegistry.getRegistry( ipServer, Util.PORTA );

			// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
			AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
			
			JOptionPane.showInputDialog( null, stub.imprimirAutomatoCliente( ) );
			
			return false;
		}	
	    return true;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    //USUÁRIO VEZ
	private static void aguardarVezOutroUsuarioCli1( )
	{
		JOptionPane.showMessageDialog( null, "Cli1 \nAguarde o outro usuário terminar!\nAguarde 30 segundos e tente novamente!" );
	}
	
    //USUÁRIO VEZ
    private static void aguardarVezOutroUsuarioCli2( )
    {
    	JOptionPane.showMessageDialog( null, "Cli2 \nAguarde o outro usuário terminar!\nAguarde 30 segundos e tente novamente!" );
    }
}
