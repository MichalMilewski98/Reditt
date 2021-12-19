package reditt.query.base;

import com.querydsl.core.FetchableQuery;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class AbstractQueryService <T extends EntityPathBase<T>> {

    @PersistenceContext
    private EntityManager entityManager;

    private final T entityPath;

    public AbstractQueryService(T entityPath) {
        this.entityPath = entityPath;
    }

    protected JPAQueryFactory query() {
        return new JPAQueryFactory(this.entityManager);
    }

    protected boolean exists(FetchableQuery<?, ?> query) {
        return query.select(Expressions.ONE).fetchFirst() != null;
    }

    protected boolean notExists(FetchableQuery<?, ?> query) {
        return !this.exists(query);
    }

}
