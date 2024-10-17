package com.example.handling_form_submission;



import ch.qos.logback.classic.pattern.EnsureExceptionHandling;
import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.core.client.model.AiModelBaseData;
import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.context.WebContext;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sap.ai.sdk.core.Core.getClient;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_35_TURBO;

@org.springframework.stereotype.Controller
public class Controller {
    private static final ScenarioApi API = new ScenarioApi(getClient());


    @GetMapping("/message")
    public String messageForm(Model model) {
        List<String> FoundationModels = API.queryModels("foundation-models", "default").getResources().stream().map(mod -> mod.getModel()).collect(Collectors.toList());

        model.addAttribute("FoundationModels", FoundationModels);
        model.addAttribute("message", new Message());

        return "message";
    }


    @PostMapping("/message")
    public String messageSubmit(@ModelAttribute Message text, Model model) {


        OpenAiModel foundationmodel = new OpenAiModel(text.getFoundationmodel());
        OpenAiClient Client = OpenAiClient.forModel(foundationmodel);

        OpenAiChatCompletionOutput result =
                Client.withSystemPrompt("You are a helpful AI")
                        .chatCompletion(text.getContent());

        String resultMessage = result.getContent();

        text.setContent(resultMessage);

        //model.addAttribute("text", message);
        return "result";
    }

}