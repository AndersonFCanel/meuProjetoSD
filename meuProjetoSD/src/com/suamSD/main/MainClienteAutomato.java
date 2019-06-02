package com.suamSD.main;

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
public class MainClienteAutomato extends Thread 
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
        
        ClienteService.info( Util.infoAutomato( ) );
        
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
            	JOptionPane.showMessageDialog( null, "Aplicação funciona apenas 1 ou 2 Clientes!" );
     
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
                     * Execução do primeiro método  - Passo 1
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
                            stub.incrementaContaPasso( );
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                    }
                    while( !"OK".equals( requestIsValid ) );
                    //Fim - passo 3 
                    
                    if( !( stub.getIdentificaUsuario( ) =='A' ) )
                    {
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli( "A" );
                        } 
                        while ( stub.getContaPasso( ) <= 2  );
                    }
                    
                    //CASO A EXECUÇÃO SEJA APENAS DE UM CLIENTE
                    /**
                     * Execução do Segundo método  - Passo 2
                     */
                    if( stub.getIdentificaUsuario( ) =='A' )
                    {
                        do
                        {
                            entrada         = ClienteService.entraConjuntoEstado(        );
                            requestIsValid = stub.setEstados( entrada                   );
                            
                            if( !"OK".equals( requestIsValid ) )
                                JOptionPane.showMessageDialog( null, requestIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                            System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                        }
                        while( !"OK".equals( requestIsValid ) );
                    }
                   //Fim - passo 2
                    
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
                    				 
                            entrada         = ClienteService.entraFuncaoTransicao(         ); 
                            requestIsValid  = stub.setRegra                      ( entrada );
                                                                                           
                            if( !"OK".equals( requestIsValid ) ) 
                            {
                            	cont2 = stub.getContadorFuncTran  ( );
                                JOptionPane.showMessageDialog( null, requestIsValid );
                            }
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                            System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) + 
           			             "\nPosição função corrente: " + stub.getContadorFuncTran( ) + " de " +
           			          cont1 );
                          }
                      }
                      while( !"OK".equals( requestIsValid ) );
                    //Fim passo - 3
                    
                     
                    if( !( stub.getIdentificaUsuario( ) =='A' ) )
                    {
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli( "A" );
                        } 
                        while ( stub.getContaPasso( ) <= 4  );
                    }
                    
                    //CASO A EXECUÇÃO SEJA APENAS DE UM CLIENTE
                    /**
                     * Execução do Quarto método  - Passo 4
                     */
                    if( stub.getIdentificaUsuario( ) =='A' )
                    {                     
                        do
                        {                    
                            entrada         = ClienteService.entraEstIN(         );
                            requestIsValid  = stub.setEstInicial       ( entrada );
                            
                            if( !"OK".equals( requestIsValid ) )
                                JOptionPane.showMessageDialog( null, requestIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                            System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                        }
                        while( !"OK".equals( requestIsValid ) );
                    }
                    //Fim - passo 4
                    
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
                            stub.incrementaContaPasso( );
                        }
                        System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                    }
                    while( !"OK".equals( requestIsValid ) );
                    
                    
                    if( !( stub.getIdentificaUsuario( ) =='A' ) )
                    {
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli( "A" );
                        } 
                        while (  stub.getContaPasso( ) <= 6  );
                    }
                    
                    //CASO A EXECUÇÃO SEJA APENAS DE UM CLIENTE
                    /**
                     * Execução do Quarto método  - Passo 6
                     */
                    if( stub.getIdentificaUsuario( ) =='A' )
                    {                    
                        do
                        {
                              entrada = ClienteService.entraPalavra (  );
                              
                              if ( entrada == null)
                              {
                            	  System.out.println( "Cliente encerrado!" );
                            	  break;
                              }
                            	  requestIsValid = stub.checaAceitacaoPalavra( entrada );
                              
                             if( !"OK".equals( requestIsValid ) )
                                  JOptionPane.showMessageDialog( null, requestIsValid );
                             else 
                             {
                                 JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                 stub.incrementaContaPasso( );
                             }
                             System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                             
                        }
                        while( !"OK".equals( requestIsValid ) );
                    }
                    
                    if( stub.getIdentificaUsuario( ) =='A' )
                    {
                        //ClienteService.valoresAtuais( "?", '@' );
                    	ClienteService.info( stub.imprimirAutomatoCliente( 'P' ) ) ;
                    }
                    else
                    {
                    	ClienteService.info( stub.imprimirAutomatoCliente( 'A' ) ) ;
                    	 //ClienteService.valoresAtuais( "?", 'A' );
                    }
                    
                    System.out.println( "\nENTRADAS::\n\n" + stub.imprimirAutomatoCliente( 'P' ) );
                    
                    //método responsável pela criação do arquivo (automato.txt)
           			CriaArquivo f = new CriaArquivo( );
           			try 
           			{
           				f.criarArq( stub.imprimirAutomatoCliente( 'P' ) );
           			} 
           			catch ( IOException e )
           			{
           				e.printStackTrace();
           			}
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

                System.out.println( "Fim da execução do cliente_1!" );
            } 
            catch (RemoteException | NotBoundException ex) 
            {
            	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + ex );
                Logger.getLogger( MainClienteAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
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

                System.out.println("\n*****************************************************");
                System.out.println( "USUÁRIO: "    + stub.getIdentificaUsuario( )           );               
                System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
                System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
                System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
                System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
                System.out.println("*****************************************************\n");
                        
                try 
                {
                        String requestIsValid;
                        String entrada;
                                               
                        //Controle de execução //Próximo usuário será o C (Não existe na nossa aplição)
                        //É setado um usuário inválido para impedir que o 2 cliente seja executado de forma duplicada.
                        stub.setIdentificaUsuario( 'C' );
                         
                        //Método executado pelo Cliente A 
                        // stub.setAlfabeto ( );  - Passo 1
                       
                         do 
                         {
                             ClienteService.aguardarVezOutroUsuarioCli( "B" );
                         } 
                         while ( stub.getContaPasso( ) <= 1);
                   
                        //Executando segundo método   - Passo 2
                        do
                        {
                            entrada               = ClienteService.entraConjuntoEstado(        );
                            requestIsValid               = stub.setEstados( entrada                   );
                                                      
                            if( !"OK".equals( requestIsValid ) )
                                JOptionPane.showMessageDialog( null, requestIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                            System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                        }
                        while( !"OK".equals( requestIsValid ) );
                        
                        //Método executado pelo Cliente A 
                        // stub.setRegra ( );  - Passo 3
                     
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli( "B" );
                        } 
                        while ( stub.getContaPasso( ) <= 3  );
                           
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
                                stub.incrementaContaPasso( );
                            }
                            System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                        }
                        while( !"OK".equals( requestIsValid ) );
                        
                       //Método executado pelo Cliente A 
                       //stub.setConjuntoEstadosFinais( );  - Passo 5
                      
                       //Executando Sexto Método  - Passo 6
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli( "B" );
                        } 
                        while ( stub.getContaPasso( ) <= 5  );
                                           
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
                               stub.incrementaContaPasso( );                     
                           }
                           System.out.println( "PASSO CORRENTE: " + stub.getContaPasso( ) );
                       }
                       while( !"OK".equals( requestIsValid ) );
                                     
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
               } 
                catch ( RemoteException e ) 
                {
                	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
                    stub.setIdentificaUsuario( 'B' );
                    e.printStackTrace( );
                } 
                catch ( InterruptedException e ) 
                {
                	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + e );
                	stub.setIdentificaUsuario( 'B' );
					e.printStackTrace( );
				} 
                
                 System.out.println( "Fim da execução do cliente_2!" );
            } 
            catch (RemoteException | NotBoundException ex) 
            {
            	JOptionPane.showMessageDialog( null, "Algo não saiu como o esperado: \n " + ex );
                Logger.getLogger( MainClienteAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
            }
       }
    };
}
