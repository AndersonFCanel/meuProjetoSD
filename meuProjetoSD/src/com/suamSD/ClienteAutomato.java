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
    public static String  ipServer;
    
    public static void main ( String[ ] args ) 
    {
        ipServer = Util.defineIP( );
    	
        if ( ipServer == null )
        {
            System.out.println( "IP inválido" );
            return;
        }
        
        ClienteService.info( );
        
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
                
                System.out.println("\n***********************************************************************");
                System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getIdentificaUsuario( ) +"\n\n");               
                System.out.println( "ID: "         + Thread.currentThread( ).getId( )              );
                System.out.println( "Nome: "       + Thread.currentThread( ).getName( )            );
                System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( )        );
                System.out.println( "Estado: "     + Thread.currentThread( ).getState( )           );
                System.out.println("***********************************************************************\n");

                try 
                {
                    //Variáveis para armazenar valores devolvidos do servidor.
                    String  entrada ;
                    String  responseIsValid;
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
                        responseIsValid = stub.setAlfabeto( entrada                         );
                    
                        if( !"OK".equals( responseIsValid ) )
                            JOptionPane.showMessageDialog( null, responseIsValid );
                        else 
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                            stub.incrementaContaPasso( );
                        }
                    
                    }
                    while( !"OK".equals( responseIsValid ) );
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
                            responseIsValid = stub.setEstados( entrada                   );
                            
                            if( !"OK".equals( responseIsValid ) )
                                JOptionPane.showMessageDialog( null, responseIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( responseIsValid ) );
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
                    				 
                            entrada         = ClienteService.entraFuncaoTransicao(              ); 
                            responseIsValid = stub.setRegra                      ( entrada      );
                                                     
                            if( !"OK".equals( responseIsValid ) )
                                JOptionPane.showMessageDialog( null, responseIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                          }
                      }
                      while( !"OK".equals( responseIsValid ) );
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
                            entrada         = ClienteService.entraEstIN   (              );
                            responseIsValid = stub.setEstInicial          ( entrada      );
                            
                            if( !"OK".equals( responseIsValid ) )
                                JOptionPane.showMessageDialog( null, responseIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( responseIsValid ) );
                    }
                    //Fim - passo 4
                    
                    /**
                     * Execução do quinto método  - Passo 5
                     */               
                    do
                    {
                        entrada         = ClienteService.entraCjtEstFinal(         );
                        responseIsValid = stub.setConjuntoEstadosFinais  ( entrada );
                        
                        if( !"OK".equals( responseIsValid ) )
                            JOptionPane.showMessageDialog( null, responseIsValid );
                        else 
                        {
                            JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                            stub.incrementaContaPasso( );
                        }
                    }
                    while( !"OK".equals( responseIsValid ) );
                    
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
                              entrada         = ClienteService.entraPalavra (         );
                              responseIsValid = stub.checaPalavra           ( entrada );
                              
                             if( !"OK".equals( responseIsValid ) )
                                  JOptionPane.showMessageDialog( null, responseIsValid );
                             else 
                             {
                                 JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                 stub.incrementaContaPasso( );
                             }

                        }
                        while( !"OK".equals( responseIsValid ) );
                    }
                    
                    if( stub.getIdentificaUsuario( ) =='A' )
                    {
                        ClienteService.valoresAtuais( "?", '@' );
                    }
                    else
                    {
                    	 ClienteService.valoresAtuais( "?", 'A' );
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
                        String responseIsValid;
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
                            responseIsValid               = stub.setEstados( entrada                   );
                                                      
                            if( !"OK".equals( responseIsValid ) )
                                JOptionPane.showMessageDialog( null, responseIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( responseIsValid ) );
                        
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
                            entrada               = ClienteService.entraEstIN   (              );
                            responseIsValid               = stub.setEstInicial          ( entrada      );
                            
                            if( !"OK".equals( responseIsValid ) )
                                JOptionPane.showMessageDialog( null, responseIsValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( responseIsValid ) );
                        
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
                           entrada               = ClienteService.entraPalavra (              );
                           responseIsValid               = stub.checaPalavra           ( entrada      );
                                                     
                           if( !"OK".equals( responseIsValid ) )
                               JOptionPane.showMessageDialog( null, responseIsValid );
                           else // implementar entrada igual a 0
                           {
                               JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                               stub.incrementaContaPasso( );                     
                           }
                       }
                       while( !"OK".equals( responseIsValid ) );
     
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
                Logger.getLogger( ClienteAutomato.class.getName( ) ).log( Level.SEVERE, null, ex );
            }
       }
    };
}
