package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.worker.Worker;

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
    
    public static final byte[] $classHash = new byte[] { -11, 93, 76, -95, -41,
    -12, -111, -109, -111, 72, -10, 110, 37, 2, -91, -49, -110, 107, 127, -31,
    -68, 110, -93, 95, 117, 112, 84, 48, -114, 50, 49, -57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
