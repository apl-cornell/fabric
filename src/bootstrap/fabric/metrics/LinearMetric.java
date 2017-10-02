package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.LinkedHashMap;
import fabric.util.Map;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.util.ArrayList;
import fabric.util.HashMap;
import fabric.util.Iterator;
import fabric.util.List;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.DirectPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Matrix;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import java.util.logging.Level;

/**
 * A {@link Metric} for the entries of Cm\u20d7 where m\u20d7 is a {@link Metric} and C is
 * a {@link Matrix} of coefficients.
 *
 * Currently deprecated since it's not as easy to generalize for more and more
 * operations.
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
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(boolean useWeakCache);
    
    /**
   * @param i
   *            an index in the value vector
   * @return the freshly computed ith entry in the value vector
   */
    public double computeValue(int i);
    
    public double computeVelocity(boolean useWeakCache);
    
    public double computeNoise(boolean useWeakCache);
    
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
    
    /**
   * XXX/TODO: This is not appropriately "low contention" and still uses
   * strong operations for model parameters.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
      fabric.metrics.contracts.Bound bound, boolean useWeakCache);
    
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
        
        public double computeValue(int arg1) {
            return ((fabric.metrics.LinearMetric) fetch()).computeValue(arg1);
        }
        
        public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store arg1) {
            return ((fabric.metrics.LinearMetric) fetch()).copyOn(arg1);
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
   * The {@link Matrix} that defines the transformation applied to this
   * {@link LinearMetric}s terms.
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
        
        /**
   * The {@link Matrix} that defines the transformation applied to this
   * {@link LinearMetric}s terms's noise estimates.
   */
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
            if (matrix.rows() == 0)
                throw new java.lang.IllegalArgumentException(
                        "LinearMetric needs at least 1 coefficient row!");
            if (matrix.columns() != terms.get$length())
                throw new java.lang.IllegalArgumentException(
                        "LinearMetric coefficient columns size as the dimension of term: " +
                          matrix.toString());
            this.set$matrix(
                   ((fabric.metrics.LinearMetric._Impl)
                      this.fetch()).normalize(matrix).copy(getStore()));
            this.set$varMatrix(
                   this.get$matrix().elementMultiply(
                                       this.get$matrix()).copy(getStore()));
            initialize();
            return (fabric.metrics.LinearMetric) this.$getProxy();
        }
        
        public double computePresetR() { return 0.0; }
        
        public double computePresetB() { return 0.0; }
        
        public double computePresetV() { return 0.0; }
        
        public double computePresetN() { return 0.0; }
        
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
        
        public double computeValue(boolean useWeakCache) {
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINER,
                  "RECOMPUTING LINEAR METRIC AT {0}", getStore().name());
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
                              this.get$terms().get(i)).value(useWeakCache));
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < results.get$length(); i++) {
                result = java.lang.Math.min(result, (double) results.get(i));
            }
            return result;
        }
        
        /**
   * @param i
   *            an index in the value vector
   * @return the freshly computed ith entry in the value vector
   */
        public double computeValue(int i) {
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
        
        public double computeVelocity(boolean useWeakCache) {
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
                              this.get$terms().get(i)).velocity(useWeakCache));
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            double result = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < results.get$length(); i++) {
                result = java.lang.Math.min(result, (double) results.get(i));
            }
            return result;
        }
        
        /**
   * @param i
   *            an index in the value vector
   * @return the freshly estimated ith entry in the velocity vector
   */
        private double computeVelocity(int i) {
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
        
        public double computeNoise(boolean useWeakCache) {
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
                              this.get$terms().get(i)).noise(useWeakCache));
            }
            fabric.lang.arrays.doubleArray resultNoises =
              this.get$varMatrix().multiply(noises);
            double noise = 0;
            for (int i = 0; i < resultNoises.get$length(); i++) {
                noise = java.lang.Math.max(noise, (double) resultNoises.get(i));
            }
            return noise;
        }
        
        /**
   * @param i
   *            an index in the value vector
   * @return the freshly estimated ith entry in the noise vector
   */
        private double computeNoise(int i) {
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
            return str + ")@" + getStore().name();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            final fabric.worker.Store s = getStore();
            return fabric.metrics.Metric._Impl.
              findDerivedMetric(
                s,
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
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other) {
            final fabric.worker.Store s = getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.LinearMetric &&
                  other.$getStore().equals(s)) {
                fabric.metrics.LinearMetric that =
                  (fabric.metrics.LinearMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.util.Set termsBag =
                  ((fabric.util.TreeSet)
                     new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy(
                                                                       )).
                  fabric$util$TreeSet$();
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
                fabric.util.Iterator mIter = termsBag.iterator();
                while (mIter.hasNext()) {
                    fabric.metrics.Metric m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.$getProxy(mIter.next());
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
                    s,
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
                    s,
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(newCs, this.get$terms()));
            }
            else if (isSingleStore() && !other.$getStore().equals(s)) {
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    s,
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
                s,
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
                  other.$getStore().equals(s)) {
                fabric.metrics.LinearMetric that =
                  (fabric.metrics.LinearMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                fabric.util.Set termsBag =
                  ((fabric.util.TreeSet)
                     new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy(
                                                                       )).
                  fabric$util$TreeSet$();
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
                fabric.util.Iterator mIter = termsBag.iterator();
                while (mIter.hasNext()) {
                    fabric.metrics.Metric m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.$getProxy(mIter.next());
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
                    s,
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
                    s,
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(newCs, this.get$terms()));
            }
            else if (isSingleStore() && !other.$getStore().equals(s)) {
                return fabric.metrics.Metric._Impl.
                  findDerivedMetric(
                    s,
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
                s,
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
        
        /**
   * XXX/TODO: This is not appropriately "low contention" and still uses
   * strong operations for model parameters.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound bound, boolean useWeakCache) {
            if (isSingleStore())
                return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                          new fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DirectPolicy$(
                    (fabric.metrics.LinearMetric) this.$getProxy(), bound);
            fabric.util.Map witnesses =
              ((fabric.util.LinkedHashMap)
                 new fabric.util.LinkedHashMap._Impl(
                   this.$getStore()).$getProxy()).fabric$util$LinkedHashMap$();
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.value(currentTime);
            double rate = bound.get$rate();
            for (int i = 0; i < this.get$matrix().rows(); i++) {
                double totalValue = computeValue(i);
                double totalVelocity = ((fabric.metrics.LinearMetric._Impl)
                                          this.fetch()).computeVelocity(i);
                double totalNoise = ((fabric.metrics.LinearMetric._Impl)
                                       this.fetch()).computeNoise(i);
                double numTerms = this.get$terms().get$length();
                for (int j = 0; j < numTerms; j++) {
                    double c = this.get$matrix().get(i, j);
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value() * c;
                    double scaledV = m.velocity() * c;
                    double scaledN = m.noise() * c * c;
                    double r = scaledV - (totalVelocity - rate) / numTerms;
                    r = r / c;
                    double b = scaledX - scaledN / totalNoise *
                      (totalValue - base);
                    b = b / c;
                    if (c < 0) {
                        m = m.times(-1);
                        b = -b;
                        r = -r;
                    }
                    final fabric.worker.Store s = m.$getStore();
                    fabric.metrics.contracts.Bound witnessBound =
                      ((fabric.metrics.contracts.Bound)
                         new fabric.metrics.contracts.Bound._Impl(s).$getProxy(
                                                                       )).
                      fabric$metrics$contracts$Bound$(r, b, currentTime);
                    if (!witnesses.containsKey(m) ||
                          !((fabric.metrics.contracts.MetricContract)
                              fabric.lang.Object._Proxy.$getProxy(
                                                          witnesses.get(m))).
                          getBound().implies(witnessBound)) {
                        witnesses.put(m, m.getContract(witnessBound));
                    }
                }
            }
            fabric.lang.arrays.ObjectArray
              finalWitnesses =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.
                      $getStore()).
                fabric$lang$arrays$ObjectArray$(
                  this.get$$updateLabel(), this.get$$updateLabel().confPolicy(),
                  fabric.metrics.contracts.MetricContract._Proxy.class,
                  witnesses.size()).$getProxy();
            int i = 0;
            for (fabric.util.Iterator iter = witnesses.values().iterator();
                 iter.hasNext(); ) {
                finalWitnesses.set(
                                 i++,
                                 (fabric.metrics.contracts.MetricContract)
                                   fabric.lang.Object._Proxy.$getProxy(
                                                               iter.next()));
            }
            final fabric.worker.Store bndStore = bound.$getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                finalWitnesses);
        }
        
        public int hashCode() {
            int hash = fabric.util.Arrays._Impl.hashCode(this.get$terms()) *
              32 + this.get$matrix().hashCode();
            return hash * 32 + $getStore().hashCode();
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
    
    public static final byte[] $classHash = new byte[] { -12, -20, -89, 20, 17,
    -124, -45, -14, -61, 14, 83, -5, -104, 5, -47, 100, -96, -91, 96, 70, -104,
    -115, -75, -64, 121, -29, -87, -80, 5, 99, 100, -68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506451157000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
