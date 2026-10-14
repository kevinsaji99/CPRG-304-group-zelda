/**
 * 
 */
package products;

/*
 * Abstract base class for every product read from the provided text files
 */
public abstract class Product implements Comparable<Product> {
	private final double originalPrice;
	
	private final double updatedPrice;
	
	protected Product(double originalPrice, double updatedPrice) {
		this.originalPrice = originalPrice;
		this.updatedPrice = updatedPrice;
		
	}
	//The products original price

	public double getOriginalPrice() {
		return originalPrice;
	}
	//The products updated price
	
	public double getUpdatedPrice() {
		return updatedPrice;
	}
	// calculates the differences between products
	public abstract double calcRealDiff();
	
	@Override
	public int compareTo(Product other) {
		return Double.compare(this.calcRealDiff(), other.calcRealDiff());
	}
	
	// returns a readable description of this product
	@Override
	public String toString() {
		return getClass().getName() + "[original=" + originalPrice + ", updated=" + updatedPrice
				+ ", realDiff=" + calcRealDiff() + "]";
		
	}
	
}
