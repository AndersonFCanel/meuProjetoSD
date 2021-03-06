package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutomatoInterface extends Remote 
{                                                          
    public String    setAlfabeto           ( String s )    throws RemoteException;
                                                           
    public String    setEstados            ( String s )    throws RemoteException;
                                                           
    public String    setRegra              ( String s )    throws RemoteException;
                                                           
    public String    setEstInicial         ( String s )    throws RemoteException;
                                                           
    public String setConjuntoEstadosFinais ( String s )    throws RemoteException;
    
    public String    checaAceitacaoPalavra ( String s )    throws RemoteException;
    
    public String  imprimirAutomatoCliente ( Character c ) throws RemoteException;
    
    /************************
     * Métodos para controle                  
     ************************/
    
    /**
     * Identificação do usuário corrente
     * @param i
     * @throws RemoteException
     */
    public void      setIdentificaUsuario  ( Character i ) throws RemoteException;

    /**
     * Identificação do usuário corrente
     * @param i
     * @throws RemoteException
     */
    public Character getIdentificaUsuario  ( )throws RemoteException; 
                                       
    /**
     * Identificação da evolução da execução do programa, a cada método executado deve ocorrer um incremento no controle.
     * @return
     * @throws RemoteException
     */
    public int   getMetetodoCorrente      ( )throws RemoteException; 
            
    /**
     * Identificação da evolução da execução do programa, a cada método executado deve ocorrer um incremento no controle.
     * @return
     * @throws RemoteException
     */
    public void  setMetetodoCorrente      ( int metodo )throws RemoteException;
    
    /**
	 * Zera contador para uma nova execução
	 * @throws RemoteException
	 */
    public void zeraContaPasso             ( ) throws RemoteException;
    
    /**
    * Identificação da evolução da inserção de dados no conjunto delta
    * @return
    */
    public int getContadorFuncTran         ( )throws RemoteException; 
    
    /**
     * Identificação da evolução da inserção de dados no conjunto delta
     * @return
     */
	public int getTamanhoCjtFuncTran       ( )throws RemoteException; 
	
	/**
	 * Zera contador para uma nova execução
	 * @throws RemoteException
	 */
	public void zeraContadorFuncTran       ( )throws RemoteException; 

    /**
     * Definição de quantidade de usuários no sistenma
     * @param i
     * @throws RemoteException
     */
    public void      setQtdUsuario         ( int i ) throws RemoteException;

    /**
     * Definição de quantidade de usuários no sistenma
     * @param i
     * @throws RemoteException
     */
    public int       getQtdUsuario  ( )throws RemoteException; 
	                                 
}
