/**
 * Implementation of the Fabric networking layer.
 *
 * <p>
 * Data-flow diagram:
 * <pre>
 *                                        MuxedOutputStream
 *                                       +---------------------------------------------+
 *         app --> BufferedOutputStream -+-> DataOutputStream --> BufferedOutputStream |
 *          A                            +---------------------------------+-----------+
 *          |                                                              |
 *          |                                                              V
 *   PipedInputStream                                                    socket
 *          A                                                              |
 *          |                                                              V
 *   PipedOutputStream <-- (Channel.run()) <-- DataInputStream <-- BufferedInputStream
 * </pre>
 */

package fabric.common.net;

