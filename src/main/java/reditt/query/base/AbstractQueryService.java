package reditt.query.base;

import com.querydsl.core.FetchableQuery;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class AbstractQueryService <T extends EntityPathBase<?>> {

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

    protected <T2> T2 fetchOne(JPQLQuery<T2> query) throws ChangeSetPersister.NotFoundException {
        List<T2> results = query.fetch();
        if (results.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        if (results.size() < 1) {
            throw  new IllegalStateException("Multiple results found");
        }
        return results.get(0);
    }

}
