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
    
    public fabric.metrics.Metric times(double scalar);
    
    /**
   * {@inheritDoc}
   * <p>
   * {@link LinearMetric}s try to consolidate local computations so that there
   * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
   * {@link #handleUpdates()}.
   */
    public fabric.metrics.Metric plus(fabric.metrics.Metric other);
    
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
        
        public fabric.metrics.Metric times(double scalar) {
            final fabric.worker.Store s = $getStore();
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                $getStore(),
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(
                      this.get$matrix().multiply(scalar), this.get$terms()));
        }
        
        /**
   * {@inheritDoc}
   * <p>
   * {@link LinearMetric}s try to consolidate local computations so that there
   * isn't unnecessary nodes in the {@link Subject}-{@link Observer} tree for
   * {@link #handleUpdates()}.
   */
        public fabric.metrics.Metric plus(fabric.metrics.Metric other) {
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
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    $getStore(),
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(combined, newTerms));
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
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    $getStore(),
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(newCs, this.get$terms()));
            }
            else if (isSingleStore() &&
                       !other.$getStore().equals($getStore())) {
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    $getStore(),
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(
                          fabric.metrics.util.Matrix._Impl.constant(1, 2, 1),
                          fabric.lang.arrays.internal.Compat.
                              convert(
                                this.$getStore(),
                                this.get$$updateLabel(),
                                this.get$$updateLabel(
                                       ).confPolicy(), new fabric.lang.Object[] { (fabric.metrics.LinearMetric) this.$getProxy(), other })));
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
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                $getStore(),
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(newCs, newTerms));
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
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    $getStore(),
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(combined, newTerms));
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
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    $getStore(),
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(newCs, this.get$terms()));
            }
            else if (isSingleStore() &&
                       !other.$getStore().equals($getStore())) {
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    $getStore(),
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(
                          fabric.metrics.util.Matrix._Impl.identity(2),
                          fabric.lang.arrays.internal.Compat.
                              convert(
                                this.$getStore(),
                                this.get$$updateLabel(),
                                this.get$$updateLabel(
                                       ).confPolicy(), new fabric.lang.Object[] { (fabric.metrics.LinearMetric) this.$getProxy(), other })));
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
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                $getStore(),
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(newCs, newTerms));
        }
        
        public fabric.metrics.DerivedMetric copyOn(
          final fabric.worker.Store s) {
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(this.get$matrix(),
                                                 this.get$terms()));
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
    
    public static final byte[] $classHash = new byte[] { 92, 55, -6, 1, -91, 6,
    -102, -14, 59, 21, -35, -59, -48, 39, -24, -40, -123, -26, 72, 117, 48, -56,
    72, 65, -44, 94, -71, -38, 64, 31, 33, 102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491862429000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
