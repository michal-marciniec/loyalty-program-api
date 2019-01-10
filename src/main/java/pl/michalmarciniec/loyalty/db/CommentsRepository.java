package pl.michalmarciniec.loyalty.db;

import pl.michalmarciniec.loyalty.domain.entity.Comment;

public interface CommentsRepository extends JpaRepositoryWrapper<Comment, Long> {
}
