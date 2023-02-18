package CompWeb.Homepage.Service;

import CompWeb.Homepage.DTO.AddHistoryDTO;
import CompWeb.Homepage.Entity.History;
import CompWeb.Homepage.Repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistroyService {
    private final HistoryRepository historyRepository;

    public Long addHistory(AddHistoryDTO addHistoryDTO){
        History history = History.builder()
                .content(addHistoryDTO.getContent())
                .build();
        return historyRepository.save(history).getId();
    }

    public void deleteHistory(){
        historyRepository.deleteAll();
    }

    public List<AddHistoryDTO> getHistoryList(){
        List<History> histories = historyRepository.findAll();
        List<AddHistoryDTO> historyDTO = new ArrayList<>();
        for( History history : histories ){
            AddHistoryDTO addHistoryDTO = AddHistoryDTO.builder()
                    .id(history.getId())
                    .content(history.getContent())
                    .build();
            historyDTO.add(addHistoryDTO);
        }
        return historyDTO;
    }
}
