package com.study.Ex15Board.service;

import com.study.Ex15Board.domain.board.Board;
import com.study.Ex15Board.domain.board.BoardRepository;
import com.study.Ex15Board.dto.BoardResponseDto;

import com.study.Ex15Board.dto.BoardSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 트랜잭션 처리 - 단위 작업 처리시 중간에 오류 발생시 Rollback처리해준다.
    //             - 정상 처리되면 commit(물리적 파일에 기록)됨.
    // Rollback은 insert, update, delete에만 필요. Select는 필요없음.

    // 게시글 전체목록 읽기
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAll(){
        List<Board> list =boardRepository.findAll();

        // stream()메소드 사용 : List<Board> -> List<DTO>
        return list.stream().map(BoardResponseDto:: new).collect((Collectors.toList()));
    }

    // 게시글 저장하기
    @Transactional
    public Long save(final BoardSaveRequestDto dto){
        Board entity = boardRepository.save(dto.toEntity());
        return entity.getBoardIdx();
    }

    // 글쓰기 하고 나서 실제 게시글이 있는지 확인 용도
    @Transactional(readOnly = true)
    public boolean existsById(Long boardIdx){
        boolean isFound = boardRepository.existsById(boardIdx);
        return isFound;
    }

    // 게시글 DB인덱스로 게시글 정보 가져오기
    @Transactional(readOnly = true)
    public BoardResponseDto findById(Long boardIdx){
        Board entity = boardRepository.findById(boardIdx).
                orElseThrow(()-> new IllegalArgumentException("없는 글인덱스입니다."));

        // Optional클래스 함수 orElseThrow(람다식) : null이면 람다 실행함.

        return new BoardResponseDto(entity);
    }

    // 조회수 업데이트하기
    @Transactional
    public void updateHit(final Long boardIdx, final Integer hit){
        // BIGIN TRACTION
        Board entity = boardRepository.findById(boardIdx).
                orElseThrow(()-> new IllegalArgumentException("없는 글인덱스입니다."));

        // 엔티티 인스터스의 필드를 수정하면, 자동으로 save된다.
        // repo의 save함수를 별도로 사용하지 않아도 됨.
        entity.updateHit(hit);
        // COMMIT(ROLLBACK)이 자동 발생한다.
    }

    // 게시글 업데이트
    @Transactional
    public boolean update(final Long boardIdx, final BoardSaveRequestDto dto){
        Board entity = boardRepository.findById(boardIdx).
                orElseThrow(()-> new IllegalArgumentException("없는 글인덱스입니다."));

        entity.update(dto.getBoardName(), dto.getBoardTitle(), dto.getBoardContent(),dto.getBoardHit());

        return true;
    }

    // 게시글 지우기
    @Transactional
    public void delete(final Long boardIdx){
        Board entity = boardRepository.findById(boardIdx).
                orElseThrow(()-> new IllegalArgumentException("없는 글인덱스입니다."));

        boardRepository.delete(entity);
    }

}
