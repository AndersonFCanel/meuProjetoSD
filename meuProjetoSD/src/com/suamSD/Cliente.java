package com.suamSD;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente de uma aplicação Java RMI
 *
 */
public class Cliente extends Thread 
{
	//private static final Runnable Cliente1 = new Cliente( );
	//private static final Runnable Cliente2 = new Cliente( );

	static String  ipServer;
	//static Integer thread = 1;
	
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
			//ContaExecucaoInterface stubCont = (ContaExecucaoInterface) registro.lookup( Util.NOMEOBJDIST+"cont" );

			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getContaExecucao( ) );
			
			//thread  ;
			
		    switch ( stub.getContaExecucao( ) ) 
			{
			case 1:
				stub.setContaThreads( stub.getContaExecucao( ) + 1 );
				new Thread(t1).start();
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

		System.out.println( "Fim da execução do cliente!" );
	}

	
	private static Runnable t1 = new Runnable( )
	{
        public void run( )
        {
            try
            {
            	// Obtendo referência do serviço de registro
    			Registry registro = LocateRegistry.getRegistry( ipServer, Util.PORTA );

    			// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
    			AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
    			//ContaExecucaoInterface stubCont = (ContaExecucaoInterface) registro.lookup( Util.NOMEOBJDIST +"cont");
    			
    			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getContaExecucao( ).toString( ) );

        		try 
				{
					stub.setAlfabeto( );
					// stub.setEstados ( );
					stub.setRegra( );
					// stub.setEstInicial ( );
					// stub.setConjuntoEstadosFinais( );
					stub.checaPalavra( );
				}
        		catch (RemoteException e) 
				{
					e.printStackTrace( );
				}
				// código para executar em paralelo
				System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
				System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
				System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
				System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
            } 
            catch (RemoteException | NotBoundException ex) 
    		{
    			Logger.getLogger( Cliente.class.getName( ) ).log( Level.SEVERE, null, ex );
    		}
 
        }
    };
 
    private static Runnable t2 = new Runnable( ) 
    {
        public void run( ) 
        {
            try
            {
            	// Obtendo referência do serviço de registro
    			Registry registro = LocateRegistry.getRegistry( ipServer, Util.PORTA );

    			// Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
    			AutomatoInterface stub = (AutomatoInterface) registro.lookup( Util.NOMEOBJDIST );
  			    //ContaExecucaoInterface stubCont = (ContaExecucaoInterface) registro.lookup( Util.NOMEOBJDIST +"cont");
    			
    			System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getContaExecucao( ) );
    		    //thread = stub.getContaExecucao( ) + 1;
    		
            	try 
				{
					// stub.setAlfabeto ( );
					stub.setEstados( );
					// stub.setRegra ( );
					stub.setEstInicial( );
					stub.setConjuntoEstadosFinais( );
					// stub.checaPalavra ( );
				} 
				catch (RemoteException e) 
				{
					e.printStackTrace( );
				}
				
				// código para executar em paralelo
				System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
				System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
				System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
				System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
            } 
            catch (RemoteException | NotBoundException ex) 
    		{
    			Logger.getLogger( Cliente.class.getName( ) ).log( Level.SEVERE, null, ex );
    		}
       }
    };
}
