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
import fabric.metrics.contracts.MetricContract;
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
    
    public double computeValue();
    
    public double computeVelocity();
    
    public double computeNoise();
    
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
    
    public fabric.metrics.Metric copyOn(final fabric.worker.Store s);
    
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
        
        public double computeValue() {
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINE,
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
        
        private double computeValue(int i) {
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
        
        public double computeVelocity() {
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
        
        public double computeNoise() {
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
        
        public fabric.metrics.Metric times(double scalar) {
            final fabric.worker.Store s = getStore();
            return fabric.metrics.Metric._Impl.
              findMetric(
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
        public fabric.metrics.Metric plus(fabric.metrics.Metric other) {
            final fabric.worker.Store s = getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.LinearMetric &&
                  other.getStore().equals(getStore())) {
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
                  findMetric(
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
                  findMetric(
                    s,
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(newCs, this.get$terms()));
            }
            else if (isSingleStore() && !other.getStore().equals(getStore())) {
                return fabric.metrics.Metric._Impl.
                  findMetric(
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
              findMetric(
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
            final fabric.worker.Store s = getStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof fabric.metrics.LinearMetric &&
                  other.getStore().equals(getStore())) {
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
                  findMetric(
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
                  findMetric(
                    s,
                    ((fabric.metrics.LinearMetric)
                       new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                        fabric$metrics$LinearMetric$(newCs, this.get$terms()));
            }
            else if (isSingleStore() && !other.getStore().equals(getStore())) {
                return fabric.metrics.Metric._Impl.
                  findMetric(
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
              findMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(newCs, newTerms));
        }
        
        public fabric.metrics.Metric copyOn(final fabric.worker.Store s) {
            return fabric.metrics.Metric._Impl.
              findMetric(
                s,
                ((fabric.metrics.LinearMetric)
                   new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                    fabric$metrics$LinearMetric$(this.get$matrix(),
                                                 this.get$terms()));
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policyFor(
          fabric.metrics.contracts.Bound bound) {
            fabric.util.Map witnesses =
              ((fabric.util.HashMap)
                 new fabric.util.HashMap._Impl(this.$getStore()).$getProxy()).
              fabric$util$HashMap$();
            long currentTime = java.lang.System.currentTimeMillis();
            double base = bound.value(currentTime);
            double rate = bound.get$rate();
            for (int i = 0; i < this.get$matrix().rows(); i++) {
                double totalValue = ((fabric.metrics.LinearMetric._Impl)
                                       this.fetch()).computeValue(i);
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
                    final fabric.worker.Store s = m.getStore();
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
            final fabric.worker.Store bndStore = bound.getStore();
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      new fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Impl(bndStore).
                      $getProxy()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(
                witnesses.values());
        }
        
        public int hashCode() {
            int hash = fabric.util.Arrays._Impl.hashCode(this.get$terms()) *
              32 + this.get$matrix().hashCode();
            return hash * 32 + getStore().hashCode();
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
                  this.getStore().equals(that.getStore());
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
    
    public static final byte[] $classHash = new byte[] { -97, -5, 25, 114, 35,
    -57, -2, 68, 81, -96, 25, -15, -4, 62, 15, 122, -17, 37, 16, -99, 107, -27,
    64, 16, -36, -49, -122, 29, 60, 123, -23, 39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
