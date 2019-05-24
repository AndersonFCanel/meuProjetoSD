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
                
                switch ( stub.getIdentificaUsuario( ) ) 
                {
                case 'A':
                    new Thread(t1).start( );
                    break;

                case 'B':
                    new Thread(t2).start();
                    break;

                default:
                    System.out.println( "Aplicação para usar com apenas 1 ou 2 Clients!" );
                    t1.interrupt( );
                    t2.interrupt( );
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
                System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getIdentificaUsuario( ) +"\n\n");               
                System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
                System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
                System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
                System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
                System.out.println("***********************************************************************\n");

                try 
                {
                    //Controle de execução //se houver, próximo usuário será o 'B'
                    String  resposta;
                    boolean b = false;
                    
                    //Variáveis para armazenar valores devolvidos do servidor.
                    boolean verEntradasAnteriores;
                    String  entrada ;
                    String  isValid;
                    
                    do 
                    {
                        resposta = JOptionPane.showInputDialog( null,
                                "Para um cliente ENTRE 1, para dois ENTRE 2." );
                        
                        resposta = resposta.trim( );
                        
                        if ( resposta == null)
                        {
                            stub.setIdentificaUsuario( 'A' );
                        }
                        if ( "1".equals( resposta ) )
                        {
                            stub.setIdentificaUsuario( 'A' );
                        }
                        if ( "2".equals( resposta ) )
                        {
                            stub.setIdentificaUsuario( 'B' );
                        }
                    } 
                    
                    while ( b );
                        /**
                         * Execução do primeiro método
                         */
                        do
                        {
                            entrada = ClienteService.entrarConjuntoCaracteres_Alfabeto( ); 
                            isValid = stub.setAlfabeto( entrada                         );
                        
                            if( !"OK".equals( isValid ) )
                                JOptionPane.showMessageDialog( null, isValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        
                        }
                        while( !"OK".equals( isValid ) );
                                            
                        //CASO A EXECUÇÃO SEJA APENAS DE UM CLIENTE
                        /**
                         * Execução do Segundo método
                         */
                        if( stub.getIdentificaUsuario( ) =='A' )
                        {
                            do 
                            {
                                ClienteService.aguardarVezOutroUsuarioCli2( );
                            } 
                            while ( stub.getContaPasso() != 2);
                       
                            do
                            {
                                entrada               = ClienteService.entraConjuntoEstado(        );
                                isValid               = stub.setEstados( entrada                   );
                                verEntradasAnteriores = ClienteService.valoresAtuais( entrada, 'B' );
                                
                                if( !"OK".equals( isValid ) && verEntradasAnteriores  )
                                    JOptionPane.showMessageDialog( null, isValid );
                                else 
                                {
                                    JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                    stub.incrementaContaPasso( );
                                }
                            }
                            while( !"OK".equals( isValid )  && verEntradasAnteriores );
                        }
                       
                        
                        /**
                         * Execução do terceiro método
                         */
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli1( );
                        }      
                        while ( stub.getContaPasso() != 3);
                        
                        ClienteService.tutorialTransicao( );
                        do
                        {   
                            entrada               = ClienteService.entraFuncaoTransicao(              ); 
                            isValid               = stub.setRegra                      ( entrada      );
                            verEntradasAnteriores = ClienteService.valoresAtuais       ( entrada, 'B' );
                            
                            if( !"OK".equals( isValid ) && verEntradasAnteriores )
                                JOptionPane.showMessageDialog( null, isValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                            
                            if ("I".equalsIgnoreCase( entrada ) )  
                            {
                                ClienteService.tutorialTransicao( );
                            }
                        
                        }
                        while( !"OK".equals( isValid ) && verEntradasAnteriores );

                        //CASO A EXECUÇÃO SEJA APENAS DE UM CLIENTE
                        /**
                         * Execução do Quarto método
                         */
                        if( stub.getIdentificaUsuario( ) =='A' )
                        {
                            do 
                            {
                                ClienteService.aguardarVezOutroUsuarioCli2( );
                            } 
                            while ( stub.getContaPasso() != 4);
                                                   
                            do
                            {
                                entrada               = ClienteService.entraEstIN   (              );
                                isValid               = stub.setEstInicial          ( entrada      );
                                verEntradasAnteriores = ClienteService.valoresAtuais( entrada, 'B' );
                                
                                if( !"OK".equals( isValid ) && verEntradasAnteriores )
                                    JOptionPane.showMessageDialog( null, isValid );
                                else 
                                {
                                    JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                    stub.incrementaContaPasso( );
                                }
                            }
                            while( !"OK".equals( isValid ) && verEntradasAnteriores );
                        }
                        
                        /*
                         * Execução do quinto método
                         */
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli2( );
                        } 
                        while ( stub.getContaPasso() != 5);
                                               
                        do
                        {
                            entrada               = ClienteService.entraCjtEstFinal(              );
                            isValid               = stub.setConjuntoEstadosFinais  ( entrada      );
                            verEntradasAnteriores = ClienteService.valoresAtuais   ( entrada, 'B' );
                            
                            if( !"OK".equals( isValid ) && verEntradasAnteriores )
                                JOptionPane.showMessageDialog( null, isValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( isValid ) && verEntradasAnteriores );
                        
                        //CASO A EXECUÇÃO SEJA APENAS DE UM CLIENTE
                        /**
                         * Execução do Quarto método
                         */
                        if( stub.getIdentificaUsuario( ) =='A' )
                        { 
                            do 
                            {
                                 ClienteService.aguardarVezOutroUsuarioCli2( );
                            } 
                            while ( stub.getContaPasso() != 4);
                                                
                            do
                            {
                             entrada               = ClienteService.entraPalavra (              );
                             isValid               = stub.checaPalavra           ( entrada      );
                             verEntradasAnteriores = ClienteService.valoresAtuais( entrada, 'B' );
                             
                            if( !"OK".equals( isValid ) && verEntradasAnteriores )
                                 JOptionPane.showMessageDialog( null, isValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                            }
                            while( !"OK".equals( isValid ) && verEntradasAnteriores );
                       }
      
                }
                catch ( RemoteException e ) 
                {
                    stub.setIdentificaUsuario( 'A' );
                    e.printStackTrace( );
                } catch (InterruptedException e) {
					e.printStackTrace();
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
                System.out.println( "CONTADOR DE EXECUÇÃO: " + stub.getIdentificaUsuario( ) +"\n\n");               
                System.out.println( "ID: "         + Thread.currentThread( ).getId( )       );
                System.out.println( "Nome: "       + Thread.currentThread( ).getName( )     );
                System.out.println( "Prioridade: " + Thread.currentThread( ).getPriority( ) );
                System.out.println( "Estado: "     + Thread.currentThread( ).getState( )    );
                System.out.println("***********************************************************************\n");
                        
                try 
                {
                        String isValid;
                        String entrada;
                        boolean verEntradasAnteriores;
                        
                        //Controle de execução //Próximo usuário será o C (Não existe na nossa aplição)
                         stub.setIdentificaUsuario( 'C' );
                         
                        //Método executado pelo Cliente A 
                        // stub.setAlfabeto ( );
                       
                        //Executando segundo método 
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli2( );
                        } 
                        while ( stub.getContaPasso() != 2);
                   
                        do
                        {
                            entrada               = ClienteService.entraConjuntoEstado(        );
                            isValid               = stub.setEstados( entrada                   );
                            verEntradasAnteriores = ClienteService.valoresAtuais( entrada, 'B' );
                            
                            if( !"OK".equals( isValid ) && verEntradasAnteriores  )
                                JOptionPane.showMessageDialog( null, isValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( isValid )  && verEntradasAnteriores );
                        
                        //Método executado pelo Cliente A 
                        // stub.setRegra ( );
                       
                        //Executando Quarto Método
                        do 
                        {
                            ClienteService.aguardarVezOutroUsuarioCli2( );
                        } 
                        while ( stub.getContaPasso() != 4);
                                               
                        do
                        {
                            entrada               = ClienteService.entraEstIN   (              );
                            isValid               = stub.setEstInicial          ( entrada      );
                            verEntradasAnteriores = ClienteService.valoresAtuais( entrada, 'B' );
                            
                            if( !"OK".equals( isValid ) && verEntradasAnteriores )
                                JOptionPane.showMessageDialog( null, isValid );
                            else 
                            {
                                JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                                stub.incrementaContaPasso( );
                            }
                        }
                        while( !"OK".equals( isValid ) && verEntradasAnteriores );
                        
                       //Método executado pelo Cliente A 
                       //stub.setConjuntoEstadosFinais( );
                      
                       //Executando Sexto Método
                       do 
                       {
                            ClienteService.aguardarVezOutroUsuarioCli2( );
                       } 
                       while ( stub.getContaPasso() != 4);
                                           
                       do
                       {
                           entrada               = ClienteService.entraPalavra (              );
                           isValid               = stub.checaPalavra           ( entrada      );
                           verEntradasAnteriores = ClienteService.valoresAtuais( entrada, 'B' );
                           
                           if( !"OK".equals( isValid ) && verEntradasAnteriores )
                               JOptionPane.showMessageDialog( null, isValid );
                           else // implementar entrada igual a 0
                           {
                               JOptionPane.showMessageDialog( null, "Entrada Verificada" );
                               stub.incrementaContaPasso( );
                           }
                       }
                       while( !"OK".equals( isValid ) && verEntradasAnteriores );
                     
                } 
                catch ( RemoteException e ) 
                {
                    stub.setIdentificaUsuario( 'B' );
                    e.printStackTrace( );
                } 
                catch ( InterruptedException e ) 
                {
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
    

}
