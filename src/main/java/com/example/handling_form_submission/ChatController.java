package com.example.handling_form_submission;

import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.sap.ai.sdk.core.Core.getClient;

@Controller
public class ChatController {
  private static final ScenarioApi API = new ScenarioApi(getClient());

  @GetMapping("/message")
  public String messageForm(Model model) {
    List<String> FoundationModels =
        API.queryModels("foundation-models", "default").getResources().stream()
            .map(mod -> mod.getModel())
            .collect(Collectors.toList());

    model.addAttribute("FoundationModels", FoundationModels);
    model.addAttribute("message", new Message());

    return "message";
  }

  @PostMapping("/message")
  public String messageSubmit(@ModelAttribute Message text) {

    OpenAiModel foundationmodel = new OpenAiModel(text.getFoundationmodel());
    OpenAiClient Client = OpenAiClient.forModel(foundationmodel);

    OpenAiChatCompletionOutput result =
        Client.withSystemPrompt("You are a helpful AI").chatCompletion(text.getContent());

    String resultMessage = result.getContent();

    text.setContent(resultMessage);

    return "result";
  }
}
