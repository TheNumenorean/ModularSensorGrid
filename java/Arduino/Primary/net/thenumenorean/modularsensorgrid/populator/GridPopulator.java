/**
 * 
 */
package net.thenumenorean.modularsensorgrid.populator;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;

/**
 * @author The Numenorean
 *
 */
public abstract class GridPopulator {
	
	private ModularSensorGrid msg;

	public GridPopulator(ModularSensorGrid msg){
		this.msg = msg;
		
	}

	public ModularSensorGrid getMSG() {
		return msg;
	}
	
	public abstract void start();
	public abstract void stop();

}
