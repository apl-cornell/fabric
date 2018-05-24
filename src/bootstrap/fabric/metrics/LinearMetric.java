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
            java.util.Map witnesses = new java.util.LinkedHashMap();
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
                    double[] normalized =
                      fabric.metrics.contracts.Bound._Impl.createBound(
                                                             r, b, currentTime);
                    if (!witnesses.
                          containsKey(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(m)) ||
                          !((fabric.worker.metrics.treaties.MetricTreaty)
                              witnesses.
                              get(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      m))).
                          implies(m, normalized[0], normalized[1])) {
                        witnesses.put(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          m),
                                    m.getThresholdTreaty(r, b, currentTime));
                    }
                }
            }
            fabric.worker.metrics.treaties.MetricTreaty[] finalWitnesses =
              new fabric.worker.metrics.treaties.MetricTreaty[witnesses.size()];
            int i = 0;
            for (java.util.Iterator iter = witnesses.values().iterator();
                 iter.hasNext(); ) {
                finalWitnesses[i++] =
                  (fabric.worker.metrics.treaties.MetricTreaty) iter.next();
            }
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     finalWitnesses);
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
                return new fabric.metrics.LinearMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -69, 23, 125, -67, 23,
    -109, 55, 46, -128, -51, 81, -119, 91, -4, 53, 94, -28, 90, 116, 20, -73,
    64, -40, -54, 9, 43, -73, 90, -62, 3, -90, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527195022000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0abXBU1fXuJiTZGEgI3yGEABHla7f4WYxYYCsQDBIJMBqU+PL2bvLg7XvLe3eTjUqlWIvWGX7Ih9pRxs7gF0ZonVq1NlPHav2qjh+tyg+FTrXVQWqdqmWmfvSce+/uvn379rnbMTPvnLfv3nPuOeeer/teRk6RMbZFZseVPk0Ps+EktcMrlb6Ozi7Fsmksqiu2vQGe9qpnVHYc+PCBWEuQBDtJnaoYpqGpit5r2IyM69yqDCoRg7LIxvUd7ZtJSEXC1Yo9wEhw84q0RVqTpj7cr5tMLlLAf/+CyL47tjQ8WkHqe0i9ZnQzhWlq1DQYTbMeUpegiT5q2ctjMRrrIeMNSmPd1NIUXbsOJppGD2m0tX5DYSmL2uupbeqDOLHRTiWpxdfMPETxTRDbSqnMtED8BiF+iml6pFOzWXsnqYprVI/Z28mPSGUnGRPXlX6YOLkzo0WEc4ysxOcwvVYDMa24otIMSeU2zYgxMtNNkdW47TKYAKTVCcoGzOxSlYYCD0ijEElXjP5IN7M0ox+mjjFTsAojTUWZwqSapKJuU/ppLyNT3fO6xBDMCnGzIAkjk9zTOCfYsybXnjl269TlF++53lhtBEkAZI5RVUf5a4CoxUW0nsapRQ2VCsK6+Z0HlMmjtwQJgcmTXJPFnMdv+HTZwpanXxBzpnvMWde3laqsVz3UN+715ui8JRUoRk3StDV0hTzN+a52yZH2dBK8fXKWIw6GM4NPr//jVTsP05NBUttBqlRTTyXAq8arZiKp6dRaRQ1qKYzGOkiIGrEoH+8g1XDfqRlUPF0Xj9uUdZBKnT+qMvlvMFEcWKCJquFeM+Jm5j6psAF+n04SQqrhIgFCgq2EdOyC+zmEVI5jZE1kwEzQSJ+eokPg3hG4qGKpAxGIW0tTI7alRqyUwTSYJB+BFwGyIyibYq3lv8IgRfI75ZZG2RuGAgEw60zVjNE+xYY9kv6yokuHkFht6jFq9ar6ntEOMmH0Lu4zIfRzG3yVWyUA+9zszhBO2n2pFZd+eqT3ZeFvSCuNxsh0IWJYihh2ighS1WEghSE1hSE1jQTS4ejBjoe5v1TZPLCyjOqA0UVJXWFx00qkSSDAtZrI6bmjAOdtkD4gQ9TN675mzbW3zK4AD00OVeKmwdQ2d7zkskwH3CkQBL1q/e4Pvzh6YIeZixxG2goCupASA3K220SWqdIYJLwc+/mtymO9ozvagphMQpDnmAL2gKTR4l4jLzDbM0kOrTGmk5yBNlB0HMpkplo2YJlDuSd868chaBRegMZyCcjz49Lu5D3vvPrRubxyZFJpvSPndlPW7ghfZFbPA3V8zvYbLEph3rt3du3df2r3Zm54mDHHa8E2hFEIWwXi1bRufmH7sePvHfpzMLdZjFQlU326pqa5LuO/gb8AXF/jhTGIDxBDJo7K+G/NJoAkrjw3JxukAh3SEYhut200EmZMi2tKn07RU76sP3PxYx/vaRDbrcMTYTyLLPx2Brnn01aQnS9v+U8LZxNQsRTl7JebJvLbhBzn5ZalDKMc6R+/MeOu55V7wPMhO9nadZQnHMLtQfgGnsNtsYjDxa6x8xDMFtZq5s+DdmGuX4lFM+eLPZGRu5uil5wUAZ/1ReQxyyPgNymOMDnncOLz4Oyq54Kkuoc08HqtGGyTAjkL3KAHKq4dlQ87ydi88fzqKUpFezbWmt1x4FjWHQW5RAP3OBvva4XjC8cBQ0xGI7XBdTbk6jclfg5HJyQRTkwHCL+5iJPM4XAugnkZZwwlLZOBlDSWzrINItszJLvfSvwrB1vw4YQCGUyQTIK2wJUIuQOs5VNwRpMIUoQX5At/FlyLCBlzk8SWh/BRITyCpYUyIlW/xFvyZAwNQi7OyrCcy5AuYgi8nZ+zAP+rkkVwrMTVDu4ObyRpcMcZxfoV3msd2rXvYGzdfYtFV9GY3wNcaqQSj7z11Z/Cd5540aPGhJiZXKTTQao71pwMS84qaJzX8nYu58gnTs5YEt32Qb9YdqZLRPfsh9aOvLhqrnp7kFRkPbagh8wnas/301qLQgtsbMjz1tasUUNorCvhuhBs+6TEW50bnnMTvtsd+btdI0k0iVX3fuTyR1DkCb7vYKrpTlOtgfjkuUl0fFsg0782/MkBYSR33+mY+K+R4yffGDvjCC9vldhscCXdDXthP57XZnMd67KKnY+KtcPVRUjtJom7GLns/++TfghHEzhq5LVd3yW7bNBPcgW9mMLjPbsRAdmh8NBHcE0mqlXvSOR7Np9BMtQMRfR4CyDd6NToZwMeeb/L0hJQugdlj09v2fezb8J79olAEgehOQVnESeNOAzxhcby1TCcZ/mtwilW/uPojqce3LE7KKvUWkYqYOfx9uq02w8zFhPVEWsDJAgTekUotHxsGsQ5toC6CefarIFF/6eZ4exps0908tvTnga+QhiYy+CIIq6VT4Ed9hm7HsEgbIeK8mYEa8jpIWqcEIpTXOXDbSeCDVAIhee0Sc9pc/bNbbkkcEV+6oCDCVkD9z+ROFFe6kASXeJ48dThlHe3z9itCHYxMg4PaXA47sKYYuv53AHpSIi2gffGzFRmpz10gpAP/VPit8vTCUnekvi10nTa6zO2H8Eet04r8OltxaSHpFV7pcTLy5MeSZZJfFFp0t/tM3YQwR1u6Tf5Sn81LP2MxA+XJz2SHJb4UGnS3+cz9gCCe93SX+4lfS0SzYNrKyF1qyQ+u4j0Hj1fdRJSOpwmXB1fSHI6S+LW4koFcl3Tcr7YL300exTBQ5DhDH6wg/6f07mU4m1XFK4bCak/IvENJSrFq8YjDM/V+OrOpViD5Ha9xIniilVwdhVcQAS8PdnKl37KR8VRBL9mZGxWxfXmkF1UzSVwHSJkoiLxkrLURPCEh4rI6fsSzy+uYjB7hprh6iAh+zr6nVcfOD1ttO2j05kKt1kktZxDetb3BVjfXT/x5iW/co8gLmo9gv6s5Z/3bde9CApqL/7kr7xsn6Ip1HoewZDPNr/pM/YXBK8j4E67Q3Dk8DkfMp7wf8+gdzaHxHsDHh1bXW5ThwTnwnWUkEnLJW7ySVgeJyUkmSZxw7fGdqbWt8gub8i0tlEr2+zhO3J7rZLMtC/5b7C4NO/5qP0+gmMMX4fzbMdPz165jis+F64/EDLlWYkPl6c4kjwk8b0lJTUR8Sd9FDiF4O+lKnABXK8QMvVvEj9ZngJI8oTER0tS4Djn+pmPAl8g+ISR+owCFJpPjQ176cALDpaF44Q0fS3xX310GCmsLEhyQuJjZWzCVz46fIPgdGk6ZCPoI0KaZwg8/Yvy9gFJPpf449L3IVBVXIdADYJAzpEuNzXb05H4JuDLnv8S0hKWeGJ5m4AkEySuK30TAvU+CoxHUFuCArzpagK+YwiZeYnEi3wUKGy6OMlCiecWV8Ap31SfMcyggQmM1DBTfOrKZD3HCccxUJDnvDRcAOI1EzLrQYl/Wp6GSHKzxDeWtEW3cVXO9FHzLAStcIrDA76d0bHZdX7PO+njnCYv9aDvDMAOzHlX4tHy1EOS30n8m5LUu5ar8D0f9c5BsICRyqSewgmBeV6CzwJ+NxGy+HaJrytPcCQZltguQ/AlPoK3IziPkYqExj89XOslN7o7JP6ln0n8enlyI8lrEr/0rXJnfGNCftXvZqaVfVNRWOoDK3yUXI1gKcPPmsnhdUbR/dFg+WOE/OAtiQfL0xNJUhKbxfWs5JJV8rjJguMoVJRLu85HE2wVA2ug3LABsMaAqce6TF1ThzNGu9i7VWIWhUMJtNjUAIupNEENFr40dy+YII/tXnaZAko2ErLqqMS/KMsunOReiX9eWsbs8Rm7GsFGyJgDij0QNWOe3SqX+0xYdCohq1WJV5UnN5KslHhZyX7bKLfA8W7Kx21VH0U1BFvAben2lKKLpOl6r1PdZ5o6VYw0MHe+wsIPYdM9vkjL/45Qo8/SQx9ctnBSka/RUwv+X0XSHTlYXzPl4Ma3xRvozH8+hDpJTTyl684PRo77qqRF4+ITSEh8Pkpy/bYzMi6/ADD+phrveBI1xTwGJhDz8FeKb0JTBgTmie87KQv/12bk31NOV9VsOMG/doIdW5+asmN0yt4LwztfueLWzV+ev+X9Hjbx8WXvvBRa8HjPMxX33/8//Ia9YAMkAAA=";
}
