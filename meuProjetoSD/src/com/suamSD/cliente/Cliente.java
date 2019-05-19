package com.suamSD.cliente;

import java.rmi.Naming;

import javax.swing.JOptionPane;

import com.suamSD.AutomatoInterface;
import com.suamSD.Util;

public class Cliente {

	public static void main( String[ ] args ) 
	{

		try 
		{
			// invocando servidor
			AutomatoInterface obj = (AutomatoInterface) Naming.lookup(
					"rmi://" + Util.LOCALHOST + "/" + Util.NOMEOBJDIST);
			obj.setAlfabeto( );

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
