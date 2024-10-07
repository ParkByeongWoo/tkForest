package com.kdigital.spring7.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kdigital.spring7.dto.BoardDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name="board")
@EntityListeners(AuditingEntityListener.class)
public class BoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="board_num")
	private Long boardNum;
	
	@Column(name="board_writer", nullable = false)
	private String boardWriter;
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@Column(name="hit_count")
	private int hitCount;
	
	@Column(name="create_date")
	@CreationTimestamp 	// 게시글이 처음 생성될 때 자동으로 날짜 세팅
	private LocalDateTime createDate;
	
	@Column(name="update_date")
	@LastModifiedDate	// 게시글이 수정된 마지막 날짜/시간을 세팅
	private LocalDateTime updateDate;
	
	// 첨부파일이 있을 경우 추가
	@Column(name="original_file_name")
	private String originalFileName;
	
	@Column(name="saved_file_name")
	private String savedFileName;
	
	// 댓글 개수 처리
	@Formula("(SELECT count(1) FROM reply r WHERE board_num  = r.board_num)")
	private int replyCount;
	
	
	
	
	/* 참고: 양방향 관계가 설정되어 있는 경우
	 * one에 해당하는 테이블 엔티티에 설정함!
	 * - mappedBy : 양방향 참조를 할 경우 one에 해당하는 테이블 엔티티값
	 * - CascadeType.REMOVE : DB에서 테이블 생성할 때 on delete cascade 옵션과 동일
	 * - fetch = FetchType.LAZY(지연호출), FetchType.EAGER(즉시호출)
	 * - orphanRemoval : 1:N의 관계일 때 지정하는 옵션
	 *      자식 엔티티의 변경이 일어나면 JPA (i->u->d)
	 *      PK(JoinColumn)의 값이 Null 이되는 자식을 고아객체라고 하는데, 고아객체는 연결점을 잃게된다.
	 *      orphanRemoval을 true로 설정하면 고아객체가 삭제된다.
	 *      (게시판은 양방향관계가 아니므로 아래 코드는 불필요)
	 */
	/*
	@OneToMany(
			mappedBy = "boardEntity"
			, cascade = CascadeType.REMOVE
			, fetch = FetchType.LAZY
			, orphanRemoval = true
			)
	@OrderBy("reply_num asc")
	private List<ReplyEntity> replyEntity = new ArrayList<>();
	*/
	
	//  DTO를 받아서 ----> Entity 반환 
	public static BoardEntity toEntity(BoardDTO boardDTO) {
		return BoardEntity.builder()
				.boardNum(boardDTO.getBoardNum())
				.boardWriter(boardDTO.getBoardWriter())
				.boardTitle(boardDTO.getBoardTitle())
				.boardContent(boardDTO.getBoardContent())
				.hitCount(boardDTO.getHitCount())
			
				.originalFileName(boardDTO.getOriginalFileName())
				.savedFileName(boardDTO.getSavedFileName())
//				.createDate(boardDTO.getCreateDate())
//				.updateDate(boardDTO.getUpdateDate())
				.build();
	}
}




