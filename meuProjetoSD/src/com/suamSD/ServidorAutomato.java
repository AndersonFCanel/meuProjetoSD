package com.suamSD;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Classe responsável por criar uma instância 
 * do objeto serviceAutomato e registrá-la
 * em um serviço de registro de objetos distribuídos
 *
 */
public class ServidorAutomato 
{
	public static void main( String args[ ] )
    {
		/*String ipServer = Util.defineIPservidor( );
		
		if ( ipServer == null )
		{
			System.out.println( "IP inválido" );
			return;
		}*/
		
		String ipServer = Util.IPSERVIDOR;
		
		String javaHome = System.getenv("JAVA_HOME");
		System.out.println       ( "JDK: "+javaHome);
        
		try 
        {
            // Criando objeto autómato
            ServiceAutomato a = new ServiceAutomato( );
            
            //Criando contador
            //ServiceContaExecucao c =  new ServiceContaExecucao( );
            
            // Definindo o hostname do servidor
            System.setProperty( "java.rmi.server.hostname", ipServer );

            AutomatoInterface stub = (AutomatoInterface) UnicastRemoteObject.exportObject( a, 0 );
            System.out.println       ( "Objeto  ServiceAutomato Carrregado. "                   );
            
            //ContaExecucaoInterface stubContador = (ContaExecucaoInterface) UnicastRemoteObject.exportObject(c, 0 );
            //System.out.println( "Objeto ServiceContaExecucao Carrregado. " );
            
            //Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry( Util.PORTA );

            //Registrando objeto distribuído
            registro.bind( Util.NOMEOBJDIST, stub );
            //registro.bind( Util.NOMEOBJDIST+"cont", stubContador );

            int input ;
            do 
            {
            	System.out.println( "Servidor pronto!\n"                                                   );
            	System.out.println( "Se esiver executando pelo prompt pressione CTRL + C para encerrar..." );
            	
            	input = JOptionPane.showConfirmDialog(null,
        				"Servidor pronto!\n"
        				+ "Para parar aperte ok.\n\n",
        				"WARNING", JOptionPane.WARNING_MESSAGE);
                //Possíveis retornos 0=yes, 1=no, 2=cancel
            	
            	if( input == 0 )
                {
                    if ( registro != null ) 
                    {
                        UnicastRemoteObject.unexportObject( registro, true   );
                        System.out.println                ( "unexportObject" );
                    }
                   
                    return;
                }
            }
            while ( input == 2 );
            System.out.println( "Você parou o servidor" );

        } 
        catch ( RemoteException | AlreadyBoundException ex )
        {
            Logger.getLogger( ServidorAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        
        System.out.println( "Fim" );
    }

}
