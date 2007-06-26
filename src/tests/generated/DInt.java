package generated;

import diaspora.client.DObject;
import diaspora.client.Core;

public class DInt extends DObject
{
	private int[] value;

	public int  get_value()        { return this.value[0]; }
	public void set_value( int v ) { this.value[0] = v;    }

	public DInt( Core core )
	{
		super( core );
		value = new int[100];
	}
}
