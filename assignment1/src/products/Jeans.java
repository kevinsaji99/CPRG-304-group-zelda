package products;

public class Jeans extends Product {

	//Adjustment factor applied to the price difference for dresses.
	private static final double ADJUSTMENT = 0.5;

	public Jeans(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates { 0.5 * (originalPrice - updatedPrice)}.
	 * @return the calculated real difference
	 */
	
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}
