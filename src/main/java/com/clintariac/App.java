package com.clintariac;

import javax.swing.JFrame;
import com.clintariac.components.dashboard.DashboardController;

/**
 * Interfaccia che contiene il metodo main, a partire dal quale viene avviata l'applicazione.
 * 
 * @author Cristina Zappata
 * @author Giuseppe Marino
 * 
 */

/**
 * Returns a sequential {@code Stream} with this collection as its source.
 *
 * <p>
 * This method should be overridden when the {@link #spliterator()} method cannot return a
 * spliterator that is {@code IMMUTABLE}, {@code CONCURRENT}, or <em>late-binding</em>. (See
 * {@link #spliterator()} for details.)
 *
 * @implSpec The default implementation creates a sequential {@code Stream} from the collection's
 *           {@code Spliterator}.
 *
 * @return a sequential {@code Stream} over the elements in this collection
 * @since 1.8
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

		DashboardController dashboard = new DashboardController();

		JFrame frame = new JFrame("Clintariac - Gestionale per appuntamenti");
		frame.getContentPane().add(dashboard.getView());
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
