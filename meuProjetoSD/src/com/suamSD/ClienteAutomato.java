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
				
			    switch ( stub.getContaUsuario( ) ) 
				{
				case 1:
					new Thread(t1).start( );
					break;

				case 2:
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
    			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getContaUsuario( ).toString( ) +"\n\n");    			
    			System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
				System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
				System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
				System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
				System.out.println("***********************************************************************\n");

        		try 
				{
        			    //Controle de execução
        			    stub.setContaUsuario( stub.getContaUsuario( ) + 1 );
        			   
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
					    {
					        do 
            		        {
            		        	aguardarVezOutroUsuarioCli1( );
					 }      
            		        while ( stub.getContaPasso() != 3);
					    }
                        */					    
					    
        			   //Execução do terceiro método
					    stub.setRegra( );
					    stub.incrementaContaPasso( );
					    
					    if( stub.getContaUsuario( ) == 2 )
					    {
					    	stub.setEstInicial ( );
						    stub.setConjuntoEstadosFinais( );
					        stub.incrementaContaPasso( );
					    }
					    
					    if( stub.getContaUsuario( ) != 2 )
					    {
					        do 
            		        {
            		        	aguardarVezOutroUsuarioCli1( );
						    } 
            		        while ( stub.getContaPasso() != 5);
					    }
					    stub.checaPalavra( );
        			//}
				}
        		catch ( RemoteException e ) 
				{
        			stub.setContaUsuario( stub.getContaUsuario( ) -1 );
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
    			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getContaUsuario( ).toString( ) +"\n\n");    			
    			System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
				System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
				System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
				System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
				System.out.println("***********************************************************************\n");
    					
            	try 
				{
            		//synchronized (stub)
            		//{
            		     stub.setContaUsuario( stub.getContaUsuario( ) + 1 );
            		     
					     // stub.setAlfabeto ( );
					     
            		    do 
            		    {
            		    	aguardarVezOutroUsuarioCli2( );
						} 
            		    while ( stub.getContaPasso() != 2);
            		    
            		     
            		    //Executando segundo método 
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
         			    
         			    
            		    stub.incrementaContaPasso( );
	     			    
            		    // stub.setRegra ( );
					     
            		    do 
            		    {
            		    	aguardarVezOutroUsuarioCli2( );
						} 
            		    while ( stub.getContaPasso() != 4);
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
					stub.setContaUsuario( stub.getContaUsuario( ) -1 );
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
