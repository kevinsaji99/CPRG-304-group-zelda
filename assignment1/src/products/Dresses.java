package products;

/**
 * Represents a dress product. The real price difference for a dress
 * is adjusted by a factor of 0.6.
 */
public class Dresses extends Product {

	/**
	 * Adjustment factor applied to the price difference for dresses.
	 */
	private static final double ADJUSTMENT = 0.6;

	/**
	 * Creates a new Dresses product with the given prices.
	 *
	 * @param originalPrice the original price of the dress
	 * @param updatedPrice the updated price of the dress
	 */
	public Dresses(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for a dress as
	 * {@code 0.6 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}