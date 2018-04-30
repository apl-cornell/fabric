package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.metrics.LinearMetric;
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.Matrix._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 90, -41, 110, 37, 92,
    -62, -36, 115, 20, 72, -71, -40, -99, -50, 117, 89, -30, 116, 29, 8, 29,
    -42, 50, -38, 44, 64, 0, 31, -84, 20, -120, 117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
