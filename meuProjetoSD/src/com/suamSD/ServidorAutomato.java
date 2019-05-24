package com.suamSD;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
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
		
		String javaHome = System.getenv( "JAVA_HOME" );
		
		System.out.println( "Versão do JDK local: " + javaHome );
        
		try 
        {
            // Criando objeto autômato
            AutomatoService automatoRemoto = new AutomatoService( );           
                  
            // Definindo o hostname do servidor
            System.setProperty( "java.rmi.server.hostname", ipServer );

            //Exportando objeto remoto autômato 
            AutomatoInterface stub = (AutomatoInterface) UnicastRemoteObject.exportObject( automatoRemoto, 0 );
            System.out.println       ( "Objeto  ServiceAutomato Carrregado. "                   );
            
            //Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry( Util.PORTA );

            //Registrando objeto distribuído
            registro.bind( Util.NOMEOBJDIST, stub );
   

            int input ;
            do 
            {
            	System.out.println( "Servidor pronto!\n"                                                   );
            	System.out.println( "Se estiver executando pelo prompt pressione CTRL + C para encerrar..." );
            	
            	do
            	{
            		input = JOptionPane.showConfirmDialog(null,
            				"Servidor pronto!\n"
            				+ "Para parar o servidor pressioneCANCELAR.\n\n",
            				"WARNING", JOptionPane.WARNING_MESSAGE);
                    //Possíveis retornos 0=yes, 1=no, 2=cancel
                	
                	if( input == 2 )
                    {
                        if ( registro != null ) 
                        {
                            UnicastRemoteObject.unexportObject( registro, true   );
                            System.out.println                ( "unexportObject" );
                            registro.unbind( Util.NOMEOBJDIST ) ;
                        }
                       
                        return;
                    }
            	}
            	while( input == 0 );

            }
            while ( input == 2 );
            System.out.println( "Você parou o servidor" );

        } 
        catch ( RemoteException ex )
        {
            Logger.getLogger( ServidorAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
        } 
		catch (NotBoundException e) 
		{
			e.printStackTrace();
		}
		catch ( AlreadyBoundException ex ) 
		{
			ex.printStackTrace();
		}
        
        System.out.println( "Fim" );
    }

}
