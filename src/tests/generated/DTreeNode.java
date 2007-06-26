package generated;

import java.lang.ref.SoftReference;

import diaspora.client.DObject;
import diaspora.client.DRef;
import diaspora.client.Core;

class DTreeNode extends DObject
{
	// parameter left //////////////////////////////////////////////////////////

	private DRef left_ref;

	DTreeNode get_left()
	{
		if (left_ref == null)
			return null;

		DTreeNode result = (DTreeNode) left_ref.get();
		left_ref         = result.this_ref;

		return result;
	}

	void set_left( DTreeNode new_left )
	{
		if (new_left == null)
			left_ref = null;
		else
			left_ref = new_left.this_ref;
	}

	// parameter right /////////////////////////////////////////////////////////

	private DRef right_ref;

	DTreeNode get_right()
	{
		if (right_ref == null)
			return null;

		DTreeNode result = (DTreeNode) right_ref.get();
		right_ref         = result.this_ref;

		return result;
	}

	void set_right( DTreeNode new_right )
	{
		if (new_right == null)
			right_ref = null;
		else
			right_ref = new_right.this_ref;
	}

	// parameter value /////////////////////////////////////////////////////////

	private DRef value_ref;

	DInt get_value()
	{
		if (value_ref == null)
			return null;

		DInt result = (DInt) value_ref.get();
		value_ref         = result.this_ref;

		return result;
	}

	void set_value( DInt new_value )
	{
		if (new_value == null)
			value_ref = null;
		else
			value_ref = new_value.this_ref;
	}

	// constructors ////////////////////////////////////////////////////////////////

	public DTreeNode (Core core, DInt value)
	{
		this( core, null, null, null );
	}

	public DTreeNode (Core core, DTreeNode left, DTreeNode right, DInt value)
	{
		super( core );
		set_left( left );
		set_right( right );
		set_value( value );
	}
}

