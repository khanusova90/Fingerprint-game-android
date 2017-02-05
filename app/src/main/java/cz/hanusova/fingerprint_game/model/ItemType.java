/**
 * 
 */
package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;

/**
 * Type of item to increase material gaining
 * 
 * @author khanusova
 *
 */
public class ItemType implements Serializable {

	private Long idItemType;

	private String name;

	private ActivityEnum activity;

	private Material material;

	/**
	 * @return the idItemType
	 */
	public Long getIdItemType() {
		return idItemType;
	}

	/**
	 * @param idItemType
	 *            the idItemType to set
	 */
	public void setIdItemType(Long idItemType) {
		this.idItemType = idItemType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the activity
	 */
	public ActivityEnum getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(ActivityEnum activity) {
		this.activity = activity;
	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material
	 *            the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

}
