package info.itsthesky.webhooks.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.*;
import ch.njol.util.Kleenean;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.generator.Module;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@Name("Make Message")
@Description("Make a new webhook message builder to change its avatar, name, text and embeds, all that though a section.")
@Examples({"make new webhook message:",
        "\tset webhook avatar of builder to \"avatar url\"",
        "\tset webhook name of builder to \"avatar url\"",
        "\tset webhook content of builder to \"Here's some text\""
})
@Module("Webhooks")
public class MakeMessage extends EffectSection {

    static {
        Skript.registerSection(
                MakeMessage.class,
                "make [a] [new] webhook message [builder]"
        );
    }

    private WebhookMessageBuilder builder;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult, @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        if (!hasSection())
            return false;
        if (sectionNode == null)
            return false;
        loadCode(sectionNode);
        this.builder = new WebhookMessageBuilder();
        return true;
    }

    public WebhookMessageBuilder getBuilder() {
        return builder;
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event e) {
        return walk(e, true);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "make new webhook message";
    }
}
