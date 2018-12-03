package nextstep.web;

import javax.annotation.Resource;
import nextstep.CannotDeleteException;
import nextstep.domain.Question;
import nextstep.domain.User;
import nextstep.security.LoginUser;
import nextstep.service.QnaService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/questions")
public class QuestionController {

  private static final String QUESTION_LIST_PATE = "redirect:/questions";

  @Resource(name = "qnaService")
  private QnaService qnaService;

  @GetMapping
  public String list(final Model model,
      final Pageable pageable) {
    model.addAttribute("question", qnaService.findAll(pageable));
    return "/qna/show";
  }

  @SuppressWarnings("unused")
  @GetMapping("/form")
  public String updateForm(@LoginUser final User loginUser) {
    return "/qna/form";
  }

  @GetMapping("/{id}/form")
  public String updateForm(@LoginUser final User loginUser,
      final Model model,
      @PathVariable final long id) {
    model.addAttribute("question", qnaService.findByUserAndId(loginUser, id));
    return "/qna/updateForm";
  }

  @PutMapping("/{id}")
  public String update(@LoginUser final User loginUser,
      @PathVariable final long id,
      final Question question) {
    qnaService.update(loginUser, id, question);
    return QUESTION_LIST_PATE;
  }

  @PostMapping
  public String create(@LoginUser final User loginUser,
      final Question question) {
    qnaService.create(loginUser, question);
    return QUESTION_LIST_PATE;
  }

  @DeleteMapping("/{id}")
  public String delete(@LoginUser final User loginUser,
      @PathVariable final long id) throws CannotDeleteException {
    qnaService.delete(loginUser, id);
    return QUESTION_LIST_PATE;
  }

}
