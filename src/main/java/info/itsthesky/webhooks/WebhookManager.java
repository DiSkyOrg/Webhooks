package info.itsthesky.webhooks;

import club.minnced.discord.webhook.WebhookClient;

import java.util.HashMap;
import java.util.Map;

public final class WebhookManager {

    private final Map<String, WebhookClient> clients = new HashMap<>();

    public void addClient(String name, WebhookClient client) {
        clients.put(name, client);
    }

    public WebhookClient getClient(String name) {
        return getClients().getOrDefault(name, null);
    }

    public Map<String, WebhookClient> getClients() {
        return clients;
    }
}
