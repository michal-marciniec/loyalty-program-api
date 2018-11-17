package pl.michalmarciniec.loyalty.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;

public class DtoMapper {

    private static final ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE);
    }

    public static <S, D> D map(S source, Class<D> destination) {
        return mapper.map(source, destination);
    }
}
