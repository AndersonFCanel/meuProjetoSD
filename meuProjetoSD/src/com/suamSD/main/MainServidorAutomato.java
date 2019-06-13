package com.suamSD.main;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.suamSD.AutomatoInterface;
import com.suamSD.Util;
import com.suamSD.service.AutomatoImplementsService;

/**
 * Classe responsável por criar uma instância 
 * do objeto serviceAutomato e registrá-la
 * em um serviço de registro de objetos distribuídos
 *
 */
public class MainServidorAutomato 
{
    public static void main( String args[ ] ) 
    		throws InterruptedException
    {
    	/*
    	 try 
    	{
			JOptionPane.showMessageDialog( null,  "O ip local é: " + InetAddress.getLocalHost( ).getHostAddress( ) + "\n"
			                		+ "Para atribui-lo basta digitar no input a seguir." );
		}
    	catch (HeadlessException | UnknownHostException e1) 
    	{
			e1.printStackTrace();
		}
		*/
    	
        String ipServer = Util.defineIP( );
        
        if ( ipServer == null )
        {
            System.out.println( "IP inválido" );
            Util.interrompeThread( );
        }
      
        String javaHome = System.getenv( "JAVA_HOME" );
      
        System.out.println( "Versão do JDK local: " + javaHome );
        
        try 
        {
            // Criando objeto autômato
            AutomatoImplementsService automatoRemoto = new AutomatoImplementsService( );           
            System.out.println( "Criada a instancia de automatoRemoto." );      
            
            // Definindo o hostname do servidor
            System.setProperty( "java.rmi.server.hostname", ipServer );
            System.out.println("System.setProperty: "  +"java.rmi.server.hostname" + ipServer);
            
            //Exportando objeto remoto autômato 
            AutomatoInterface stubAutomato = (AutomatoInterface) UnicastRemoteObject.exportObject( automatoRemoto, 0 );
            System.out.println               ( "Objeto  stubAutomato exportado. "                                    );
            
            //Criando serviço de registro
			//Código para startar RMI REGISTRY sem necessidade do cmd "start mriregistry" no prompt	
            Registry registro = LocateRegistry.createRegistry( Util.PORTA         );
            System.out.println( "LocateRegistry.createRegistry: " + Util.PORTA );

            //Registrando objeto distribuído
            registro.bind( Util.NOMEOBJDIST, stubAutomato );
            System.out.println("registro.bind: "+ Util.NOMEOBJDIST + " stubAutomato" );
   
           // boolean b = true;
            /*do 
            {
            	//Definindo a quantidade de usuários que irão utilizar o sistema.
                String[ ] options = new String[ ] {"1- Usuário", "2 - Usuários", "Cancelar" };
                
                Integer response = JOptionPane.showOptionDialog( null, "Selecione a quantidade de usuários:", "Configurando Usuários",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[ 0  ] );
                // response == 0 para "1";  1 para "2"; 2 para Escape/Cancel.
                

                if ( response == 2 ) 
                {
                	UnicastRemoteObject.unexportObject( automatoRemoto, true );
                    System.out.println                ( "unexportObject"     );
                    registro.unbind                   ( Util.NOMEOBJDIST     );
               
                    System.out.println("FINALIZANDO.....");
                    
                	Util.interrompeThread( );
                }
            
                if ( response == 0 ) 
                {
                 	b = false;
                	
                    stubAutomato.setIdentificaUsuario( 'A' );
                	stubAutomato.setQtdUsuario( 1 );
                	
                	System.out.println( "\nOperando com 1 cliente apenas.\n" );
                }
                
                if ( response == 1 ) 
            	{
            	    b = false;
                	
                    stubAutomato.setIdentificaUsuario( 'A' );
                    stubAutomato.setQtdUsuario( 2 );

                	System.out.println( "\nOperando com 2 clientes.\n" );
            	}
                              
			} while ( b );
			*/
            
            // Setando dois usuarios estaticamente
            stubAutomato.setIdentificaUsuario( 'A' );
            stubAutomato.setQtdUsuario( 2 );
            
            
            int input ;
            do 
            {
                System.out.println( "Servidor pronto!\n"                                                   );
                System.out.println( "Se estiver executando pelo prompt pressione CTRL + C para encerrar..." );
                
                do
                {
                    input = JOptionPane.showConfirmDialog(null,
                            "Servidor pronto!\n"
                            + "Para parar o servidor pressione CANCELAR.\n\n",
                            "WARNING", JOptionPane.WARNING_MESSAGE);
                    //Possíveis retornos 0=yes, 1=no, 2=cancel
                    
                    if( input == 2 )
                    {
                    	//PARANDO O SERVIDOR, EVITANDO PROCESSO PRESO
                        if ( registro != null ) 
                        {
                            UnicastRemoteObject.unexportObject( automatoRemoto, true );
                            System.out.println                ( "unexportObject"     );
                            registro.unbind                   ( Util.NOMEOBJDIST     ) ;
                       
                            System.out.println("FINALIZANDO.....");
                        }
                        Util.interrompeThread( );
                        //return;
                    }
                }
                while( input == 0 );

            }
            while ( input == 2 );
            System.out.println( "Você parou o servidor" );

        } 
        catch ( RemoteException e )
        {
            Logger.getLogger( MainServidorAutomato.class.getName( ) ).log( Level.SEVERE, null, e );
        } 
        catch (NotBoundException e) 
        {
            e.printStackTrace( );
        }
        catch ( AlreadyBoundException e ) 
        {
            e.printStackTrace( );
        } 
        catch (InterruptedException e) 
        {
			e.printStackTrace( );
		}
        
        System.out.println( "Fim" );
    }

}
