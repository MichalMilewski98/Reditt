package reditt.query.service.impl;

import reditt.model.QPost;
import reditt.query.base.AbstractQueryService;
import reditt.query.service.PostQueryService;

public class PostQueryServiceImpl extends AbstractQueryService<QPost> implements PostQueryService {

    private static final QPost POST = QPost.post;

    protected PostQueryServiceImpl() {
        super(POST);
    }



}
