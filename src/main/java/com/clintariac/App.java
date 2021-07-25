package com.clintariac;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.clintariac.components.dashboard.DashboardController;

/**
 * Interfaccia che contiene il metodo main, a partire dal quale viene avviata l'applicazione.
 * 
 * @author Cristina Zappata
 * @author Giuseppe Marino
 * 
 */

interface App {

	/**
	 * Metodo di avvio dell'applicativo. Al suo interno viene instanziato e configurato il frame
	 * principale, all'interno del quale è aggiunta la view ricavata dal Dashboard controller
	 * instanziato. Essa costituisce la view principale dellàapplicativo.
	 * 
	 * @param args
	 */
	public static void main(String... args) {

		SwingUtilities.invokeLater(() -> {

			DashboardController dashboard = new DashboardController();

			JFrame frame = new JFrame("Clintariac - Gestionale per appuntamenti");
			frame.getContentPane().add(dashboard.getView());
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
