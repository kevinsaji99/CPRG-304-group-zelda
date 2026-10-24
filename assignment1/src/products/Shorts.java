package products;

/**
 * Represents a shorts product. The real price difference for shorts
 * is adjusted by a factor of 0.3.
 */
public class Shorts extends Product {

	/**
	 * Adjustment factor applied to the price difference for shorts.
	 */
	private static final double ADJUSTMENT = 0.3;

	/**
	 * Creates a new Shorts product with the given prices.
	 *
	 * @param originalPrice the original price of the shorts
	 * @param updatedPrice the updated price of the shorts
	 */
	public Shorts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for shorts as
	 * {@code 0.3 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}