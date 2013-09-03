package fabric.common;

/**
 * Implements a proportional-integral-derivative controller.
 */
public class PIDController {
  // This class is based on code originally published in 2008 by the FIRST
  // Robotics Competition under a three-clause BSD licence, as well as code
  // released under GPLv3 from the Arduino PID Library by Brett Beauregard
  // <br3ttb@gmail.com>.

  /**
   * Proportional gain.
   */
  private double kP;

  /**
   * Integral gain.
   */
  private double kI;

  /**
   * Derivative gain.
   */
  private double kD;

  /**
   * Maximum manipulated-variable (output) value.
   */
  private double maximumOutput;

  /**
   * Minimum manipulated-variable (output) value.
   */
  private double minimumOutput;

  /**
   * The setpoint.
   */
  private double sp;

  /**
   * The current error derivative.
   */
  private double errorDerivative;

  /**
   * Tuning parameter for low-pass-filtering the derivative calculation.
   */
  private double alpha;

  //
  // Accumulators for the controller's internal state.
  //

  /**
   * The time of the last process-variable measurement.
   */
  private long lastPVSampleTime;

  /**
   * The previous error between the process variable (input) and the setpoint.
   */
  private double previousError;

  /**
   * The current integral term. This is already weighted by kI and is restricted
   * to be at least <code>minimumOutput</code> and at most
   * <code>maximumOutput</code>.
   */
  private double integralTerm;

  /**
   * The controller's manipulated variable (output).
   */
  private double mv;

  /**
   * Allocates a PID object with the given constants for P, I, and D.
   * 
   * @param kP the proportional coefficient
   * @param kI the integral coefficient
   * @param kD the derivative coefficient
   */
  public PIDController(double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;

    this.maximumOutput = 1.0;
    this.minimumOutput = -1.0;
    this.previousError = 0.0;
    this.integralTerm = 0.0;
    this.sp = 0.0;
    this.errorDerivative = 0.0;
    this.alpha = 0.0;
    this.mv = 0.0;
    this.lastPVSampleTime = System.currentTimeMillis();
  }

  /**
   * Gets the Proportional coefficient.
   * 
   * @return proportional coefficient
   */
  public double getP() {
    return kP;
  }

  /**
   * Gets the Integral coefficient.
   * 
   * @return integral coefficient
   */
  public double getI() {
    return kI;
  }

  /**
   * Gets the Differential coefficient.
   * 
   * @return differential coefficient
   */
  public double getD() {
    return kD;
  }

  /**
   * Sets the PID Controller gain parameters.
   * 
   * @param p Proportional coefficient
   * @param i Integral coefficient
   * @param d Differential coefficient
   */
  public void setPID(double p, double i, double d) {
    kP = p;
    kI = i;
    kD = d;
  }

  /**
   * Sets the minimum and maximum output values.
   *
   * @param minimumOutput the minimum value output
   * @param maximumOutput the maximum value output
   */
  public void setOutputRange(double minimumOutput, double maximumOutput) {
    this.minimumOutput = minimumOutput;
    this.maximumOutput = maximumOutput;
  }

  /**
   * Sets the alpha parameter for low-pass-filtering the error derivatives.
   */
  public void setAlpha(double alpha) {
    this.alpha = alpha;
  }

  /**
   * Returns the current setpoint of the PIDController.
   * 
   * @return the current setpoint
   */
  public double getSetpoint() {
    return sp;
  }

  /**
   * Sets the setpoint for the PIDController.
   * 
   * @param setpoint the desired setpoint
   */
  public void setSetpoint(double setpoint) {
    this.sp = setpoint;
  }

  /**
   * Sets the process variable (input) and computes the resulting manipulated
   * variable (output) value. The result is always constrained to the current
   * output range.
   */
  public double setInput(double input) {
    return setInput(input, System.currentTimeMillis());
  }

  /**
   * Computes the resulting manipulated variable value from the given process
   * variable. The result is always constrained to the current output range.
   */
  public double setInput(double pv, long sampleTime) {
    // Calculate the elapsed time since the last input.
    long dt = sampleTime - lastPVSampleTime;

    if (dt > 0) {
      double error = sp - pv;
      integralTerm += error * dt * kI;
      if (integralTerm > maximumOutput)
        integralTerm = maximumOutput;
      else if (integralTerm < minimumOutput) integralTerm = minimumOutput;

      errorDerivative =
          (1 - alpha) * errorDerivative + alpha * (error - previousError) / dt;

      mv = kP * error + integralTerm + kD * errorDerivative;

      // Set up for the next cycle.
      previousError = error;
      lastPVSampleTime = sampleTime;
    }

    // Make sure the final result is within bounds
    if (mv > maximumOutput) {
      mv = maximumOutput;
    } else if (mv < minimumOutput) {
      mv = minimumOutput;
    }

    return mv;
  }
}
