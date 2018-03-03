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
    
    public static final byte[] $classHash = new byte[] { -17, 121, -48, -55,
    -113, 28, 44, 33, -128, 28, 70, 88, 55, -97, -6, 84, -127, 78, -95, -7, -53,
    -104, -122, -92, -104, 95, -115, 103, -107, -71, 90, -5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
