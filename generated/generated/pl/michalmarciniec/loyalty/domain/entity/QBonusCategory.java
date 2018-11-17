package pl.michalmarciniec.loyalty.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBonusCategory is a Querydsl query type for BonusCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBonusCategory extends EntityPathBase<BonusCategory> {

    private static final long serialVersionUID = 1579280474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBonusCategory bonusCategory = new QBonusCategory("bonusCategory");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> editedAt = _super.editedAt;

    public final NumberPath<Long> editPeriodInHours = createNumber("editPeriodInHours", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> limitPeriodInDays = createNumber("limitPeriodInDays", Long.class);

    public final EnumPath<BonusCategoryName> name = createEnum("name", BonusCategoryName.class);

    public final QPermission permission;

    public final NumberPath<Long> pointsLimit = createNumber("pointsLimit", Long.class);

    public QBonusCategory(String variable) {
        this(BonusCategory.class, forVariable(variable), INITS);
    }

    public QBonusCategory(Path<? extends BonusCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBonusCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBonusCategory(PathMetadata metadata, PathInits inits) {
        this(BonusCategory.class, metadata, inits);
    }

    public QBonusCategory(Class<? extends BonusCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.permission = inits.isInitialized("permission") ? new QPermission(forProperty("permission")) : null;
    }

}

