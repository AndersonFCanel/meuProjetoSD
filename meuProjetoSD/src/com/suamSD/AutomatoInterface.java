package com.suamSD;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

public interface AutomatoInterface extends Remote 
{
	public void setAlfabeto     ( )throws RemoteException;
     
    public void setEstados      ( )throws RemoteException;
     
    public void setRegra        ( )throws RemoteException;
    
    public void setEstIni       ( )throws RemoteException;
    
    public void setEstadosFinais( )throws RemoteException;

    public void checaPalavra    ( )throws RemoteException;

}