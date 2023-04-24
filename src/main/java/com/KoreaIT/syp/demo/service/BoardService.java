package com.KoreaIT.syp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.syp.demo.repository.BoardRepository;
import com.KoreaIT.syp.demo.vo.Board;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int boardId) {
		return boardRepository.getBoardById(boardId);
	}
}