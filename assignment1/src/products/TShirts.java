package products;

public class TShirts extends Product {

	//Adjustment factor applied to the price difference for dresses.
	private static final double ADJUSTMENT = 0.2;

	public TShirts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * Calculates { 0.2 * (originalPrice - updatedPrice)}.
	 * @return the calculated real difference
	 */
	
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}
