package reditt.query.service.impl;

import org.springframework.stereotype.Service;
import reditt.model.QPost;
import reditt.query.base.AbstractQueryService;
import reditt.query.service.PostQueryService;

@Service
public class PostQueryServiceImpl extends AbstractQueryService<QPost> implements PostQueryService {

    private static final QPost POST = QPost.post;

    protected PostQueryServiceImpl() {
        super(POST);
    }
}
