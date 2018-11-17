package pl.michalmarciniec.loyalty.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBonus is a Querydsl query type for Bonus
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBonus extends EntityPathBase<Bonus> {

    private static final long serialVersionUID = 1940767548L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBonus bonus = new QBonus("bonus");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBonusCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> editedAt = _super.editedAt;

    public final NumberPath<Long> giverId = createNumber("giverId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> points = createNumber("points", Long.class);

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public QBonus(String variable) {
        this(Bonus.class, forVariable(variable), INITS);
    }

    public QBonus(Path<? extends Bonus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBonus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBonus(PathMetadata metadata, PathInits inits) {
        this(Bonus.class, metadata, inits);
    }

    public QBonus(Class<? extends Bonus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QBonusCategory(forProperty("category"), inits.get("category")) : null;
    }

}

