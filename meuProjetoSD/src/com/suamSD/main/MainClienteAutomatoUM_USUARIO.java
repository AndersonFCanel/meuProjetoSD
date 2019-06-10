package com.suamSD.main;

import java.awt.HeadlessException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.suamSD.AutomatoInterface;
import com.suamSD.Util;
import com.suamSD.gravadorArquivo.CriaArquivo;
import com.suamSD.service.ClienteService;

/**
 * Cliente aplicação Java RMI
 *
 */
public class MainClienteAutomatoUM_USUARIO extends Thread 
{
    public static String  ipServer;
    
    public static void main ( String[ ] args ) 
    {
        ipServer = Util.defineIP( );
    	
        if ( ipServer == null )
        {
            System.out.println( "IP inválido" );
            return;
        }
        
        //ClienteService.info( Util.infoAutomato( ) );
        
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
                //new Thread(t2).start( );//TESTE
                break;

            case 'B':
                //new Thread(t2).start();
                break;

            default:
                Util.interrompeThread( );
                break;
            }  
        }
        catch ( RemoteException e ) 
        {
        	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
            e.printStackTrace();
        } 
        catch ( NotBoundException e )
        {
        	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
        	e.printStackTrace( );
        }
        catch ( InterruptedException e ) 
        {
        	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
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
                
                System.out.println("\n*****************************************************");
                System.out.println( "USUÁRIO: "    + stub.getIdentificaUsuario( )           );               
                System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
                System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
                System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
                System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
                System.out.println("*****************************************************\n");

                try 
                {
                    /**
                     * Variáveis para armazenar valores devolvidos( RESPONSE ) do servidor.
                     */
                    String  entrada ;
                    String  requestIsValid;
                    stub.zeraContaPasso( );
                    
                    /**
                     * Caso no servidor tenha sido informado que a 
                     * aplicação será executada com dois clientes
                     * de forma simultânea, o próximo usuário será 
                     * identificado por 'B'.
                     */
                    if ( stub.getQtdUsuario( ) == 2 ) 
                    {
                    	stub.setIdentificaUsuario( 'B' );
					}
                                        
                    /**
                     * Execução do primeiro método  - Passo 1 - ConjuntoCaracteres_Alfabeto
                     */
                    do
                    {
                        entrada         = ClienteService.entrarConjuntoCaracteres_Alfabeto( ); 
                        requestIsValid  = stub.setAlfabeto( entrada                         );
                    
                        if( !"OK".equals( requestIsValid ) )
                            JOptionPane.showMessageDialog( null, requestIsValid );
                        else 
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                            
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getMetetodoCorrente( ) );
                    }
                    while( !"OK".equals( requestIsValid ) );
                    System.out.println("Usuário 'A' efetuou entrada do alfabeto!");
                   
                    
                    //Executando segundo método   - Passo 2
                    do
                    {
                        entrada        = ClienteService.entraConjuntoEstado(        );
                        requestIsValid = stub.setEstados( entrada                   );
                                                  
                        if( !"OK".equals( requestIsValid ) )
                            JOptionPane.showMessageDialog( null, requestIsValid );
                        else 
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                           // stub.incrementaContaPasso( );
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getMetetodoCorrente( ) );
                    }
                    while( !"OK".equals( requestIsValid ) );
                    System.out.println( "Usuário 'B' efetuou entrada de estados!" );
                   
                    
                    
                    
                    /**
                     * Execução do terceiro método  - Passo 3
                     */
                    stub.zeraContadorFuncTran( );
                    int cont1 = stub.getTamanhoCjtFuncTran( );
                    int cont2 = stub.getContadorFuncTran  ( );
                    ClienteService.tutorialTransicao( );
                   
                    do
                    {   
                        while ( cont2 < cont1 )
                        {
                   	       cont2 = stub.getContadorFuncTran  ( ) + 1;
                   	    
                   	       entrada = ClienteService.entraFuncaoTransicao( );
                    	   
                           //Entrada defaut para demonstração
                           if( "d".equals( entrada ) )
                           {
                        	   stub.setAlfabeto( "a,b,c" );
                        	   stub.setEstados( "0,1,2" );
                        	   stub.zeraContadorFuncTran( );
                        	   String[ ] deltaTeste = new String[ ] {"0,a;1", "1,b;2", "2,c;0", "", "0,b;2", "1,a;1","","","" };
                               for ( String string : deltaTeste ) 
                               {
                            	   requestIsValid  = stub.setRegra (string );
                            	   System.out.println( "PASSO CORRENTE: " + ( stub.getMetetodoCorrente( ) + 1 ) + 
                      			            "\nPosição função corrente: " + stub.getContadorFuncTran( ) + " de " +
                      			         cont1 );	
						       }
                               
                               if ( stub.getContadorFuncTran( ) == 9 ) 
                        	   {
                        		   requestIsValid = "OK";
                        		   break;
                        	   }
                           }
                          
                           requestIsValid  = stub.setRegra ( entrada );
                                                                                        
                           if( !"OK".equals( requestIsValid ) ) 
                           {
                           	cont2 = stub.getContadorFuncTran  ( );
                               JOptionPane.showMessageDialog( null, requestIsValid );
                           }
                           else 
                           {
                               JOptionPane.showMessageDialog( null, "Entrada Verificada" );                               
                           }
                           System.out.println( "PASSO CORRENTE: " + (stub.getMetetodoCorrente( ) ) + 
           			            "\nPosição função corrente: " + stub.getContadorFuncTran( ) + " de " +
           			         cont1 );
                        }
                     }
                     while( !"OK".equals( requestIsValid ) );
                    //Fim passo - 3
                    System.out.println("Usuário 'A' efetuou entrada do cjt delta!");
                   
                    
                    
                    //Executando Quarto Método  - Passo 4
                    do
                    {
                        entrada         = ClienteService.entraEstIN   (              );
                        requestIsValid = stub.setEstInicial          ( entrada      );
                        
                        if( !"OK".equals( requestIsValid ) )
                            JOptionPane.showMessageDialog( null, requestIsValid );
                        else 
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                            //stub.incrementaContaPasso( );
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getMetetodoCorrente( ) );
                    }
                    while( !"OK".equals( requestIsValid ) );
                    System.out.println("Usuário 'B' efetuou entrada de estado inicial!");
          
                    
                    /**
                     * Execução do quinto método  - Passo 5
                     */               
                    do
                    {
                        entrada         = ClienteService.entraCjtEstFinal(         );
                        requestIsValid  = stub.setConjuntoEstadosFinais  ( entrada );
                        
                        if( !"OK".equals( requestIsValid ) )
                            JOptionPane.showMessageDialog( null, requestIsValid );
                        else 
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                           // stub.incrementaContaPasso( );
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getMetetodoCorrente( ) );
                    }
                    while( !"OK".equals( requestIsValid ) );
                    System.out.println("Usuário 'A' efetuou entrada de estados finais!");
                    
                   
                    do
                    {
                        entrada = ClienteService.entraPalavra ( );
                        if ( entrada == null )
                        {
                      	  System.out.println( "Clinete encerrado!" );
                      	  break;
                        }
                        requestIsValid = stub.checaAceitacaoPalavra( entrada );
                                                  
                        if( !"OK".equals( requestIsValid ) )
                            JOptionPane.showMessageDialog( null, requestIsValid );
                        else // implementar entrada igual a 0
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                            //stub.incrementaContaPasso( );                     
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getMetetodoCorrente( ) + "\n"
                         		+ "Palavra: " + entrada + " : =>>  " + requestIsValid);
                    }
                    while( !"OK".equals( requestIsValid ) );
                    System.out.println( "Usuário 'B' entrou com palavra(s)" );
                    stub.setMetetodoCorrente( 7 );
                    
                                  
                    //método responsável pela criação do arquivo (automato.txt)
           			CriaArquivo f = new CriaArquivo( );
           			try 
           			{
           				f.criarArq( stub.imprimirAutomatoCliente( 'P' ) );
           			} 
           			catch( IOException e )
           			{
           				e.printStackTrace( );
           			}
           			
           		   ClienteService.info( stub.imprimirAutomatoCliente( 'B' ) ) ;
                    
                    
                    ClienteService.info( stub.imprimirAutomatoCliente( 'A' ) ) ;
                    
                    System.out.println( "\nENTRADAS::\n\n" + stub.imprimirAutomatoCliente( 'P' ) );
                    
                  
                }
                catch ( RemoteException e ) 
                {
                    stub.setIdentificaUsuario( 'A' );
                    JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
                    e.printStackTrace( );
                } 
                catch (InterruptedException e) 
                {
                	stub.setIdentificaUsuario( 'A' );
                	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
                	e.printStackTrace();
				}
                catch (HeadlessException e)
                {
					e.printStackTrace();
				} 
                catch (IOException e) 
                {
                  	e.printStackTrace();
				}

                System.out.println( "Fim da execução do cliente_1!" );
            } 
            catch (RemoteException | NotBoundException ex) 
            {
            	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + ex );
                Logger.getLogger( MainClienteAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
            }
        }
    };
 
    
}
