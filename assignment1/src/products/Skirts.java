package products;

/**
 * Represents a skirt product. The real price difference for a skirt
 * is adjusted by a factor of 0.1.
 */
public class Skirts extends Product {

	/**
	 * Adjustment factor applied to the price difference for skirts.
	 */
	private static final double ADJUSTMENT = 0.1;

	/**
	 * Creates a new Skirts product with the given prices.
	 *
	 * @param originalPrice the original price of the skirt
	 * @param updatedPrice the updated price of the skirt
	 */
	public Skirts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for a skirt as
	 * {@code 0.1 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}