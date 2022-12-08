package com.herohuapp.parkhyeoncheolboot2.domain.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManyFileRepository extends JpaRepository<ManyFile, Long> {
	@Query(value = "SELECT * FROM many_file p where p.posts_id = :posts_id ORDER BY p.id DESC", nativeQuery = true)
    List<ManyFile> fileAllDesc(@Param("posts_id") Long id);
	//native쿼리는 위 처럼 파라미터가 필요한 쿼리가 사용가능하고, 주로 다중 파라미터값을 쿼리에 바인딩 할 때 직관적인 코딩으로 많이 사용한다.
}
