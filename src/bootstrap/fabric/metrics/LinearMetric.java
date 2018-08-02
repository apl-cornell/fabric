package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;
import fabric.metrics.contracts.Bound;
import fabric.metrics.util.Matrix;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

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
      fabric.metrics.util.Matrix matrix, fabric.metrics.Metric[] terms);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @param i
   *            an index in the value vector
   * @return the freshly computed ith entry in the value vector
   */
    public double computeValue(int i);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
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
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
                      final fabric.worker.Store str);
    
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
          fabric.metrics.util.Matrix arg1, fabric.metrics.Metric[] arg2) {
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
          fabric.metrics.util.Matrix matrix, fabric.metrics.Metric[] terms) {
            fabric$metrics$DerivedMetric$(terms);
            if (matrix.rows() == 0)
                throw new java.lang.IllegalArgumentException(
                        "LinearMetric needs at least 1 coefficient row!");
            if (matrix.columns() != terms.length)
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
            fabric.metrics.Metric[] normTerms =
              new fabric.metrics.Metric[this.get$terms().length()];
            java.lang.System.arraycopy(this.get$terms().array(), 0, normTerms,
                                       0, this.get$terms().length());
            java.util.Arrays.sort(normTerms);
            fabric.metrics.util.Matrix colNormed =
              ((fabric.metrics.util.Matrix)
                 new fabric.metrics.util.Matrix._Impl(
                   this.$getStore()).$getProxy()).fabric$metrics$util$Matrix$(
                                                    matrix.rows(),
                                                    this.get$terms().length());
            for (int i = 0; i < this.get$terms().length(); i++) {
                int oldIdx =
                  java.util.Arrays.asList(this.get$terms().array()).
                  indexOf(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(normTerms[i]));
                colNormed.setColumn(i, matrix.getColumn(oldIdx));
            }
            this.set$terms(
                   fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                                  normTerms));
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
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            fabric.lang.arrays.doubleArray values =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().length()).$getProxy();
            for (int i = 0; i < this.get$terms().length(); i++) {
                values.set(i, term(i).value(weakStats));
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
                                      this.get$terms().length()).$getProxy();
            for (int j = 0; j < this.get$terms().length(); j++) {
                values.set(j, term(j).value());
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            return (double) results.get(i);
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.lang.arrays.doubleArray values =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().length()).$getProxy();
            for (int i = 0; i < this.get$terms().length(); i++) {
                values.set(i, term(i).velocity(weakStats));
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
                                      this.get$terms().length()).$getProxy();
            for (int j = 0; j < this.get$terms().length(); j++) {
                values.set(j, term(j).velocity());
            }
            fabric.lang.arrays.doubleArray results =
              this.get$matrix().multiply(values);
            return (double) results.get(i);
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            fabric.lang.arrays.doubleArray noises =
              (fabric.lang.arrays.doubleArray)
                new fabric.lang.arrays.doubleArray._Impl(
                  this.$getStore()).fabric$lang$arrays$doubleArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      this.get$terms().length()).$getProxy();
            for (int i = 0; i < this.get$terms().length(); i++) {
                noises.set(i, term(i).noise(weakStats));
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
                                      this.get$terms().length()).$getProxy();
            for (int j = 0; j < this.get$terms().length(); j++) {
                noises.set(j, term(j).noise());
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
                for (int j = 0; j < this.get$terms().length(); j++) {
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
                          fabric.lang.WrappedJavaInlineable.$unwrap(term(j))) +
                      " * " +
                      this.get$matrix().get(i, j) +
                      ")";
                }
                if (nonEmptyRow) nonEmpty = true;
            }
            return str + ")@" + getStore().name();
        }
        
        public fabric.metrics.DerivedMetric times(double scalar) {
            fabric.metrics.Metric[] termsCopy =
              new fabric.metrics.Metric[this.get$terms().length()];
            for (int i = 0; i < this.get$terms().length(); i++)
                termsCopy[i] = term(i);
            final fabric.worker.Store s = getStore();
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(this.get$matrix().multiply(scalar),
                                           termsCopy);
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
                java.util.Set termsBag = new java.util.TreeSet();
                termsBag.addAll(
                           java.util.Arrays.asList(this.get$terms().array()));
                termsBag.addAll(
                           java.util.Arrays.asList(that.get$terms().array()));
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
                fabric.metrics.Metric[] newTerms =
                  new fabric.metrics.Metric[termsBag.size()];
                int aggIdx = 0;
                int thisIdx = 0;
                int thatIdx = 0;
                java.util.Iterator mIter = termsBag.iterator();
                while (mIter.hasNext()) {
                    fabric.metrics.Metric
                      m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              mIter.next()));
                    if (this.term(thisIdx).equals(m)) {
                        thisExpanded.setColumn(
                                       aggIdx,
                                       this.get$matrix().getColumn(thisIdx++));
                    }
                    if (that.term(thatIdx).equals(m)) {
                        thatExpanded.setColumn(
                                       aggIdx,
                                       that.get$matrix().getColumn(thatIdx++));
                    }
                    newTerms[aggIdx++] = m;
                }
                fabric.metrics.util.Matrix combined =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(thisExpanded.rows() *
                                                  thatExpanded.rows(),
                                              newTerms.length);
                for (int i = 0; i < thisExpanded.rows(); i++) {
                    for (int j = 0; j < thatExpanded.rows(); j++) {
                        int row = i * thatExpanded.rows() + j;
                        for (int k = 0; k < newTerms.length; k++) {
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
            else if (java.util.Arrays.asList(this.get$terms().array()).
                       indexOf(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.$unwrap(other)) >=
                       0) {
                int idx =
                  java.util.Arrays.asList(this.get$terms().array()).
                  indexOf((java.lang.Object)
                            fabric.lang.WrappedJavaInlineable.$unwrap(other));
                fabric.metrics.util.Matrix newCs = this.get$matrix().copy();
                for (int i = 0; i < newCs.rows(); i++) {
                    newCs.set(i, idx, newCs.get(i, idx) + 1);
                }
                fabric.metrics.Metric[] termsCopy =
                  new fabric.metrics.Metric[this.get$terms().length()];
                for (int i = 0; i < this.get$terms().length(); i++)
                    termsCopy[i] = term(i);
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(newCs, termsCopy);
            }
            else if (isSingleStore() && !other.$getStore().equals(s)) {
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(
                    fabric.metrics.util.Matrix._Impl.constant(1, 2, 1),
                    new fabric.metrics.Metric[] { (fabric.metrics.LinearMetric)
                                                    this.$getProxy(),
                      other });
            }
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[this.get$terms().length() + 1];
            for (int i = 0; i < this.get$terms().length(); i++)
                newTerms[i] = term(i);
            newTerms[this.get$terms().length()] = other;
            java.util.Arrays.sort(newTerms, 0, newTerms.length);
            int idx =
              java.util.Arrays.asList(newTerms).
              indexOf((java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.$unwrap(other));
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
                  other.$getStore().equals(s)) {
                fabric.metrics.LinearMetric that =
                  (fabric.metrics.LinearMetric)
                    fabric.lang.Object._Proxy.$getProxy(other);
                java.util.Set termsBag = new java.util.TreeSet();
                termsBag.addAll(
                           java.util.Arrays.asList(this.get$terms().array()));
                termsBag.addAll(
                           java.util.Arrays.asList(that.get$terms().array()));
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
                fabric.metrics.Metric[] newTerms =
                  new fabric.metrics.Metric[termsBag.size()];
                int aggIdx = 0;
                int thisIdx = 0;
                int thatIdx = 0;
                java.util.Iterator mIter = termsBag.iterator();
                while (mIter.hasNext()) {
                    fabric.metrics.Metric
                      m =
                      (fabric.metrics.Metric)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              mIter.next()));
                    if (this.term(thisIdx).equals(m)) {
                        thisExpanded.setColumn(
                                       aggIdx,
                                       this.get$matrix().getColumn(thisIdx++));
                    }
                    if (that.term(thatIdx).equals(m)) {
                        thatExpanded.setColumn(
                                       aggIdx,
                                       that.get$matrix().getColumn(thatIdx++));
                    }
                    newTerms[aggIdx++] = m;
                }
                fabric.metrics.util.Matrix combined =
                  ((fabric.metrics.util.Matrix)
                     new fabric.metrics.util.Matrix._Impl(
                       this.$getStore()).$getProxy()).
                  fabric$metrics$util$Matrix$(thisExpanded.rows() +
                                                  thatExpanded.rows(),
                                              newTerms.length);
                for (int i = 0; i < thisExpanded.rows(); i++) {
                    for (int j = 0; j < newTerms.length; j++) {
                        combined.set(i, j, thisExpanded.get(i, j));
                        combined.set(i + thisExpanded.rows(), j,
                                     thatExpanded.get(i, j));
                    }
                }
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(combined, newTerms);
            }
            else if (java.util.Arrays.asList(this.get$terms().array()).
                       indexOf(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.$unwrap(other)) >=
                       0) {
                int idx =
                  java.util.Arrays.asList(this.get$terms().array()).
                  indexOf((java.lang.Object)
                            fabric.lang.WrappedJavaInlineable.$unwrap(other));
                fabric.metrics.util.Matrix newCs = this.get$matrix().copy();
                fabric.lang.arrays.doubleArray newRow =
                  (fabric.lang.arrays.doubleArray)
                    new fabric.lang.arrays.doubleArray._Impl(
                      this.$getStore()).fabric$lang$arrays$doubleArray$(
                                          this.get$$updateLabel(),
                                          this.get$$updateLabel().confPolicy(),
                                          this.get$terms().length()).$getProxy(
                                                                       );
                newRow.set(idx, 1);
                newCs.addRow(newRow);
                fabric.metrics.Metric[] termsCopy =
                  new fabric.metrics.Metric[this.get$terms().length()];
                for (int i = 0; i < this.get$terms().length(); i++)
                    termsCopy[i] = term(i);
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(newCs, termsCopy);
            }
            else if (isSingleStore() && !other.$getStore().equals(s)) {
                return ((fabric.metrics.LinearMetric)
                          new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
                  fabric$metrics$LinearMetric$(
                    fabric.metrics.util.Matrix._Impl.identity(2),
                    new fabric.metrics.Metric[] { (fabric.metrics.LinearMetric)
                                                    this.$getProxy(),
                      other });
            }
            fabric.metrics.Metric[] newTerms =
              new fabric.metrics.Metric[this.get$terms().length() + 1];
            for (int i = 0; i < this.get$terms().length(); i++)
                newTerms[i] = term(i);
            newTerms[this.get$terms().length()] = other;
            java.util.Arrays.sort(newTerms, 0, newTerms.length);
            int idx =
              java.util.Arrays.asList(newTerms).
              indexOf((java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.$unwrap(other));
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
                                      this.get$terms().length() + 1).$getProxy(
                                                                       );
            newRow.set(idx, 1);
            newCs.addRow(newRow);
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(newCs, newTerms);
        }
        
        public fabric.metrics.DerivedMetric copyOn(
          final fabric.worker.Store s) {
            fabric.metrics.Metric[] termsCopy =
              new fabric.metrics.Metric[this.get$terms().length()];
            for (int i = 0; i < this.get$terms().length(); i++)
                termsCopy[i] = term(i);
            return ((fabric.metrics.LinearMetric)
                      new fabric.metrics.LinearMetric._Impl(s).$getProxy()).
              fabric$metrics$LinearMetric$(this.get$matrix(), termsCopy);
        }
        
        /**
   * XXX/TODO: This is not appropriately "low contention" and still uses
   * strong operations for model parameters.
   */
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store str) {
            if (isSingleStore())
                return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                         singleton;
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            long currentTime = java.lang.System.currentTimeMillis();
            double baseNow =
              fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                         currentTime);
            for (int i = 0; i < this.get$matrix().rows(); i++) {
                double totalValue = computeValue(i);
                double totalVelocity = ((fabric.metrics.LinearMetric._Impl)
                                          this.fetch()).computeVelocity(i);
                double totalNoise = ((fabric.metrics.LinearMetric._Impl)
                                       this.fetch()).computeNoise(i);
                double numTerms = this.get$terms().length();
                for (int j = 0; j < numTerms; j++) {
                    double c = this.get$matrix().get(i, j);
                    fabric.metrics.Metric m = term(j);
                    double scaledX = m.value() * c;
                    double scaledV = m.velocity() * c;
                    double scaledN = m.noise() * c * c;
                    double r = scaledV - (totalVelocity - rate) / numTerms;
                    r = r / c;
                    double b = scaledX - scaledN / totalNoise *
                      (totalValue - baseNow);
                    b = b / c;
                    if (c < 0) {
                        m = m.times(-1);
                        b = -b;
                        r = -r;
                    }
                    fabric.worker.metrics.treaties.statements.TreatyStatement
                      newStmt =
                      new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                        r, b, currentTime);
                    boolean implied = false;
                    for (java.util.Iterator iter =
                           witnesses.
                           get((java.lang.Object)
                                 fabric.lang.WrappedJavaInlineable.$unwrap(m)).
                           iterator();
                         iter.hasNext();
                         ) {
                        fabric.worker.metrics.treaties.statements.TreatyStatement
                          stmt =
                          (fabric.worker.metrics.treaties.statements.TreatyStatement)
                            iter.next();
                        if (stmt.implies(newStmt)) {
                            implied = true;
                            break;
                        }
                    }
                    if (!implied) {
                        witnesses.put(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          m),
                                    newStmt);
                    }
                }
            }
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
        }
        
        public int hashCode() {
            int hash = java.util.Arrays.hashCode(this.get$terms().array()) *
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
                  java.util.Arrays.deepEquals(this.get$terms().array(),
                                              that.get$terms().array()) &&
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.LinearMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 84, -2, -101, -13, 44,
    -100, 29, -73, 4, -44, -56, -103, -99, 67, 55, 0, -34, 7, -106, -44, 125,
    86, -39, 32, -71, -44, 116, -13, -6, -31, 73, 47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
