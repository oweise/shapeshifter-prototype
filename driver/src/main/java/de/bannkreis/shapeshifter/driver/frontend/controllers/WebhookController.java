package de.bannkreis.shapeshifter.driver.frontend.ollers;

import de.bannkreis.shapeshifter.driver.frontend.entities.Webhook;
import de.bannkreis.shapeshifter.driver.frontend.entities.JobStartResponse;
import de.bannkreis.shapeshifter.driver.jobengine.WebhookProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping(value="/webhooks",consumes = "application/json", produces="application/json")
public class WebhookController {

    private final WebhookProcessor webHookProcessor;

    public WebhookController(WebhookProcessor webhookProcessor) {
        this.webHookProcessor = webhookProcessor;
    }

    @PostMapping
    public JobStartResponse receiveWebhook(@RequestBody Webhook webHook) {
        List<UUID> jobRunIds = webHookProcessor.processWebhook(webHook);
        return new JobStartResponse().jobRunIds(jobRunIds);
    }

}
