package com.suamSD.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.suamSD.CambioInterface;

public class ServiceCotacao extends UnicastRemoteObject implements CambioInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double taxa = 0.0;
	private double valor = 0.0;

	public ServiceCotacao( )throws RemoteException 
	{
	}

	public double getTaxa( )throws RemoteException 
	{
		System.out.println( "A taxa informada foi: " + this.taxa );
		return this.taxa;
	}

	public void setTaxa( double taxa ) throws RemoteException {
		//System.out.println( "Taxa informada: (SET)" + taxa );
		this.taxa = taxa;
	}
	
	public double getValor( )throws RemoteException 
	{
		System.out.println( "O valor informado foi: " + this.valor );
		return this.valor;
	}

	public void setValor( double valor ) throws RemoteException {
		//System.out.println( "O Valor informado: (SET)" + valor );
		this.valor = valor;
	}
	
	public void calculaValorTaxa(  ) throws RemoteException {
		System.out.println( "O Valor Final é: " + this.valor * this.valor );
	}
	
	
}
