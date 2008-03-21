package fabric.lang.arrays;

import fabric.lang.Object;

/**
 * author: kvikram
 * This class implements a resizable array using regular java arrays as a primitive
 * The smaller java array pieces are arranged as a tree so as to 
 * 
 * This version of ResizableArray assumes elements are Object instances
 * For primitive types, other versions may be written
 * 
 * Possible optimizations:
 * 1. convert the length to a base 128 representation
 *    will make it easier to take logs then
 *
 **/

class ResizableArray {
  
    /**
     * The number of elements in each little array
     * Dependent on the MTU? Analogous to a block in a file system
     * Also directly determines the fanout 
     */
    static int CHUNK_SIZE = 128;
    
    /**
     * The height of the tree of little arrays. 
     * Depends on the chunk size (determining the branching factor)
     *  and the number of expected elements in the bigger array
     */
    int height;
    
    /**
     * The number of expected elements in this big array
     * Can be modified even after an instance has been created
     */
    long length;
    
    /**
     * The root of the tree of little arrays. 
     * The runtime type of root is simply a java array of objects
     * Each object in the array is either a further array of objects
     *  or is an array element if this array is at the leaf level
     */
    Object[] root;
    
    public ResizableArray(long length) {
        this.length = length;
        this.height = (int) Math.ceil(Math.log10(length)/Math.log10(CHUNK_SIZE));
        root = new Object[CHUNK_SIZE];
    }
    
    public Object get(long i) {
        if(root == null) {
            return null;
        }
        return getByLevel(root, height, i, true, null);
    }

    /**
     * getOrSet = true if get and false if set
     */
    private Object getByLevel(Object[] node, int level, long i, 
        boolean getOrSet, Object data) {
        
        if(node == null) {
            // we're boldly going where no one has gone before
            node = new Object[];
        }
        
        long divider = (long) Math.pow(CHUNK_SIZE, level - 1);
        int firstDigit = (int) Math.floor(i/divider);
        long otherDigits = i - firstDigit * divider;
        
        if(level == 1) {
            if(getOrSet) {
                return node[i];
            } else {
                return node[i] = data;
            }
        } else {
            return getByLevel((Object[])node[firstDigit], 
                level - 1, otherDigits, getOrSet, data);            
        }
    }
    
    public Object set(long i, Object data) {
        return getByLevel(root, height, i, false, data);
    }
    
    public int modifySize(int newSize) {
        
        int newHeight = (int) Math.ceil(Math.log10(length)/Math.log10(CHUNK_SIZE));
        int difference = newHeight - this.height;
        if(difference == 0) return newSize;
        
        if(difference > 0) {
            // make sure the leaves are at the right level - push down the root
            while(difference-- > 0) {
                Object[] newRoot = new Object[CHUNK_SIZE];
                newRoot[0] = root;
                root = newRoot;
            }
            return newSize;
        }
        
        if(difference < 0) {
            // truncate the last so many array slots
            while(difference++ < 0) {
                Object[] rootArray = (Object[])root;
                root = rootArray[0];
            }
            return newSize;            
        }
        
    }
}