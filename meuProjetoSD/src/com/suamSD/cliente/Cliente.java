package com.suamSD.cliente;

import java.rmi.Naming;

import javax.swing.JOptionPane;

import com.suamSD.CambioInterface;
import com.suamSD.Util;

public class Cliente {

	private static double resp;

	public static void main(String[] args) {

		try {
			// invocando servidor
			CambioInterface obj = (CambioInterface) Naming.lookup("rmi://" + Util.LOCALHOST + "/" + Util.NOMEOBJDIST);

			Double taxa = Double.parseDouble(JOptionPane.showInputDialog(null, "Informe a Taxa Cambial",
					"Taxa De Cambio", JOptionPane.QUESTION_MESSAGE));

			obj.setTaxa(taxa);

			JOptionPane.showMessageDialog(null, " Você digitou:\n " + obj.getTaxa());

			while (taxa != null) {
				// taxa = JOptionPane.showInputDialog("Digite uma mensagem para um servidor: ");
				// obj.setTaxa(resp);
				// JOptionPane.showMessageDialog(null, " Você digitou:\n " + obj.getTaxa());
			}
			if (taxa == null)
				System.out.println("Cliente cancelou a operação");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
