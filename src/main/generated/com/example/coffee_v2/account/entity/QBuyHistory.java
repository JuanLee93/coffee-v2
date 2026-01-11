package com.example.coffee_v2.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuyHistory is a Querydsl query type for BuyHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuyHistory extends EntityPathBase<BuyHistory> {

    private static final long serialVersionUID = 330912744L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuyHistory buyHistory = new QBuyHistory("buyHistory");

    public final DatePath<java.time.LocalDate> buyDate = createDate("buyDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QBuyHistory(String variable) {
        this(BuyHistory.class, forVariable(variable), INITS);
    }

    public QBuyHistory(Path<? extends BuyHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuyHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuyHistory(PathMetadata metadata, PathInits inits) {
        this(BuyHistory.class, metadata, inits);
    }

    public QBuyHistory(Class<? extends BuyHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

