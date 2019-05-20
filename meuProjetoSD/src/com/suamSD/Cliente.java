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
	private static final Runnable Cliente1 = new Cliente();
	private static final Runnable Cliente2 = new Cliente();

	public static void main(String[] args)
    {
		
		try {
            // Obtendo referência do serviço de registro
            Registry registro = LocateRegistry.getRegistry(Util.IPSERVIDOR, Util.PORTA);

            // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
            AutomatoInterface stub = (AutomatoInterface) registro.lookup(Util.NOMEOBJDIST);
            
            stub.setContaThreads();
            System.out.println("CONTADOR DE THREADS: " + stub.getContaThreads( ));
            int thread = stub.getContaThreads( );
            
            
            switch (thread) {
			case 1: 
				    stub.setAlfabeto 			 ( );
		            //stub.setEstados  			 ( );
		            stub.setRegra				 ( );
		            //stub.setEstInicial			 ( );
		            //stub.setConjuntoEstadosFinais( );
		            stub.checaPalavra			 ( );
		            
		            Thread  t = new Thread(Cliente1); //Cria a linha de execução
		       	    t.start();                 //Ativa a thread
		       	    t.setName( "Cliente: ==> "+ stub.getContaThreads( ));
				break;
			case 2:
				//stub.setAlfabeto 			 ( );
		        stub.setEstados  			 ( );
		        //stub.setRegra				 ( );
		        stub.setEstInicial			 ( );
		        stub.setConjuntoEstadosFinais( );
		        //stub.checaPalavra			 ( );
		        
		        Thread  t2= new Thread(Cliente2); //Cria a linha de execução
		   	   t2.start();                 //Ativa a thread
		   	    t2.setName( "Cliente: ==> "+ stub.getContaThreads( ));
		        
				

			default:
				break;
			}
            
           
            
       	    System.out.println("Fim da execução do cliente!");

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        
        //código para executar em paralelo
        System.out.println("ID: "         + Thread.currentThread( ).getId( )       );
        System.out.println("Nome: "       + Thread.currentThread( ).getName( )     );
        System.out.println("Prioridade: " + Thread.currentThread( ).getPriority( ) );
        System.out.println("Estado: "     + Thread.currentThread( ).getState( )    );
    
	
	
}
	
}
