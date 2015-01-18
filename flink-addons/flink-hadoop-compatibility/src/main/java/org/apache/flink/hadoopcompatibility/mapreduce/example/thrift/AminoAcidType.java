package org.apache.flink.hadoopcompatibility.mapreduce.example.thrift; /**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

import org.apache.thrift.TEnum;

public enum AminoAcidType implements TEnum {
  ALIPHATIC(1),
  HYDROXYL(2),
  CYCLIC(3),
  AROMATIC(4),
  BASIC(5),
  ACIDIC(6);

  private final int value;

  private AminoAcidType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static AminoAcidType findByValue(int value) { 
    switch (value) {
      case 1:
        return ALIPHATIC;
      case 2:
        return HYDROXYL;
      case 3:
        return CYCLIC;
      case 4:
        return AROMATIC;
      case 5:
        return BASIC;
      case 6:
        return ACIDIC;
      default:
        return null;
    }
  }
}
