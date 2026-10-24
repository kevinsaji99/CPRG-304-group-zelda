package products;

/**
 * Represents a jeans product. The real price difference for jeans
 * is adjusted by a factor of 0.5.
 */
public class Jeans extends Product {

	/**
	 * Adjustment factor applied to the price difference for jeans.
	 */
	private static final double ADJUSTMENT = 0.5;

	/**
	 * Creates a new Jeans product with the given prices.
	 *
	 * @param originalPrice the original price of the jeans
	 * @param updatedPrice the updated price of the jeans
	 */
	public Jeans(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for jeans as
	 * {@code 0.5 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}