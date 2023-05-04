package info.itsthesky.webhooks.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import club.minnced.discord.webhook.WebhookClient;
import info.itsthesky.disky.api.generator.Module;
import info.itsthesky.disky.api.skript.WaiterEffect;
import info.itsthesky.webhooks.Webhooks;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;

@Name("Register Webhook")
@Description({"Register a new webhook client through its Discord URL.",
"The URL contains both webhook's ID and token. Anyone who have it can therefore control your webhook.",
"Once a client is registered, feel free to use it specifying its name registered here."})
@Examples("register webhook name \"my_webhook\" with url \"XXXXXX\"")
@Since("1.0.0")
@Module("Webhooks")
public class RegisterWebhook extends WaiterEffect {

    public static void load() {
        Skript.registerEffect(RegisterWebhook.class,
                "register [new] webhook (named|with name) %string% with [the] url %string% [in [the] thread %-string%]");
    }

    private Expression<String> exprName;
    private Expression<String> exprUrl;
    private Expression<String> exprThread;

    @Override
    public boolean initEffect(Expression[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        exprName = (Expression<String>) expressions[0];
        exprUrl = (Expression<String>) expressions[1];
        exprThread = (Expression<String>) expressions[2];
        return true;
    }

    @Override
    public void runEffect(Event e) {
        final String name = parseSingle(exprName, e, null);
        final String url = parseSingle(exprUrl, e, null);
        final String thread = parseSingle(exprThread, e, null);
        if (anyNull(name, url)) {
            restart();
            return;
        }
        if (Webhooks.getManager().getClient(name) != null) {
            Skript.error("A webhook client with the name '"+name+"' is already registered!");
            restart();
            return;
        }
        try {
            new URL(url);
        } catch (MalformedURLException ex) {
            Skript.error("Malformed URL ('"+url+"'): " + ex.getMessage());
            restart();
            return;
        }

        final WebhookClient client = thread == null ? WebhookClient.withUrl(url) : WebhookClient.withUrl(url).onThread(Long.parseLong(thread));
        Webhooks.getManager().addClient(name, client);
        restart();
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "register new webhook with name " + exprName.toString(e, debug) + " with url " + exprUrl.toString(e, debug);
    }
}
