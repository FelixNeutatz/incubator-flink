/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.flink.hadoopcompatibility.mapreduce.example.tpch.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2015-1-17")
public class CustomerTable implements org.apache.thrift.TBase<CustomerTable, CustomerTable._Fields>, java.io.Serializable, Cloneable, Comparable<CustomerTable> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CustomerTable");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("ID", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("NAME", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ADDRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("ADDRESS", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField NATIONKEY_FIELD_DESC = new org.apache.thrift.protocol.TField("NATIONKEY", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField PHONE_FIELD_DESC = new org.apache.thrift.protocol.TField("PHONE", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField ACCTBAL_FIELD_DESC = new org.apache.thrift.protocol.TField("ACCTBAL", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField MKTSEGMENT_FIELD_DESC = new org.apache.thrift.protocol.TField("MKTSEGMENT", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField COMMENT_FIELD_DESC = new org.apache.thrift.protocol.TField("COMMENT", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CustomerTableStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CustomerTableTupleSchemeFactory());
  }

  public long ID; // optional
  public String NAME; // optional
  public String ADDRESS; // optional
  public long NATIONKEY; // optional
  public String PHONE; // optional
  public double ACCTBAL; // optional
  public String MKTSEGMENT; // optional
  public String COMMENT; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "ID"),
    NAME((short)2, "NAME"),
    ADDRESS((short)3, "ADDRESS"),
    NATIONKEY((short)4, "NATIONKEY"),
    PHONE((short)5, "PHONE"),
    ACCTBAL((short)6, "ACCTBAL"),
    MKTSEGMENT((short)7, "MKTSEGMENT"),
    COMMENT((short)8, "COMMENT");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // NAME
          return NAME;
        case 3: // ADDRESS
          return ADDRESS;
        case 4: // NATIONKEY
          return NATIONKEY;
        case 5: // PHONE
          return PHONE;
        case 6: // ACCTBAL
          return ACCTBAL;
        case 7: // MKTSEGMENT
          return MKTSEGMENT;
        case 8: // COMMENT
          return COMMENT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __NATIONKEY_ISSET_ID = 1;
  private static final int __ACCTBAL_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.NAME,_Fields.ADDRESS,_Fields.NATIONKEY,_Fields.PHONE,_Fields.ACCTBAL,_Fields.MKTSEGMENT,_Fields.COMMENT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("ID", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("NAME", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ADDRESS, new org.apache.thrift.meta_data.FieldMetaData("ADDRESS", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NATIONKEY, new org.apache.thrift.meta_data.FieldMetaData("NATIONKEY", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PHONE, new org.apache.thrift.meta_data.FieldMetaData("PHONE", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACCTBAL, new org.apache.thrift.meta_data.FieldMetaData("ACCTBAL", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.MKTSEGMENT, new org.apache.thrift.meta_data.FieldMetaData("MKTSEGMENT", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMMENT, new org.apache.thrift.meta_data.FieldMetaData("COMMENT", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CustomerTable.class, metaDataMap);
  }

  public CustomerTable() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CustomerTable(CustomerTable other) {
    __isset_bitfield = other.__isset_bitfield;
    this.ID = other.ID;
    if (other.isSetNAME()) {
      this.NAME = other.NAME;
    }
    if (other.isSetADDRESS()) {
      this.ADDRESS = other.ADDRESS;
    }
    this.NATIONKEY = other.NATIONKEY;
    if (other.isSetPHONE()) {
      this.PHONE = other.PHONE;
    }
    this.ACCTBAL = other.ACCTBAL;
    if (other.isSetMKTSEGMENT()) {
      this.MKTSEGMENT = other.MKTSEGMENT;
    }
    if (other.isSetCOMMENT()) {
      this.COMMENT = other.COMMENT;
    }
  }

  public CustomerTable deepCopy() {
    return new CustomerTable(this);
  }

  @Override
  public void clear() {
    setIDIsSet(false);
    this.ID = 0;
    this.NAME = null;
    this.ADDRESS = null;
    setNATIONKEYIsSet(false);
    this.NATIONKEY = 0;
    this.PHONE = null;
    setACCTBALIsSet(false);
    this.ACCTBAL = 0.0;
    this.MKTSEGMENT = null;
    this.COMMENT = null;
  }

  public long getID() {
    return this.ID;
  }

  public CustomerTable setID(long ID) {
    this.ID = ID;
    setIDIsSet(true);
    return this;
  }

  public void unsetID() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field ID is set (has been assigned a value) and false otherwise */
  public boolean isSetID() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIDIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public String getNAME() {
    return this.NAME;
  }

  public CustomerTable setNAME(String NAME) {
    this.NAME = NAME;
    return this;
  }

  public void unsetNAME() {
    this.NAME = null;
  }

  /** Returns true if field NAME is set (has been assigned a value) and false otherwise */
  public boolean isSetNAME() {
    return this.NAME != null;
  }

  public void setNAMEIsSet(boolean value) {
    if (!value) {
      this.NAME = null;
    }
  }

  public String getADDRESS() {
    return this.ADDRESS;
  }

  public CustomerTable setADDRESS(String ADDRESS) {
    this.ADDRESS = ADDRESS;
    return this;
  }

  public void unsetADDRESS() {
    this.ADDRESS = null;
  }

  /** Returns true if field ADDRESS is set (has been assigned a value) and false otherwise */
  public boolean isSetADDRESS() {
    return this.ADDRESS != null;
  }

  public void setADDRESSIsSet(boolean value) {
    if (!value) {
      this.ADDRESS = null;
    }
  }

  public long getNATIONKEY() {
    return this.NATIONKEY;
  }

  public CustomerTable setNATIONKEY(long NATIONKEY) {
    this.NATIONKEY = NATIONKEY;
    setNATIONKEYIsSet(true);
    return this;
  }

  public void unsetNATIONKEY() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NATIONKEY_ISSET_ID);
  }

  /** Returns true if field NATIONKEY is set (has been assigned a value) and false otherwise */
  public boolean isSetNATIONKEY() {
    return EncodingUtils.testBit(__isset_bitfield, __NATIONKEY_ISSET_ID);
  }

  public void setNATIONKEYIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NATIONKEY_ISSET_ID, value);
  }

  public String getPHONE() {
    return this.PHONE;
  }

  public CustomerTable setPHONE(String PHONE) {
    this.PHONE = PHONE;
    return this;
  }

  public void unsetPHONE() {
    this.PHONE = null;
  }

  /** Returns true if field PHONE is set (has been assigned a value) and false otherwise */
  public boolean isSetPHONE() {
    return this.PHONE != null;
  }

  public void setPHONEIsSet(boolean value) {
    if (!value) {
      this.PHONE = null;
    }
  }

  public double getACCTBAL() {
    return this.ACCTBAL;
  }

  public CustomerTable setACCTBAL(double ACCTBAL) {
    this.ACCTBAL = ACCTBAL;
    setACCTBALIsSet(true);
    return this;
  }

  public void unsetACCTBAL() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACCTBAL_ISSET_ID);
  }

  /** Returns true if field ACCTBAL is set (has been assigned a value) and false otherwise */
  public boolean isSetACCTBAL() {
    return EncodingUtils.testBit(__isset_bitfield, __ACCTBAL_ISSET_ID);
  }

  public void setACCTBALIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACCTBAL_ISSET_ID, value);
  }

  public String getMKTSEGMENT() {
    return this.MKTSEGMENT;
  }

  public CustomerTable setMKTSEGMENT(String MKTSEGMENT) {
    this.MKTSEGMENT = MKTSEGMENT;
    return this;
  }

  public void unsetMKTSEGMENT() {
    this.MKTSEGMENT = null;
  }

  /** Returns true if field MKTSEGMENT is set (has been assigned a value) and false otherwise */
  public boolean isSetMKTSEGMENT() {
    return this.MKTSEGMENT != null;
  }

  public void setMKTSEGMENTIsSet(boolean value) {
    if (!value) {
      this.MKTSEGMENT = null;
    }
  }

  public String getCOMMENT() {
    return this.COMMENT;
  }

  public CustomerTable setCOMMENT(String COMMENT) {
    this.COMMENT = COMMENT;
    return this;
  }

  public void unsetCOMMENT() {
    this.COMMENT = null;
  }

  /** Returns true if field COMMENT is set (has been assigned a value) and false otherwise */
  public boolean isSetCOMMENT() {
    return this.COMMENT != null;
  }

  public void setCOMMENTIsSet(boolean value) {
    if (!value) {
      this.COMMENT = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetID();
      } else {
        setID((Long)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetNAME();
      } else {
        setNAME((String)value);
      }
      break;

    case ADDRESS:
      if (value == null) {
        unsetADDRESS();
      } else {
        setADDRESS((String)value);
      }
      break;

    case NATIONKEY:
      if (value == null) {
        unsetNATIONKEY();
      } else {
        setNATIONKEY((Long)value);
      }
      break;

    case PHONE:
      if (value == null) {
        unsetPHONE();
      } else {
        setPHONE((String)value);
      }
      break;

    case ACCTBAL:
      if (value == null) {
        unsetACCTBAL();
      } else {
        setACCTBAL((Double)value);
      }
      break;

    case MKTSEGMENT:
      if (value == null) {
        unsetMKTSEGMENT();
      } else {
        setMKTSEGMENT((String)value);
      }
      break;

    case COMMENT:
      if (value == null) {
        unsetCOMMENT();
      } else {
        setCOMMENT((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getID());

    case NAME:
      return getNAME();

    case ADDRESS:
      return getADDRESS();

    case NATIONKEY:
      return Long.valueOf(getNATIONKEY());

    case PHONE:
      return getPHONE();

    case ACCTBAL:
      return Double.valueOf(getACCTBAL());

    case MKTSEGMENT:
      return getMKTSEGMENT();

    case COMMENT:
      return getCOMMENT();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetID();
    case NAME:
      return isSetNAME();
    case ADDRESS:
      return isSetADDRESS();
    case NATIONKEY:
      return isSetNATIONKEY();
    case PHONE:
      return isSetPHONE();
    case ACCTBAL:
      return isSetACCTBAL();
    case MKTSEGMENT:
      return isSetMKTSEGMENT();
    case COMMENT:
      return isSetCOMMENT();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CustomerTable)
      return this.equals((CustomerTable)that);
    return false;
  }

  public boolean equals(CustomerTable that) {
    if (that == null)
      return false;

    boolean this_present_ID = true && this.isSetID();
    boolean that_present_ID = true && that.isSetID();
    if (this_present_ID || that_present_ID) {
      if (!(this_present_ID && that_present_ID))
        return false;
      if (this.ID != that.ID)
        return false;
    }

    boolean this_present_NAME = true && this.isSetNAME();
    boolean that_present_NAME = true && that.isSetNAME();
    if (this_present_NAME || that_present_NAME) {
      if (!(this_present_NAME && that_present_NAME))
        return false;
      if (!this.NAME.equals(that.NAME))
        return false;
    }

    boolean this_present_ADDRESS = true && this.isSetADDRESS();
    boolean that_present_ADDRESS = true && that.isSetADDRESS();
    if (this_present_ADDRESS || that_present_ADDRESS) {
      if (!(this_present_ADDRESS && that_present_ADDRESS))
        return false;
      if (!this.ADDRESS.equals(that.ADDRESS))
        return false;
    }

    boolean this_present_NATIONKEY = true && this.isSetNATIONKEY();
    boolean that_present_NATIONKEY = true && that.isSetNATIONKEY();
    if (this_present_NATIONKEY || that_present_NATIONKEY) {
      if (!(this_present_NATIONKEY && that_present_NATIONKEY))
        return false;
      if (this.NATIONKEY != that.NATIONKEY)
        return false;
    }

    boolean this_present_PHONE = true && this.isSetPHONE();
    boolean that_present_PHONE = true && that.isSetPHONE();
    if (this_present_PHONE || that_present_PHONE) {
      if (!(this_present_PHONE && that_present_PHONE))
        return false;
      if (!this.PHONE.equals(that.PHONE))
        return false;
    }

    boolean this_present_ACCTBAL = true && this.isSetACCTBAL();
    boolean that_present_ACCTBAL = true && that.isSetACCTBAL();
    if (this_present_ACCTBAL || that_present_ACCTBAL) {
      if (!(this_present_ACCTBAL && that_present_ACCTBAL))
        return false;
      if (this.ACCTBAL != that.ACCTBAL)
        return false;
    }

    boolean this_present_MKTSEGMENT = true && this.isSetMKTSEGMENT();
    boolean that_present_MKTSEGMENT = true && that.isSetMKTSEGMENT();
    if (this_present_MKTSEGMENT || that_present_MKTSEGMENT) {
      if (!(this_present_MKTSEGMENT && that_present_MKTSEGMENT))
        return false;
      if (!this.MKTSEGMENT.equals(that.MKTSEGMENT))
        return false;
    }

    boolean this_present_COMMENT = true && this.isSetCOMMENT();
    boolean that_present_COMMENT = true && that.isSetCOMMENT();
    if (this_present_COMMENT || that_present_COMMENT) {
      if (!(this_present_COMMENT && that_present_COMMENT))
        return false;
      if (!this.COMMENT.equals(that.COMMENT))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_ID = true && (isSetID());
    list.add(present_ID);
    if (present_ID)
      list.add(ID);

    boolean present_NAME = true && (isSetNAME());
    list.add(present_NAME);
    if (present_NAME)
      list.add(NAME);

    boolean present_ADDRESS = true && (isSetADDRESS());
    list.add(present_ADDRESS);
    if (present_ADDRESS)
      list.add(ADDRESS);

    boolean present_NATIONKEY = true && (isSetNATIONKEY());
    list.add(present_NATIONKEY);
    if (present_NATIONKEY)
      list.add(NATIONKEY);

    boolean present_PHONE = true && (isSetPHONE());
    list.add(present_PHONE);
    if (present_PHONE)
      list.add(PHONE);

    boolean present_ACCTBAL = true && (isSetACCTBAL());
    list.add(present_ACCTBAL);
    if (present_ACCTBAL)
      list.add(ACCTBAL);

    boolean present_MKTSEGMENT = true && (isSetMKTSEGMENT());
    list.add(present_MKTSEGMENT);
    if (present_MKTSEGMENT)
      list.add(MKTSEGMENT);

    boolean present_COMMENT = true && (isSetCOMMENT());
    list.add(present_COMMENT);
    if (present_COMMENT)
      list.add(COMMENT);

    return list.hashCode();
  }

  @Override
  public int compareTo(CustomerTable other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetID()).compareTo(other.isSetID());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetID()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ID, other.ID);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNAME()).compareTo(other.isSetNAME());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNAME()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.NAME, other.NAME);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetADDRESS()).compareTo(other.isSetADDRESS());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetADDRESS()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ADDRESS, other.ADDRESS);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNATIONKEY()).compareTo(other.isSetNATIONKEY());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNATIONKEY()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.NATIONKEY, other.NATIONKEY);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPHONE()).compareTo(other.isSetPHONE());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPHONE()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.PHONE, other.PHONE);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetACCTBAL()).compareTo(other.isSetACCTBAL());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetACCTBAL()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ACCTBAL, other.ACCTBAL);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMKTSEGMENT()).compareTo(other.isSetMKTSEGMENT());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMKTSEGMENT()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.MKTSEGMENT, other.MKTSEGMENT);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCOMMENT()).compareTo(other.isSetCOMMENT());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCOMMENT()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.COMMENT, other.COMMENT);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CustomerTable(");
    boolean first = true;

    if (isSetID()) {
      sb.append("ID:");
      sb.append(this.ID);
      first = false;
    }
    if (isSetNAME()) {
      if (!first) sb.append(", ");
      sb.append("NAME:");
      if (this.NAME == null) {
        sb.append("null");
      } else {
        sb.append(this.NAME);
      }
      first = false;
    }
    if (isSetADDRESS()) {
      if (!first) sb.append(", ");
      sb.append("ADDRESS:");
      if (this.ADDRESS == null) {
        sb.append("null");
      } else {
        sb.append(this.ADDRESS);
      }
      first = false;
    }
    if (isSetNATIONKEY()) {
      if (!first) sb.append(", ");
      sb.append("NATIONKEY:");
      sb.append(this.NATIONKEY);
      first = false;
    }
    if (isSetPHONE()) {
      if (!first) sb.append(", ");
      sb.append("PHONE:");
      if (this.PHONE == null) {
        sb.append("null");
      } else {
        sb.append(this.PHONE);
      }
      first = false;
    }
    if (isSetACCTBAL()) {
      if (!first) sb.append(", ");
      sb.append("ACCTBAL:");
      sb.append(this.ACCTBAL);
      first = false;
    }
    if (isSetMKTSEGMENT()) {
      if (!first) sb.append(", ");
      sb.append("MKTSEGMENT:");
      if (this.MKTSEGMENT == null) {
        sb.append("null");
      } else {
        sb.append(this.MKTSEGMENT);
      }
      first = false;
    }
    if (isSetCOMMENT()) {
      if (!first) sb.append(", ");
      sb.append("COMMENT:");
      if (this.COMMENT == null) {
        sb.append("null");
      } else {
        sb.append(this.COMMENT);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CustomerTableStandardSchemeFactory implements SchemeFactory {
    public CustomerTableStandardScheme getScheme() {
      return new CustomerTableStandardScheme();
    }
  }

  private static class CustomerTableStandardScheme extends StandardScheme<CustomerTable> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CustomerTable struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ID = iprot.readI64();
              struct.setIDIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.NAME = iprot.readString();
              struct.setNAMEIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ADDRESS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ADDRESS = iprot.readString();
              struct.setADDRESSIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NATIONKEY
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.NATIONKEY = iprot.readI64();
              struct.setNATIONKEYIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PHONE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.PHONE = iprot.readString();
              struct.setPHONEIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ACCTBAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.ACCTBAL = iprot.readDouble();
              struct.setACCTBALIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // MKTSEGMENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.MKTSEGMENT = iprot.readString();
              struct.setMKTSEGMENTIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // COMMENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.COMMENT = iprot.readString();
              struct.setCOMMENTIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CustomerTable struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetID()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI64(struct.ID);
        oprot.writeFieldEnd();
      }
      if (struct.NAME != null) {
        if (struct.isSetNAME()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.NAME);
          oprot.writeFieldEnd();
        }
      }
      if (struct.ADDRESS != null) {
        if (struct.isSetADDRESS()) {
          oprot.writeFieldBegin(ADDRESS_FIELD_DESC);
          oprot.writeString(struct.ADDRESS);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetNATIONKEY()) {
        oprot.writeFieldBegin(NATIONKEY_FIELD_DESC);
        oprot.writeI64(struct.NATIONKEY);
        oprot.writeFieldEnd();
      }
      if (struct.PHONE != null) {
        if (struct.isSetPHONE()) {
          oprot.writeFieldBegin(PHONE_FIELD_DESC);
          oprot.writeString(struct.PHONE);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetACCTBAL()) {
        oprot.writeFieldBegin(ACCTBAL_FIELD_DESC);
        oprot.writeDouble(struct.ACCTBAL);
        oprot.writeFieldEnd();
      }
      if (struct.MKTSEGMENT != null) {
        if (struct.isSetMKTSEGMENT()) {
          oprot.writeFieldBegin(MKTSEGMENT_FIELD_DESC);
          oprot.writeString(struct.MKTSEGMENT);
          oprot.writeFieldEnd();
        }
      }
      if (struct.COMMENT != null) {
        if (struct.isSetCOMMENT()) {
          oprot.writeFieldBegin(COMMENT_FIELD_DESC);
          oprot.writeString(struct.COMMENT);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CustomerTableTupleSchemeFactory implements SchemeFactory {
    public CustomerTableTupleScheme getScheme() {
      return new CustomerTableTupleScheme();
    }
  }

  private static class CustomerTableTupleScheme extends TupleScheme<CustomerTable> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CustomerTable struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetID()) {
        optionals.set(0);
      }
      if (struct.isSetNAME()) {
        optionals.set(1);
      }
      if (struct.isSetADDRESS()) {
        optionals.set(2);
      }
      if (struct.isSetNATIONKEY()) {
        optionals.set(3);
      }
      if (struct.isSetPHONE()) {
        optionals.set(4);
      }
      if (struct.isSetACCTBAL()) {
        optionals.set(5);
      }
      if (struct.isSetMKTSEGMENT()) {
        optionals.set(6);
      }
      if (struct.isSetCOMMENT()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetID()) {
        oprot.writeI64(struct.ID);
      }
      if (struct.isSetNAME()) {
        oprot.writeString(struct.NAME);
      }
      if (struct.isSetADDRESS()) {
        oprot.writeString(struct.ADDRESS);
      }
      if (struct.isSetNATIONKEY()) {
        oprot.writeI64(struct.NATIONKEY);
      }
      if (struct.isSetPHONE()) {
        oprot.writeString(struct.PHONE);
      }
      if (struct.isSetACCTBAL()) {
        oprot.writeDouble(struct.ACCTBAL);
      }
      if (struct.isSetMKTSEGMENT()) {
        oprot.writeString(struct.MKTSEGMENT);
      }
      if (struct.isSetCOMMENT()) {
        oprot.writeString(struct.COMMENT);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CustomerTable struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.ID = iprot.readI64();
        struct.setIDIsSet(true);
      }
      if (incoming.get(1)) {
        struct.NAME = iprot.readString();
        struct.setNAMEIsSet(true);
      }
      if (incoming.get(2)) {
        struct.ADDRESS = iprot.readString();
        struct.setADDRESSIsSet(true);
      }
      if (incoming.get(3)) {
        struct.NATIONKEY = iprot.readI64();
        struct.setNATIONKEYIsSet(true);
      }
      if (incoming.get(4)) {
        struct.PHONE = iprot.readString();
        struct.setPHONEIsSet(true);
      }
      if (incoming.get(5)) {
        struct.ACCTBAL = iprot.readDouble();
        struct.setACCTBALIsSet(true);
      }
      if (incoming.get(6)) {
        struct.MKTSEGMENT = iprot.readString();
        struct.setMKTSEGMENTIsSet(true);
      }
      if (incoming.get(7)) {
        struct.COMMENT = iprot.readString();
        struct.setCOMMENTIsSet(true);
      }
    }
  }

}

