package com.infopulse.entity;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class AddressType
        extends AbstractSingleColumnStandardBasicType<Address>
        implements DiscriminatorType<Address> {

    public static final AddressType INSTANCE = new AddressType();

    public AddressType() {
        super(VarcharTypeDescriptor.INSTANCE, AddressTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "address";
    }

    @Override
    public Address stringToObject(String string) throws Exception {
        return fromString(string);
    }

    @Override
    public String objectToSQLString(Address t, Dialect dlct) throws Exception {
        return toString(t);
    }

}