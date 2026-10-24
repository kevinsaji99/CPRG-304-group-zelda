package products;

/**
 * Abstract base class for every product read from the provided text files.
 * <p>
 * Each product has an original price and an updated price. Every subclass
 * (Jeans, TShirts, Skirts, Dresses, Shorts, Jackets and Shoes) calculates its
 * own real difference between the two prices using the adjustment factor for
 * its product type.
 * </p>
 * <p>
 * This class implements {@link Comparable} to provide a natural ordering of
 * Product objects based on their calculated real difference.
 * </p>
 *
 * @author Kevin Saji
 */
public abstract class Product implements Comparable<Product> {
	/** The product's original price. */
	private final double originalPrice;
	
	/** The product's updated price. */
	private final double updatedPrice;
	
	/**
	 * Constructs a product with the specified prices. Called by each subclass
	 * through {@code super}.
	 *
	 * @param originalPrice the product's original price
	 * @param updatedPrice the product's updated price
	 */
	protected Product(double originalPrice, double updatedPrice) {
		this.originalPrice = originalPrice;
		this.updatedPrice = updatedPrice;
		
	}

	/**
	 * Returns the product's original price.
	 *
	 * @return the original price
	 */
	public double getOriginalPrice() {
		return originalPrice;
	}
	
	/**
	 * Returns the product's updated price.
	 *
	 * @return the updated price
	 */
	public double getUpdatedPrice() {
		return updatedPrice;
	}

	/**
	 * Calculates the difference between the two prices, adjusted for the
	 * product type: Adjustment * |Original Price - Updated Price|.
	 *
	 * @return the calculated real difference
	 */
	public abstract double calcRealDiff();
	
	/**
	 * Compares this product to another product based on the calculated real
	 * difference.
	 *
	 * @param other the product to compare against
	 * @return a negative integer, zero, or a positive integer as this
	 *         product's real difference is less than, equal to, or greater
	 *         than the other product's real difference
	 */
	@Override
	public int compareTo(Product other) {
		return Double.compare(this.calcRealDiff(), other.calcRealDiff());
	}
	
	/**
	 * Returns a readable description of this product.
	 *
	 * @return the class name, both prices and the calculated real difference
	 */
	@Override
	public String toString() {
		return getClass().getName() + "[original=" + originalPrice + ", updated=" + updatedPrice
				+ ", realDiff=" + calcRealDiff() + "]";
		
	}
	
}