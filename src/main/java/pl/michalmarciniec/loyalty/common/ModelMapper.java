package pl.michalmarciniec.loyalty.common;

import org.modelmapper.convention.NamingConventions;

public class ModelMapper {

    private static final org.modelmapper.ModelMapper mapper;

    static {
        mapper = new org.modelmapper.ModelMapper();
        mapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE);
    }

    public static <S, D> D map(S source, Class<D> destination) {
        return mapper.map(source, destination);
    }
}
