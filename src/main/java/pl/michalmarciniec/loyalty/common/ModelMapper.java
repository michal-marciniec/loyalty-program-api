package pl.michalmarciniec.loyalty.common;

import com.google.common.base.Preconditions;
import org.modelmapper.convention.NamingConventions;

import java.util.List;

public final class ModelMapper {
    private static final org.modelmapper.ModelMapper mapper;

    static {
        mapper = new org.modelmapper.ModelMapper();
        mapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE);
    }

    private ModelMapper() {
    }

    public static <S, D> D map(S source, Class<D> destination) {
        return mapper.map(source, destination);
    }

    public static <S, D> D map(List<S> sources, Class<D> destination) {
        Preconditions.checkArgument(!sources.isEmpty(), "Collection must not be empty");
        D result = map(sources.get(0), destination);
        for (int i = 1; i < sources.size(); ++i) {
            mapper.map(sources.get(i), result);
        }
        return result;
    }
}
