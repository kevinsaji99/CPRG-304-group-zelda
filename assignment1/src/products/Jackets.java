package products;

/**
 * Represents a jacket product. The real price difference for a jacket
 * is adjusted by a factor of 0.7.
 */
public class Jackets extends Product {

	/**
	 * Adjustment factor applied to the price difference for jackets.
	 */
	private static final double ADJUSTMENT = 0.7;

	/**
	 * Creates a new Jackets product with the given prices.
	 *
	 * @param originalPrice the original price of the jacket
	 * @param updatedPrice the updated price of the jacket
	 */
	public Jackets(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for a jacket as
	 * {@code 0.7 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}