package com.infopulse.entity;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

public class AddressTypeDescriptor extends AbstractTypeDescriptor<Address> {
    public static final AddressTypeDescriptor INSTANCE =
            new AddressTypeDescriptor();

    public AddressTypeDescriptor() {
        super( Address.class );
    }

    @Override
    public String toString(Address t) {
        String s=t.getCity()+";"+t.getStreet();
        return s;
    }

    @Override
    public Address fromString(String string) {
        String[] arr=string.split(";");
        Address t=new Address();
        t.setCity(arr[0]);
        t.setStreet(arr[1]);
        return t;
    }

    @Override
    public <X> X unwrap(Address t, Class<X> type, WrapperOptions wo) {
        if ( t == null ) {
            return null;
        }
        if ( Address.class.isAssignableFrom( type ) ) {
            return (X) t;
        }
        if ( String.class.isAssignableFrom( type ) ) {
            return (X) toString(t);
        }
        throw unknownUnwrap( type );
    }

    @Override
    public <X> Address wrap(X x, WrapperOptions wo) {
        if ( x == null ) {
            return null;
        }
        if ( String.class.isInstance(x) ) {
            return fromString( (String)x );
        }
        if ( Address.class.isInstance(x ) ) {
            return (Address)x;
        }
        throw unknownWrap(x.getClass() );
    }

}