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
    
    public static final byte[] $classHash = new byte[] { -10, -91, 69, 117, -35,
    71, 56, 31, 88, -17, -95, -32, 11, 99, 0, -64, -34, 82, -45, -14, -53, -28,
    -49, 110, -62, -37, -98, 32, 6, -84, 41, -19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1532369172000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0aC3BU1fW+TdhkQyAhCEIIEOKCBnC3onWKERW2IMENxAQYDUp4eXs3efD2veW9u2Sj4lhaB60tbeWjTpVqB8aqUTtOnTrjMHU69d/xb63TCkyrrZYyaD9IZ7T2nHvv/t7uPnc7Zuad8/bde84959zzu+9l/CSZ4NikI64O6UaIjSWpE1qlDnVHe1XbobGIoTrOeng6qE2s7T7w4YOxOT7ii5JGTTUtU9dUY9B0GJkc3aruUMMmZeENfd1dm0hAQ8LVqjPCiG/TirRN2pOWMTZsWEwuUsR//6Lwvrs2Nz9RQ5oGSJNu9jOV6VrEMhlNswHSmKCJIWo7y2MxGhsgU0xKY/3U1lVDvwEmWuYAaXH0YVNlKZs6fdSxjB04scVJJanN18w8RPEtENtOacyyQfxmIX6K6UY4qjusK0r8cZ0aMWc7uZnURsmEuKEOw8Tp0YwWYc4xvAqfw/QGHcS046pGMyS123QzxshcN0VW4+BVMAFI6xKUjVjZpWpNFR6QFiGSoZrD4X5m6+YwTJ1gpWAVRlrLMoVJ9UlV26YO00FGZrjn9YohmBXgZkESRqa5p3FOsGetrj3L262Tay/dc6O52vQRBWSOUc1A+euBaI6LqI/GqU1NjQrCxoXRA+r0I7f5CIHJ01yTxZxf3vTJFYvnPPOCmDOrxJx1Q1upxga1Q0OTX2+LdC6tQTHqk5ajoysUaM53tVeOdKWT4O3TsxxxMJQZfKbvuWtveZie8JGGbuLXLCOVAK+aolmJpG5Q+0pqUltlNNZNAtSMRfh4N6mD+6huUvF0XTzuUNZNag3+yG/x32CiOLBAE9XBvW7Grcx9UmUj/D6dJITUwUUUQnznEbImBvedhEy4iJE14RErQcNDRoqOgnuH4aKqrY2EIW5tXQs7tha2UybTYZJ8BF4EyAmjbKrdw3+FQIrkV8otjbI3jyoKmHWuZsXokOrAHkl/WdFrQEistowYtQc1Y8+RbjL1yD3cZwLo5w74KreKAvvc5s4Q+bT7UitWfvLY4MvC35BWGo2RWULEkBQxlC8iSNWIgRSC1BSC1DSupEORg92PcH/xOzywsowagdElSUNlcctOpImicK3O4vTcUYDzNkgfkCEaO/uvX7Plto4a8NDkaC1uGkwNuuMll2W64U6FIBjUmnZ/ePrxAzutXOQwEiwK6GJKDMgOt4lsS6MxSHg59gvb1ScHj+wM+jCZBCDPMRXsAUljjnuNgsDsyiQ5tMaEKJmINlANHMpkpgY2YlujuSd86ycjaBFegMZyCcjz47L+5H2/f+WjC3nlyKTSpryc209ZV174IrMmHqhTcrZfb1MK8967u3fv/pO7N3HDw4xzSi0YRBiBsFUhXi371he2v3vs6KG3fLnNYsSfTA0Zupbmukz5Av4UuP6LF8YgPkAMmTgi4789mwCSuPKCnGyQCgxIRyC6E9xgJqyYHtfVIYOip3zWNP+CJ/++p1lstwFPhPFssvjLGeSez1xBbnl586dzOBtFw1KUs19umshvU3Ocl9u2OoZypL/1xux7nlfvA8+H7OToN1CecAi3B+EbuITb4nwOL3CNXYSgQ1irjT/3OcW5fhUWzZwvDoTH722NXHZCBHzWF5HHvBIBv1HNC5MlDyf+7evwP+sjdQOkmddr1WQbVchZ4AYDUHGdiHwYJZMKxgurpygVXdlYa3PHQd6y7ijIJRq4x9l43yAcXzgOGGI6GikIVwhy9X8kPoGjU5MIz0orhN9cwknO4XABgs6MMwaStsVAShpLZ9n6kO1Eye64xO/ksQUfTqiQwQTJNGgLXImQO0APn4IzWkWQIry4UPhz4bqQEP9DEt9dQviIEB7BsmIZker7En+nQMbADsjFWRmWcxnSZQyBtwtzFuB/flkEL5T4/Dzued5I0uCOs8v1K7zXOrRr38HYusMXiK6ipbAHWGmmEo/+7vPfhu4+/mKJGhNgVvJ8g+6gRt6a02HJeUWNcw9v53KOfPzE7KWRbR8Mi2XnukR0z36oZ/zFKxdod/pITdZji3rIQqKuQj9tsCm0wOb6Am9tzxo1gMa6Bq5l0G0ck/iH+RuecxO+292Fu10vSX4g8e3u/cjlD5/IE3zfwVSz8k21BuKT5ybR8W2GTP/a2KkDwkjuvjNv4sfjx068MWn2Y7y81WKzwZV0N+zF/XhBm811bMwq9nVUrAuuPvDmZokbGLnq/++TvglHEzhqFLRdXyW7bNBPcwW9mMLjPbsRiuxQeOgjuD4T1VrpSOR7tpBBMtRNVfR4iyDdGNQcZiMl8n6vrSegdO+QPT69bd93vwjt2ScCSRyEzik6i+TTiMMQX2gSXw3DeZ7XKpxi1V8f3/n0z3bu9skq1cNIDew83l6XdvthxmKiOmJtgARhQa8IhZaPzYQ4xxbQsOBcmzWw6P90K5Q9bQ6JTn57uqSBrxYG5jLkRRHXyqPAjnmM3YhgB2yHhvJmBGvO6SFqnBCKU1zrwe0WBOuhEArPCUrPCeb3zcFcEri6MHW0w9VLSMPDEu+tLnUgyZ0Sf6986siXd7fHGM8+uxiZjIc0OBz3YkyxPj53RDoSom3gvTErldnpEjpthJA/S+CGz6vTCUk+k/h0ZTrt9Rjbj2CPW6cV+PSOctJfB9LfLPFwddIjSVziLZVJf6/H2EEEd7ml3+gp/RAs/aHEr1cnPZK8JvFLlUl/2GPsQQT3u6VfW0r6BiI6EgJt9KSExGvLSF+i56tLQkqH04Sr4wtITj0SryqvlJLrmpbzxX7uodkTCB6CDGfygx30/5zOpRRvuyJw3UpI81sS/7RCpXjVeJThuRpf3bkUa5bcHpB4b3nFaji7Gi4ggq0c8KWf9lDxCIJfMDIpq2KfNeqUVXMpXJDFpu2WeLAqNRE8VUJF5LRZ4t7yKvqyZ6jZrg4Ssm9ev/PKg2dmHgl+dCZT4TaJpJZzyJL1fRHWd9dPvHnJq9wjiItaj2A4a/nnPdv1UgRFtRd/8ldejkfRFGo9j2DUY5vf9Bh7GwFPITch2Ck4cvisBxk/Vv2KQe9sjYr3Bjw6trrcphEJ8CDyJDT/wxJf4ZGwSpyUkORyiS/+0tjO1Po5sssbtext1M42e/iO3OlRk5n2pfANFpfmqIfa7yN4l+HrcJ7t+Om5VK7jii+A60VCZvxN4teqUxxJXpX4uYqSmoj4Ex4KnETwl0oVuBiuNwlpDQg881h1CiDJUYnfrkiBY5zrvzwU4O3CKUaaMgpQaD51NlZKB15w8Kj+ASFtQYnrPHQYL64sSOIXeNYXVWzC5x46cD5nKtMhG0GnCJkdkbitun1AklkSt1S+D4q/vA5KPQIl50hrLd0p6Uh8E8D4ikLI3PUSX1LVJnCSpRIvqXwTlCYPBaYgaKhAAd50tQJfMGV7TOJ+DwWKmy5O0idxtLwC+fLN8BjDF1PKVEbqmSU+dWWyXt4JJ2+gKM+V0nARiNdBSMcrEo9XpyGSPCLx4Yq26A6uynwPNc9F0A6nODzgOxkd21zn94KTPs5pLaUe9J3KEkLm1wgc/HN16iHJnyT+Y0XqbeEqfM1DPfRkZREjtUkjhROUzlKCzwN+txOy5CmJ769OcCT5icT3VCH4Ug/BuxBcxEhNQuefHraUknsxsIPEf3mrwJd9Wp3cSHJa4o+/VO6Mb0wtrPr9zLKzbyqKS72ywkPJ1QiWMfysmRxbZ5bdHx2WP0bIFZ9JfG91eiLJjyXeX17PWi5ZLY+bLMByrES4tOs8NMFWUVkD5YaNgDVGLCPWaxm6NpYx2qWlWyVmUziUQItNTbCYRhPUZKGVuXvBBHlsL2WXs0FJ2PvVP5L421XZhZPskvimyjLmgMfYdQg2QMYcUZ2RiBUr2a1yuefDou2EdK+UuLM6uZHkPIk7KvbbFrkFee+mPNxW81BUR7AZ3JZuT6mGSJqu9zp1Q5ZlUNVMA/P8V1j4IWxWiS/S8r8jtMhv6KEPrlo8rczX6BlF/68i6R472FR/9sEN74g30Jn/fAhESX08ZRj5H4zy7v1Jm8bFJ5CA+HyU5PptZ2RyYQFg/E013vEkaol5DEwg5uGvFN+E1gxQOsX3nZSN/2sz/s+zz/jr1x/nXzvBju2nD69MvXflN+Zec+qB4xM18szRvjf/8fL7r5q//sPBdv9458n/AQvBkW8DJAAA";
}
