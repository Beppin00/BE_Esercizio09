/**
 * 
 */
package it.epicode.catalogoprodotti.week2.giorno4;

/**
 * @author Rino
 *
 */
public class Customer {
	private long id;
	private String name;
	private Integer tier;
	/**
	 * @param id
	 * @param name
	 * @param tier
	 */
	public Customer(long id, String name, Integer tier) {
		this.id = id;
		this.name = name;
		this.tier = tier;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the tier
	 */
	public Integer getTier() {
		return tier;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", tier=" + tier + "]";
	}
	
}
