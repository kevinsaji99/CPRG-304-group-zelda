package external;

import java.util.Comparator;

import products.Product;

public class UpdatedPriceCompare implements Comparator<Product>
{
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

