package model.pathloss;

public class OkumuraHata_PathLossModel {

	/**
	 * Get path loss value in open rural areas and frequency bigger than 300 MHz
	 * @param frequency Frequency used in MHz (300 ... 1000 MHz)
	 * @param distance in m (1000 ... 20000 m)
	 * @param transmitterHeight Transmitter height over ground (30 ... 200 m)
	 * @param receiverHeight Receiver height over ground (1 ... 10 m)
	 * @return path loss value
	 */
	public static double getPathLossInDecibel( double frequency, 
			double distance, double transmitterHeight, double receiverHeight ) {
		double beta_urban = 3.2 * Math.pow(Math.log10(11.75*receiverHeight),2) - 4.97;
		double beta = beta_urban - 4.78 * Math.pow(Math.log10(frequency),2)
			+ 18.33 * Math.log10(frequency) - 40.94;
		double pl = 69.55;
		pl += 26.16 * Math.log10(frequency);
		pl += -13.82 * Math.log10(transmitterHeight);
		pl += (44.9 - (6.55*Math.log10(transmitterHeight))) * Math.log10(distance);
//		System.out.println(Math.log10(distance));
		pl += -beta;
		return pl;
	}
	
	public static double getPathLoss( double frequency, 
			double distance, double transmitterHeight, double receiverHeight ) {
		return Math.pow(10, (1/10.0)*getPathLossInDecibel(frequency, distance, transmitterHeight, receiverHeight));
	}
}