package com.suamSD.servidor;

import java.rmi.Naming;
import java.rmi.RemoteException;

import com.suamSD.CambioInterface;
import com.suamSD.Util;

public class Servidor {

	

	public static void main(String[] args) {
		// Código para startar RMI REGISTRY sem necessidade do cmd "start rmiregistry"
		// no prompt
		try {
			java.rmi.registry.LocateRegistry.createRegistry( Util.PORTA );
		} catch (RemoteException e1) {
			System.out.println("Ocorreu um erro ao executar o 'createRegistry', o erro foi :" + e1);
			e1.printStackTrace();
		}

		try {
			//ServiceTaxa servidor = new ServiceTaxa();
			CambioInterface servidor = new ServiceCotacao();
			
			Naming.rebind( Util.NOMEOBJDIST , servidor);
			
			System.out.println( "Servidor no ar" );
		
		} catch (Exception e) {
		
			e.printStackTrace( );
			System.out.println("Algo deu errado: "+e );
		
		}
	}

}
