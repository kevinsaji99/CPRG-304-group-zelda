package external;

import java.util.Comparator;

import products.Product;


/**
 * Comparator that orders Product objects by their original price,
 * from lowest to highest.
 * @author Kevin Saji
 */
public class OriginalPriceCompare implements Comparator<Product>
{
	/**
	 * Compares two products based on their original price.
	 *
	 * @param prod1 the first product to compare
	 * @param prod2 the second product to compare
	 * @return 1 if prod1 has a higher original price, -1 if lower,
	 *         or 0 if they are equal
	 */
	@Override
	public int compare( Product prod1, Product prod2 )
	{
		if( prod1.getOriginalPrice() > prod2.getOriginalPrice() )
		{
			return 1;
		}
		else if( prod1.getOriginalPrice() < prod2.getOriginalPrice() )
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
