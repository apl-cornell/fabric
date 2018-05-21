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
    
    public static final byte[] $classHash = new byte[] { 36, -108, -20, 103, 69,
    -68, 113, -71, -78, -60, -70, -37, 101, 40, -66, 34, 36, 118, 118, 34, 25,
    -98, 125, 66, 69, 114, 104, 91, -24, 62, -60, 119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753776000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
