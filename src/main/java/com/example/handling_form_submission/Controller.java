package com.example.handling_form_submission;



import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/message")
    public String greetingForm(Model model) {
        model.addAttribute("message", new Message());
        return "message";
    }


    @PostMapping("/message")
    public String greetingSubmit(@ModelAttribute Message message, Model model) {

        OpenAiChatCompletionOutput result =
                OpenAiClient.forModel(GPT_35_TURBO)
                        .withSystemPrompt("You are a helpful AI")
                        .chatCompletion(message.getContent());

        String resultMessage = result.getContent();

        message.setContent(resultMessage);

        model.addAttribute("message", message);
        return "result";
    }

}