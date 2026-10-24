package external;

import java.util.Comparator;

import products.Product;

/**
 * Comparator that orders Product objects by their updated price,
 * from lowest to highest.
 * @author Kevin Saji
 */
public class UpdatedPriceCompare implements Comparator<Product>
{
	/**
	 * Compares two products based on their updated price.
	 *
	 * @param prod1 the first product to compare
	 * @param prod2 the second product to compare
	 * @return 1 if prod1 has a higher updated price, -1 if lower,
	 *         or 0 if they are equal
	 */
	@Override
	public int compare( Product prod1, Product prod2 )
	{
		if( prod1.getUpdatedPrice() > prod2.getUpdatedPrice() )
		{
			return 1;
		}
		else if( prod1.getUpdatedPrice() < prod2.getUpdatedPrice() )
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}