package jif.types.label;



/**
 * This label represents a pair of a confidentiality policy and an
 * integrity policy.
 */
public interface PairLabel extends Label {
    ConfPolicy confPolicy();
    IntegPolicy integPolicy();
}
