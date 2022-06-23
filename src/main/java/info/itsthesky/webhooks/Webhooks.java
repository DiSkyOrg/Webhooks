package info.itsthesky.webhooks;


import ch.njol.skript.SkriptAddon;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.DiSky;
import info.itsthesky.disky.api.modules.DiSkyModule;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public final class Webhooks extends DiSkyModule {

    private static Webhooks instance;
    private static WebhookManager manager;

    public Webhooks(String name, String author, String version, File moduleJar) {
        super(name, author, version, moduleJar);
    }

    @Override
    public void init(DiSky disky, SkriptAddon addon) {
        instance = this;
        manager = new WebhookManager();

        try {
            loadClasses("info.itsthesky.webhooks.elements");
            registerType(WebhookMessageBuilder.class, "webhookmessage", builder -> "webhook message builder");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Webhooks getInstance() {
        return instance;
    }

    public static WebhookManager getManager() {
        return manager;
    }
}
