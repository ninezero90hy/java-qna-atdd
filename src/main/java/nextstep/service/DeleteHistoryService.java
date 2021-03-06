package nextstep.service;

import java.util.List;
import javax.annotation.Resource;
import nextstep.domain.DeleteHistory;
import nextstep.domain.DeleteHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("deleteHistoryService")
public class DeleteHistoryService {

  @Resource(name = "deleteHistoryRepository")
  private DeleteHistoryRepository deleteHistoryRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveAll(List<DeleteHistory> deleteHistories) {
    for (DeleteHistory deleteHistory : deleteHistories) {
      deleteHistoryRepository.save(deleteHistory);
    }
  }
}
