package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.worker.Worker;
import fabric.worker.Store;

/**
 * Utility class for matrix representation in {@link LinearMetric}.
 */
public interface Matrix extends fabric.lang.Object {
    public int get$rows();
    
    public int set$rows(int val);
    
    public int postInc$rows();
    
    public int postDec$rows();
    
    public int get$columns();
    
    public int set$columns(int val);
    
    public int postInc$columns();
    
    public int postDec$columns();
    
    public fabric.util.ArrayList get$data();
    
    public fabric.util.ArrayList set$data(fabric.util.ArrayList val);
    
    public fabric.worker.Store getStore();
    
    /**
   * @param rows
   *        the number of rows in the blank {@link Matrix} produced.
   * @param columns
   *        the number of columns in the blank {@link Matrix} produced.
   */
    public fabric.metrics.util.Matrix fabric$metrics$util$Matrix$(int rows,
                                                                  int columns);
    
    /** @return the number of rows in this {@link Matrix} */
    public int rows();
    
    /** @return the number of columns in this {@link Matrix} */
    public int columns();
    
    /**
   * @param i
   *        a row index into this {@link Matrix}
   * @return a copy of row i
   */
    public fabric.lang.arrays.doubleArray getRow(int i);
    
    /**
   * @param i
   *        a column index into this {@link Matrix}
   * @return a copy of column i
   */
    public fabric.lang.arrays.doubleArray getColumn(int i);
    
    /**
   * @param i
   *        a row index into this {@link Matrix}
   * @param j
   *        a column index into this {@link Matrix}
   * @return the entry at (i, j)
   */
    public double get(int i, int j);
    
    /**
   * @param i
   *        a row index into this {@link Matrix}
   * @param newRow
   *        the row to replace with at index i in this {@link Matrix}
   */
    public void setRow(int i, fabric.lang.arrays.doubleArray newRow);
    
    /**
   * @param i
   *        a column index into this {@link Matrix}
   * @param newColumn
   *        the column to replace with at index i in this {@link Matrix}
   */
    public void setColumn(int i, fabric.lang.arrays.doubleArray newColumn);
    
    /**
   * @param i
   *        a row index into this {@link Matrix}
   * @param j
   *        a column index into this {@link Matrix}
   * @param v
   *        the value to place at (i, j)
   */
    public void set(int i, int j, double v);
    
    /**
   * @param i
   *        a row index
   * @param newRow
   *        a row to insert (shifting rows over if necessar) at index i in
   *        this {@link Matrix}
   */
    public void insertRow(int i, fabric.lang.arrays.doubleArray newRow);
    
    /**
   * @param newRow
   *        the row to append to this {@link Matrix}
   */
    public void addRow(fabric.lang.arrays.doubleArray newRow);
    
    /**
   * @param i
   *        a column index
   * @param newCol
   *        a column to insert (shifting columns over if necessar) at
   *        index i in this {@link Matrix}
   */
    public void insertColumn(int i, fabric.lang.arrays.doubleArray newCol);
    
    /**
   * @param newCol
   *        the column to append to this {@link Matrix}
   */
    public void addColumn(fabric.lang.arrays.doubleArray newCol);
    
    /**
   * @param i
   *        the row index to remove from this {@link Matrix} (shifting
   *        back other rows, if necessary).
   */
    public void removeRow(int i);
    
    /**
   * @param i
   *        the column index to remove from this {@link Matrix} (shifting
   *        back other columns, if necessary).
   */
    public void removeColumn(int i);
    
    /**
   * @param v
   *        a vector (given as a double[])
   * @return the result of multiplying this {@link Matrix} with v
   */
    public fabric.lang.arrays.doubleArray multiply(fabric.lang.arrays.doubleArray v);
    
    /**
   * @param m
   *        another {@link Matrix} to multiply with
   * @return the result of multiplying this {@link Matrix} with v
   */
    public fabric.metrics.util.Matrix multiply(fabric.metrics.util.Matrix m);
    
    /**
   * @param a
   *        a scalar to multiply the entries of this {@link Matrix} by
   * @return the result of multiplying this {@link Matrix}'s entries by a
   */
    public fabric.metrics.util.Matrix multiply(double a);
    
    /**
   * @param m
   *        another {@link Matrix} to multiply with element-wise
   * @return the element-wise product of this and m
   */
    public fabric.metrics.util.Matrix elementMultiply(
      fabric.metrics.util.Matrix m);
    
    /**
   * @return a copy of this {@link Matrix}
   */
    public fabric.metrics.util.Matrix copy();
    
    /**
   * @return a copy of this {@link Matrix}
   */
    public fabric.metrics.util.Matrix copy(final fabric.worker.Store s);
    
    public boolean equals(fabric.lang.Object other);
    
    public int hashCode();
    
    public java.lang.String toString();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Matrix {
        public int get$rows() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).get$rows();
        }
        
        public int set$rows(int val) {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).set$rows(val);
        }
        
        public int postInc$rows() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).postInc$rows();
        }
        
        public int postDec$rows() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).postDec$rows();
        }
        
        public int get$columns() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).get$columns();
        }
        
        public int set$columns(int val) {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).set$columns(
                                                                  val);
        }
        
        public int postInc$columns() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).postInc$columns(
                                                                  );
        }
        
        public int postDec$columns() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).postDec$columns(
                                                                  );
        }
        
        public fabric.util.ArrayList get$data() {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).get$data();
        }
        
        public fabric.util.ArrayList set$data(fabric.util.ArrayList val) {
            return ((fabric.metrics.util.Matrix._Impl) fetch()).set$data(val);
        }
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.util.Matrix) fetch()).getStore();
        }
        
        public fabric.metrics.util.Matrix fabric$metrics$util$Matrix$(
          int arg1, int arg2) {
            return ((fabric.metrics.util.Matrix) fetch()).
              fabric$metrics$util$Matrix$(arg1, arg2);
        }
        
        public int rows() {
            return ((fabric.metrics.util.Matrix) fetch()).rows();
        }
        
        public int columns() {
            return ((fabric.metrics.util.Matrix) fetch()).columns();
        }
        
        public fabric.lang.arrays.doubleArray getRow(int arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).getRow(arg1);
        }
        
        public fabric.lang.arrays.doubleArray getColumn(int arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).getColumn(arg1);
        }
        
        public double get(int arg1, int arg2) {
            return ((fabric.metrics.util.Matrix) fetch()).get(arg1, arg2);
        }
        
        public void setRow(int arg1, fabric.lang.arrays.doubleArray arg2) {
            ((fabric.metrics.util.Matrix) fetch()).setRow(arg1, arg2);
        }
        
        public void setColumn(int arg1, fabric.lang.arrays.doubleArray arg2) {
            ((fabric.metrics.util.Matrix) fetch()).setColumn(arg1, arg2);
        }
        
        public void set(int arg1, int arg2, double arg3) {
            ((fabric.metrics.util.Matrix) fetch()).set(arg1, arg2, arg3);
        }
        
        public void insertRow(int arg1, fabric.lang.arrays.doubleArray arg2) {
            ((fabric.metrics.util.Matrix) fetch()).insertRow(arg1, arg2);
        }
        
        public void addRow(fabric.lang.arrays.doubleArray arg1) {
            ((fabric.metrics.util.Matrix) fetch()).addRow(arg1);
        }
        
        public void insertColumn(int arg1,
                                 fabric.lang.arrays.doubleArray arg2) {
            ((fabric.metrics.util.Matrix) fetch()).insertColumn(arg1, arg2);
        }
        
        public void addColumn(fabric.lang.arrays.doubleArray arg1) {
            ((fabric.metrics.util.Matrix) fetch()).addColumn(arg1);
        }
        
        public void removeRow(int arg1) {
            ((fabric.metrics.util.Matrix) fetch()).removeRow(arg1);
        }
        
        public void removeColumn(int arg1) {
            ((fabric.metrics.util.Matrix) fetch()).removeColumn(arg1);
        }
        
        public fabric.lang.arrays.doubleArray multiply(
          fabric.lang.arrays.doubleArray arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).multiply(arg1);
        }
        
        public fabric.metrics.util.Matrix multiply(
          fabric.metrics.util.Matrix arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).multiply(arg1);
        }
        
        public fabric.metrics.util.Matrix multiply(double arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).multiply(arg1);
        }
        
        public fabric.metrics.util.Matrix elementMultiply(
          fabric.metrics.util.Matrix arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).elementMultiply(arg1);
        }
        
        public fabric.metrics.util.Matrix copy() {
            return ((fabric.metrics.util.Matrix) fetch()).copy();
        }
        
        public fabric.metrics.util.Matrix copy(fabric.worker.Store arg1) {
            return ((fabric.metrics.util.Matrix) fetch()).copy(arg1);
        }
        
        public static fabric.metrics.util.Matrix identity(int arg1) {
            return fabric.metrics.util.Matrix._Impl.identity(arg1);
        }
        
        public static fabric.metrics.util.Matrix singleRow(
          fabric.lang.arrays.doubleArray arg1) {
            return fabric.metrics.util.Matrix._Impl.singleRow(arg1);
        }
        
        public static fabric.metrics.util.Matrix singleColumn(
          fabric.lang.arrays.doubleArray arg1) {
            return fabric.metrics.util.Matrix._Impl.singleColumn(arg1);
        }
        
        public static fabric.metrics.util.Matrix constant(int arg1, int arg2,
                                                          double arg3) {
            return fabric.metrics.util.Matrix._Impl.constant(arg1, arg2, arg3);
        }
        
        public _Proxy(Matrix._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.Matrix {
        public int get$rows() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.rows;
        }
        
        public int set$rows(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.rows = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$rows() {
            int tmp = this.get$rows();
            this.set$rows((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$rows() {
            int tmp = this.get$rows();
            this.set$rows((int) (tmp - 1));
            return tmp;
        }
        
        private int rows;
        
        public int get$columns() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.columns;
        }
        
        public int set$columns(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.columns = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$columns() {
            int tmp = this.get$columns();
            this.set$columns((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$columns() {
            int tmp = this.get$columns();
            this.set$columns((int) (tmp - 1));
            return tmp;
        }
        
        private int columns;
        
        public fabric.util.ArrayList get$data() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.data;
        }
        
        public fabric.util.ArrayList set$data(fabric.util.ArrayList val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.data = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.ArrayList data;
        
        public fabric.worker.Store getStore() { return $getStore(); }
        
        /**
   * @param rows
   *        the number of rows in the blank {@link Matrix} produced.
   * @param columns
   *        the number of columns in the blank {@link Matrix} produced.
   */
        public fabric.metrics.util.Matrix fabric$metrics$util$Matrix$(
          int rows, int columns) {
            fabric$lang$Object$();
            this.set$rows((int) rows);
            this.set$columns((int) columns);
            this.set$data(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         rows));
            for (int i = 0; i < rows; i++) {
                this.get$data().add(
                                  (fabric.lang.arrays.doubleArray)
                                    new fabric.lang.arrays.doubleArray._Impl(
                                      this.$getStore(
                                             )).fabric$lang$arrays$doubleArray$(
                                                  this.get$$updateLabel(),
                                                  this.get$$updateLabel(
                                                         ).confPolicy(),
                                                  columns).$getProxy());
            }
            return (fabric.metrics.util.Matrix) this.$getProxy();
        }
        
        /** @return the number of rows in this {@link Matrix} */
        public int rows() { return this.get$rows(); }
        
        /** @return the number of columns in this {@link Matrix} */
        public int columns() { return this.get$columns(); }
        
        /**
   * @param i
   *        a row index into this {@link Matrix}
   * @return a copy of row i
   */
        public fabric.lang.arrays.doubleArray getRow(int i) {
            fabric.lang.arrays.doubleArray row =
              (fabric.lang.arrays.doubleArray)
                fabric.lang.Object._Proxy.$getProxy(this.get$data().get(i));
            fabric.lang.arrays.doubleArray copy =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$columns()).$getProxy();
            for (int j = 0; j < row.get$length(); j++) {
                copy.set(j, (double) row.get(j));
            }
            return copy;
        }
        
        /**
   * @param i
   *        a column index into this {@link Matrix}
   * @return a copy of column i
   */
        public fabric.lang.arrays.doubleArray getColumn(int i) {
            fabric.lang.arrays.doubleArray copy =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$rows()).$getProxy();
            for (int j = 0; j < this.get$rows(); j++) {
                copy.set(
                       j,
                       (double)
                         ((fabric.lang.arrays.doubleArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        this.get$data(
                                                               ).get(j))).get(
                                                                            i));
            }
            return copy;
        }
        
        /**
   * @param i
   *        a row index into this {@link Matrix}
   * @param j
   *        a column index into this {@link Matrix}
   * @return the entry at (i, j)
   */
        public double get(int i, int j) {
            return (double)
                     ((fabric.lang.arrays.doubleArray)
                        fabric.lang.Object._Proxy.$getProxy(
                                                    this.get$data().get(i))).
                     get(j);
        }
        
        /**
   * @param i
   *        a row index into this {@link Matrix}
   * @param newRow
   *        the row to replace with at index i in this {@link Matrix}
   */
        public void setRow(int i, fabric.lang.arrays.doubleArray newRow) {
            if (newRow.get$length() != this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Row of wrong size (" + this.get$columns() + "): " +
                          newRow.get$length());
            if (i >= this.get$rows())
                throw new java.lang.IllegalArgumentException(
                        "Row index out of bounds (" + this.get$rows() + "): " +
                          i);
            fabric.lang.arrays.doubleArray row =
              (fabric.lang.arrays.doubleArray)
                fabric.lang.Object._Proxy.$getProxy(this.get$data().get(i));
            for (int j = 0; j < this.get$columns(); j++) {
                row.set(j, (double) newRow.get(j));
            }
        }
        
        /**
   * @param i
   *        a column index into this {@link Matrix}
   * @param newColumn
   *        the column to replace with at index i in this {@link Matrix}
   */
        public void setColumn(int i, fabric.lang.arrays.doubleArray newColumn) {
            if (newColumn.get$length() != this.get$rows())
                throw new java.lang.IllegalArgumentException(
                        "Column of wrong size (" + this.get$rows() + "): " +
                          newColumn.get$length());
            if (i >= this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Row index out of bounds (" + this.get$columns() +
                          "): " + i);
            for (int j = 0; j < this.get$rows(); j++) {
                ((fabric.lang.arrays.doubleArray)
                   fabric.lang.Object._Proxy.$getProxy(this.get$data().get(j))).
                  set(i, (double) newColumn.get(j));
            }
        }
        
        /**
   * @param i
   *        a row index into this {@link Matrix}
   * @param j
   *        a column index into this {@link Matrix}
   * @param v
   *        the value to place at (i, j)
   */
        public void set(int i, int j, double v) {
            ((fabric.lang.arrays.doubleArray)
               fabric.lang.Object._Proxy.$getProxy(this.get$data().get(i))).
              set(j, v);
        }
        
        /**
   * @param i
   *        a row index
   * @param newRow
   *        a row to insert (shifting rows over if necessar) at index i in
   *        this {@link Matrix}
   */
        public void insertRow(int i, fabric.lang.arrays.doubleArray newRow) {
            if (newRow.get$length() != this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Row of wrong size (" + this.get$columns() + "): " +
                          newRow.get$length());
            if (i > this.get$rows())
                throw new java.lang.IllegalArgumentException("Bad row index: " +
                                                               i);
            this.get$data().add(i, newRow);
            this.postInc$rows();
        }
        
        /**
   * @param newRow
   *        the row to append to this {@link Matrix}
   */
        public void addRow(fabric.lang.arrays.doubleArray newRow) {
            insertRow(this.get$rows(), newRow);
        }
        
        /**
   * @param i
   *        a column index
   * @param newCol
   *        a column to insert (shifting columns over if necessar) at
   *        index i in this {@link Matrix}
   */
        public void insertColumn(int i, fabric.lang.arrays.doubleArray newCol) {
            if (newCol.get$length() != this.get$rows())
                throw new java.lang.IllegalArgumentException(
                        "Column of wrong size (" + this.get$rows() + "): " +
                          newCol.get$length());
            if (i > this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Bad column index: " + i);
            for (int j = 0; j < this.get$rows(); j++) {
                fabric.lang.arrays.doubleArray oldRow =
                  (fabric.lang.arrays.doubleArray)
                    fabric.lang.Object._Proxy.$getProxy(this.get$data().get(j));
                fabric.lang.arrays.doubleArray newRow =
                  (fabric.lang.arrays.doubleArray)
                    new fabric.lang.arrays.doubleArray._Impl(
                      this.$getStore()).fabric$lang$arrays$doubleArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          this.get$columns() + 1).$getProxy();
                for (int k = 0; k < this.get$columns() + 1; k++) {
                    if (k == i) {
                        newRow.set(k, (double) newCol.get(j));
                    } else if (k > i) {
                        newRow.set(k, (double) oldRow.get(k - 1));
                    } else {
                        newRow.set(k, (double) oldRow.get(k));
                    }
                }
                this.get$data().set(i, newRow);
            }
            this.postInc$columns();
        }
        
        /**
   * @param newCol
   *        the column to append to this {@link Matrix}
   */
        public void addColumn(fabric.lang.arrays.doubleArray newCol) {
            insertColumn(this.get$columns(), newCol);
        }
        
        /**
   * @param i
   *        the row index to remove from this {@link Matrix} (shifting
   *        back other rows, if necessary).
   */
        public void removeRow(int i) {
            if (i >= this.get$rows())
                throw new java.lang.IllegalArgumentException("Bad row index: " +
                                                               i);
            this.get$data().remove(i);
            this.postDec$rows();
        }
        
        /**
   * @param i
   *        the column index to remove from this {@link Matrix} (shifting
   *        back other columns, if necessary).
   */
        public void removeColumn(int i) {
            if (i >= this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Bad column index: " + i);
            for (int j = 0; j < this.get$rows(); j++) {
                fabric.lang.arrays.doubleArray newRow =
                  (fabric.lang.arrays.doubleArray)
                    new fabric.lang.arrays.doubleArray._Impl(
                      this.$getStore()).fabric$lang$arrays$doubleArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          this.get$columns() - 1).$getProxy();
                fabric.lang.arrays.doubleArray oldRow =
                  (fabric.lang.arrays.doubleArray)
                    fabric.lang.Object._Proxy.$getProxy(this.get$data().get(j));
                for (int k = 0; k < this.get$columns(); k++) {
                    if (k == i) {
                        continue;
                    } else if (k > i) {
                        newRow.set(k - 1, (double) oldRow.get(k));
                    } else {
                        newRow.set(k, (double) oldRow.get(k));
                    }
                }
                this.get$data().set(j, newRow);
            }
            this.postDec$columns();
        }
        
        /**
   * @param v
   *        a vector (given as a double[])
   * @return the result of multiplying this {@link Matrix} with v
   */
        public fabric.lang.arrays.doubleArray multiply(
          fabric.lang.arrays.doubleArray v) {
            if (v.get$length() != this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Bad vector length: " + v.get$length());
            fabric.lang.arrays.doubleArray result =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$rows()).$getProxy();
            for (int row = 0; row < this.get$rows(); row++) {
                for (int col = 0; col < this.get$columns(); col++) {
                    result.set(row,
                               (double) result.get(row) + get(row, col) *
                                   (double) v.get(col));
                }
            }
            return result;
        }
        
        /**
   * @param m
   *        another {@link Matrix} to multiply with
   * @return the result of multiplying this {@link Matrix} with v
   */
        public fabric.metrics.util.Matrix multiply(
          fabric.metrics.util.Matrix m) {
            if (m.rows() != this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Bad matrix size: " + m.rows() + "x" + m.columns());
            fabric.metrics.util.Matrix result =
              ((fabric.metrics.util.Matrix)
                 new fabric.metrics.util.Matrix._Impl(
                   this.$getStore()).$getProxy()).fabric$metrics$util$Matrix$(
                                                    0, m.get$columns());
            for (int row = 0; row < m.columns(); row++) {
                result.addRow(multiply(m.getColumn(row)));
            }
            return result;
        }
        
        /**
   * @param a
   *        a scalar to multiply the entries of this {@link Matrix} by
   * @return the result of multiplying this {@link Matrix}'s entries by a
   */
        public fabric.metrics.util.Matrix multiply(double a) {
            fabric.metrics.util.Matrix result =
              ((fabric.metrics.util.Matrix)
                 new fabric.metrics.util.Matrix._Impl(
                   this.$getStore()).$getProxy()).fabric$metrics$util$Matrix$(
                                                    this.get$rows(),
                                                    this.get$columns());
            for (int i = 0; i < this.get$rows(); i++) {
                for (int j = 0; j < this.get$columns(); j++) {
                    result.set(i, j, get(i, j) * a);
                }
            }
            return result;
        }
        
        /**
   * @param m
   *        another {@link Matrix} to multiply with element-wise
   * @return the element-wise product of this and m
   */
        public fabric.metrics.util.Matrix elementMultiply(
          fabric.metrics.util.Matrix m) {
            if (m.rows() != this.get$rows() || m.columns() !=
                  this.get$columns())
                throw new java.lang.IllegalArgumentException(
                        "Bad matrix size: " + m.rows() + "x" + m.columns());
            fabric.metrics.util.Matrix result =
              ((fabric.metrics.util.Matrix)
                 new fabric.metrics.util.Matrix._Impl(
                   this.$getStore()).$getProxy()).fabric$metrics$util$Matrix$(
                                                    this.get$rows(),
                                                    this.get$columns());
            for (int i = 0; i < this.get$rows(); i++) {
                for (int j = 0; j < this.get$columns(); j++) {
                    result.set(i, j, get(i, j) * m.get(i, j));
                }
            }
            return result;
        }
        
        /**
   * @return a copy of this {@link Matrix}
   */
        public fabric.metrics.util.Matrix copy() {
            return copy(fabric.worker.Worker.getWorker().getLocalStore());
        }
        
        /**
   * @return a copy of this {@link Matrix}
   */
        public fabric.metrics.util.Matrix copy(final fabric.worker.Store s) {
            fabric.metrics.util.Matrix result =
              ((fabric.metrics.util.Matrix)
                 new fabric.metrics.util.Matrix._Impl(s).$getProxy()).
              fabric$metrics$util$Matrix$(this.get$rows(), this.get$columns());
            for (int i = 0; i < this.get$rows(); i++) {
                for (int j = 0; j < this.get$columns(); j++) {
                    result.set(i, j, get(i, j));
                }
            }
            return result;
        }
        
        /**
   * @param size
   *        the size of the identity {@link Matrix} to create
   * @return an identity {@link Matrix} of the given size
   */
        public static fabric.metrics.util.Matrix identity(int size) {
            fabric.metrics.util.Matrix
              m =
              ((fabric.metrics.util.Matrix)
                 new fabric.
                   metrics.
                   util.
                   Matrix.
                   _Impl(
                   fabric.metrics.util.Matrix._Static._Proxy.$instance.
                       $getStore()).
                 $getProxy()).fabric$metrics$util$Matrix$(size, size);
            for (int i = 0; i < size; i++) { m.set(i, i, 1); }
            return m;
        }
        
        /**
   * @param row
   *        the single row to put in the created {@link Matrix}
   * @return a {@link Matrix} with a single row with the given contents
   */
        public static fabric.metrics.util.Matrix singleRow(
          fabric.lang.arrays.doubleArray row) {
            fabric.metrics.util.Matrix
              m =
              ((fabric.metrics.util.Matrix)
                 new fabric.
                   metrics.
                   util.
                   Matrix.
                   _Impl(
                   fabric.metrics.util.Matrix._Static._Proxy.$instance.
                       $getStore()).
                 $getProxy()).fabric$metrics$util$Matrix$(1, row.get$length());
            m.setRow(0, row);
            return m;
        }
        
        /**
   * @param column
   *        the single column to put in the created {@link Matrix}
   * @return a {@link Matrix} with a single column with the given contents
   */
        public static fabric.metrics.util.Matrix singleColumn(
          fabric.lang.arrays.doubleArray column) {
            fabric.metrics.util.Matrix
              m =
              ((fabric.metrics.util.Matrix)
                 new fabric.
                   metrics.
                   util.
                   Matrix.
                   _Impl(
                   fabric.metrics.util.Matrix._Static._Proxy.$instance.
                       $getStore()).
                 $getProxy()).fabric$metrics$util$Matrix$(column.get$length(),
                                                          1);
            m.setColumn(0, column);
            return m;
        }
        
        /**
   * @param rows
   *        the number of rows in the returned {@link Matrix}
   * @param columns
   *        the number of columns in the returned {@link Matrix}
   * @param c
   *        a constant to initialize all entries with
   * @return a {@link Matrix} with all entries initialized to c
   */
        public static fabric.metrics.util.Matrix constant(int rows, int columns,
                                                          double c) {
            fabric.metrics.util.Matrix
              m =
              ((fabric.metrics.util.Matrix)
                 new fabric.
                   metrics.
                   util.
                   Matrix.
                   _Impl(
                   fabric.metrics.util.Matrix._Static._Proxy.$instance.
                       $getStore()).
                 $getProxy()).fabric$metrics$util$Matrix$(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) { m.set(i, j, c); }
            }
            return m;
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.util.Matrix) {
                fabric.metrics.util.Matrix that =
                  (fabric.metrics.util.Matrix)
                    fabric.lang.Object._Proxy.$getProxy(other);
                if (this.rows() != that.rows() || this.columns() !=
                      that.columns())
                    return false;
                for (int i = 0; i < this.get$rows(); i++) {
                    for (int j = 0; j < this.get$columns(); j++) {
                        if (this.get(i, j) != that.get(i, j)) return false;
                    }
                }
                return true;
            }
            return false;
        }
        
        public int hashCode() {
            int code = 31 * this.get$rows() + this.get$columns();
            for (int i = 0; i < this.get$rows(); i++) {
                for (int j = 0; j < this.get$columns(); j++) {
                    code = 31 * code + java.lang.Double.hashCode(get(i, j));
                }
            }
            return code;
        }
        
        public java.lang.String toString() {
            java.lang.String str = "[";
            for (int i = 0; i < this.get$rows(); i++) {
                if (i > 0) str += ", ";
                str += "[";
                for (int j = 0; j < this.get$columns(); j++) {
                    if (j > 0) str += ", ";
                    str += get(i, j);
                }
                str += "]";
            }
            return str + "]";
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.Matrix._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.rows);
            out.writeInt(this.columns);
            $writeRef($getStore(), this.data, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.rows = in.readInt();
            this.columns = in.readInt();
            this.data = (fabric.util.ArrayList)
                          $readRef(fabric.util.ArrayList._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.Matrix._Impl src =
              (fabric.metrics.util.Matrix._Impl) other;
            this.rows = src.rows;
            this.columns = src.columns;
            this.data = src.data;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.Matrix._Static {
            public _Proxy(fabric.metrics.util.Matrix._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.Matrix._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  Matrix.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.Matrix._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.Matrix._Static._Impl.class);
                $instance = (fabric.metrics.util.Matrix._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.Matrix._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.Matrix._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -90, 73, 117, -27, -79,
    23, -53, 96, 22, 72, 127, 41, -101, -61, 115, -120, -110, 4, 44, 69, 44, 71,
    99, -18, -78, -54, 19, 60, 15, -59, 115, -121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492455019000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1ae5AUxRnv2Vvu5cEddzxP7oDjRO84dsUHPk4sYeVxuAi5A1OegXNutvduZHZmnZm9W4wYY2KBFCHGHKhJoFIVImpOrJhYVsoisQIYEbXUSqL+oVJJ8BHEgmiMqcSY7+vufc3ODjtVUsz3zU331/P7vv4e3b0zfppMsEzSFpcHVS1kb0lSK7RCHuyJrpNNi8YimmxZ6+HpgHJesGfPBwdirQESiJI6RdYNXVVkbUC3bDIpeps8Iod1aoc39PZ030JqFBRcJVvDNgncsixtkjlJQ9sypBm2eEnR+LsXhMce3NTwVAWp7yf1qt5ny7aqRAzdpmm7n9QlaGKQmtbSWIzG+slkndJYHzVVWVPvgI6G3k8aLXVIl+2USa1eahnaCHZstFJJarJ3Zh4ifANgmynFNkyA38Dhp2xVC0dVy+6Oksq4SrWYdTu5iwSjZEJck4eg47RoRoswGzG8Ap9D91oVYJpxWaEZkeBmVY/ZZLZTIqtx+w3QAUSrEtQeNrKvCuoyPCCNHJIm60PhPttU9SHoOsFIwVts0lxyUOhUnZSVzfIQHbDJDGe/dbwJetUws6CITaY6u7GRYM6aHXOWN1unb7xm1zf1VXqASIA5RhUN8VeDUKtDqJfGqUl1hXLBus7oHnnaoe0BQqDzVEdn3ueZO89e19X63Au8z/kufdYO3kYVe0DZPzjptVmRjqsqEEZ10rBUdIUCzdmsrhMt3ekkePu07IjYGMo0Ptf7/M13P05PBUhtD6lUDC2VAK+arBiJpKpRcyXVqSnbNNZDaqgei7D2HlIF91FVp/zp2njconYPCWrsUaXB/gYTxWEINFEV3Kt63MjcJ2V7mN2nk4SQKriIBP8fIaRLh/uJcJ21SU942EjQ8KCWoqPg3mG4qGwqw2GIW1NVwpaphM2UbqvQSTwCLwJmcf3XyPBHOgQgkl/lYGlE3jAqSWDU2YoRo4OyBTMkvGXZOg0CYpWhxag5oGi7DvWQpkMPM4+pQS+3wFOZTSSY5VnO/JAvO5ZatvzswYHj3NtQVpgMYoEjDAmEfEY5QgBVh1EUgrwUgrw0LqVDkX09v2DOUmmxqMqOUwfjXJ3UZDtumIk0kSSm1BQmz8aEOd4MuQPSQ11H38bVt25vqwD3TI4Gccaga7szWHIppgfuZIiAAaV+2wefPblnq5ELG5u0F0VzsSRGY5vTQqah0Bhku9zwnXPkpwcObW0PYCapgSRny+CGkDFane8oiMruTIZDa0yIkvPQBrKGTZm0VGsPm8Zo7gmb+UlIGrkToLEcAFlyXNKX3PvmKx9eyspGJo/W5yXcPmp358UuDlbPonRyzvbrTUqh39sPrfvh7tPbbmGGhx7z3F7YjjQCMStDsBrmvS/c/ta77+z/YyA3WTapTKYGNVVJM10mfwn/JLj+hxcGID5ADmk4IoJ/Tjb6k/jm+TlskAc0yEUA3WrfoCeMmBpX5UGNoqf8t/6CRU9/tKuBT7cGT7jxTNJ17gFyz2cuI3cf3/SvVjaMpGAdytkv140nt6bcyEtNU96CONLffr3l4T/Ie8HzITVZ6h2UZRvC7EHYBF7CbLGQ0UWOtsuQtHFrzWLPK6ziRL8CK2bOF/vD4z9pjlx7isd71hdxjLku8X6TnBcmlzye+GegrfJogFT1kwZWrGXdvkmGjAVu0A/l1oqIh1EysaC9sHTyOtGdjbVZzjjIe60zCnJ5Bu6xN97XcsfnjgOGqEUjNcE1CRwrw2uxtSmJdEpaIuzmaiYyj9H5SDoyzliVNNUR8Kx0dtAADlojBsvwQN6gNglCNFouk7DOVBMQRyOi2tLtYzu+DO0a4w7IlyTzilYF+TJ8WcK0nIhkAabRuV5vYRIr3n9y67OPbt3GS3ZjYYFdrqcST/z5i5dCD5045pLCK2DxxHMI0sWFtp0GVz2o3yl4q4ttV3HbIllSbESUahF8WoERq3iNZyKRkgguhKsBZHsFv9wFwRpPBCh1meALC6cxJtsyE5gK6zBRygpjl7XOdBYohjZdwqfwtjPnTuxfpVhMnBH873k48gKb4HS3lFr3sanef8/Yvtjany8KiOyw3CY1tpFcqNERquUN1YKOU7SvWMNWu7lQP3Gq5arI5pND3HFmO97s7P3YmvFjK+crDwRIRTami5bYhULdhZFca1LYIejrC+J5TtZWOF1kJlyNYMeE4BvzZzznJ2y6v1443dVC5BuC3+Q0s3uGjXu0seWhbJPqIWr3QUWjGX9pEv4yapibqRnKtRV5C3u6qVDJZXDNAtTfE3y9PyVRpE/wNaWVDDBFAtkIi7ChLQ91U0hgCX8+165dLOzaMSra+cKuPQfPoRRm4IugPL0q+PP+lEKRo4L/rryZu9Oj7S4koyJZM+3dMGOGW0hIcIngF/vDjCJhwTvKw3yvR9s2JHc7cqMb7NlwLYZ3nhD8RX+wUeSY4IdLw5ZyGY27zi4P7PcjuQ+WdxApvWByyGSO/AOexBIrr46vHPh85qH2Dz/nuce5283reGb83VOvT2w5yNbVQdzksNzhPCYoPgUo2NwzjHVIFvCIXJp3fz3AjhmwLKVO3XlN4l2h3Dj+xJsfudcBFnWdNqxqVF3me7UF8BaN6kN8z8kicsyziDAhJPtyAmlndGcTElt64sILSoahUzmjDSSkGkxImqEAkEx3vrlSjVD2HGeQ75H3p11tsIkrnQea+RZ76uEV4x5tB5E8BiZSEG8GWENOD76AzAPliII2uK6H6jpf8In+ogBF6gSv9BEFz3jo9BskT4HFIQr4IUXOYg70c+GCJF61U3DbH3oUsQRPlEbvXgOe81Dh90iehZUhqIC3u93Ad8G1EYDsFFz1Bx5FhgUfLB/8GBv6mAf440iOQKBZPAu5RHpwxFBjbjpdAhfszGo/FvyoP51Q5Ijgv/Wr0588dHoDyavgU1a+T73spkIHXN+FNebXBF/kTwUUuVjwztIqVPBtaM6nmJuw8d/x0OMEkrfAsSzuWK4aYDX9Pmy6Ngp+hT8NUGSx4BeX1sB9Et73AP8hkr/AJKi6RU30rZIqXADXXtjyvCe4z+KMIscEL684c/RnPND/A8kpCAs5FvOCjiHwKOyVPhP8ZX/QUeQlwZ8vDd3d+v/2wP8fJJ/Cyppb/xxRACtIcpiQKRWcN/3NnxYo8lfB3y5/AqRAaQWkID78AtwHJuAc6FvhAiNOeUvwI/7Qo8hhwQ+VhZ6VBOk8D/RYWKVKQG/ShDFCvTwIq9qbsK6u4XzqWX/oUeSM4EW7ZA/0Hitudu4g1YPzcPTnMD8uJs4SMuOA4Dv8KYAi9wn+HR/O0+KhwGwkM2ATmkhptprUtjCxEisiCaA0Py74D3yBZyL3C76jLPANDOB8D/AXIZnrAO+6f0TwsG2f9bLgv/YHHkV+JfgTZYFnJUsKe4DH+il1lgMeKq8Evt9qCd7nDzyK9Aoe9WH5Kz3AX43kUpvUU40mqG6vOZcOsBmWYJA5GwRf5U8HFFkp+NLSOuRDXOrRhqVBugbWa4qRLI15HrzwSnjhR4K/7g8zirwm+PGy7H4bA7faAzjOn7TcA3hDxmHAWm17BN9aAjijLhvLSov9Ru84uK4Xo90puFGWTjyHbvDQCTd60o0QCGoMPEm1S+sVgjHXE9Ie5Hzee770wtf0uuiEI50U/M2ydOJpdcBDJxnJzbiuVvUhLVPVXJW6DAYdgJXdzYJf/pUohSNlRr7Qh1Kqh1KbkShQ7LhSuWLnqtd1MK5OyIWrBW/+SvTCkWYKXldar9LbCMnjjFLCM0opAa6Y+empZG6Ahbi0BZaDvYIv9pcbUORywcPnnB7291SbNIqD4bwTDNZUdC7MlPE4wZTuQTIKkU5vT8kaN4ljL1s1aBgalXU37acDqB2EdAY47/jUn/Yo8ongp0trnw94p0fbLiTbYNaGZWs4YsTYWZPr2WYzvPRBQhasFfxaf7hRZIngV5SHe7dH24NI7gfctsE/BcrMc945VV5D8SzD/PGDc/x9+HyX7zTEF0NK5Ajdf/KGrqklvtGYUfQNl5A7uK++evq+DW/w89HM10A1UVIdT2la/u+oefeVSZPGVWbOGv6rapKp+9PcLxv5H3VAMUOGKkr7eM+f2WRSYU+bnbjiXX6/R8AAvB/+dYBNRnMBkbYyBM0pEz9XG/9k+ueV1etPsG8GYBbmPNKTOvnL6cdvnbbqWx0/PmxtfyDYtbxrpfLxUy82XVN/1Nr2fyKz6C5GJwAA";
}