package reditt.query.service.impl;

import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Service;
import reditt.dto.UserDto;
import reditt.model.QUser;
import reditt.query.base.AbstractQueryService;
import reditt.query.service.UserQueryService;

import java.util.List;

@Service
public class UserQueryServiceImpl extends AbstractQueryService<QUser> implements UserQueryService {

    private static final QUser USER = QUser.user;

    protected UserQueryServiceImpl() {
        super(USER);
    }

    public List<UserDto> getAllUsers() {
        return this.query()
                .select(Projections.constructor(UserDto.class, USER.email, USER.username, USER.isActive))
                .fetch();
    }

}
