package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.util.Arrays;
import fabric.util.HashMap;
import fabric.util.Iterator;
import fabric.util.List;
import fabric.util.Map;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.DerivedMetricContract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Matrix;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

/**
 * A {@link Metric} for the entries of Cm\u20d7 where m\u20d7 is a {@link Metric} and C is
 * a {@link Matrix} of coefficients.
 */
public interface LinearMetric extends fabric.metrics.DerivedMetric {
    public fabric.metrics.util.Matrix get$matrix();
    
    public fabric.metrics.util.Matrix set$matrix(
      fabric.metrics.util.Matrix val);
    
    public fabric.metrics.util.Matrix get$varMatrix();
    
    public fabric.metrics.util.Matrix set$varMatrix(fabric.metrics.util.Matrix val);
    
    /**
   * @param store
   *        the Store that holds this {@link Metric}
   * @param matrix
   *        The coefficients matrix as a double[][]
   * @param terms
   *        The {@link Metric}s this applies to
   */
    public fabric.metrics.LinearMetric fabric$metrics$LinearMetric$(
      fabric.metrics.util.Matrix matrix, fabric.lang.arrays.ObjectArray terms);
    
    public double value();
    
    public double velocity();
    
    public double noise();
    
    public fabric.metrics.contracts.MetricContract createContract(
      fabric.metrics.contracts.Bound bound);
    
    public java.lang.String toString();
    
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
   * {@inheritDoc}
   * <p>
   * {@link LinearMetric}s try to consolidate local computations so that there
   * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
   * {@link #handleUpdates()}.
   */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
   * {@inheritDoc}
   * <p>
   * {@link LinearMetric}s try to consolidate local computations so that there
   * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
   * {@link #handleUpdates()}.
   */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    public fabric.metrics.DerivedMetric copyOn(final fabric.worker.Store s);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policyFor(
      fabric.metrics.contracts.Bound bound);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.LinearMetric {
        public fabric.metrics.util.Matrix get$matrix() {
            return ((fabric.metrics.LinearMetric._Impl) fetch()).get$matrix();
        }
        
        public fabric.metrics.util.Matrix set$matrix(
          fabric.metrics.util.Matrix val) {
            return ((fabric.metrics.LinearMetric._Impl) fetch()).set$matrix(
                                                                   val);
        }
        
        public fabric.metrics.util.Matrix get$varMatrix() {
            return ((fabric.metrics.LinearMetric._Impl) fetch()).get$varMatrix(
                                                                   );
        }
        
        public fabric.metrics.util.Matrix set$varMatrix(
          fabric.metrics.util.Matrix val) {
            return ((fabric.metrics.LinearMetric._Impl) fetch()).set$varMatrix(
                                                                   val);
        }
        
        public fabric.metrics.LinearMetric fabric$metrics$LinearMetric$(
          fabric.metrics.util.Matrix arg1,
          fabric.lang.arrays.ObjectArray arg2) {
            return ((fabric.metrics.LinearMetric) fetch()).
              fabric$metrics$LinearMetric$(arg1, arg2);
        }
        
        public _Proxy(LinearMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.LinearMetric {
        public fabric.metrics.util.Matrix get$matrix() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.matrix;
        }
        
        public fabric.metrics.util.Matrix set$matrix(
          fabric.metrics.util.Matrix val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.matrix = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The {@link Matrix} that defines this {@link Metric} along with the terms.
   */
        protected fabric.metrics.util.Matrix matrix;
        
        public fabric.metrics.util.Matrix get$varMatrix() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.varMatrix;
        }
        
        public fabric.metrics.util.Matrix set$varMatrix(
          fabric.metrics.util.Matrix val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.varMatrix = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.Matrix varMatrix;
        
        /**
   * @param store
   *        the Store that holds this {@link Metric}
   * @param matrix
   *        The coefficients matrix as a double[][]
   * @param terms
   *        The {@link Metric}s this applies to
   */
        public fabric.metrics.LinearMetric fabric$metrics$LinearMetric$(
          fabric.metrics.util.Matrix matrix,
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$DerivedMetric$(terms);
            this.set$matrix(((fabric.metrics.LinearMetric._Impl)
                               this.fetch()).normalize(matrix));
            this.set$varMatrix(
                   this.get$matrix().elementMultiply(this.get$matrix()));
            if (matrix.rows() == 0)
                throw new java.lang.IllegalArgumentException(
                        "LinearMetric needs at least 1 coefficient row!");
            if (matrix.columns() != terms.get$length())
                throw new java.lang.IllegalArgumentException(
                        "LinearMetric coefficient columns size as the dimension of term: " +
                          matrix.toString());
            initialize();
            return (fabric.metrics.LinearMetric) this.$getProxy();
        }
        
        /**
   * Utility to normalize the {@link Metric} terms and {@link Matrix}
   * associated with this {@link LinearMetric}.
   *
   * @param matrix
   *        the original matrix
   * @return the matrix rearranged for sorted terms, sorted rows, and
   *       deduplication of rows.
   */
        private fabric.metrics.util.Matrix normalize(
          fabric.metrics.util.Matrix matrix) {
            fabric.lang.arrays.ObjectArray normTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, normTerms,
                                               0,
                                               this.get$terms().get$length());
            fabric.util.Arrays._Impl.sort(normTerms);
            fabric.metrics.util.Matrix colNormed =
              ((fabric.metrics.util.Matrix)
                 new fabric.metrics.util.Matrix._Impl(
                   this.$getStore()).$getProxy()).fabric$metrics$util$Matrix$(
                                                    matrix.rows(),
                                                    this.get$terms().get$length(
                                                                       ));
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                int oldIdx =
                  fabric.util.Arrays._Impl.asList(this.get$terms()).
                  indexOf((fabric.metrics.Metric) normTerms.get(i));
                colNormed.setColumn(i, matrix.getColumn(oldIdx));
            }
            this.set$terms(normTerms);
            return fabric.metrics.LinearMetric._Impl.normalizeRows(
                                                       colNormed, 0,
                                                       colNormed.rows());
        }
        
        /**
   * Utility for normalizing a coefficients {@link Matrix} by sorting rows and
   * eliminating duplicates.
   *
   * @param m
   *        the {@link Matrix} being normalized
   * @return the normalized {@link Matrix}
   */
        private static fabric.metrics.util.Matrix normalizeRows(
          fabric.metrics.util.Matrix m, int start, int end) {
            if (end - start > 1) {
                int mid = start + (end - start) / 2;
                fabric.metrics.util.Matrix sub1 =
                  fabric.metrics.LinearMetric._Impl.normalizeRows(m, start,
                                                                  mid);
                fabric.metrics.util.Matrix sub2 =
                  fabric.metrics.LinearMetric._Impl.normalizeRows(m, mid, end);
                fabric.metrics.util.Matrix
                  merged =
                  ((fabric.metrics.util.Matrix)
                     new fabric.
                       metrics.
                       util.
                       Matrix.
                       _Impl(
                       fabric.metrics.LinearMetric._Static._Proxy.$instance.
                           $getStore()).
                     $getProxy()).fabric$metrics$util$Matrix$(end - start,
                                                              m.columns());
                int idx1 = 0;
                int idx2 = 0;
                for (int i = 0; i < merged.rows(); i++) {
                    int comp =
                      fabric.metrics.LinearMetric._Impl.rowCompare(
                                                          sub1.getRow(idx1),
                                                          sub2.getRow(idx2));
                    if (comp < 0) {
                        merged.setRow(i, sub1.getRow(idx1++));
                    } else if (comp > 0) {
                        merged.setRow(i, sub2.getRow(idx2++));
                    } else {
                        idx2++;
                        merged.removeRow(i--);
                    }
                }
                return merged;
            }
            else if (start == end) {
                return ((fabric.metrics.util.Matrix)
                          new fabric.
                            metrics.
                            util.
                            Matrix.
                            _Impl(
                            fabric.metrics.LinearMetric._Static._Proxy.
                              $instance.
                                $getStore()).
                          $getProxy()).fabric$metrics$util$Matrix$(0, 0);
            }
            return fabric.metrics.util.Matrix._Impl.singleRow(m.getRow(start));
        }
        
        /**
   * Utility for comparing two rows of a {@link Matrix} during
   * {@link #normalizeRows(Matrix)}.
   *
   * @param r1
   *        the first row as a double[]
   * @param r2
   *        the second row as a double[]
   * @return 0 if r1 == r2, 1 if r1 > r2 (lexicographically), and -1
   *       otherwise.
   */
        private static int rowCompare(fabric.lang.arrays.doubleArray r1,
                                      fabric.lang.arrays.doubleArray r2) {
            for (int i = 0; i < r1.get$length(); i++) {
                if ((double) r1.get(i) < (double) r2.get(i)) return -1;
                if ((double) r1.get(i) > (double) r2.get(i)) return 1;
            }
            return 0;
        }
        
        public double value() {
            fabric.lang.arrays.doubleArray values =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                values.set(i,
                           ((fabric.metrics.Metric)
                              this.get$terms().get(i)).value());
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < results.get$length(); i++) {
                result = java.lang.Math.min(result, (double) results.get(i));
            }
            return result;
        }
        
        private double value(int i) {
            fabric.lang.arrays.doubleArray values =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            for (int j = 0; j < this.get$terms().get$length(); j++) {
                values.set(j,
                           ((fabric.metrics.Metric)
                              this.get$terms().get(j)).value());
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            return (double) results.get(i);
        }
        
        public double velocity() {
            fabric.lang.arrays.doubleArray values =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                values.set(i,
                           ((fabric.metrics.Metric)
                              this.get$terms().get(i)).velocity());
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < results.get$length(); i++) {
                result = java.lang.Math.min(result, (double) results.get(i));
            }
            return result;
        }
        
        private double velocity(int i) {
            fabric.lang.arrays.doubleArray values =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            for (int j = 0; j < this.get$terms().get$length(); j++) {
                values.set(j,
                           ((fabric.metrics.Metric)
                              this.get$terms().get(j)).velocity());
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            return (double) results.get(i);
        }
        
        public double noise() {
            fabric.lang.arrays.doubleArray noises =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                noises.set(i,
                           ((fabric.metrics.Metric)
                              this.get$terms().get(i)).noise());
            }
            fabric.lang.arrays.doubleArray resultNoises =
              this.get$varMatrix().multiply(noises);
            double noise = 0;
            for (int i = 0; i < resultNoises.get$length(); i++) {
                noise = java.lang.Math.max(noise, (double) resultNoises.get(i));
            }
            return noise;
        }
        
        private double noise(int i) {
            fabric.lang.arrays.doubleArray noises =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            for (int j = 0; j < this.get$terms().get$length(); j++) {
                noises.set(j,
                           ((fabric.metrics.Metric)
                              this.get$terms().get(j)).noise());
            }
            fabric.lang.arrays.doubleArray resultNoises =
              this.get$varMatrix().multiply(noises);
            return (double) resultNoises.get(i);
        }
        
        public fabric.metrics.contracts.MetricContract createContract(
          fabric.metrics.contracts.Bound bound) {
            return ((fabric.metrics.contracts.DerivedMetricContract)
                      new fabric.metrics.contracts.DerivedMetricContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$DerivedMetricContract$(
                (fabric.metrics.LinearMetric) this.$getProxy(), bound);
        }
        
        public java.lang.String toString() {
            java.lang.String str = "min(";
            boolean nonEmpty = false;
            for (int i = 0; i < this.get$matrix().rows(); i++) {
                boolean nonEmptyRow = false;
                for (int j = 0; j < this.get$terms().get$length(); j++) {
                    if (this.get$matrix().get(i, j) == 0) continue;
                    if (nonEmptyRow) {
                        str += " + ";
                    } else if (nonEmpty) {
                        str += ", ";
                    }
                    nonEmptyRow = true;
                    str +=
                      "(" +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.Metric)
                                        this.get$terms().get(j))) +
                      " * " +
                      this.get$matrix().get(i, j) +
                      ")";
                }
                if (nonEmptyRow) nonEmpty = true;
            }
            return str + ")@" + $getStore().name();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            final fabric.worker.Store s = $getStore();
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(this.get$matrix().multiply(scalar),
                                           this.get$terms());
        }
        
        /**
   * {@inheritDoc}
   * <p>
   * {@link LinearMetric}s try to consolidate local computations so that there
   * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
   * {@link #handleUpdates()}.
   */
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            final fabric.worker.Store s = $getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.LinearMetric &&
                  other.$getStore().equals($getStore())) {
                fabric.metrics.LinearMetric that =
                  (fabric.metrics.LinearMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.util.Set termsBag =
                  (fabric.util.TreeSet)
                    new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy();
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(this.get$terms()));
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(that.get$terms()));
                fabric.metrics.util.Matrix thisExpanded =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(this.get$matrix().rows(),
                                              termsBag.size());
                fabric.metrics.util.Matrix thatExpanded =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(that.get$matrix().rows(),
                                              termsBag.size());
                fabric.lang.arrays.ObjectArray newTerms =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(
                      this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          fabric.metrics.Metric._Proxy.class,
                                          termsBag.size()).$getProxy();
                int aggIdx = 0;
                int thisIdx = 0;
                int thatIdx = 0;
                fabric.util.Iterator iter = termsBag.iterator();
                while (iter.hasNext()) {
                    fabric.metrics.Metric m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (((fabric.metrics.Metric) this.get$terms().get(thisIdx)).
                          equals(m)) {
                        thisExpanded.setColumn(
                                       aggIdx,
                                       this.get$matrix().getColumn(thisIdx++));
                    }
                    if (((fabric.metrics.Metric) that.get$terms().get(thatIdx)).
                          equals(m)) {
                        thatExpanded.setColumn(
                                       aggIdx,
                                       that.get$matrix().getColumn(thatIdx++));
                    }
                    newTerms.set(aggIdx++, m);
                }
                fabric.metrics.util.Matrix combined =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(thisExpanded.rows() *
                                                  thatExpanded.rows(),
                                              newTerms.get$length());
                for (int i = 0; i < thisExpanded.rows(); i++) {
                    for (int j = 0; j < thatExpanded.rows(); j++) {
                        int row = i * thatExpanded.rows() + j;
                        for (int k = 0; k < newTerms.get$length(); k++) {
                            combined.set(row,
                                         k,
                                         thisExpanded.get(i, k) +
                                             thatExpanded.get(j, k));
                        }
                    }
                }
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(combined, newTerms);
            }
            else if (fabric.util.Arrays._Impl.asList(this.get$terms()).
                       indexOf(other) >= 0) {
                int idx =
                  fabric.util.Arrays._Impl.asList(this.get$terms()).indexOf(
                                                                      other);
                fabric.metrics.util.Matrix newCs = this.get$matrix().copy();
                for (int i = 0; i < newCs.rows(); i++) {
                    newCs.set(i, idx, newCs.get(i, idx) + 1);
                }
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(newCs, this.get$terms());
            }
            else if (isSingleStore() &&
                       !other.$getStore().equals($getStore())) {
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(
                    fabric.metrics.util.Matrix._Impl.constant(1, 2, 1),
                    fabric.lang.arrays.internal.Compat.
                        convert(
                          this.$getStore(), this.get$$updateLabel(),
                          this.get$$updateLabel().confPolicy(),
                          new fabric.lang.Object[] { (fabric.metrics.LinearMetric) this.$getProxy(), other }));
            }
            fabric.lang.arrays.ObjectArray newTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length() +
                                          1).$getProxy();
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            newTerms.set(this.get$terms().get$length(), other);
            fabric.util.Arrays._Impl.sort(newTerms, 0, newTerms.get$length());
            int idx = fabric.util.Arrays._Impl.asList(newTerms).indexOf(other);
            fabric.lang.arrays.doubleArray newCol =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$matrix().rows()).$getProxy();
            for (int i = 0; i < newCol.get$length(); i++) { newCol.set(i, 1); }
            fabric.metrics.util.Matrix newCs = this.get$matrix().copy();
            newCs.insertColumn(idx, newCol);
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(newCs, newTerms);
        }
        
        /**
   * {@inheritDoc}
   * <p>
   * {@link LinearMetric}s try to consolidate local computations so that there
   * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
   * {@link #handleUpdates()}.
   */
        public fabric.metrics.Metric min(fabric.metrics.Metric other) {
            final fabric.worker.Store s = $getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.LinearMetric &&
                  other.$getStore().equals($getStore())) {
                fabric.metrics.LinearMetric that =
                  (fabric.metrics.LinearMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.util.Set termsBag =
                  (fabric.util.TreeSet)
                    new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy();
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(this.get$terms()));
                termsBag.addAll(
                           fabric.util.Arrays._Impl.asList(that.get$terms()));
                fabric.metrics.util.Matrix thisExpanded =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(this.get$matrix().rows(),
                                              termsBag.size());
                fabric.metrics.util.Matrix thatExpanded =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(that.get$matrix().rows(),
                                              termsBag.size());
                fabric.lang.arrays.ObjectArray newTerms =
                  (fabric.lang.arrays.ObjectArray)
                    new fabric.lang.arrays.ObjectArray._Impl(
                      this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          fabric.metrics.Metric._Proxy.class,
                                          termsBag.size()).$getProxy();
                int aggIdx = 0;
                int thisIdx = 0;
                int thatIdx = 0;
                fabric.util.Iterator iter = termsBag.iterator();
                while (iter.hasNext()) {
                    fabric.metrics.Metric m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (((fabric.metrics.Metric) this.get$terms().get(thisIdx)).
                          equals(m)) {
                        thisExpanded.setColumn(
                                       aggIdx,
                                       this.get$matrix().getColumn(thisIdx++));
                    }
                    if (((fabric.metrics.Metric) that.get$terms().get(thatIdx)).
                          equals(m)) {
                        thatExpanded.setColumn(
                                       aggIdx,
                                       that.get$matrix().getColumn(thatIdx++));
                    }
                    newTerms.set(aggIdx++, m);
                }
                fabric.metrics.util.Matrix combined =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(thisExpanded.rows() +
                                                  thatExpanded.rows(),
                                              newTerms.get$length());
                for (int i = 0; i < thisExpanded.rows(); i++) {
                    for (int j = 0; j < newTerms.get$length(); j++) {
                        combined.set(i, j, thisExpanded.get(i, j));
                        combined.set(i + thisExpanded.rows(), j,
                                     thatExpanded.get(i, j));
                    }
                }
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(combined, newTerms);
            }
            else if (fabric.util.Arrays._Impl.asList(this.get$terms()).
                       indexOf(other) >= 0) {
                int idx =
                  fabric.util.Arrays._Impl.asList(this.get$terms()).indexOf(
                                                                      other);
                fabric.metrics.util.Matrix newCs = this.get$matrix().copy();
                fabric.lang.arrays.doubleArray newRow =
                  (fabric.lang.arrays.doubleArray)
                    new fabric.lang.arrays.doubleArray._Impl(
                      this.$getStore()).fabric$lang$arrays$doubleArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          this.get$terms().get$length(
                                                             )).$getProxy();
                newRow.set(idx, 1);
                newCs.addRow(newRow);
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(newCs, this.get$terms());
            }
            else if (isSingleStore() &&
                       !other.$getStore().equals($getStore())) {
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(
                    fabric.metrics.util.Matrix._Impl.identity(2),
                    fabric.lang.arrays.internal.Compat.
                        convert(
                          this.$getStore(), this.get$$updateLabel(),
                          this.get$$updateLabel().confPolicy(),
                          new fabric.lang.Object[] { (fabric.metrics.LinearMetric) this.$getProxy(), other }));
            }
            fabric.lang.arrays.ObjectArray newTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length() +
                                          1).$getProxy();
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, newTerms, 0,
                                               this.get$terms().get$length());
            newTerms.set(this.get$terms().get$length(), other);
            fabric.util.Arrays._Impl.sort(newTerms, 0, newTerms.get$length());
            int idx = fabric.util.Arrays._Impl.asList(newTerms).indexOf(other);
            fabric.metrics.util.Matrix newCs = this.get$matrix().copy();
            newCs.addColumn(
                    (fabric.lang.arrays.doubleArray)
                      new fabric.lang.arrays.doubleArray._Impl(
                        this.$getStore()).fabric$lang$arrays$doubleArray$(
                                            this.get$$updateLabel(),
                                            this.get$$updateLabel().confPolicy(
                                                                      ),
                                            this.get$matrix().rows()).$getProxy(
                                                                        ));
            fabric.lang.arrays.doubleArray newRow =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().get$length() +
                                          1).$getProxy();
            newRow.set(idx, 1);
            newCs.addRow(newRow);
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(newCs, newTerms);
        }
        
        public fabric.metrics.DerivedMetric copyOn(
          final fabric.worker.Store s) {
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(this.get$matrix(), this.get$terms());
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policyFor(
          fabric.metrics.contracts.Bound bound) {
            fabric.util.Map witnesses =
              (fabric.util.HashMap)
                new fabric.util.HashMap._Impl(this.$getStore()).$getProxy();
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.get$rate() *
              (currentTime - bound.get$startTime()) + bound.get$base();
            double rate = bound.get$rate();
            for (int i = 0; i < this.get$matrix().rows(); i++) {
                double totalValue = ((fabric.metrics.LinearMetric._Impl)
                                       this.fetch()).value(i);
                double totalVelocity = ((fabric.metrics.LinearMetric._Impl)
                                          this.fetch()).velocity(i);
                double totalNoise = ((fabric.metrics.LinearMetric._Impl)
                                       this.fetch()).noise(i);
                double numTerms = this.get$terms().get$length();
                for (int j = 0; j < numTerms; j++) {
                    double c = this.get$matrix().get(i, j);
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value() * c;
                    double scaledV = m.velocity() * c;
                    double scaledN = m.noise() * c * c;
                    double r = scaledV - (totalVelocity - rate) / numTerms;
                    r /= c;
                    double b = scaledX - scaledN / totalNoise *
                      (totalValue - base);
                    b /= c;
                    if (c < 0) {
                        m = m.times(-1);
                        b = -b;
                        r = -r;
                        fabric.metrics.contracts.Bound witnessBound =
                          ((fabric.metrics.contracts.Bound)
                             new fabric.metrics.contracts.Bound._Impl(
                               this.$getStore()).$getProxy()).
                          fabric$metrics$contracts$Bound$(r, b, currentTime);
                        if (!witnesses.containsKey(m) ||
                              !((fabric.metrics.contracts.MetricContract)
                                  fabric.lang.Object._Proxy.$getProxy(
                                                              witnesses.get(
                                                                          m))).
                              getBound().implies(witnessBound)) {
                            witnesses.put(m, m.getContract(witnessBound));
                        }
                    }
                    else {
                        fabric.metrics.contracts.Bound witnessBound =
                          ((fabric.metrics.contracts.Bound)
                             new fabric.metrics.contracts.Bound._Impl(
                               this.$getStore()).$getProxy()).
                          fabric$metrics$contracts$Bound$(r, b, currentTime);
                        if (!witnesses.containsKey(m) ||
                              !((fabric.metrics.contracts.MetricContract)
                                  fabric.lang.Object._Proxy.$getProxy(
                                                              witnesses.get(
                                                                          m))).
                              getBound().implies(witnessBound)) {
                            witnesses.put(m, m.getContract(witnessBound));
                        }
                    }
                }
            }
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(this.$getStore()).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                witnesses.values());
        }
        
        public int hashCode() {
            fabric.util.List l =
              ((fabric.util.ArrayList)
                 new fabric.util.ArrayList._Impl(this.$getStore()).$getProxy()).
              fabric$util$ArrayList$(this.get$terms().get$length() + 2);
            l.add(this.get$matrix());
            l.add(fabric.lang.WrappedJavaInlineable.$wrap($getStore()));
            l.addAll(fabric.util.Arrays._Impl.asList(this.get$terms()));
            return l.hashCode();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.LinearMetric) {
                fabric.metrics.LinearMetric that =
                  (fabric.metrics.LinearMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                return this.get$matrix().equals(that.get$matrix()) &&
                  fabric.util.Arrays._Impl.deepEquals(this.get$terms(),
                                                      that.get$terms()) &&
                  this.$getStore().equals(that.$getStore());
            }
            return false;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.LinearMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.matrix, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.varMatrix, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.matrix = (fabric.metrics.util.Matrix)
                            $readRef(fabric.metrics.util.Matrix._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.varMatrix = (fabric.metrics.util.Matrix)
                               $readRef(fabric.metrics.util.Matrix._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.LinearMetric._Impl src =
              (fabric.metrics.LinearMetric._Impl) other;
            this.matrix = src.matrix;
            this.varMatrix = src.varMatrix;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.LinearMetric._Static {
            public _Proxy(fabric.metrics.LinearMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.LinearMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  LinearMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.LinearMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.LinearMetric._Static._Impl.class);
                $instance = (fabric.metrics.LinearMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.LinearMetric._Static {
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
                return new fabric.metrics.LinearMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 52, 13, 112, -24, 52,
    -12, 72, -114, -37, -61, 28, 70, 122, 26, 63, -57, -100, -39, 115, -108, 15,
    38, 77, -59, -3, -69, -102, -15, -83, 32, -117, 25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491848575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
