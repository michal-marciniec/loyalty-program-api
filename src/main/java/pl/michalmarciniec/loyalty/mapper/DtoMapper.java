package pl.michalmarciniec.loyalty.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;

public class DtoMapper {

    public static <S, D> D map(S source, Class<D> destination) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE);

        return mapper.map(source, destination);
    }
}
