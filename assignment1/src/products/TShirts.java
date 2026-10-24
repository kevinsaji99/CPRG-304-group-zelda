package products;

/**
 * Represents a T-shirt product. The real price difference for a T-shirt
 * is adjusted by a factor of 0.2.
 */
public class TShirts extends Product {

	/**
	 * Adjustment factor applied to the price difference for T-shirts.
	 */
	private static final double ADJUSTMENT = 0.2;

	/**
	 * Creates a new TShirts product with the given prices.
	 *
	 * @param originalPrice the original price of the T-shirt
	 * @param updatedPrice the updated price of the T-shirt
	 */
	public TShirts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates the real difference for a T-shirt as
	 * {@code 0.2 * |originalPrice - updatedPrice|}.
	 *
	 * @return the calculated real difference
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}