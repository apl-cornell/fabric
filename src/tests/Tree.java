import generated.DTree;
import generated.DInt;
import client.Core;
import java.net.InetSocketAddress;
import java.util.Random;

public class  Main
{
	public static void main( String[] args )
	{
		InetSocketAddress node_addr  = new InetSocketAddress( "localhost", 2001 );
		Core              node_core  = Core.get_core( node_addr );

		InetSocketAddress value_addr = new InetSocketAddress( "localhost", 2002 );
		Core              value_core = Core.get_core( value_addr );

		DTree   tree = new DTree( value_core, node_core );
		Random random = new Random();

		for( int cycle = 0; ; cycle++ )
		{
			for( int i = 0; i < 50; i++)
			{
				DInt to_insert = new DInt( value_core );
				to_insert.set_value( random.nextInt() );
				tree.insert_iterative( to_insert );
			}

			for( int i = 0; i < 50; i++)
			{
				DInt to_find = new DInt( node_core );
				to_find.set_value( random.nextInt() );
				tree.lookup( to_find );
			}
			System.out.println( "cycle " + cycle );
		}
	}
}
