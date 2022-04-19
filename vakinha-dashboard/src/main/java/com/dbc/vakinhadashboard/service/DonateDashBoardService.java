package com.dbc.vakinhadashboard.service;

import com.dbc.vakinhadashboard.dto.DonateDashBoardDTO;
import com.dbc.vakinhadashboard.repository.DonateDashBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonateDashBoardService {

    private final DonateDashBoardRepository donateDashBoardRepository;

    public List<DonateDashBoardDTO> donatesDashBoard() throws Exception {
        return donateDashBoardRepository.donatesDashBoard();
    }

    @Scheduled(fixedDelay = 10000)  //10 sec
    public void autoListDashBoard() throws Exception {
        donateDashBoardRepository.donatesDashBoard().forEach(System.out::println);
    }

}
