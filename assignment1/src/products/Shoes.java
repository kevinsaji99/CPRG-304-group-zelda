package products;

/**
 * Represents a shoes product. The real price difference for shoes
 * is adjusted by a factor of 0.4.
 */
public class Shoes extends Product {

	/**
	 * Adjustment factor applied to the price difference for shoes.
	 */
	private static final double ADJUSTMENT = 0.4;

	/**
	 * Creates a new Shoes product with the given prices.
	 *
	 * @param originalPrice the original price of the shoes
	 * @param updatedPrice the updated price of the shoes
	 */
	public Shoes(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for shoes as
	 * {@code 0.4 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}