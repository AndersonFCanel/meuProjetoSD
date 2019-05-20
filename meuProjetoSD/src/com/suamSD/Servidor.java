package com.suamSD;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por criar uma instância do objeto Contador e registrá-la
 * em um serviço de registro de objetos distribuídos
 *
 */
public class Servidor 
{
	public static void main( String args[ ] )
    {
		String ipServer = Util.defineIPservidor( );
		
        try {
            // Criando objeto autómato
            ServiceAutomato a = new ServiceAutomato( );

            // Definindo o hostname do servidor
            System.setProperty( "java.rmi.server.hostname", ipServer );

            AutomatoInterface stub = (AutomatoInterface) UnicastRemoteObject.exportObject( a, 0 );

            // Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry( Util.PORTA );

            // Registrando objeto distribuído
            registro.bind( Util.NOMEOBJDIST, stub );

            System.out.println( "Servidor pronto!\n" );
           // System.out.println("Pressione CTRL + C para encerrar...");
            
        } 
        catch ( RemoteException | AlreadyBoundException ex )
        {
            Logger.getLogger( Servidor.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
    }

}
