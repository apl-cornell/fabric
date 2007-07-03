package generated;

import diaspora.client.Core;
import diaspora.client.DObject;
import diaspora.client.DRef;

public class DTree extends DObject
{
	// TODO: Core parameter treated separately, or dynamic value treated the same?
	private Core node_location;

	// parameter root //////////////////////////////////////////////////////////

	private DRef root_ref;

	private DTreeNode get_root()
	{
		if (root_ref == null)
			return null;

		DTreeNode result = (DTreeNode) root_ref.get();
		root_ref         = result.this_ref;

		return result;
	}

	private void set_root( DTreeNode new_root )
	{
		if (new_root == null)
			root_ref = null;
		else
			root_ref = new_root.this_ref;
	}

	// constructors ////////////////////////////////////////////////////////////////

	public DTree (Core core)
	{
		this( core, core );
	}

	public DTree (Core core, Core node_location)
	{
		super( core );
		set_root( null );
		this.node_location = node_location;
	}

	public void insert( DInt value )
	{
		/* ORIGINAL IMPLEMENTATION:
		root = insert_helper( value );
		*/
		set_root( insert_helper( get_root(), value ) );
	}

	private DTreeNode insert_helper( DTreeNode current, DInt value )
	{
		/* ORIGINAL IMPLEMENTATION:
		if (current == null)
			return new DTreeNode( value, null, null )
		else if (current.value.value < value)
			current.right = insert_helper( current.right, value );
		else
			current.left  = insert_helper( current.left,  value );
		return current;
		*/
		if (current == null)
			return new DTreeNode( node_location, null, null, value );
		else if (current.get_value().get_value() < value.get_value())
			current.set_right( insert_helper( current.get_right(), value ) );
		else
			current.set_left(  insert_helper( current.get_left (), value ) );
		return current;
	}

	public void insert_iterative( DInt value )
	{
		/* ORIGINAL IMPLEMENTATION:
		if (root == null)
		{
			root = new DTreeNode( value );
			return;
		}

		TreeNode current = root;
		while( true )
		{
			if (current.value.value < value.value)
			{
				if (current.right == null)
				{
					current.right = new TreeNode@node_location( value, null, null );
					break;
				}
				else
					current = current.right;
			}
			else
			{
				if (current.left == null )
				{
					current.left  = new TreeNode@node_location( value, null, null );
					break;
				}
				else
					current = current.left;
			}
		}
		*/

		if (get_root() == null)
		{
			set_root( new DTreeNode( node_location, null, null, value ) );
			return;
		}

		DTreeNode current = get_root();
		while( true )
		{
			if (current.get_value().get_value() < value.get_value())
			{
				if (current.get_right() == null)
				{
					current.set_right( new DTreeNode( node_location, null, null, value ) );
					break;
				}
				else
					current = current.get_right();
			}
			else
			{
				if (current.get_left() == null )
				{
					current.set_left( new DTreeNode( node_location, null, null, value ) );
					break;
				}
				else
					current = current.get_left();
			}
		}
	}

	public DInt lookup( DInt value )
	{
		/* ORIGINAL IMPLEMENTATION:
		DTreeNode current = root;
		while( current != null )
		{
			if (current.value.value == value.value)
				return current.value;
			else if (current.value.value < value.value)
				current = current.left;
			else
				current = current.right;
		}
		return null;
		*/
		DTreeNode current = get_root();
		while( current != null )
		{
			if (current.get_value().get_value() == value.get_value())
				return current.get_value();
			else if( current.get_value().get_value() < value.get_value() )
				current = current.get_left();
			else
				current = current.get_right();
		}
		return null;
	}
}

