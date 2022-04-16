package com.dbc.vakinhadashboard.service;

import com.dbc.vakinhadashboard.dto.DonateDashBoardDTO;
import com.dbc.vakinhadashboard.repository.DonateDashBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonateDashBoardService {

    private final DonateDashBoardRepository donateDashBoardRepository;

    public List<DonateDashBoardDTO> donatesDashBoard() throws Exception {
        return donateDashBoardRepository.donatesDashBoard();
    }

}
