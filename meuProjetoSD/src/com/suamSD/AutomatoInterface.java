package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutomatoInterface extends Remote 
{                                                         
    public String    setAlfabeto          ( String s )    throws RemoteException;
                                                          
    public String    setEstados           ( String s )    throws RemoteException;
                                                          
    public String    setRegra             ( String s )    throws RemoteException;
                                                          
    public String    setEstInicial        ( String s )    throws RemoteException;
                                                          
    public String setConjuntoEstadosFinais( String s )    throws RemoteException;
    //Métodos de controle
    public void      setIdentificaUsuario ( Character i ) throws RemoteException;
                                                          
    public Character getIdentificaUsuario ( )             throws RemoteException; 
                                                          
    public Integer   getContaPasso        ( )             throws RemoteException; 
                                                          
    public void      incrementaContaPasso ( )             throws RemoteException;
    
    public String    checaPalavra         ( String s )    throws RemoteException;
    
    
    //Pronto até aqui    
  
    
    //Implementar lógica no servidor
    public String  imprimirAutomatoCliente( Character c) throws RemoteException;
    
     
    
    
}
